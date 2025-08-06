package com.eatz.infrastructure.storage;

import com.eatz.infrastructure.properties.MinioProperties;
import io.minio.*;
import io.minio.http.Method;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.util.Base64;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@EnableConfigurationProperties(MinioProperties.class)
public class MinIOService {

    private final MinioClient minioClient;
    private static String BUCKET_NAME;

    public MinIOService(MinioClient minioClient, MinioProperties minioProperties) {
        this.minioClient = minioClient;
        BUCKET_NAME = minioProperties.bucketName();
        createBucketIfNotExists();
    }

    private void createBucketIfNotExists() {
        try {
            boolean bucketExists = minioClient.bucketExists(
                    BucketExistsArgs.builder().bucket(BUCKET_NAME).build()
            );
            if (!bucketExists) {
                minioClient.makeBucket(
                        MakeBucketArgs.builder().bucket(BUCKET_NAME).build()
                );
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar bucket: " + e.getMessage(), e);
        }
    }

    public String uploadImageFromBase64(String base64Image, String contentType) {
        try {
            // Remove o prefixo data:image/...;base64, se existir
            String base64Data = base64Image;
            if (base64Image.contains(",")) {
                base64Data = base64Image.split(",")[1];
            }

            // Decodifica o Base64
            byte[] imageBytes = Base64.getDecoder().decode(base64Data);

            // Determina a extensão baseada no content type
            String extension = getExtensionFromContentType(contentType);
            String fileName = generateFileName(extension);

            // Faz o upload
            minioClient.putObject(
                    PutObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(fileName)
                            .stream(new ByteArrayInputStream(imageBytes), imageBytes.length, -1)
                            .contentType(contentType != null ? contentType : "image/jpeg")
                            .build()
            );

            return fileName;
        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload da imagem: " + e.getMessage(), e);
        }
    }

    public void removeImage(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(BUCKET_NAME)
                            .object(fileName)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao remover imagem: " + e.getMessage(), e);
        }
    }

    public String generateSignedUrl(String fileName) {
        try {
            return minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(BUCKET_NAME)
                            .object(fileName)
                            .expiry(7, TimeUnit.DAYS)
                            .build()
            );
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar URL assinada: " + e.getMessage(), e);
        }
    }

    private String generateFileName(String extension) {
        return UUID.randomUUID().toString() + extension;
    }

    private String getExtensionFromContentType(String contentType) {
        if (contentType == null) return ".jpg";

        return switch (contentType.toLowerCase()) {
            case "image/jpeg", "image/jpg" -> ".jpg";
            case "image/png" -> ".png";
            case "image/gif" -> ".gif";
            case "image/webp" -> ".webp";
            default -> ".jpg";
        };
    }
}
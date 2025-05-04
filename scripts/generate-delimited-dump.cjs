const fs = require("fs");
const path = require("path");

const baseDir = path.join(__dirname, "../src/main/java"); // Caminho para sua pasta src/
const outputPath = path.join(__dirname, "backend-dump.txt");

function walk(dir, fileList = []) {
  const files = fs.readdirSync(dir);
  for (const file of files) {
    const filepath = path.join(dir, file);
    const stat = fs.statSync(filepath);
    if (stat.isDirectory()) {
      walk(filepath, fileList);
    } else if (file.endsWith(".java")) {
      fileList.push(filepath);
    }
  }
  return fileList;
}

function generateDump() {
  const files = walk(baseDir);
  const lines = [];

  for (const file of files) {
    const relativePath = path.relative(path.join(__dirname, ".."), file).replace(/\\/g, "/");
    const content = fs.readFileSync(file, "utf-8");
    lines.push(`--- FILE: ${relativePath} ---\n${content}\n`);
  }

  fs.writeFileSync(outputPath, lines.join("\n"), "utf-8");
  console.log(`✅ Dump gerado com sucesso em: ${outputPath}`);
}

generateDump();

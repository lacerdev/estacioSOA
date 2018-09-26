const homedir = require('os').homedir();
const separator = require('path').sep;

const filePath = homedir + separator + 'repositorioArquivos';
const sourceFileName = 'teste.txt';
const sourceFileFullName = filePath + separator + sourceFileName;
const destinationFile = "estatisticas.json"
const destinationFileFullName = filePath + separator + destinationFile;

module.exports = {
    filePath: filePath,
    sourceFileName: sourceFileName,
    sourceFileFullName: sourceFileFullName,
    destinationFile: destinationFile,
    destinationFileFullName: destinationFileFullName
}
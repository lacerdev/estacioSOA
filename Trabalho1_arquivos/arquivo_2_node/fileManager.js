const fs = require('fs');
const lineReader = require('readline')
const chardet = require('chardet');

function getFileEncoding(filePath) {
    let encoding = chardet.detectFileSync(filePath);
    if('ISO-8859-1' == encoding.toUpperCase()) {
        encoding = 'latin1';
    }
    console.log('Codificação do arquivo "%s" é "%s"', filePath, encoding);
    return encoding;
}

//Carrega o arquivo inteiro em memória
function getWholeFileInLineArray(filePath) {
    let data = fs.readFileSync(filePath, getFileEncoding(filePath))
    return data.split('\r\n').slice(1)
}

// Para arquivos muito grandes o correto seria ir lendo linha a linha
// const lr = lineReader.createInterface({
//     input: fs.createReadStream(sourceFileFullName, {encoding: getFileEncoding(sourceFileFullName)})
// });
// let lineCount = 0;  
// lr.on('line', function (line) {
//     lineCount++;
//     console.log('Line from file:', line);
// });

module.exports = {
    getWholeFileInLineArray: getWholeFileInLineArray,
    writeToFile: fs.writeFileSync,
    fileExists: fs.existsSync
}

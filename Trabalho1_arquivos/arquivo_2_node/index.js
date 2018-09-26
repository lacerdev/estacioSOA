const props = require('./constants');
const fm = require('./fileManager');

const lineArrayToPersonConverter = require('./functions').lineArrayToPersonConverter;
const personMale = require('./functions').personMale;
const personFemale = require('./functions').personFemale;
const sumAge = require('./functions').sumAge;
const ageAvg = require('./functions').ageAvg;


if (!fm.fileExists(props.sourceFileFullName)) {
    console.log('O arquivo "%s" de origem não existe.', props.sourceFileFullName);
    process.exit(1);
}
const lineArray = fm.getWholeFileInLineArray(props.sourceFileFullName);
console.info('Array de linhas do arquivo', lineArray);

const personArray = lineArray.map(lineArrayToPersonConverter);
console.info('Array de pessoas', personArray);

const maleArray = personArray.filter(personMale);
console.info('Array de Homens', maleArray);

const femaleArray = personArray.filter(personFemale);
console.info('Array de Mulheres', femaleArray);

const estat = {
    avgMale: ageAvg(maleArray),
    avgFemale: ageAvg(femaleArray)
};

fm.writeToFile(props.destinationFileFullName, JSON.stringify(estat));
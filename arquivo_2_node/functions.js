const lineArrayToPersonConverter = function (line) {
    const Person = require('./person');
    const columns = line.split(';');
    return new Person(columns[0], columns[1], columns[2]);
}

const personMale = function (person) {
    if ('M' == person.gender.toUpperCase()) {
        return person;
    }
}

const personFemale = function (person) {
    if ('F' == person.gender.toUpperCase()) {
        return person;
    }
}

const sumAge = function (a, b) {
    return new Number(a) + new Number(b);
}

function ageAvg(personArray) {
    return personArray.map(function (value) {
        return value.age;
    }).reduce(sumAge) / personArray.length;
}

module.exports = {
    lineArrayToPersonConverter: lineArrayToPersonConverter,
    personMale: personMale,
    personFemale: personFemale,
    sumAge: sumAge,
    ageAvg: ageAvg
}
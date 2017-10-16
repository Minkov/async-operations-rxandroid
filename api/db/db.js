const mongodb = require('mongodb');

const init = (connectionString) => {
    return mongodb.connect(connectionString);
};

module.exports = { init };

const bodyParser = require('body-parser');

const setup = (app) => {
    app.use(bodyParser.json());
};

module.exports = { setup };

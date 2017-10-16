const express = require('express');
const Book = require('../models/book');

const init = (data) => {
    const app = express();
    require('./config').setup(app);

    const booksRouter = require('./routers/books').init(data.get(Book));

    const apiRouter = new express.Router();
    apiRouter.use('/books', booksRouter);

    app.use('/api', apiRouter);

    return {
        run(port) {
            return new Promise((resolve) => {
                app.listen(port, () => {
                    resolve();
                });
            });
        },
    };
};

module.exports = { init };

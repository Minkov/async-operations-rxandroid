const { Router } = require('express');

const init = (books) => {
    const router = new Router();
    router
        .get('/', async (req, res) => {
            const bookItems = await books.getAll();
            return res.send(bookItems);
        })
        .get('/:id', async (req, res) => {
            const id = req.params.id;
            const book = await books.getById(id);
            return res.send(book);
        })
        .post('/', async (req, res) => {
            const book = req.body;
            const savedBook = await books.create(book);
            return res.send(savedBook);
        });
    return router;
};

module.exports = {
    init,
};

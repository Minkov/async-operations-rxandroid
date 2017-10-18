const fs = require('fs');

const Koa = require('koa');
const Router = require('koa-router');
const bodyParser = require('koa-bodyparser');
const morgan = require('koa-morgan');

const Book = require('../models/book.model')
const { wait } = require('../utils/wait');

const init = (dataProvider) => {
    const app = new Koa();

    const booksRouter = new Router({
        prefix: "/books"
    });

    const booksData = dataProvider.getFor(Book);

    booksRouter
        .get('/', async (ctx, next) => {
            await wait(2);
            const books = await booksData.getAll();

            ctx.body = books;
            ctx.status = 200;

            await next();
        })
        .get('/:id', async(ctx, next) => {
            await wait(1);
            const book = await booksData.getById(ctx.params.id);
            ctx.body = book;
            ctx.status = 200;

            await next();
        })
        .post('/', async (ctx, next) => {
            console.log(ctx.request.body);
            const book = await booksData.create(ctx.request.body);
            ctx.status = 201;
            ctx.body = book;
            await next();
        });

    const router = new Router({
        prefix: "/api",
    });

    router.use(booksRouter.routes())

    const accessLogStream = fs.createWriteStream(__dirname + '/access.log',
        { flags: 'a' });

    app
        .use(morgan('combined', { stream: accessLogStream }))
        .use(bodyParser())
        .use(router.routes())
        .use(router.allowedMethods());

    return {
        start(port) {
            return new Promise((resolve) => {
                app.listen(port, resolve);
            });
        },
    };
};

module.exports = {
    init,
};

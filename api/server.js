const config = require('./config');

const runServer = async () => {
    const db = await require('./db').init(config.connectionString);
    const data = await require('./data').init(db);
    const app = await require('./app').init(data);

    await app.run(config.port);
    console.log(`App running at ${config.port}`);
};

runServer();

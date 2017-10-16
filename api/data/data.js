const { ObjectID } = require('mongodb');
class Data {
    constructor(db, Klass) {
        this.db = db;
        this.Klass = Klass;
        this.collection = db.collection(this.getCollectioName(Klass));
    }

    getCollectioName(Klass) {
        return Klass.name.toLowerCase() + 's';
    }

    async create(obj) {
        await this.collection.insert(obj);
        return obj;
    }

    async getAll() {
        const items = await this.collection.find().toArray();
        return items;
    }

    async getById(id) {
        const item = await this.collection.findOne({
            _id: new ObjectID(id),
        });
        return item;
    }
}

class DataProvider {
    constructor(db) {
        this.db = db;
        this.datas = {};
    }

    get(Klass) {
        if (typeof this.datas[Klass.name] === 'undefined') {
            this.datas[Klass.name] = new Data(this.db, Klass);
        }

        return this.datas[Klass.name];
    }
}

const init = (db) => {
    return new DataProvider(db);
};

module.exports = { init };

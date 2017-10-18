class Book {
    constructor(title, description) {
        this.title = title;
        this.description = description;
    }

    static toViewModel(model) {
        return {
            id: model._id,
            title: model.title,
            description: model.description,
        }
    }
}

module.exports = Book;

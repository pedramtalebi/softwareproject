//Bus in database

var mongoose = require('mongoose');

var busSchema = new bus.Schema({
    id: {type: String, unique: true},
    driver: {type: String, unique: true},

    created_at: Date,
    updated_at: Date
});

var bus = mongoose.model('bus', busSchema);

module.exports = bus;
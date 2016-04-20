var mongoose = require('mongoose');

var rerouteSchema = new mongoose.Schema({
    id: {type: String, unique: true},
    mapPath: {type: String},
    
    created_at: Date,
    updated_at: Date
});

var Reroute = mongoose.model('reroute', rerouteSchema);

module.exports = Reroute;
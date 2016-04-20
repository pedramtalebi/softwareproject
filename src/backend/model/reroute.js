var mongoose = require('mongoose');
var uuid = require('node-uuid');

var rerouteSchema = new mongoose.Schema({
  id: {type: String, unique: true},
  mapPath: {type: String},

  created_at: Date,
  updated_at: Date
});

rerouteSchema.pre('save', function(next) {
  var currentDate = new Date();
  this.updated_at = currentDate;
  
  if (!this.created_at) {
    this.created_at = currentDate;
  }
  
  if (!this.id) {
    this.id = uuid.v4();
  }
  
  next();
});

var Reroute = mongoose.model('reroute', rerouteSchema);

module.exports = Reroute;
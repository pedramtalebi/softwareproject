var mongoose = require('mongoose');
var uuid = require('node-uuid');
require('mongoose-double')(mongoose);

var rerouteSchema = new mongoose.Schema({
  id: {type: String, unique: true},
  coordinates: [
    {
        lat: mongoose.Schema.Types.Double,
        long: mongoose.Schema.Types.Double
    }
  ],
  affectedLines : [String],

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
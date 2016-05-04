//Line in database, containing a path to a .jpg of the map and a line number.

var mongoose = require('mongoose');
var uuid = require('node-uuid');

var lineSchema = new mongoose.Schema({
    coordinates: [ {
      lat: Number,
      long: Number
    }],
    nr: {type: String, unique: true},
    buses: [Number],

    created_at: Date,
    updated_at: Date
});

//Automatically generates date and id
lineSchema.pre('save', function (next) {
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

var Line = mongoose.model('line', lineSchema);

module.exports = Line;
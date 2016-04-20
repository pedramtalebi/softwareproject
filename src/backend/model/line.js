//Line in database, containing a path to a .jpg of the map and a line number.

var mongoose = require('mongoose');

var lineSchema = new line.Schema({
    map: {type: String},
    nr: {type: String, unique: true},

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

module.exports = line;
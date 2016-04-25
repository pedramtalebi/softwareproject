//Bus in database

var mongoose = require('mongoose');

var busSchema = new mongoose.Schema({
  id: {type: String, unique: true},
  driver: {type: String, unique: true},

  created_at: Date,
  updated_at: Date
});

busSchema.pre('save', function (next) {
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

var Bus = mongoose.model('bus', busSchema);

module.exports = Bus;
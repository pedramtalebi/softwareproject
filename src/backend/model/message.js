//Message in database

var mongoose = require('mongoose');

var messageSchema = new mongoose.Schema({
  id: {type: String, unique: true},
  content: {type: String},

  created_at: Date,
  updated_at: Date
});

messageSchema.pre('save', function (next) {
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

var Message = mongoose.model('message', messageSchema);

module.exports = message;
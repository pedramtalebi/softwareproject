import express from 'express';
import mongoose from 'mongoose';
import morgan from 'morgan';

import rerouteApi from './routes/reroute-api';
import lineApi from './routes/line-api';
import busApi from './routes/bus-api';

var path = require('path');

mongoose.connect('mongodb://localhost/database');
//mongoose.connect('mongodb://mongo/softwareproject');

var app = express();
app.use('/', express.static(__dirname + '/public'));

// log all requests to the console 
app.use(morgan('dev'));

app.use(rerouteApi);
app.use(lineApi);
app.use(busApi);

app.get('/', function(req, res) {
	res.sendFile(path.join(__dirname + '/views/index.html'));
});

app.listen(3000, () => {
  console.log('Listening :3000');
});
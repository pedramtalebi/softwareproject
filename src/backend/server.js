import express from 'express';
import mongoose from 'mongoose';
import morgan from 'morgan';
import path from 'path';

import socket from './websocket';

import rerouteApi from './routes/reroute-api';
import lineApi from './routes/line-api';
import busApi from './routes/bus-api';

//mongoose.connect('mongodb://localhost/database');
mongoose.connect('mongodb://mongo/softwareproject');

var app = express();
var server = socket(app);

app.use('/', express.static(__dirname));

// log all requests to the console 
app.use(morgan('dev'));

app.use(rerouteApi);
app.use(lineApi);
app.use(busApi);

app.get('/', (req, res) => {
	res.sendFile(path.join(__dirname + '/views/index.html'));
});

server.listen(3000, () => {
  console.log('Listening :3000');
});

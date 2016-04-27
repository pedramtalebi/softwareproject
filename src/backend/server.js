import express from 'express';
import mongoose from 'mongoose';
import morgan from 'morgan';
import path from 'path';

import rerouteApi from './routes/reroute-api';
import lineApi from './routes/line-api';
import busApi from './routes/bus-api';

//mongoose.connect('mongodb://localhost/database');
mongoose.connect('mongodb://mongo/softwareproject');

var app = express();
app.use('/', express.static(__dirname + '/public'));

// log all requests to the console 
app.use(morgan('dev'));

app.use(rerouteApi);
app.use(lineApi);
app.use(busApi);

app.get('/', (req, res) => {
	res.sendFile(path.join(__dirname + '/views/index.html'));
});

import http from 'http';
import socketio from 'socket.io';

var server = http.Server(app);
var io = socketio(server);

var counter = 0;

io.on('connection', (socket) => {
  console.log('User connected!');
  
  socket.on('update request', (foo) => {
    counter = counter + 1;
    console.log('counter='+counter);
    socket.emit('updated counter', counter);
  });

  socket.on('disconnect', () => {
    console.log('user disconnected');
  });
});

server.listen(3000, () => {
  console.log('Listening :3000');
});

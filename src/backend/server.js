import express from 'express';
import mongoose from 'mongoose';
import morgan from 'morgan';
import path from 'path';

import WebSocket from './websocket';
import Reroute from './model/reroute';

var app = express();
WebSocket.init(app);

import rerouteApi from './routes/reroute-api';
import lineApi from './routes/line-api';
import busApi from './routes/bus-api';

mongoose.connect('mongodb://localhost/database');
//mongoose.connect('mongodb://mongo/softwareproject');

app.use('/', express.static(__dirname));

// log all requests to the console 
app.use(morgan('dev'));

app.use(rerouteApi);
app.use(lineApi);
app.use(busApi);

app.get('/', (req, res) => {
	res.sendFile(path.join(__dirname + '/views/index.html'));
});

WebSocket.listen(3000, () => {
  console.log('Listening :3000');

  // Send all reroutes upon connection
  WebSocket.on('connection', (socket) => {
    Reroute.find({}, (err, reroutes) => {
      if (err) {
        console.log(err);
        throw err;
      }

      console.log('New connection!');
      _.forEach(reroutes, (reroute) => {
        socket.emit('new reroute', reroute);
      });
    });
  });
});

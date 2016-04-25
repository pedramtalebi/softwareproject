import express from 'express'
import mongoose from 'mongoose';

import rerouteApi from './routes/reroute-api';
import lineApi from './routes/line-api';
import busApi from './routes/bus-api';


mongoose.connect('mongodb://localhost/database');
//mongoose.connect('mongodb://mongo/softwareproject');

var app = express();
app.use('/', express.static(__dirname + '/public'));

app.use(rerouteApi);
app.use(lineApi);
app.use(busApi);

app.listen(3000, () => {
  console.log('Listening :3000');
});



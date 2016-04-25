import express from 'express'
import mongoose from 'mongoose';

import rerouteApi from './routes/reroute-api';

mongoose.connect('mongodb://localhost/database');
//mongoose.connect('mongodb://mongo/softwareproject');

var app = express();
app.use('/', express.static(__dirname + '/public'));

app.use(rerouteApi);

app.listen(3000, () => {
  console.log('Listening :3000');
});



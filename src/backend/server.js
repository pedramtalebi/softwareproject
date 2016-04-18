import express from 'express'
import _ from 'lodash';

var app = express();
app.use('/', express.static(__dirname + '/public'));

app.get('/hello', (req, res) => {
  res.send('HELLO');
});

app.listen(3000, () => {
  console.log('Listening :3000');
});

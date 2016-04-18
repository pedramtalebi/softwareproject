import express from 'express'
import _ from 'lodash';

var app = express();
app.use('/', express.static(__dirname + '/public'));

app.get('/hello/:name', (req, res)=> {
  var name = req.params.name;
  res.send('Fuck you ' + name);
});

app.listen(3000, () => {
  console.log('Listening :3000');
});



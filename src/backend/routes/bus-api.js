//External packages
import _ from 'lodash';
import {Router} from 'express';
import bodyParser from 'body-parser';

//Internal
import Bus from '../model/bus';
const jsonParser = bodyParser.json();

const router = Router();
export default router;

//GET/buses - Fetches all active buses
router.get('/v1/buses', (req, res) => {
  Bus.find({}, (err, buses) => {
    if (err) {
      res.send(err);
      throw err;
    }

    res.send(buses);
  });
});

//POST/buses - Adds a new bus to a line. Should be called from driver app
router.post('/v1/buses', jsonParser, (req, res) => {
  var data = req.body;
  var newBus = Bus(data);

  Bus.save((err) => {
    if (err) {
      res.send(err);
      throw err;
    }

    console.log('Created a new bus');
    res.send(newReroute);

    WebSocket.emit('new bus', newBus);
  })

});



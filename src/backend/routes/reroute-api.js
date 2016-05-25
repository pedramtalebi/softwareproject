//External packages
import _ from 'lodash';
import {Router} from 'express';
import bodyParser from 'body-parser';
import mongoose from 'mongoose';
import _ from 'lodash';

var ObjectId = mongoose.Types.ObjectId;

//Internal
import Reroute from '../model/reroute';
const jsonParser = bodyParser.json();

import WebSocket from '../websocket';

const router = Router();
export default router;

//Returns list of reroutes
router.get('/v1/reroute', (req, res) => {
  Reroute.find({}, (err, reroutes) => {
    if (err) {
      res.send(err);
      throw err;
    }

    res.send(reroutes);
  });
});

//Returns a specific reroute
router.get('/v1/reroute/:id', (req, res) => {
  var id = req.params.id;

  Reroute.find({id: id}, (err, reroute) => {
    if (err) {
      res.send(err);
      throw err;
    }

    res.send(reroute);
  });
});

//Adds a new reroute to the database
router.post('/v1/reroute', jsonParser, (req, res) => {
  var data = req.body;
  var newReroute = Reroute(data);

  newReroute.save((err) => {
    if (err) {
      res.send(err);
      throw err;
    }

    console.log('Created a new reroute');
    res.send(newReroute);

    WebSocket.emit('new reroute', newReroute);
  })

});

//DELETE /reroute/:id - Removes a specific reroute from the database
router.delete('/v1/reroute/:id', jsonParser, (req, res) => {
    var id = req.params.id;

    Reroute.findByIdAndRemove(id, (err, reroute) => {
        if (err) {
            res.send(err);
            return;
        }

        res.send(reroute);

        WebSocket.emit('delete reroute', id);
    });
});

//PUT /reroutes/:id - Modifies an already existing route in the database
router.put('/v1/reroute/:id', jsonParser, (req, res) => {

  var id = req.params.id;
  var reRoute = req.body;

  Reroute.findByIdAndUpdate(ObjectId(id), {
    $set: {
      coordinates: reRoute.coordinates,
      affectedLines: reRoute.affectedLines
    }
  }, (err, reroute) => {
    if (err) {
      res.send(err);
      return;
    }

    res.send(reroute);

    WebSocket.emit('changed reroute', reRoute);
  });
});


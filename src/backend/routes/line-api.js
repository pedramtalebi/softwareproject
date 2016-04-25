//External packages
import _ from 'lodash';
import {Router} from 'express';
import bodyParser from 'body-parser';

//Internal
import Line from '../model/line';

const router = Router();
export default router;

//GET /lines -Fetch all stored lines
router.get('/v1/line', (req, res) => {
  Line.find({}, (err, lines) => {
    if (err) {
      res.send(err);
      throw err;
    }

    res.send(lines);
  });
});



//POST /lines -Post a new line -> FORBIDDEN! Should  be handled

//GET /lines:id -Gets a line with a specific ID
router.get('/v1/line/:id', (req, res) => {
  var id = req.params.id;

  Line.find({id: id}, (err, line) => {
    if (err) {
      res.send(err);
      throw err;
    }

    res.send(line);
  });
});

//GET /lines:id/buses -Fetches the currently active buses of a specific line
router.get('/v1/line/:id/buses', (req, res) => {
  var id = req.params.id;

  Line.find({id: id}, (err, [Number]) => {
    if (err) {
      res.send(err);
      throw err;
    }

    res.send([Number]);
  });
});


//External packages
import _ from 'lodash';
import {Router} from 'express';
import bodyParser from 'body-parser';

//Internal
import Bus from '../model/bus';

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
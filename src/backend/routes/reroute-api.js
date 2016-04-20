import _ from 'lodash';
import {Router} from 'express';

import Reroute from '../model/reroute';

const router = Router();
export default router;

router.get('/v1/reroute', (req, res) => {
  Reroute.find({}, (err, reroutes) => {
    if (err) {
      res.send(err);
      throw err;
    }
    
    res.send(reroutes);
  });
});

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
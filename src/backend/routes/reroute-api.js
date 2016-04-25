//External packages
import _ from 'lodash';
import {Router} from 'express';
import bodyParser from 'body-parser';

//Internal
import Reroute from '../model/reroute';


const jsonParser = bodyParser.json();

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


});


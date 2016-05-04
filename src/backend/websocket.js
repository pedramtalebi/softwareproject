 import http from 'http';
 import socketio from 'socket.io';
 
 export default function(app, port, fn) {
   var server = http.Server(app);
   var io = socketio(server);
   
   global.io = io;

   io.on('connection', (socket) => {
     console.log(socket.id + " connected");
   });

   server.listen(port, fn);
 }

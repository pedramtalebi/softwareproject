import http from 'http';
import socketio from 'socket.io';

class WebSocket {
  init(app) {
    this.server = http.Server(app);
    this.io = socketio(this.server);

    global.io = this.io;

    this.io.on('connection', (socket) => {
      console.log(socket.id + " connected");
    });
  }

  listen(port, fn) {
    this.server.listen(port, fn);
  }

  emit(event, data) {
    this.io.emit(event, data);
  }
}

var socket = new WebSocket();

export default socket;


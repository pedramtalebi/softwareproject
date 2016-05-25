import http from 'http';
import socketio from 'socket.io';

class WebSocket {
  init(app) {
    this.server = http.Server(app);
    this.io = socketio(this.server);

    global.io = this.io;
  }

  listen(port, fn) {
    this.server.listen(port, fn);
  }

  emit(event, data) {
    this.io.emit(event, data);
  }
  
  on(event, fn) {
    this.io.on(event, fn);
  }
}

var socket = new WebSocket();

export default socket;


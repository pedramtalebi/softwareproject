import http from 'http';
import socketio from 'socket.io';

class WebSocket {
  constructor(app) {
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

  static emit(event, data) {
    this.io.emit(event, data);
  }
}

export default function (app) {
  return new WebSocket(app);
}


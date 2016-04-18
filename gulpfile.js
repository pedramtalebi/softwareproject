var gulp = require('gulp');
var gulpSequence = require('gulp-sequence');
var babel = require('gulp-babel');
var child = require('child_process');
var util = require('gulp-util');

var path = {
  SERVER_JS: ['src/backend/**/*.js'],

  DEST: 'dist/'
};

var server = null;

gulp.task('watch', function() {
  gulp.watch(path.SERVER_JS, ['server:build', 'server:spawn']);
});

gulp.task('server:build', function() {
  gulp.src(path.SERVER_JS)
    .pipe(babel({
      presets: ['es2015']
    }))
    .pipe(gulp.dest(path.DEST));
});

gulp.task('server:spawn', function() {
  if (server) {
    server.kill();
  }

  server = child.spawn('node', ['dist/server.js']);
  server.on('close', function(code) {
      if (code === 8) {
        util.log('Error detected, waiting for changes...');
      }
  });

  server.stdout.on('data', function(data) {
      util.log(util.colors.green(String(data).trim()));
  });

  server.stderr.on('data', function(data) {
      util.log(util.colors.red(String(data).trim()));
  });
});

gulp.task('server', gulpSequence(['server:build', 'server:spawn', 'watch']));

process.on('exit', function() {
    if (server) {
      server.kill();
    }
});

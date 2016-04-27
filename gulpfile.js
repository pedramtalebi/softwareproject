var gulp = require('gulp');
var gulpSequence = require('gulp-sequence');
var babel = require('gulp-babel');
var child = require('child_process');
var util = require('gulp-util');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var jshint = require('gulp-jshint');
// var clean = require('gulp-clean');

var path = {
  SERVER_JS: ['src/backend/**/*.js'],
  FRONT_JS: ['src/frontend/app/*.js', 'src/frontend/app/**/*.js'],
  VIEWS: ['src/frontend/app/views/*.html', 'src/frontend/app/views/partials/*.html'],
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

// task to lint, minify, and concat frontend files
gulp.task('watch', function() {
  return gulp.src(path.FRONT_JS)
    .pipe(jshint())
    .pipe(jshint.reporter('default'))
    .pipe(concat('all.js'))
    .pipe(uglify())
    .pipe(gulp.dest(path.DEST));
});

gulp.task('watch', function(){
  // the base option sets the relative root for the set of files,
  // preserving the folder structure
  gulp.src(path.VIEWS, {base: 'src/frontend/app'})
  .pipe(gulp.dest(path.DEST));
});

gulp.task('server:spawn', function() {
  if (server) {
    server.kill();
  };

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

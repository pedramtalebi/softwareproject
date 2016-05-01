var gulp = require('gulp');
var gulpSequence = require('gulp-sequence');
var babel = require('gulp-babel');
var child = require('child_process');
var util = require('gulp-util');
var concat = require('gulp-concat');
var uglify = require('gulp-uglify');
var jshint = require('gulp-jshint');
var ngAnnotate = require('gulp-ng-annotate');

var path = {
  SERVER_JS: ['src/backend/**/*.js'],
  FRONT_JS: ['src/frontend/app/app.js', 'src/frontend/app/**/*.js'],
  VIEWS: ['src/frontend/app/views/*.html', 'src/frontend/app/views/partials/*.html'],
  ANGULAR: ['src/frontend/assets/libs/angular/*.*', 'src/frontend/assets/libs/angular-route/*.*', 'src/frontend/assets/libs/angular-animate/*.*', 'src/frontend/assets/libs/angular-strap/dist/*.*', 'src/frontend/assets/libs/angular-strap/dist/modules/*.*', 'src/frontend/assets/libs/bootstrap-additions/dist/*.*','src/frontend/assets/libs/bootstrap-additions/dist/modules/*.*','src/frontend/assets/libs/angular-motion/dist/*.*','src/frontend/assets/libs/angular-sanitize/*.*'],
  LIBS: ['src/frontend/assets/libs/bootstrap/dist/**/*.{ttf,woff,eof,svg,min.js,min.css,min.js.map,min.css.map}', 'src/frontend/assets/libs/jquery/dist/*.min.js','src/frontend/assets/css/*.css' ],
  DEST: 'dist/'
};

var server = null;

gulp.task('watch', function() {
  gulp.watch(path.SERVER_JS, ['server:build', 'server:spawn']);
  gulp.watch(path.FRONT_JS, ['frontend:build', 'frontend:copy']);
  gulp.watch(path.VIEWS, ['frontend:build', 'frontend:copy']);
  gulp.watch(path.LIBS, ['frontend:build', 'frontend:copy']);
});

gulp.task('server:build', function() {
  gulp.src(path.SERVER_JS)
    .pipe(babel({
      presets: ['es2015']
    }))
    .pipe(gulp.dest(path.DEST));
});

// task to lint, minify, and concat frontend files

gulp.task('frontend:build', function() {
  return gulp.src(path.FRONT_JS)
    .pipe(jshint())
    .pipe(jshint.reporter('default'))
    .pipe(ngAnnotate())
    .pipe(concat('all.js'))
    .pipe(uglify())
    .pipe(gulp.dest(path.DEST));
});


gulp.task('frontend:copy', function(){
  // the base option sets the relative root for the set of files,
  // preserving the folder structure
  gulp.src(path.VIEWS, {base: 'src/frontend/app'})
  .pipe(gulp.dest(path.DEST));
  gulp.src(path.ANGULAR, {base:'src/frontend/assets'})
   .pipe(gulp.dest(path.DEST));
  gulp.src(path.LIBS, {base:'src/frontend/assets'})
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

gulp.task('server', gulpSequence([
  'frontend:build',
  'frontend:copy',
  'server:build', 
  'server:spawn', 
  'watch'
  ]));

process.on('exit', function() {
    if (server) {
      server.kill();
    }
});

/* jslint node: true */
'use strict';

var gulp = require('gulp'),
    bowerfiles = require('gulp-bower-files'),
    bump = require('gulp-bump'),
    clean = require('gulp-clean'),
    compass = require('gulp-compass'),
    crypto = require('crypto'),
    es = require('event-stream'),
    filter = require('gulp-filter'),
    googlecdn = require('gulp-google-cdn'),
    gulpif = require('gulp-if'),
    htmlmin = require('gulp-htmlmin'),
    jshint = require('gulp-jshint'),
    livereload = require('gulp-livereload'),
    minifycss = require('gulp-minify-css'),
    path = require('path'),
    rename = require('gulp-rename'),
    replace = require('gulp-replace'),
    revreplace = require('gulp-rev-replace'),
    uglify = require('gulp-uglify'),
    useref = require('gulp-useref');

var revision = function() {
  return es.map(function(file, callback) {
    var contents = file.contents.toString();
    var hash = crypto.createHash('md5').update(contents, 'utf8').digest('hex');
		var filename = hash.slice(0, 8) + '.' + path.basename(file.path);
		file.revOrigPath = file.path;
		file.revOrigBase = file.base;
		file.path = path.join(path.dirname(file.path), filename);
    callback(null, file);
  });
};

gulp.task('bump', function () {
  return gulp.src(['./package.json', './bower.json'])
    .pipe(bump())
    .pipe(gulp.dest('.'));
});

gulp.task('clean', function() {
  return gulp.src(['dist/**', 'app/styles/cudu.css'], { read: false })
    .pipe(clean());
});

gulp.task('compass', function() {
  return gulp.src('app/styles/cudu.scss')
    .pipe(compass({
      project: path.join(__dirname, 'app'),
      css: 'styles', sass: 'styles', image: 'images',
      style: 'expanded' 
    }))
    .pipe(minifycss())
    .pipe(gulp.dest('app/styles'));
});

gulp.task('bower-files', function() {
  return bowerfiles().pipe(gulp.dest('dist/lib'));
});

gulp.task('images', function() {
  return gulp.src('app/images/**/*').pipe(gulp.dest('dist/images'));
});

gulp.task('preflight', ['compass', 'bower-files', 'images'], function() {
  var stylesFilter  = filter('**/*.css');
  var scriptsFilter = filter('**/*.js');  
  return gulp.src('app/**/*.html')
    .pipe(useref.assets())
    .pipe(scriptsFilter)
      .pipe(revision())
      .pipe(uglify())
      .pipe(rename({ suffix: '.min' }))
    .pipe(scriptsFilter.restore())
    .pipe(stylesFilter)
      .pipe(revision())
      .pipe(rename({ suffix: '.min' }))
    .pipe(stylesFilter.restore())
    .pipe(useref.restore())
    .pipe(useref())
    .pipe(revreplace())
    .pipe(gulp.dest('dist'));
});

gulp.task('default', ['preflight'], function() {
  return gulp.src('dist/**/*.html')
    .pipe(gulpif(!opts.readable, googlecdn(require('./bower.json'), { componentsPath: 'lib' })))
    .pipe(replace(
      'lib/bootstrap/dist/css/bootstrap.css', 
      '//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css'))
    .pipe(gulp.dest('dist'));
});

gulp.task('watch', function() {
  gulp.watch('app/styles/**/*.scss', ['compass']);

  var server = livereload();
  gulp.watch(['dist/**']).on('change', function(file) {
    server.changed(file.path);
  });
});



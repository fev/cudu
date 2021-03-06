/* jslint node: true */
'use strict';

var gulp = require('gulp'),
    bowerfiles = require('main-bower-files'),
    bump = require('gulp-bump'),
    compass = require('gulp-compass'),
    connect = require('gulp-connect'),
    crypto = require('crypto'),
    es = require('event-stream'),
    filter = require('gulp-filter'),
    fs = require('fs'),
    googlecdn = require('gulp-google-cdn'),
    gulpif = require('gulp-if'),
    header = require('gulp-header'),
    htmlmin = require('gulp-htmlmin'),
    jshint = require('gulp-jshint'),
    livereload = require('gulp-livereload'),
    minifycss = require('gulp-minify-css'),
    path = require('path'),
    proxy = require('proxy-middleware'),
    rename = require('gulp-rename'),
    replace = require('gulp-replace'),
    revreplace = require('gulp-rev-replace'),
    rimraf = require('gulp-rimraf'),
    uglify = require('gulp-uglify'),
    useref = require('gulp-useref'),
    url = require('url');

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

var pkg = require('./package.json');

gulp.task('bump', function () {
  return gulp.src(['./package.json', './bower.json'])
    .pipe(bump())
    .pipe(gulp.dest('.'));
});

gulp.task('clean', function() {
  return gulp.src(['dist/', 'app/styles/cudu.css'], { read: false })
    .pipe(rimraf());
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
  return gulp.src(bowerfiles())
    .pipe(gulp.dest('dist/lib'));
});

gulp.task('images', function() {
  return gulp.src('app/images/**/*').pipe(gulp.dest('dist/images'));
});

gulp.task('lint', function() {
  return gulp.src('app/scripts/**/*.js')
    .pipe(jshint('.jshintrc'))
    .pipe(jshint.reporter('jshint-stylish'));
})

gulp.task('preflight', ['compass', 'bower-files', 'images'], function() {
  var stylesFilter  = filter('**/*.css');
  var scriptsFilter = filter('**/*.js');
  var assets = useref.assets();
  return gulp.src('app/**/*.html')
    .pipe(assets)
    .pipe(scriptsFilter)
      .pipe(revision())
      .pipe(gulpif(/\.cudu.js$/, uglify()))
      .pipe(gulpif(/\.cudu.js$/, header(fs.readFileSync('header.txt', 'utf8'), { pkg : pkg } )))
      .pipe(rename({ suffix: '.min' }))
    .pipe(scriptsFilter.restore())
    .pipe(stylesFilter)
      .pipe(revision())
      .pipe(rename({ suffix: '.min' }))
    .pipe(stylesFilter.restore())
    .pipe(assets.restore())
    .pipe(useref())
    .pipe(revreplace())
    .pipe(gulp.dest('dist'));
});

gulp.task('default', ['preflight'], function() {
  return gulp.src('dist/**/*.html')
    /* TODO htmlmin, cdn replace if required
    .pipe(googlecdn(require('./bower.json'), { componentsPath: 'lib' }))
    .pipe(replace(
      'lib/bootstrap/dist/css/bootstrap.css',
      '//netdna.bootstrapcdn.com/bootstrap/3.1.1/css/bootstrap.min.css'))
    */
    .pipe(gulp.dest('dist'));
});

gulp.task('connect', function() {
  connect.server({
    root: 'app',
    port: process.env.PORT || 9000,
    livereload: true,
    middleware: function(connect, o) {
      return [(function() {
        var options = url.parse('http://localhost:8080/');
        options.route = '/api';
        options.cookieRewrite = true;
        return proxy(options);
      })()];
    }
  });
});

gulp.task('watch', function() {
  gulp.watch('app/styles/**/*.scss', ['compass']);
});

gulp.task('serve', ['compass', 'connect', 'watch']);

/* jslint node: true */
'use strict';

var gulp = require('gulp'),
    bump = require('gulp-bump'),
    bowerfiles = require('gulp-bower-files'),
    cache = require('gulp-cache'),
    changed = require('gulp-changed'),
    clean = require('gulp-clean'),
    concat = require('gulp-concat'),
    crypto = require('crypto'),
    es = require('event-stream'),
    googlecdn = require('gulp-google-cdn'),
    gulpif = require('gulp-if'),
    htmlmin = require('gulp-htmlmin'),
    inject = require('gulp-inject'),
    jshint = require('gulp-jshint'),
    livereload = require('gulp-livereload'),
    minifycss = require('gulp-minify-css'),
    path = require('path'),
    rename = require('gulp-rename'),
    replace = require('gulp-replace'),
    sass = require('gulp-ruby-sass'),
    uglify = require('gulp-uglify');

var opts = require('yargs').argv;

var revision = function() {
  return es.map(function(file, callback) {
    var contents = file.contents.toString();
    var hash = crypto.createHash('md5').update(contents, 'utf8').digest('hex');
		var filename = hash.slice(0, 8) + '.' + path.basename(file.path);
		file.path = path.join(path.dirname(file.path), filename);
    callback(null, file);
  });
};

gulp.task('styles', function() {
  return gulp.src('app/styles/cudu.scss')
    .pipe(revision())
    .pipe(sass({ style: 'compact' }))
    .pipe(gulp.dest('dist/styles'))
    .pipe(rename({ suffix: '.min' }))
    .pipe(minifycss())
    .pipe(gulp.dest('dist/styles'));
});

gulp.task('scripts', function() {
  return gulp.src('app/scripts/**/*.js')
    .pipe(jshint('.jshintrc'))
    .pipe(jshint.reporter('default'))
    .pipe(concat('cudu.js'))
    .pipe(revision())
    .pipe(gulp.dest('dist/scripts'))
    .pipe(rename({ suffix: '.min' }))
    .pipe(uglify())
    .pipe(gulp.dest('dist/scripts'));
});

gulp.task('bower-files', function(){
    bowerfiles().pipe(gulp.dest('dist/lib'));
});

gulp.task('inject', ['styles', 'scripts'], function() {
  var source = ['dist/scripts/cudu.min.js', 'dist/styles/*cudu.min.css'];
  if (opts.readable) {
    source = ['dist/scripts/cudu.js', 'dist/styles/*cudu.css'];
  }
  return gulp.src('app/index.html')
    .pipe(gulpif(!opts.readable, googlecdn(require('./bower.json'), { componentsPath: 'lib' })))
    .pipe(gulpif(!opts.readable, replace('bootstrap.', 'bootstrap.min.')))
    .pipe(inject(gulp.src(source, { read: false }), { addRootSlash: false, ignorePath: '/dist/' }))
    .pipe(gulpif(!opts.readable, htmlmin({ removeComments: true, collapseWhitespace: true })))
    .pipe(gulp.dest('dist'));
});

gulp.task('clean', function() {
  return gulp.src(['dist/**'], {read: false})
    .pipe(clean());
});

gulp.task('default', ['clean'], function() {
    gulp.start('styles', 'scripts', 'inject', 'bower-files');
});

gulp.task('bump', function () {
  return gulp.src(['./package.json', './bower.json'])
    .pipe(bump())
    .pipe(gulp.dest('.'));
});

gulp.task('watch', function() {
  gulp.watch('app/**/*.html', ['inject']);
  gulp.watch('app/styles/**/*.scss', ['styles']);
  gulp.watch('app/scripts/**/*.js', ['scripts']);
  gulp.watch('app/images/**/*', ['images']);

  var server = livereload();
  gulp.watch(['dist/**']).on('change', function(file) {
    server.changed(file.path);
  });
});
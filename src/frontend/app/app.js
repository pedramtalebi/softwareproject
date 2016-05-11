// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'mainCtrl',
  'lineCtrl',
  'rerouteCtrl',
  'mainService',
  'ngAnimate',
  'mgcrea.ngStrap',
  'mgcrea.ngStrap.modal',
  'mgcrea.ngStrap.aside',
  'ngSanitize',
  'ngMap'
])
.config(['$routeProvider',
  function($routeProvider) {
  $routeProvider.
    when('/', {
      templateUrl : 'views/partials/buslines.html',
      controller  : 'mainController'
    }).
    when('/linje/:id', {
      templateUrl : 'views/partials/linepage.html',
      controller  : 'lineController'
    }).
    when('/reroutes', {
      templateUrl : 'views/partials/reroutes.html',
      controller  : 'rerouteController'
    })
    .otherwise({ redirectTo: '/' }); 
}]);


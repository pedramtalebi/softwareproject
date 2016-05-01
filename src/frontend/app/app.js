// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'mainCtrl',
  'lineCtrl',
  'ngAnimate',
  'mgcrea.ngStrap',
  'mgcrea.ngStrap.modal',
  'mgcrea.ngStrap.aside',
  'ngSanitize'
])
.config(['$routeProvider',
  function($routeProvider) {
  $routeProvider.
    when('/', {
      templateUrl : 'views/partials/buslines.html',
      controller  : 'mainController'
    }).
    when('/:id', {
      templateUrl : 'views/partials/linepage.html',
      controller  : 'lineController'
    })
    .otherwise({ redirectTo: '/' }); 
}]);


// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'mainCtrl',
  'lineCtrl',
  'ngAnimate',
  'mgcrea.ngStrap',
  'mgcrea.ngStrap.modal',
  'mgcrea.ngStrap.aside'
])

.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
  
  $routeProvider.
    when('/', {
      templateUrl : 'views/partials/buslines.html',
      controller  : 'mainController'
    }).
    when('/:lineid', {
      templateUrl : 'views/partials/linepage.html',
      controller  : 'lineController'
    })
    .otherwise({ redirectTo: '/' });
        
    $locationProvider.html5Mode(true); 
}]);


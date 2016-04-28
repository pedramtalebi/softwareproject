// Declare app level module which depends on views, and components
angular.module('myApp', [
  'ngRoute',
  'mainCtrl'
])

.config(['$routeProvider', '$locationProvider', function($routeProvider, $locationProvider) {
  
  $routeProvider.
    when('/', {
      templateUrl : 'views/partials/buslines.html',
      controller  : 'mainController'
    })
    .otherwise({ redirectTo: '/' });
        
    $locationProvider.html5Mode(true); 
}]);


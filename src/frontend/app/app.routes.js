angular.module('app.routes', ['ngRoute']) .config(function($routeProvider, $locationProvider) {
  $routeProvider
    .when('/', {
      templateUrl : 'app/views/pages/index.html',
      controller  : 'mainController',
      controllerAs: 'main'
});
$locationProvider.html5Mode(true); });

// myApp.config(['$routeProvider', '$httpProvider', '$locationProvider',  
//     function($routeProvider, $httpProvider, $locationProvider) {
//         $routeProvider.otherwise({redirectTo: '/'});
//         // set our app up to have pretty URLS
//             $locationProvider.html5Mode(true);
//             $httpProvider.interceptors.push('AuthInterceptor');
//     }]);
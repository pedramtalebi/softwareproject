'use strict';

// Declare app level module which depends on views, and components
var myApp = angular.module('myApp', [
  'ngRoute',
  'mainCtrl',
  'mainService'
]);

myApp.config(['$routeProvider', '$httpProvider', '$locationProvider',  
    function($routeProvider, $httpProvider, $locationProvider) {
        $routeProvider.otherwise({redirectTo: '/'});
        // set our app up to have pretty URLS
            $locationProvider.html5Mode(true);
            $httpProvider.interceptors.push('AuthInterceptor');
    }]);
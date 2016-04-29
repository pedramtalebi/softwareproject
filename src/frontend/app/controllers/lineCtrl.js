angular.module('lineCtrl', [])

.controller('lineController', ['$scope','$routeParams', function($scope, $routeParams) {
    // Id nummer för specifik linje
    $scope.id = $routeParams.id;
//Dummie reroutes
    $scope.reroutes = [
    	{name: 'Korsvägen'},
    	{name: 'Avenyn'},
    	{name: 'Brunnsparken'},
    	{name: 'Domkyrkan'}
    ];
     
}]);
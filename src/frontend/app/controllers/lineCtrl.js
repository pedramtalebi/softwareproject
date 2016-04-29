angular.module('lineCtrl', [])

.controller('lineController', ['$scope','$routeParams', function($scope, $routeParams) {
    // Id nummer för specifik linje
    $scope.id = $routeParams.id;
     
}]);
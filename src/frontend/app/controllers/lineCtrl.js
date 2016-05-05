angular.module('lineCtrl', ['mainService'])

.controller('lineController', ['$scope','$routeParams','NgMap','Lines', function($scope, $routeParams, NgMap, Lines) {
    // Id nummer för specifik linje
    $scope.id = $routeParams.id;
    //Dummie reroutes
        $scope.reroutes = [
            {name: 'Korsvägen'},
            {name: 'Avenyn'},
            {name: 'Brunnsparken'},
            {name: 'Domkyrkan'}
        ];
    // Object and array for saving coordinates 
    $scope.rerouteCoordinates = {};
    $scope.rerouteCoordinatesArray = [];   
    
    NgMap.getMap().then(function(map) {
       
    });
    
   $scope.addNewRoute = function(map){
        for(var i=0; i<map.directionsRenderers[0].directions.routes[0].overview_path.length; i++){
            $scope.rerouteCoordinates.lat = map.directionsRenderers[0].directions.routes[0].overview_path[i].lat();
            $scope.rerouteCoordinates.long = map.directionsRenderers[0].directions.routes[0].overview_path[i].lng();
            $scope.rerouteCoordinatesArray.push($scope.rerouteCoordinates);
        }
        Lines.postNewRoute($scope.rerouteCoordinatesArray);
   };
     
}]);
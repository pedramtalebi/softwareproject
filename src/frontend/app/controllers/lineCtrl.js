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
     var obj = {};
    // Object and array for saving coordinates 
    rerouteCoordinates = {};
    rerouteCoordinatesArray = [];  
    $scope.affectedLines = [$scope.id]; 
    // Variables from the google API map
    $scope.origin = 'Göteborg';
    $scope.destination = 'Göteborg';
    
    // Google maps API
    NgMap.getMap().then(function(map) {
       console.log(map.directionsRenderers[0]);
       console.log(map.directionsRenderers[0].origin);
    });
    
   $scope.addNewRoute = function(map){
       var rerouteCoordinatesArray = [];
       var path = map.directionsRenderers[0].directions.routes[0].overview_path;
        for(var i=0; i<path.length; i++){
            var rerouteCoordinates = {
                lat: path[i].lat(),
                long: path[i].lng()
            };
            rerouteCoordinatesArray.push(rerouteCoordinates);
        }
        obj.origin = map.directionsRenderers[0].origin;
        obj.coordinates = rerouteCoordinatesArray;
        obj.affectedLines = $scope.affectedLines;
        obj.name = map.directionsRenderers[0].directions.routes[0].summary;
        console.log(obj);
        Lines.postNewRoute(obj).success(function(data) {
            console.log("reroute added");
        });
   };
   
   $scope.createMapVariables = function(map){
       $scope.origin = map.directionsRenderers[0].origin;
       $scope.destination = map.directionsRenderers[0].destination;
       console.log(map.directionsRenderers[0].directions);
    //    $scope.origin = map.directionsRenderers[0].origin;
    //    $scope.destination = map.directionsRenderers[0].destination;
   };
     
}]);
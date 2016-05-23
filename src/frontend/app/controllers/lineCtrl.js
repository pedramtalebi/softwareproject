angular.module('lineCtrl', ['mainService'])

.controller('lineController', ['$scope','$routeParams','NgMap','Lines', function($scope, $routeParams, NgMap, Lines) {
    // Id(busnummer) nummer för specifik linje
    $scope.id = $routeParams.id;
     
    // Get reroutes for listing them
    var busLine = parseInt($scope.id);
    $scope.reroutesSpecLine = [];
    Lines.getReroutes().success(function(data){
        for(i=0; i<data.length; i++){
            if(data[i].affectedLines.indexOf(busLine)!=-1){
                $scope.reroutesSpecLine.push(data[i].id);
            }
        } 
    });

    var obj = {};
    // Object and array for saving coordinates 
    rerouteCoordinates = {};
    rerouteCoordinatesArray = [];  
    $scope.affectedLines = [$scope.id]; 
    // Variables from the google API map
    $scope.origin = '';
    $scope.destination = '';
    
    // Google maps API
    NgMap.getMap().then(function(map) {
       
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
                
        Lines.postNewRoute(obj).success(function(data) {
            console.log("reroute added");
        });
   };
}]);
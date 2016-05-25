angular.module('rerouteCtrl', [])

.controller('rerouteController', ['$scope', 'NgMap','Lines', function($scope, NgMap, Lines) {
    
    // Variables for modal 
    $scope.affectedLines = '';
    $scope.datum = '';
    $scope.tid = '';
    $scope.id = '';
    $scope.name = '';
    
    $scope.allRerouteFlags = [];
    Lines.getReroutes().success(function(data){ 
        $scope.reroutesAll = data; 
        // console.log($scope.reroutes[0].coordinates[0].lat);
    });
    
    $scope.addForModal = function(data){
        $scope.affectedLines = data.affectedLines;
        $scope.datum = data.created_at.slice(0,10);
        $scope.tid = data.created_at.slice(11,16);
        $scope.id = data._id;
        $scope.name = data.name;
    };
    
    $scope.deleteReroute = function(id){
        Lines.deleteReroute(id).success(function(data){
           console.log('deleted');
           Lines.getReroutes().success(function(data){ 
            $scope.reroutesAll = data; 
            });
        });
    };
}]);
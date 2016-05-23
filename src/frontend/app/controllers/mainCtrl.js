angular.module('mainCtrl', [])

.controller('mainController', ['$scope', '$aside','NgMap', 'Lines', function($scope, $aside, NgMap, Lines) {
    
    
    $scope.message = 'Software Engineering Project';
    $scope.aside = {title: 'Nuvarande omdirigeringar', content: "."};
    
    $scope.lines = [
        { linje: 16 },
        { linje: 17 },
        { linje: 18 },
        { linje: 19 },
        { linje: 25 },
        { linje: 45 },
        { linje: 55 },
        { linje: 60 } 
    ];

    Lines.getReroutes().success(function(data){ 
        $scope.reroutes = data; 
        console.log($scope.reroutes[0].coordinates[0].lat);
    });
}]);
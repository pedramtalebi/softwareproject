angular.module('mainCtrl', [])

.controller('mainController', ['$scope', '$aside', function($scope, $aside) {
    
    $scope.message = 'Software Engineering Project';
    
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
    
    $scope.aside = {
        "title": "Nuvarande omdirigerar",
        "content": ""
    };
    
        
    
        
}]);
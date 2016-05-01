angular.module('mainCtrl', [])

.controller('mainController', ['$scope', '$aside', function($scope, $aside) {
    
    
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
    $scope.reroutes = [
        { linje: 16, plats: 'Korsvägen' },
        { linje: 17, plats: 'Avenyn' },
        { linje: 18, plats: 'Backaplan' },
        { linje: 19, plats: 'Johanneberg' },
        { linje: 25, plays: 'Avenyn' },
        { linje: 45, plats: 'Avenyn' },
        { linje: 55, plats: 'Avenyn' },
        { linje: 60, plats: 'Brunnsparken' } 
    ];
     
}]);
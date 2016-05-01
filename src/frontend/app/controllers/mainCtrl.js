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
        { linje: 16, omrade: 'Korsvägen' },
        { linje: 17, omrade: 'Avenyn' },
        { linje: 18, omrade: 'Backaplan' },
        { linje: 19, omrade: 'Johanneberg' },
        { linje: 25, omrade: 'Avenyn' },
        { linje: 45, omrade: 'Avenyn' },
        { linje: 55, omrade: 'Avenyn' },
        { linje: 60, omrade: 'Brunnsparken' } 
    ];
     
}]);
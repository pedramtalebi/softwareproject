angular.module('rerouteCtrl', [])

.controller('rerouteController', ['$scope', function($scope) {

    $scope.currentreroutes = [
        { linje: 16, omrade: 'Korsvägen', 	accepted: 'Ja' },
        { linje: 17, omrade: 'Avenyn', 		accepted: 'Ja' },
        { linje: 18, omrade: 'Backaplan', 	accepted: 'Nej' },
        { linje: 19, omrade: 'Johanneberg',	accepted: 'Ja' },
        { linje: 25, omrade: 'Avenyn', 		accepted: 'Nej' },
        { linje: 45, omrade: 'Avenyn', 		accepted: 'Nej' },
        { linje: 55, omrade: 'Avenyn', 		accepted: 'Ja' },
        { linje: 60, omrade: 'Brunnsparken',accepted: 'Ja' } 
    ];

}]);
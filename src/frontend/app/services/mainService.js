angular.module('mainService', [])

.factory('Lines', ['$http', function($http) {

	// create a new object
	var linesFactory = {};

	linesFactory.postNewRoute = function(data) {
		console.log(data);
	};

	// return our entire linesFactory object
	return linesFactory;
}]);
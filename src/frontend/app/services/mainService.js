angular.module('mainService', [])

.factory('Lines', ['$http', function($http) {

	// create a new object
	var linesFactory = {};

	// Get all reroutes 
	linesFactory.getReroutes = function(data) {
		return $http.get('/v1/reroute');	
	};

	// Post new reroute for specific line
	linesFactory.postNewRoute = function(data) {
		return $http.post('/v1/reroute', data);
	};
	

	// return our entire linesFactory object
	return linesFactory;
}]);
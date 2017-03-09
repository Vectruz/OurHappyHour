'use strict';

angular.module('appServices').factory('UsersService', function ($resource) {
	var resources = $resource('http://localhost:8080/OurHappyHour/rest/user/:id',{
		id: '@id'
	});
	return resources; 
});

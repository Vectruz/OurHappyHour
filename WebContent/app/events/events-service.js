'use strict';

angular.module('appServices').factory('EventsService', function ($resource) {
	var resources = $resource('http://localhost:8080/OurHappyHour/rest/event/:id',{
		id: '@id'
	});
	return resources; 
});

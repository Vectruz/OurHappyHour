angular.module('appControllers', ['ngRoute', 'ngAnimate', 'toastr']);
angular.module('appServices', ['ngResource']);

var ourHappyHour = angular.module('ourHappyHour', [
	'appControllers',
	'appServices',
	'ngAnimate',
	'toastr'
]);

ourHappyHour.config(function($routeProvider, $locationProvider){
	$routeProvider
	.when('/home', {
		templateUrl: 'app/home/home.html',
		controller: 'HomeCtrl',
	})
	
	.when('/users', {
		templateUrl: 'app/users/users.html',
		controller: 'UsersCtrl',
	})
	.when('/user', {
		templateUrl: 'app/users/user-record.html',
		controller: 'UserRecordCtrl',
	})
	.when('/user/:id', {
		templateUrl: 'app/users/user-record.html',
		controller: 'UserRecordCtrl',
	})
	
	
	.when('/events', {
		templateUrl: 'app/events/events.html',
		controller: 'EventsCtrl',
	})
	.when('/event', {
		templateUrl: 'app/events/event-record.html',
		controller: 'EventRecordCtrl',
	})
	.when('/event/:id', {
		templateUrl: 'app/events/event-record.html',
		controller: 'EventRecordCtrl',
	})
	
	.otherwise ({ redirectTo: '/home' });
});

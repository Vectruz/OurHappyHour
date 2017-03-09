ourHappyHour.controller('HomeCtrl', function ($scope, $location, $rootScope) {
	$scope.goToUsers = function () {
		$location.path("/users");
	};

	$scope.goToEvents = function () {
		$location.path("/events");
	};
});

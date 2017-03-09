ourHappyHour.controller('MainCtrl', function MainCtrl($scope, $location) {
	$scope.goToHome = function () {
		$location.path("/home");
	};
	$scope.goToUsers = function () {
		$location.path("/users");
	};
	$scope.goToEvents = function () {
		$location.path("/events");
	};
});

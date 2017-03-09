ourHappyHour.controller('EventsCtrl', function ($scope, $location, $rootScope, $window, EventsService) {
	$scope.titulo = "Events";

	$scope.newEvent = function(){
		$location.path('/event');
	};
	
	$scope.eventRecord = function(id) {
		if (!id) {
			$location.path('/event');
		} else {
			$location.path('/event/' + id);
		}
	}
	
	$scope.eventDisable = function(id) {
		var event = EventsService.get({id: id},
				function(data){
					event = data;
					event.eventUsers = [];
					event.disabled = true;
					event.$save({id: event.id},
							function(){
								$window.location.reload();
							},
							function(response){
							}
					);
				});
	}

	$scope.events = new EventsService();
	EventsService.query(
			function(data){
				$scope.events = data;
				for (var i = 0; i < $scope.events.length; i++) {
					var time = new Date($scope.events[i].time);
					var textDate = time.getFullYear() + '/';
					if ((time.getMonth()+1) < 10) {
						textDate += '0';
					}
					textDate += time.getMonth()+1 + '/';
					if (time.getDate() < 10) {
						textDate += '0';
					}
					textDate += time.getDate() + ' at ';
					if (time.getHours() < 10) {
						textDate += '0';
					}
					textDate += time.getHours() + ':';
					if (time.getMinutes() < 10) {
						textDate += '0';
					}
					textDate += time.getMinutes();
					$scope.events[i].textDate = textDate;
				}
			},
			function(response){
			}
	);
});

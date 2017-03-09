ourHappyHour.controller('EventRecordCtrl', function ($scope, $location, $routeParams, toastr, EventsService, UsersService) {
	$scope.titulo = "Event";
	
	$scope.id = $routeParams.id;

	$scope.users = new UsersService();
	UsersService.query(
			function(data){
				$scope.users = data;
			},
			function(response){
			}
	);
	
	$scope.inviteToEvent = function(user) {
		if (!$scope.event.eventUsers) {
			$scope.event.eventUsers = [];
		}
		$scope.event.eventUsers.push({user: user, paid: 0});
		for (var i = 0; i < $scope.users.length; i++) {
			if ($scope.users[i].id === user.id) {
				$scope.users.splice(i, 1);
			}
		}
		$scope.refreshValuePerUserEvent();
	}
	
	$scope.unInviteToEvent = function(eventUser) {
		if (eventUser.paid) {
			eventUser.user.balance += eventUser.paid;
			eventUser.paid = 0;
		}
		if (!$scope.event.eventUsers) {
			$scope.event.eventUsers = [];
		}
		$scope.users.push(eventUser.user);
		for (var i = 0; i < $scope.event.eventUsers.length; i++) {
			if ($scope.event.eventUsers[i].user.id === eventUser.user.id) {
				$scope.event.eventUsers.splice(i, 1);
			}
		}
		$scope.refreshValuePerUserEvent();
	}
	
	$scope.refreshValuePerUserEvent = function() {
		var newUsersDefaultPayment = 0;
		var totalCustomPayments = 0;
		if (!$scope.event.eventUsers) {
			return;
		}
		for (var i = 0; i < $scope.event.eventUsers.length; i++) {
			if ($scope.event.externalPayment && $scope.event.eventUsers[i].paid) {
				$scope.event.eventUsers[i].user.balance += $scope.event.eventUsers[i].paid;
				$scope.event.eventUsers[i].paid = 0;
			}
			if (!$scope.event.eventUsers[i].customPayment) {
				newUsersDefaultPayment++;
			} else if($scope.event.eventUsers[i].customPayment && $scope.event.eventUsers[i].customPaymentValue) {
				totalCustomPayments += $scope.event.eventUsers[i].customPaymentValue;
			}
		}
		$scope.event.valuePerUserEvent = ($scope.event.totalValue - totalCustomPayments) / newUsersDefaultPayment;
	}
	
	$scope.payEvent = function(eventUser) {
		if(!eventUser.customPayment) {
			if (eventUser.paid >= $scope.event.valuePerUserEvent) {
				toastr.info('Already paid', 'Information');
				return;
			}
			eventUser.user.balance -= $scope.event.valuePerUserEvent - eventUser.paid;
			eventUser.paid = $scope.event.valuePerUserEvent;
		} else if(eventUser.customPayment && eventUser.customPaymentValue) {
			if (eventUser.paid >= eventUser.customPaymentValue) {
				toastr.info('Already paid', 'Information');
				return;
			}
			eventUser.user.balance -= eventUser.customPaymentValue - eventUser.paid;
			eventUser.paid = eventUser.customPaymentValue;
		}
	}
	
	$scope.refoundUsersEvent = function() {
		for (var i = 0; i < $scope.event.eventUsers.length; i++) {
			if (!$scope.event.eventUsers[i].customPayment && $scope.event.eventUsers[i].paid > $scope.event.valuePerUserEvent) {
				$scope.event.eventUsers[i].user.balance += $scope.event.eventUsers[i].paid - $scope.event.valuePerUserEvent;
				$scope.event.eventUsers[i].paid = $scope.event.valuePerUserEvent;
			} else if ($scope.event.eventUsers[i].customPayment && $scope.event.eventUsers[i].paid > $scope.event.eventUsers[i].customPaymentValue) {
				$scope.event.eventUsers[i].user.balance += $scope.event.eventUsers[i].paid - $scope.event.eventUsers[i].customPaymentValue;
				$scope.event.eventUsers[i].paid = $scope.event.eventUsers[i].customPaymentValue;
			}
		}
	}
	
	$scope.event = new EventsService();

	$scope.save = function() {
		$scope.event.time.setHours($scope.event.time.getHours()-($scope.event.time.getTimezoneOffset() / 60));
		$scope.event.$save({id: $scope.event.id},
				function(){
					$location.path("/events");
				},
				function(response){
				}
			);
	}
	
	if($scope.id){		
		EventsService.get({id: $scope.id},
			function(data){
				$scope.event = data;
				$scope.event.time = new Date($scope.event.time);
				$scope.refreshValuePerUserEvent();
				for (var i = 0; i < $scope.event.eventUsers.length; i++) {
					for (var u = 0; u < $scope.users.length; u++) {
						if ($scope.event.eventUsers[i].user.id === $scope.users[u].id) {
							$scope.users.splice(u, 1);
							break;
						}
					}
				}
			},
			function() {
			}
		);
	}
	
	$scope.goToHome = function () {
		$location.path("/home");
	};
	
	$scope.goToEvents = function () {
		$location.path("/events");
	};
});

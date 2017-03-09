ourHappyHour.controller('UserRecordCtrl', function ($scope, $location, $routeParams, toastr, UsersService) {
	$scope.titulo = "User";
	
	$scope.id = $routeParams.id;
	
	$scope.user = new UsersService();

	$scope.save = function() {
		if ($scope.user.balance < 0) {
			toastr.error('Balance must be 0 or more', 'Error');
			return;
		}
		$scope.user.$save({id: $scope.user.id},
				function(){
					$location.path("/users");
				},
				function(response){
				}
			);
	}
	
	if($scope.id){		
		UsersService.get({id: $scope.id},
			function(data){
				$scope.user = data;
			},
			function() {
			}
		);
	}
	
	$scope.goToHome = function () {
		$location.path("/home");
	};
	
	$scope.goToUsers = function () {
		$location.path("/users");
	};
});

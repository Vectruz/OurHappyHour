ourHappyHour.controller('UsersCtrl', function ($scope, $location, $rootScope, $window, toastr, UsersService) {
	$scope.titulo = "Users";

	$scope.newUser = function(){
		$location.path('/user');
	};
	
	$scope.userRecord = function(id) {
		if (!id) {
			$location.path('/user');
		} else {
			$location.path('/user/' + id);
		}
	}
	
	$scope.userDisable = function(user) {
		if (user && user.balance) {
			toastr.error('To disable user must be 0 in balance', 'Error');
			return;
		}
		if (user && user.id) {
			var userToDisable = new UsersService();
			userToDisable = user;
			userToDisable.disabled = true;
			userToDisable.$save({id: user.id},
					function(){
						$window.location.reload();
					},
					function(response){
					}
				);
		} else {
		}
	}

	$scope.users = new UsersService();
	UsersService.query(
			function(data){
				$scope.users = data;
			},
			function(response){
			}
	);
});

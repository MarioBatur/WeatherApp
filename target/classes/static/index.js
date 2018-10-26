//angularjs script

var myApp = angular.module('myApp', []);

myApp
		.controller(
				'WeatherController',
				function WeatherController($scope, $http) {

					$scope.prikazano = false;
					$scope.sakriveno = false;
					$scope.dohvatiPrognozu = function() {
							$http
							.get('/main/processForm?name=' + $scope.data)
							.then(
									function(data) {
										console.log(data);
										$scope.podaci = data.data;
										if (data.data.Error === "Nepoznata lokacija molimo Vas pružite točnu lokaciju") {
											$scope.prikazano = false;
											$scope.sakriveno = true;
										} else {
											$scope.sakriveno = false;
											$scope.prikazano = true;
										}
									})
					}
				});
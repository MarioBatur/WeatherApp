//angularjs script

var myApp = angular.module('myApp', []);

myApp
		.controller(
				'WeatherController',
				function WeatherController($scope, $http) {

					$scope.opis = 'vedro';
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
												$scope.opis = data.data.currentWeather.weather[0].description;
												var opisVremena = $scope.opis;
												if(opisVremena.indexOf('pljusak')!=-1){
													$scope.opis = 'kisa'
												} else if (opisVremena.indexOf('kiša')!=-1) {
													$scope.opis = 'kisa'
												} else if (opisVremena.indexOf('rosulja')!=-1) {
													$scope.opis = 'kisa'
												} else if (opisVremena.indexOf('magla')!=-1) {
													$scope.opis = 'magla'
												} else if (opisVremena.indexOf('magli')!=-1) {
													$scope.opis = 'magla'
												} else if (opisVremena.indexOf('vedro')!=-1) {
													$scope.opis = 'vedro'
												} else if (opisVremena.indexOf('obla')!=-1) {
													$scope.opis = 'oblacno'
												} else if (opisVremena.indexOf('sunce')!=-1) {
													$scope.opis = 'sunce'
												} else if (opisVremena.indexOf('oluja')!=-1) {                 
													$scope.opis = 'oluja'
												} else if (opisVremena.indexOf('snijeg')!=-1) {
													$scope.opis = 'snijeg'
												} else if (opisVremena.indexOf('snijež')!=-1) {
													$scope.opis = 'snijeg'
												} else {
													$scope.opis = 'vedro'
													}
												$scope.sakriveno = false;
												$scope.prikazano = true;
											}
										})
					}
				});
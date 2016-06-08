angular.module('bank.login', [
    'ui.router',
    'plusOne'
])

    .config(function config($stateProvider) {
        $stateProvider.state('login', {
            url: '/login',
            views: {
                "main": {
                    controller: 'MainController',
                    templateUrl: 'login/login.tpl.html'
                }
            },
            data: {pageTitle: 'Login'}
        });
    })

    .controller("login", function ($scope, $http, $state) {
        $scope.user = {};
        $scope.submit = function () {
            $http.post('/login', $scope.user).success(function () {
                console.log('no error');
                $state.go("home");
            }).error(function (response) {
                console.log('error');
                console.log(response);
                $scope.errorMessages = response;
            });
        };
    })

    .controller('MainController', function ($scope) {

    });
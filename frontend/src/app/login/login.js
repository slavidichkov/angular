angular.module('bank.login', [
    'ui.router'
])

    .config(function config($stateProvider) {
        $stateProvider.state('login', {
            url: '/login',
            views: {
                "main": {
                    controller: 'LoginController',
                    templateUrl: 'login/login.tpl.html'
                }
            },
            data: {pageTitle: 'Login'}
        });
    })

    .controller("LoginController", function ($scope, $http, $state) {
        $scope.submit = function () {
            $http.post('/login', $scope.user).success(function () {
                $state.go("home");
            }).error(function (response) {
                $scope.errorMessages = response;
            });
        };
    });
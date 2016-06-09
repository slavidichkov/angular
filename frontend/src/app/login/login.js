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
        $scope.submit = function () {
            $http.post('/login', $scope.user).success(function () {
                $state.go("home");
            }).error(function (response) {
                $scope.errorMessages = response;
            });
        };
    });
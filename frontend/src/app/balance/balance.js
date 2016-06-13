angular.module('bank.balance', [
          'ui.router'
        ])

        .config(function config($stateProvider) {
          $stateProvider.state('balance', {
            url: '/balance',
            views: {
              "main": {
                controller: 'TransactionController',
                templateUrl: 'balance/balance.tpl.html'
              }
            },
            data: {pageTitle: 'Balance'}
          });
        })

        .controller("TransactionController", function ($scope, $http, $state) {

          $scope.init = function () {
            $http.post('/balance', $scope.transaction).success(function (response) {
              $scope.messages = response;
            }).error(function (response, status) {
              if (status === 401) {
                $state.go("login");
                return;
              }
              $scope.messages = response;
            });
          };

          $scope.submit = function () {
            $http.post('/balance', $scope.transaction).success(function (response) {
              $scope.messages = response;
            }).error(function (response, status) {
              if (status === 401) {
                $state.go("login");
                return;
              }
              $scope.messages = response;
            });
          };
        });

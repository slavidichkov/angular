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

        .controller("TransactionController", function ($scope, $http) {
          $scope.submit = function () {
            $http.post('/balance', $scope.transaction).success(function (response) {
              $scope.messages = response;
            }).error(function (response) {
              $scope.messages = response;
            });
          };
        });

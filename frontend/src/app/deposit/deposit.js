/**
 * Created by clouway on 16-6-14.
 */
angular.module('bank.deposit', [
          'ui.router'
        ])

        .config(function config($stateProvider) {
          $stateProvider.state('deposit', {
            url: '/deposit',
            views: {
              "main": {
                controller: 'DepositController',
                templateUrl: 'deposit/deposit.tpl.html'
              }
            },
            data: {pageTitle: 'Deposit'}
          });
        })

        .controller("DepositController", function ($scope, $http, $state) {

          $scope.init = function () {
            $http.post('/account/balance', $scope.transaction).success(function (response) {
              $scope.account = response;
            }).error(function (response, status) {
              if (status === 401) {
                $state.go("login");
                return;
              }
            });
          };

          $scope.deposit = function () {
            $http.post('/account/deposit', $scope.transaction).success(function (response) {
              $scope.depositDTO = response;
              $scope.init();
            }).error(function (response, status) {
              if (status === 401) {
                $state.go('login');
                return;
              }
              $scope.depositDTO = response;
              $scope.init();
            });
          };
        });
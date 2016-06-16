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

          $scope.getBalance = function () {
            $http.post('/account/').success(function (response) {
              $scope.account = response;
            }).error(function (response, status) {
              if (status === 401) {
                $state.go("login");
                return;
              }
            });
          };

          $scope.deposit = function (transaction) {
            $http.post('/account/deposit',transaction).success(function (response) {
              $scope.account = response;
              $scope.errors = {};
            }).error(function (response, status) {
              if (status === 401) {
                $state.go('login');
                return;
              }
              $scope.errors = response;
            });
          };
        });
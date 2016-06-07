angular.module('ngBoilerplate', [
    'templates-app',
    'templates-common',
    'ui.bootstrap',
    'ngBoilerplate.home',
    'ui.router'
])
    .config(function myAppConfig($stateProvider, $urlRouterProvider) {
        $urlRouterProvider.otherwise('/home');
    })

    .run(function run() {
    })

    .controller('AppCtrl', function AppCtrl($scope) {
        $scope.$on('$stateChangeSuccess', function (event, toState) {
            if (angular.isDefined(toState.data.pageTitle)) {
                $scope.pageTitle = toState.data.pageTitle;
            }
        });
    });
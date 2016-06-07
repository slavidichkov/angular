angular.module('ngBoilerplate.home', [
    'ui.router',
    'plusOne'
])
    
.config(function config($stateProvider) {
    $stateProvider.state('home', {
        url: '/home',
        views: {
            "main": {
                controller: 'MainController',
                templateUrl: 'home/home.tpl.html'
            }
        },
        data: {pageTitle: 'Home'}
    });
})

.controller('MainController', function ($scope) {

});
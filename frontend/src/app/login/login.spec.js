/**
 * Created by clouway on 27.05.16.
 */
describe('login section', function () {
  beforeEach(module('bank.login'));

  var scope, http, state, httpBackend, createController;

  beforeEach(inject(function($rootScope, $httpBackend, $controller, $http, $state) {
    httpBackend = $httpBackend;
    scope = $rootScope.$new();
    http = $http;
    state = $state;

    createController = function() {
      return $controller('login', {
        $scope: scope,
        $http: $http,
        $state: $state
      });
    };
    createController();
  }));

  it('should have a method to send request with correct data to server', function() {
    spyOn(state,'go');
    httpBackend
            .expect('POST', '/login')
            .respond(200);
    scope.submit();
    httpBackend.flush();
    state.go('balance');
    expect(state.go).toHaveBeenCalledWith('balance');
  });


  it('should have a method to send request with wrong data to server', function() {
    httpBackend
            .expect('POST', '/login')
            .respond(400 , {"wrongName":"name is incorrect"});
    scope.submit();
    httpBackend.flush();
    expect(scope.errorMessages).toEqual({"wrongName":"name is incorrect"});
  });
});
/**
 * Created by clouway on 16-6-14.
 */
describe( 'deposit section', function() {
  beforeEach( module( 'bank.deposit' ) );
  var scope, state, httpBackend, transaction;

  beforeEach(inject(function($rootScope, $httpBackend, $controller, $http, $state) {
    httpBackend = $httpBackend;
    scope = $rootScope.$new();
    state = $state;

    $controller('DepositController', {
      $scope: scope,
      $http: $http,
      $state: state

    });

  }));

  it('success deposit', function() {
    scope.transaction={"amount":'23.00'};
    httpBackend.expect('POST', '/account/deposit',scope.transaction).respond(200, {"success": 'SUCCESS_DEPOSIT'});
    httpBackend.expect('POST', '/account/balance').respond(200, {"balance":"23.00"});
    scope.deposit();
    httpBackend.flush();
    expect(scope.depositResult).toEqual({"success": 'SUCCESS_DEPOSIT'});
    expect(scope.account).toEqual({"balance":"23.00"});
  });


  it('try to deposit with invalid amount', function() {
    scope.transaction={"amount":'23dsd00'};
    httpBackend.expect('POST', '/account/deposit',scope.transaction).respond(400 , {"error":'INVALID-AMOUNT'});
    httpBackend.expect('POST', '/account/balance').respond(200, {"balance":"00.00"});
    scope.deposit();
    httpBackend.flush();
    expect(scope.depositResult).toEqual({"error":'INVALID-AMOUNT'});
    expect(scope.account).toEqual({"balance":"00.00"});
  });

  it('try to deposit not logged user', function() {
    spyOn(state,'go');
    httpBackend.expect('POST', '/account/deposit').respond(401);
    scope.deposit();
    httpBackend.flush();
    expect(state.go).toHaveBeenCalledWith('login');
  });

  it('get balance from server', function() {
    httpBackend.expect('POST', '/account/balance').respond(200, {"balance":"350"});
    scope.getBalance();
    httpBackend.flush();
    expect(scope.account).toEqual({"balance":"350"});
  });

  it('init method for not logged user', function() {
    spyOn(state,'go');
    httpBackend.expect('POST', '/account/balance').respond(401);
    scope.getBalance();
    httpBackend.flush();
    expect(state.go).toHaveBeenCalledWith('login');
  });
});
/**
 * Created by clouway on 27.05.16.
 */
describe( 'balance section', function() {
    beforeEach( module( 'bank.balance' ) );
    var scope, state, httpBackend;

    beforeEach(inject(function($rootScope, $httpBackend, $controller, $http, $state) {
        httpBackend = $httpBackend;
        scope = $rootScope.$new();
        state = $state;

        $controller('TransactionController', {
            $scope: scope,
            $http: $http,
            $state: state
        });

    }));

    it('should have a method to send request with correct data to server', function() {
        httpBackend.expect('POST', '/balance').respond(200, {"transactionMessage":"Withdraw was successful"});
        scope.submit();
        httpBackend.flush();
        expect(scope.messages).toEqual({"transactionMessage":"Withdraw was successful"});
    });


    it('should have a method to send request with wrong data to server', function() {
        httpBackend.expect('POST', '/balance').respond(400 , {"transactionErrorMessage":"Can not withdraw the given amount"});
        scope.submit();
        httpBackend.flush();
        expect(scope.messages).toEqual({"transactionErrorMessage":"Can not withdraw the given amount"});
    });

    it('not logged user', function() {
        spyOn(state,'go');
        httpBackend.expect('POST', '/balance').respond(401);
        scope.submit();
        httpBackend.flush();
        expect(state.go).toHaveBeenCalledWith('login');
    });

    it('should have a method to send request with correct data to server', function() {
        httpBackend.expect('POST', '/balance').respond(200, {"balanceMessage":"350"});
        scope.init();
        httpBackend.flush();
        expect(scope.messages).toEqual({"balanceMessage":"350"});
    });

    it('init method for not logged user', function() {
        spyOn(state,'go');
        httpBackend.expect('POST', '/balance').respond(401);
        scope.init();
        httpBackend.flush();
        expect(state.go).toHaveBeenCalledWith('login');
    });
});
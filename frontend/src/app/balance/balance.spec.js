/**
 * Created by clouway on 27.05.16.
 */
describe( 'balance section', function() {
    beforeEach( module( 'bank.balance' ) );
    var scope, myForm, httpBackend;

    beforeEach(inject(function($rootScope, $httpBackend, $controller, $http) {
        httpBackend = $httpBackend;
        scope = $rootScope.$new();

        $controller('TransactionController', {
            $scope: scope,
            $http: $http
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
});
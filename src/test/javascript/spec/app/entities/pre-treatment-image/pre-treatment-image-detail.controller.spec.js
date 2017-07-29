'use strict';

describe('Controller Tests', function() {

    describe('PreTreatmentImage Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockPreTreatmentImage, MockTreatment;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockPreTreatmentImage = jasmine.createSpy('MockPreTreatmentImage');
            MockTreatment = jasmine.createSpy('MockTreatment');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'PreTreatmentImage': MockPreTreatmentImage,
                'Treatment': MockTreatment
            };
            createController = function() {
                $injector.get('$controller')("PreTreatmentImageDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'dentoCareApp:preTreatmentImageUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

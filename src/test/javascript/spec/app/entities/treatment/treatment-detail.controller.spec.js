'use strict';

describe('Controller Tests', function() {

    describe('Treatment Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTreatment, MockPatient, MockNotes, MockOralExamination, MockPayment, MockPreTreatmentImage, MockPostTreatmentImage;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTreatment = jasmine.createSpy('MockTreatment');
            MockPatient = jasmine.createSpy('MockPatient');
            MockNotes = jasmine.createSpy('MockNotes');
            MockOralExamination = jasmine.createSpy('MockOralExamination');
            MockPayment = jasmine.createSpy('MockPayment');
            MockPreTreatmentImage = jasmine.createSpy('MockPreTreatmentImage');
            MockPostTreatmentImage = jasmine.createSpy('MockPostTreatmentImage');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Treatment': MockTreatment,
                'Patient': MockPatient,
                'Notes': MockNotes,
                'OralExamination': MockOralExamination,
                'Payment': MockPayment,
                'PreTreatmentImage': MockPreTreatmentImage,
                'PostTreatmentImage': MockPostTreatmentImage
            };
            createController = function() {
                $injector.get('$controller')("TreatmentDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'dentoCareApp:treatmentUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});

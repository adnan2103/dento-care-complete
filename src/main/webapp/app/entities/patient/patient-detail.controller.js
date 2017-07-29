(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('PatientDetailController', PatientDetailController);

    PatientDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'Patient', 'Treatment', 'Appointment', 'ContactNumber', 'Email', 'Address'];

    function PatientDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, Patient, Treatment, Appointment, ContactNumber, Email, Address) {
        var vm = this;

        vm.patient = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('dentoCareApp:patientUpdate', function(event, result) {
            vm.patient = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

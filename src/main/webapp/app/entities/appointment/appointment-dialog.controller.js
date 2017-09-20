(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('AppointmentDialogController', AppointmentDialogController);

    AppointmentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Appointment', 'Patient'];

    function AppointmentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Appointment, Patient) {
        var vm = this;

        vm.appointment = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.patients = Patient.fetchAll();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.appointment.id !== null) {
                Appointment.update({patientId:vm.appointment.patient.id},vm.appointment, onSaveSuccess, onSaveError);
            } else {
                Appointment.save({patientId:vm.appointment.patient.id},vm.appointment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dentoCareApp:appointmentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.start = false;
        vm.datePickerOpenStatus.end = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

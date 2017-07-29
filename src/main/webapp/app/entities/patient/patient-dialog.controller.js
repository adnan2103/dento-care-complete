(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('PatientDialogController', PatientDialogController);

    PatientDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'Patient', 'Treatment', 'Appointment', 'ContactNumber', 'Email', 'Address'];

    function PatientDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, Patient, Treatment, Appointment, ContactNumber, Email, Address) {
        var vm = this;

        vm.patient = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.treatments = Treatment.query();
        vm.appointments = Appointment.query();
        vm.contactnumbers = ContactNumber.query();
        vm.emails = Email.query();
        vm.addresses = Address.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.patient.id !== null) {
                Patient.update(vm.patient, onSaveSuccess, onSaveError);
            } else {
                Patient.save(vm.patient, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dentoCareApp:patientUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setPhoto = function ($file, patient) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        patient.photo = base64Data;
                        patient.photoContentType = $file.type;
                    });
                });
            }
        };

    }
})();

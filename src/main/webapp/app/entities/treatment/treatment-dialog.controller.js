(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('TreatmentDialogController', TreatmentDialogController);

    TreatmentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Treatment', 'Patient', 'Notes', 'OralExamination', 'Payment', 'PreTreatmentImage', 'PostTreatmentImage'];

    function TreatmentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Treatment, Patient, Notes, OralExamination, Payment, PreTreatmentImage, PostTreatmentImage) {
        var vm = this;

        vm.treatment = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.patients = Patient.fetchAll();
        vm.notes = Notes.fetchAll();
        vm.oralexaminations = OralExamination.fetchAll();
        vm.payments = Payment.fetchAll();
        vm.pretreatmentimages = PreTreatmentImage.fetchAll();
        vm.posttreatmentimages = PostTreatmentImage.fetchAll();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.treatment.id !== null) {
                Treatment.update(vm.treatment, onSaveSuccess, onSaveError);
            } else {
                Treatment.save(vm.treatment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dentoCareApp:treatmentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.startDate = false;
        vm.datePickerOpenStatus.lastModifiedDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

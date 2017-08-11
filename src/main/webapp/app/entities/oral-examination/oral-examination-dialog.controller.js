(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('OralExaminationDialogController', OralExaminationDialogController);

    OralExaminationDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'OralExamination', 'Treatment'];

    function OralExaminationDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, OralExamination, Treatment) {
        var vm = this;

        vm.oralExamination = entity;
        vm.clear = clear;
        vm.save = save;
        vm.treatments = Treatment.fetchAll();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.oralExamination.id !== null) {
                OralExamination.update(vm.oralExamination, onSaveSuccess, onSaveError);
            } else {
                OralExamination.save(vm.oralExamination, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dentoCareApp:oralExaminationUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('EmailDialogController', EmailDialogController);

    EmailDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Email', 'Patient'];

    function EmailDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Email, Patient) {
        var vm = this;

        vm.email = entity;
        vm.clear = clear;
        vm.save = save;
        vm.patients = Patient.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.email.id !== null) {
                Email.update(vm.email, onSaveSuccess, onSaveError);
            } else {
                Email.save(vm.email, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dentoCareApp:emailUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('ContactNumberDialogController', ContactNumberDialogController);

    ContactNumberDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'ContactNumber', 'Patient'];

    function ContactNumberDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, ContactNumber, Patient) {
        var vm = this;

        vm.contactNumber = entity;
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
            if (vm.contactNumber.id !== null) {
                ContactNumber.update(vm.contactNumber, onSaveSuccess, onSaveError);
            } else {
                ContactNumber.save(vm.contactNumber, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dentoCareApp:contactNumberUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

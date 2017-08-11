(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('PaymentDialogController', PaymentDialogController);

    PaymentDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Payment', 'Treatment'];

    function PaymentDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Payment, Treatment) {
        var vm = this;

        vm.payment = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
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
            if (vm.payment.id !== null) {
                Payment.update(vm.payment, onSaveSuccess, onSaveError);
            } else {
                Payment.save(vm.payment, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dentoCareApp:paymentUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.paymentDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();

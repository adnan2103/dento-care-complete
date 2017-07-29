(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('TreatmentDeleteController',TreatmentDeleteController);

    TreatmentDeleteController.$inject = ['$uibModalInstance', 'entity', 'Treatment'];

    function TreatmentDeleteController($uibModalInstance, entity, Treatment) {
        var vm = this;

        vm.treatment = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Treatment.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('PreTreatmentImageDeleteController',PreTreatmentImageDeleteController);

    PreTreatmentImageDeleteController.$inject = ['$uibModalInstance', 'entity', 'PreTreatmentImage'];

    function PreTreatmentImageDeleteController($uibModalInstance, entity, PreTreatmentImage) {
        var vm = this;

        vm.preTreatmentImage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PreTreatmentImage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

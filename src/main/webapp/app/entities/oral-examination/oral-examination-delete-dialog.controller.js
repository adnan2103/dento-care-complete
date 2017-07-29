(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('OralExaminationDeleteController',OralExaminationDeleteController);

    OralExaminationDeleteController.$inject = ['$uibModalInstance', 'entity', 'OralExamination'];

    function OralExaminationDeleteController($uibModalInstance, entity, OralExamination) {
        var vm = this;

        vm.oralExamination = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            OralExamination.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

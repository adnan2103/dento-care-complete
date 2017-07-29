(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('PostTreatmentImageDeleteController',PostTreatmentImageDeleteController);

    PostTreatmentImageDeleteController.$inject = ['$uibModalInstance', 'entity', 'PostTreatmentImage'];

    function PostTreatmentImageDeleteController($uibModalInstance, entity, PostTreatmentImage) {
        var vm = this;

        vm.postTreatmentImage = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PostTreatmentImage.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

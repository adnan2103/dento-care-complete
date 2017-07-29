(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('ContactNumberDeleteController',ContactNumberDeleteController);

    ContactNumberDeleteController.$inject = ['$uibModalInstance', 'entity', 'ContactNumber'];

    function ContactNumberDeleteController($uibModalInstance, entity, ContactNumber) {
        var vm = this;

        vm.contactNumber = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            ContactNumber.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();

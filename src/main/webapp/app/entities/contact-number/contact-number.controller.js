(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('ContactNumberController', ContactNumberController);

    ContactNumberController.$inject = ['ContactNumber'];

    function ContactNumberController(ContactNumber) {

        var vm = this;

        vm.contactNumbers = [];

        loadAll();

        function loadAll() {
            ContactNumber.fetchAll(function(result) {
                vm.contactNumbers = result;
                vm.searchQuery = null;
            });
        }
    }
})();

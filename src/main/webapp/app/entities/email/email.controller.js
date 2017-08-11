(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('EmailController', EmailController);

    EmailController.$inject = ['Email'];

    function EmailController(Email) {

        var vm = this;

        vm.emails = [];

        loadAll();

        function loadAll() {
            Email.fetchAll(function(result) {
                vm.emails = result;
                vm.searchQuery = null;
            });
        }
    }
})();

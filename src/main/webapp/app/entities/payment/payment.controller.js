(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('PaymentController', PaymentController);

    PaymentController.$inject = ['Payment'];

    function PaymentController(Payment) {

        var vm = this;

        vm.payments = [];

        loadAll();

        function loadAll() {
            Payment.fetchAll(function(result) {
                vm.payments = result;
                vm.searchQuery = null;
            });
        }
    }
})();

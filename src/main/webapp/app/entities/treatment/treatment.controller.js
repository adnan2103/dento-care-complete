(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('TreatmentController', TreatmentController);

    TreatmentController.$inject = ['Treatment'];

    function TreatmentController(Treatment) {

        var vm = this;

        vm.treatments = [];

        loadAll();

        function loadAll() {
            Treatment.query(function(result) {
                vm.treatments = result;
                vm.searchQuery = null;
            });
        }
    }
})();

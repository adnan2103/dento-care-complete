(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('PatientController', PatientController);

    PatientController.$inject = ['DataUtils', 'Patient'];

    function PatientController(DataUtils, Patient) {

        var vm = this;

        vm.patients = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            Patient.query(function(result) {
                vm.patients = result;
                vm.searchQuery = null;
            });
        }
    }
})();

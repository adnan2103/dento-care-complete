(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('OralExaminationController', OralExaminationController);

    OralExaminationController.$inject = ['OralExamination'];

    function OralExaminationController(OralExamination) {

        var vm = this;

        vm.oralExaminations = [];

        loadAll();

        function loadAll() {
            OralExamination.query(function(result) {
                vm.oralExaminations = result;
                vm.searchQuery = null;
            });
        }
    }
})();

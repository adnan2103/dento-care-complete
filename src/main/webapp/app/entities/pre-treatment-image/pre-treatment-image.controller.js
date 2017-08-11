(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('PreTreatmentImageController', PreTreatmentImageController);

    PreTreatmentImageController.$inject = ['DataUtils', 'PreTreatmentImage'];

    function PreTreatmentImageController(DataUtils, PreTreatmentImage) {

        var vm = this;

        vm.preTreatmentImages = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            PreTreatmentImage.fetchAll(function(result) {
                vm.preTreatmentImages = result;
                vm.searchQuery = null;
            });
        }
    }
})();

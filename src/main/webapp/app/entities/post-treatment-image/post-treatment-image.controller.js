(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('PostTreatmentImageController', PostTreatmentImageController);

    PostTreatmentImageController.$inject = ['DataUtils', 'PostTreatmentImage'];

    function PostTreatmentImageController(DataUtils, PostTreatmentImage) {

        var vm = this;

        vm.postTreatmentImages = [];
        vm.openFile = DataUtils.openFile;
        vm.byteSize = DataUtils.byteSize;

        loadAll();

        function loadAll() {
            PostTreatmentImage.fetchAll(function(result) {
                vm.postTreatmentImages = result;
                vm.searchQuery = null;
            });
        }
    }
})();

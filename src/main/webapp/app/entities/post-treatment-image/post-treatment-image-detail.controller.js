(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('PostTreatmentImageDetailController', PostTreatmentImageDetailController);

    PostTreatmentImageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'PostTreatmentImage', 'Treatment'];

    function PostTreatmentImageDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, PostTreatmentImage, Treatment) {
        var vm = this;

        vm.postTreatmentImage = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('dentoCareApp:postTreatmentImageUpdate', function(event, result) {
            vm.postTreatmentImage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

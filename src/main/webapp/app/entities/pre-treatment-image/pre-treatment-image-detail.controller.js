(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('PreTreatmentImageDetailController', PreTreatmentImageDetailController);

    PreTreatmentImageDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'DataUtils', 'entity', 'PreTreatmentImage', 'Treatment'];

    function PreTreatmentImageDetailController($scope, $rootScope, $stateParams, previousState, DataUtils, entity, PreTreatmentImage, Treatment) {
        var vm = this;

        vm.preTreatmentImage = entity;
        vm.previousState = previousState.name;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;

        var unsubscribe = $rootScope.$on('dentoCareApp:preTreatmentImageUpdate', function(event, result) {
            vm.preTreatmentImage = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

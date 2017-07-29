(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('TreatmentDetailController', TreatmentDetailController);

    TreatmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Treatment', 'Patient', 'Notes', 'OralExamination', 'Payment', 'PreTreatmentImage', 'PostTreatmentImage'];

    function TreatmentDetailController($scope, $rootScope, $stateParams, previousState, entity, Treatment, Patient, Notes, OralExamination, Payment, PreTreatmentImage, PostTreatmentImage) {
        var vm = this;

        vm.treatment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dentoCareApp:treatmentUpdate', function(event, result) {
            vm.treatment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

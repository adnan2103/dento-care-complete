(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('OralExaminationDetailController', OralExaminationDetailController);

    OralExaminationDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OralExamination', 'Treatment'];

    function OralExaminationDetailController($scope, $rootScope, $stateParams, previousState, entity, OralExamination, Treatment) {
        var vm = this;

        vm.oralExamination = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dentoCareApp:oralExaminationUpdate', function(event, result) {
            vm.oralExamination = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

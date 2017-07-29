(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('EmailDetailController', EmailDetailController);

    EmailDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Email', 'Patient'];

    function EmailDetailController($scope, $rootScope, $stateParams, previousState, entity, Email, Patient) {
        var vm = this;

        vm.email = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dentoCareApp:emailUpdate', function(event, result) {
            vm.email = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

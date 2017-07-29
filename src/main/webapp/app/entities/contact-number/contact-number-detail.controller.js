(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('ContactNumberDetailController', ContactNumberDetailController);

    ContactNumberDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'ContactNumber', 'Patient'];

    function ContactNumberDetailController($scope, $rootScope, $stateParams, previousState, entity, ContactNumber, Patient) {
        var vm = this;

        vm.contactNumber = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dentoCareApp:contactNumberUpdate', function(event, result) {
            vm.contactNumber = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

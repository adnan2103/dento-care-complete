(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('AppointmentDetailController', AppointmentDetailController);

    AppointmentDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Appointment', 'Patient'];

    function AppointmentDetailController($scope, $rootScope, $stateParams, previousState, entity, Appointment, Patient) {
        var vm = this;

        vm.appointment = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dentoCareApp:appointmentUpdate', function(event, result) {
            vm.appointment = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

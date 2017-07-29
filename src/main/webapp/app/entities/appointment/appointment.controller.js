(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('AppointmentController', AppointmentController);

    AppointmentController.$inject = ['Appointment'];

    function AppointmentController(Appointment) {

        var vm = this;

        vm.appointments = [];

        loadAll();

        function loadAll() {
            Appointment.query(function(result) {
                vm.appointments = result;
                vm.searchQuery = null;
            });
        }
    }
})();

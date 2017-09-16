(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('AppointmentController', AppointmentController);

    AppointmentController.$inject = ['Appointment', 'uiCalendarConfig'];

    function AppointmentController(Appointment, uiCalendarConfig) {

        var vm = this;

        vm.appointments = [];

        vm.eventSources = [];

        vm.calendarConfig = {
            height: 450,
            timeFormat: 'h(:mm)t',
            defaultView: 'month',
            businessHours: {
                start: '09:00',
                end: '21:00',
                dow: [1, 2, 3, 4, 5, 6]
            },
            editable: true,
            selectOverlap: false,

            eventOverlap: false,
            header: {
                left: 'prev,next, today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            events: vm.appointments
        };
        loadAll();

        function loadAll() {
            Appointment.fetchAll(function(result) {
                vm.appointments = result;
                vm.eventSources.push(result);
                vm.searchQuery = null;
            });
        }
    }
})();

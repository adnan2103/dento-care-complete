(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('AppointmentController', AppointmentController);

    AppointmentController.$inject = ['Appointment', 'uiCalendarConfig'];

    function AppointmentController(Appointment) {

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
            selectable:true,
            selectHelper:true,
            selectOverlap: false,
            eventOverlap: false,

            header: {
                left: 'prev,next, today',
                center: 'title',
                right: 'month,agendaWeek,agendaDay'
            },
            eventDrop: function (event, delta, revertFunc) {

                if (!confirm("Are you sure to set new appointment from " + event.start.format('LLLL') + " to " + event.end.format('LLLL'))) {
                    revertFunc();
                    return;
                }
                /*var appointment = $scope.findById($scope.appointments, event.id);
                appointment['start'] = event.start.format();
                appointment['end'] = event.end.format();

                $scope.saveAppointment(appointment);*/
            },
            eventResize: function (event, delta, revertFunc) {

                if (!confirm("Are you sure to set appointment from " + event.start.format('LLLL') + " to " + event.end.format('LLLL'))) {
                    revertFunc();
                    return;
                }
                /*var appointment = vm.findById($scope.appointments, event.id);
                appointment['start'] = event.start.format();
                appointment['end'] = event.end.format();

                $scope.saveAppointment(appointment);*/
            },
            select: function (start, end, jsEvent, view) {

                var treatmentPlan = prompt("New appointment is being set from " + start.format('LLLL') + " to " + end.format('LLLL') +"\n Enter appointment description.");
                if(treatmentPlan == null) {
                    return;
                }
                /*var events = new Array();
                var event = new Object();
                event.title = treatmentPlan;
                event.start = start;
                event.end = end;
                event.allDay = false;
                event.url = '#/patient/'+$routeParams.id+'/treatment';
                events.push(event);
                //uiCalendarConfig.calendars[calendar].fullCalendar('addEventSource', events);

                var appointment = {
                    "id": null,
                    "patientId": $routeParams.id,
                    "doctorId": null,
                    "start": start,
                    "end": end,
                    "plannedTreatment": treatmentPlan
                }
                $scope.saveAppointment(appointment);*/

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

        /*$scope.saveAppointment = function (appointment) {
            var promise = appointmentService.save(appointment);

            promise.then(function (response) {
                alert('Appointment Set Successfully.');
            }, function (error) {
                alert(error);
            }, function (update) {
                alert(update);
            });
        };

        vm.findById = function (source, id) {
            for (var i = 0; i < source.length; i++) {
                if (source[i].id === id) {
                    return source[i];
                }
            }
            throw "Couldn't find object with id: " + id;
        }*/
    }
})();

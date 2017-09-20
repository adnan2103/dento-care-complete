(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('Appointment', Appointment);

    Appointment.$inject = ['$resource'];

    function Appointment ($resource) {

        return $resource('', {}, {
            'fetchAll': {
                method: 'GET',
                isArray: true,
                url: 'api/appointments'
            },
            'fetchPatientAllAppointments': {
                method: 'GET',
                url: 'api/patients/:patientId/appointments',
                params: {patientId: '@patientId'}
            },
            'save':{
                method: 'POST',
                url: 'api/patients/:patientId/appointments',
                params: {patientId: '@patientId'}
            },
            'get': {
                method: 'GET',
                url: 'api/appointments/:id',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {
                method:'PUT',
                url: 'api/patients/:patientId/appointments',
                params: {patientId: '@patientId'}
            },
            'delete': {
                method: 'DELETE',
                url: 'api/appointments/:id'
            }
        });
    }
})();

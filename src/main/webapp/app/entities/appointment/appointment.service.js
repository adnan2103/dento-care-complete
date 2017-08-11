(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('Appointment', Appointment);

    Appointment.$inject = ['$resource', 'DateUtils'];

    function Appointment ($resource, DateUtils) {

        return $resource('', {}, {
            'fetchAll': {
                method: 'GET',
                isArray: true,
                url: 'api/patients/1/appointments'
            },
            'save':{
                method: 'POST',
                url: 'api/patients/1/appointments/:id'
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
                url: 'api/patients/1/appointments/:id'
            },
            'delete': {
                method: 'DELETE',
                url: 'api/appointments/:id'
            }
        });
    }
})();

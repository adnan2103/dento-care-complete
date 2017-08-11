(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('Treatment', Treatment);

    Treatment.$inject = ['$resource', 'DateUtils'];

    function Treatment ($resource, DateUtils) {

        return $resource('', {}, {
            'fetchAll': {
                method: 'GET',
                isArray: true,
                url: 'api/patients/1/treatments'
            },
            'save':{
                method: 'POST',
                url: 'api/patients/1/treatments/:id'
            },
            'get': {
                method: 'GET',
                url: 'api/treatments/:id',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {
                method:'PUT',
                url: 'api/patients/1/treatments/:id'
            },
            'delete': {
                method: 'DELETE',
                url: 'api/treatments/:id'
            }
        });
    }
})();

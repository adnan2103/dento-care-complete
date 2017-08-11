(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('Email', Email);

    Email.$inject = ['$resource'];

    function Email ($resource) {

        return $resource('', {}, {
            'fetchAll': {
                method: 'GET',
                isArray: true,
                url: 'api/patients/1/emails'
            },
            'save':{
                method: 'POST',
                url: 'api/patients/1/emails/:id'
            },
            'get': {
                method: 'GET',
                url: 'api/emails/:id',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {
                method:'PUT',
                url: 'api/patients/1/emails/:id'
            },
            'delete': {
                method: 'DELETE',
                url: 'api/emails/:id'
            }
        });
    }
})();

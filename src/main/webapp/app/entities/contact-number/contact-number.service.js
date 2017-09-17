(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('ContactNumber', ContactNumber);

    ContactNumber.$inject = ['$resource'];

    function ContactNumber ($resource) {

        return $resource('', {}, {
            'fetchAll': {
                method: 'GET',
                isArray: true,
                url: 'api/patients/1/contact-numbers'
            },
            'save':{
                method: 'POST',
                url: 'api/patients/1/contact-numbers/:id'
            },
            'get': {
                method: 'GET',
                url: 'api/contact-numbers/:id',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {
                method:'PUT',
                url: 'api/patients/1/contact-numbers/:id'
            },
            'delete': {
                method: 'DELETE',
                url: 'api/contact-numbers/:id'
            }
        });
    }
})();

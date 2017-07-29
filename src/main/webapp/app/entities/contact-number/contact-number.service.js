(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('ContactNumber', ContactNumber);

    ContactNumber.$inject = ['$resource'];

    function ContactNumber ($resource) {
        var resourceUrl =  'api/contact-numbers/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

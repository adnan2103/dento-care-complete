(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('Patient', Patient);

    Patient.$inject = ['$resource'];

    function Patient ($resource) {
        var resourceUrl =  'api/patients/:id';

        return $resource(resourceUrl, {}, {
            'fetchAll': { method: 'GET', isArray: true},
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

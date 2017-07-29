(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('Treatment', Treatment);

    Treatment.$inject = ['$resource', 'DateUtils'];

    function Treatment ($resource, DateUtils) {
        var resourceUrl =  'api/treatments/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                        data.startDate = DateUtils.convertDateTimeFromServer(data.startDate);
                        data.lastModifiedDate = DateUtils.convertDateTimeFromServer(data.lastModifiedDate);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();

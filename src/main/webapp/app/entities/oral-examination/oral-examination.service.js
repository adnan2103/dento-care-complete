(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('OralExamination', OralExamination);

    OralExamination.$inject = ['$resource'];

    function OralExamination ($resource) {
        var resourceUrl =  'api/oral-examinations/:id';

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

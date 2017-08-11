(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('PreTreatmentImage', PreTreatmentImage);

    PreTreatmentImage.$inject = ['$resource'];

    function PreTreatmentImage ($resource) {
        var resourceUrl =  'api/treatments/1/pre-treatment-images/:id';

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

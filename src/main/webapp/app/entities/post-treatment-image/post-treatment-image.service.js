(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('PostTreatmentImage', PostTreatmentImage);

    PostTreatmentImage.$inject = ['$resource'];

    function PostTreatmentImage ($resource) {
        var resourceUrl =  'api/post-treatment-images/:id';

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

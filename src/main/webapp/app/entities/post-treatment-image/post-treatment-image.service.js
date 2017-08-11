(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('PostTreatmentImage', PostTreatmentImage);

    PostTreatmentImage.$inject = ['$resource'];

    function PostTreatmentImage ($resource) {

        return $resource('', {}, {
            'fetchAll': {
                method: 'GET',
                isArray: true,
                url: 'api/treatments/1/post-treatment-images'
            },
            'save':{
                method: 'POST',
                url: 'api/treatments/1/post-treatment-images/:id'
            },
            'get': {
                method: 'GET',
                url: 'api/post-treatment-images/:id',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {
                method:'PUT',
                url: 'api/treatments/1/post-treatment-images/:id'
            },
            'delete': {
                method: 'DELETE',
                url: 'api/post-treatment-images/:id'
            }
        });
    }
})();

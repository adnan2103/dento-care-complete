(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('PreTreatmentImage', PreTreatmentImage);

    PreTreatmentImage.$inject = ['$resource'];

    function PreTreatmentImage ($resource) {

        return $resource('', {}, {
            'fetchAll': {
                method: 'GET',
                isArray: true,
                url: 'api/treatments/1/pre-treatment-images'
            },
            'save':{
                method: 'POST',
                url: 'api/treatments/1/pre-treatment-images/:id'
            },
            'get': {
                method: 'GET',
                url: 'api/pre-treatment-images/:id',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {
                method:'PUT',
                url: 'api/treatments/1/pre-treatment-images/:id'
            },
            'delete': {
                method: 'DELETE',
                url: 'api/pre-treatment-images/:id'
            }
        });
    }
})();

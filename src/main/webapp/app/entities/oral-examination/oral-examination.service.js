(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('OralExamination', OralExamination);

    OralExamination.$inject = ['$resource'];

    function OralExamination ($resource) {

        return $resource('', {}, {
            'fetchAll': {
                method: 'GET',
                isArray: true,
                url: 'api/treatments/1/oral-examinations'
            },
            'save':{
                method: 'POST',
                url: 'api/treatments/1/oral-examinations/:id'
            },
            'get': {
                method: 'GET',
                url: 'api/oral-examinations/:id',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {
                method:'PUT',
                url: 'api/treatments/1/oral-examinations/:id'
            },
            'delete': {
                method: 'DELETE',
                url: 'api/oral-examinations/:id'
            }
        });
    }
})();

(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('Payment', Payment);

    Payment.$inject = ['$resource', 'DateUtils'];

    function Payment ($resource, DateUtils) {

        return $resource('', {}, {
            'fetchAll': {
                method: 'GET',
                isArray: true,
                url: 'api/treatments/1/payments'
            },
            'save':{
                method: 'POST',
                url: 'api/treatments/1/payments/:id'
            },
            'get': {
                method: 'GET',
                url: 'api/payments/:id',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {
                method:'PUT',
                url: 'api/treatments/1/payments/:id'
            },
            'delete': {
                method: 'DELETE',
                url: 'api/payments/:id'
            }
        });
    }
})();

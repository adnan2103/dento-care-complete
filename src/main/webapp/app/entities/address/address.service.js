(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('Address', Address);

    Address.$inject = ['$resource'];

    function Address ($resource) {


        return $resource('', {}, {
            'fetchAll': {
                method: 'GET',
                isArray: true,
                url: 'api/patients/1/addresses'
            },
            'save':{
                method: 'POST',
                url: 'api/patients/1/addresses/:id'
            },
            'get': {
                method: 'GET',
                url: 'api/addresses/:id',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {
                method:'PUT',
                url: 'api/patients/1/addresses/:id'
            },
            'delete': {
                method: 'DELETE',
                url: 'api/addresses/:id'
            }
        });
    }
})();

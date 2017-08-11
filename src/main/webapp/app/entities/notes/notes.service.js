(function() {
    'use strict';
    angular
        .module('dentoCareApp')
        .factory('Notes', Notes);

    Notes.$inject = ['$resource'];

    function Notes ($resource) {

        return $resource('', {}, {
            'fetchAll': {
                method: 'GET',
                isArray: true,
                url: 'api/treatments/1/notes'
            },
            'save':{
                method: 'POST',
                url: 'api/treatments/1/notes/:id'
            },
            'get': {
                method: 'GET',
                url: 'api/notes/:id',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': {
                method:'PUT',
                url: 'api/treatments/1/notes/:id'
            },
            'delete': {
                method: 'DELETE',
                url: 'api/notes/:id'
            }
        });
    }
})();

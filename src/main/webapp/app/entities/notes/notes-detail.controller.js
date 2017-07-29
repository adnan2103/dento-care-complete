(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('NotesDetailController', NotesDetailController);

    NotesDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Notes', 'Treatment'];

    function NotesDetailController($scope, $rootScope, $stateParams, previousState, entity, Notes, Treatment) {
        var vm = this;

        vm.notes = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('dentoCareApp:notesUpdate', function(event, result) {
            vm.notes = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();

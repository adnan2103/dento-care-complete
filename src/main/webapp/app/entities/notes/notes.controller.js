(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('NotesController', NotesController);

    NotesController.$inject = ['Notes'];

    function NotesController(Notes) {

        var vm = this;

        vm.notes = [];

        loadAll();

        function loadAll() {
            Notes.query(function(result) {
                vm.notes = result;
                vm.searchQuery = null;
            });
        }
    }
})();

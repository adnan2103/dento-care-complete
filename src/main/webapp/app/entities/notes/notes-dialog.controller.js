(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('NotesDialogController', NotesDialogController);

    NotesDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Notes', 'Treatment'];

    function NotesDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Notes, Treatment) {
        var vm = this;

        vm.notes = entity;
        vm.clear = clear;
        vm.save = save;
        vm.treatments = Treatment.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.notes.id !== null) {
                Notes.update(vm.notes, onSaveSuccess, onSaveError);
            } else {
                Notes.save(vm.notes, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dentoCareApp:notesUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();

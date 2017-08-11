(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('PreTreatmentImageDialogController', PreTreatmentImageDialogController);

    PreTreatmentImageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'PreTreatmentImage', 'Treatment'];

    function PreTreatmentImageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, PreTreatmentImage, Treatment) {
        var vm = this;

        vm.preTreatmentImage = entity;
        vm.clear = clear;
        vm.byteSize = DataUtils.byteSize;
        vm.openFile = DataUtils.openFile;
        vm.save = save;
        vm.treatments = Treatment.fetchAll();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.preTreatmentImage.id !== null) {
                PreTreatmentImage.update(vm.preTreatmentImage, onSaveSuccess, onSaveError);
            } else {
                PreTreatmentImage.save(vm.preTreatmentImage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dentoCareApp:preTreatmentImageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImage = function ($file, preTreatmentImage) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        preTreatmentImage.image = base64Data;
                        preTreatmentImage.imageContentType = $file.type;
                    });
                });
            }
        };

    }
})();

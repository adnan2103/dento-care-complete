(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .controller('PostTreatmentImageDialogController', PostTreatmentImageDialogController);

    PostTreatmentImageDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'DataUtils', 'entity', 'PostTreatmentImage', 'Treatment'];

    function PostTreatmentImageDialogController ($timeout, $scope, $stateParams, $uibModalInstance, DataUtils, entity, PostTreatmentImage, Treatment) {
        var vm = this;

        vm.postTreatmentImage = entity;
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
            if (vm.postTreatmentImage.id !== null) {
                PostTreatmentImage.update(vm.postTreatmentImage, onSaveSuccess, onSaveError);
            } else {
                PostTreatmentImage.save(vm.postTreatmentImage, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('dentoCareApp:postTreatmentImageUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


        vm.setImage = function ($file, postTreatmentImage) {
            if ($file && $file.$error === 'pattern') {
                return;
            }
            if ($file) {
                DataUtils.toBase64($file, function(base64Data) {
                    $scope.$apply(function() {
                        postTreatmentImage.image = base64Data;
                        postTreatmentImage.imageContentType = $file.type;
                    });
                });
            }
        };

    }
})();

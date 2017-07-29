(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('pre-treatment-image', {
            parent: 'entity',
            url: '/pre-treatment-image',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dentoCareApp.preTreatmentImage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pre-treatment-image/pre-treatment-images.html',
                    controller: 'PreTreatmentImageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('preTreatmentImage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('pre-treatment-image-detail', {
            parent: 'pre-treatment-image',
            url: '/pre-treatment-image/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dentoCareApp.preTreatmentImage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/pre-treatment-image/pre-treatment-image-detail.html',
                    controller: 'PreTreatmentImageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('preTreatmentImage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PreTreatmentImage', function($stateParams, PreTreatmentImage) {
                    return PreTreatmentImage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'pre-treatment-image',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('pre-treatment-image-detail.edit', {
            parent: 'pre-treatment-image-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pre-treatment-image/pre-treatment-image-dialog.html',
                    controller: 'PreTreatmentImageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PreTreatmentImage', function(PreTreatmentImage) {
                            return PreTreatmentImage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pre-treatment-image.new', {
            parent: 'pre-treatment-image',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pre-treatment-image/pre-treatment-image-dialog.html',
                    controller: 'PreTreatmentImageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                image: null,
                                imageContentType: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('pre-treatment-image', null, { reload: 'pre-treatment-image' });
                }, function() {
                    $state.go('pre-treatment-image');
                });
            }]
        })
        .state('pre-treatment-image.edit', {
            parent: 'pre-treatment-image',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pre-treatment-image/pre-treatment-image-dialog.html',
                    controller: 'PreTreatmentImageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PreTreatmentImage', function(PreTreatmentImage) {
                            return PreTreatmentImage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pre-treatment-image', null, { reload: 'pre-treatment-image' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('pre-treatment-image.delete', {
            parent: 'pre-treatment-image',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/pre-treatment-image/pre-treatment-image-delete-dialog.html',
                    controller: 'PreTreatmentImageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PreTreatmentImage', function(PreTreatmentImage) {
                            return PreTreatmentImage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('pre-treatment-image', null, { reload: 'pre-treatment-image' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

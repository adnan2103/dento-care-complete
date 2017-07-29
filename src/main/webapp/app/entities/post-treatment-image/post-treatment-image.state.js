(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('post-treatment-image', {
            parent: 'entity',
            url: '/post-treatment-image',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dentoCareApp.postTreatmentImage.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/post-treatment-image/post-treatment-images.html',
                    controller: 'PostTreatmentImageController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('postTreatmentImage');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('post-treatment-image-detail', {
            parent: 'post-treatment-image',
            url: '/post-treatment-image/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dentoCareApp.postTreatmentImage.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/post-treatment-image/post-treatment-image-detail.html',
                    controller: 'PostTreatmentImageDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('postTreatmentImage');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'PostTreatmentImage', function($stateParams, PostTreatmentImage) {
                    return PostTreatmentImage.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'post-treatment-image',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('post-treatment-image-detail.edit', {
            parent: 'post-treatment-image-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/post-treatment-image/post-treatment-image-dialog.html',
                    controller: 'PostTreatmentImageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PostTreatmentImage', function(PostTreatmentImage) {
                            return PostTreatmentImage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('post-treatment-image.new', {
            parent: 'post-treatment-image',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/post-treatment-image/post-treatment-image-dialog.html',
                    controller: 'PostTreatmentImageDialogController',
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
                    $state.go('post-treatment-image', null, { reload: 'post-treatment-image' });
                }, function() {
                    $state.go('post-treatment-image');
                });
            }]
        })
        .state('post-treatment-image.edit', {
            parent: 'post-treatment-image',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/post-treatment-image/post-treatment-image-dialog.html',
                    controller: 'PostTreatmentImageDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['PostTreatmentImage', function(PostTreatmentImage) {
                            return PostTreatmentImage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('post-treatment-image', null, { reload: 'post-treatment-image' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('post-treatment-image.delete', {
            parent: 'post-treatment-image',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/post-treatment-image/post-treatment-image-delete-dialog.html',
                    controller: 'PostTreatmentImageDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['PostTreatmentImage', function(PostTreatmentImage) {
                            return PostTreatmentImage.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('post-treatment-image', null, { reload: 'post-treatment-image' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

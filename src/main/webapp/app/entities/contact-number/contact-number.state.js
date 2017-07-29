(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('contact-number', {
            parent: 'entity',
            url: '/contact-number',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dentoCareApp.contactNumber.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contact-number/contact-numbers.html',
                    controller: 'ContactNumberController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contactNumber');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('contact-number-detail', {
            parent: 'contact-number',
            url: '/contact-number/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dentoCareApp.contactNumber.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/contact-number/contact-number-detail.html',
                    controller: 'ContactNumberDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('contactNumber');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'ContactNumber', function($stateParams, ContactNumber) {
                    return ContactNumber.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'contact-number',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('contact-number-detail.edit', {
            parent: 'contact-number-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contact-number/contact-number-dialog.html',
                    controller: 'ContactNumberDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContactNumber', function(ContactNumber) {
                            return ContactNumber.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contact-number.new', {
            parent: 'contact-number',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contact-number/contact-number-dialog.html',
                    controller: 'ContactNumberDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                contactNumebr: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('contact-number', null, { reload: 'contact-number' });
                }, function() {
                    $state.go('contact-number');
                });
            }]
        })
        .state('contact-number.edit', {
            parent: 'contact-number',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contact-number/contact-number-dialog.html',
                    controller: 'ContactNumberDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['ContactNumber', function(ContactNumber) {
                            return ContactNumber.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contact-number', null, { reload: 'contact-number' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('contact-number.delete', {
            parent: 'contact-number',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/contact-number/contact-number-delete-dialog.html',
                    controller: 'ContactNumberDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['ContactNumber', function(ContactNumber) {
                            return ContactNumber.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('contact-number', null, { reload: 'contact-number' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

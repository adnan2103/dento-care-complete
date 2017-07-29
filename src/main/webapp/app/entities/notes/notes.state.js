(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('notes', {
            parent: 'entity',
            url: '/notes',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dentoCareApp.notes.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/notes/notes.html',
                    controller: 'NotesController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('notes');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('notes-detail', {
            parent: 'notes',
            url: '/notes/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dentoCareApp.notes.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/notes/notes-detail.html',
                    controller: 'NotesDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('notes');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Notes', function($stateParams, Notes) {
                    return Notes.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'notes',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('notes-detail.edit', {
            parent: 'notes-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/notes/notes-dialog.html',
                    controller: 'NotesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Notes', function(Notes) {
                            return Notes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('notes.new', {
            parent: 'notes',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/notes/notes-dialog.html',
                    controller: 'NotesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                note: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('notes', null, { reload: 'notes' });
                }, function() {
                    $state.go('notes');
                });
            }]
        })
        .state('notes.edit', {
            parent: 'notes',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/notes/notes-dialog.html',
                    controller: 'NotesDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Notes', function(Notes) {
                            return Notes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('notes', null, { reload: 'notes' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('notes.delete', {
            parent: 'notes',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/notes/notes-delete-dialog.html',
                    controller: 'NotesDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Notes', function(Notes) {
                            return Notes.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('notes', null, { reload: 'notes' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

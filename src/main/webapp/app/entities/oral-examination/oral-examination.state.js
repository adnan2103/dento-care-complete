(function() {
    'use strict';

    angular
        .module('dentoCareApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('oral-examination', {
            parent: 'entity',
            url: '/oral-examination',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dentoCareApp.oralExamination.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/oral-examination/oral-examinations.html',
                    controller: 'OralExaminationController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('oralExamination');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('oral-examination-detail', {
            parent: 'oral-examination',
            url: '/oral-examination/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'dentoCareApp.oralExamination.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/oral-examination/oral-examination-detail.html',
                    controller: 'OralExaminationDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('oralExamination');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'OralExamination', function($stateParams, OralExamination) {
                    return OralExamination.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'oral-examination',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('oral-examination-detail.edit', {
            parent: 'oral-examination-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oral-examination/oral-examination-dialog.html',
                    controller: 'OralExaminationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OralExamination', function(OralExamination) {
                            return OralExamination.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('oral-examination.new', {
            parent: 'oral-examination',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oral-examination/oral-examination-dialog.html',
                    controller: 'OralExaminationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                cost: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('oral-examination', null, { reload: 'oral-examination' });
                }, function() {
                    $state.go('oral-examination');
                });
            }]
        })
        .state('oral-examination.edit', {
            parent: 'oral-examination',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oral-examination/oral-examination-dialog.html',
                    controller: 'OralExaminationDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OralExamination', function(OralExamination) {
                            return OralExamination.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('oral-examination', null, { reload: 'oral-examination' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('oral-examination.delete', {
            parent: 'oral-examination',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/oral-examination/oral-examination-delete-dialog.html',
                    controller: 'OralExaminationDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OralExamination', function(OralExamination) {
                            return OralExamination.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('oral-examination', null, { reload: 'oral-examination' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();

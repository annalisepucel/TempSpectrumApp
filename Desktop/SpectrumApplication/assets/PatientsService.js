'use strict';

myApp.factory('PatientsService', ['$http', function ($http) {

    var urlBase = 'http://35.9.22.233:81/Api';
    var PatientsService = {};
    PatientsService.getPatients = function () {
        return $http.get(urlBase + '/patients');
    };

    return PatientsService;
}]);


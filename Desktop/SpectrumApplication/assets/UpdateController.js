'use strict';

var myApp = angular.module('myApp',[]);

myApp.controller('UpdateController', function ($scope, $filter, PatientsService) {

        getPatients();

        function getPatients() {
            PatientsService.getPatients()
                .success(function (patients) {
                    $scope.patients = patients;

                })
                .error(function (error) {
                    $scope.status = 'Unable to load patient data: ' + error.message;

                });
        }

        function getParameterByName(name, url) {
          if (!url) url = window.location.href;
          name = name.replace(/[\[\]]/g, "\\$&");
          var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"),
            results = regex.exec(url);
          if (!results) return null;
          if (!results[2]) return '';
          return decodeURIComponent(results[2].replace(/\+/g, " "));
        }

        $scope.currentId1 = getParameterByName('id');


});





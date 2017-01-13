var app = angular.module('app',[]);
app.controller('passwordCtrl',function($scope){
    $scope.password = '';
    $scope.confirmPassword = '';
    $scope.passwordStrength = 'weak';
    $scope.color = 'red';
    $scope.checkStrength = function () {
        if ($scope.password.length<8) {
            $scope.passwordStrength = 'weak'; $scope.color = 'red';
        } else if ($scope.password.length>=8 && $scope.password.length<12){
            $scope.passwordStrength = 'middle'; $scope.color = 'orange';
        } else { $scope.passwordStrength = 'strong'; $scope.color = 'green';
        }
    }
});
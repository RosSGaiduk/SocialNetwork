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

app.controller('emailCheck',['$scope', '$http', function($scope, $http){
    $scope.message = "Empty email";
    $scope.email = "";
    $scope.color = "orangered";
    
    $scope.checkingEmail = function () {
        if ($scope.email.length==0){
            $scope.message = "Empty email";
            $scope.color = "orangered";
            return;
        } else {
            $http.get("/angularFindEmail", {
                params: {
                    email: $scope.email
                },
                dataType: "json",
            }).success(function (data) {
                $scope.message = data.message;
                $scope.color = data.color;
            });
        }
    }
}]);

app.controller('myCtrl',function($scope){
    $scope.go = "Go";
});

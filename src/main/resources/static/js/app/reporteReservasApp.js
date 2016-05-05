//angular.module('BebidaApp', [])
//        .controller('RegistrarBebidaCtrl',RegistrarBebidaCtrl );


function ReportesReservasCtrl($scope, $http, $log) {
    $scope.reservas = [];

    $scope.horaDesde = new Date();
    $scope.horaDesde.setMinutes(0);
    $scope.horaHasta = new Date();
    $scope.horaHasta.setMinutes(0);

    $scope.totalItems = 0;
    $scope.currentPage = 1;
    $scope.itemsPerPage = 20;
    $scope.maxSize = 10;

    $scope.hstep = 1;
    $scope.mstep = 10;

    $scope.buscarReservas = function () {

        var horaDesde = $scope.horaDesde.getHours() + ":" + $scope.horaDesde.getMinutes() + ":00";
        var horaHasta = $scope.horaHasta.getHours() + ":" + $scope.horaHasta.getMinutes() + ":00";
        if (!$scope.filtrarTurnos) {
            horaDesde = "00:00:00";
            horaHasta = "23:59:50";
        }
        var filtro = {
            fechaDesde: $scope.fechaDesde,
            horaDesde: horaDesde,
            fechaHasta: $scope.fechaHasta,
            horaHasta: horaHasta
        };
        var pagination = "?p=" + $scope.currentPage + "&ipp=" + $scope.itemsPerPage;
        if ($scope.fechaDesde !== undefined && $scope.fechaHasta !== undefined) {
            $http.post("/reserva/reservas" + pagination, filtro).success(function (data, status, headers, config) {
                $scope.reservas = data.items;
                $scope.totalItems = data.totalItems;
            }).error(function (data, status, headers, config) {
                $log.error(status + " " + data);
            });
        }
    };

    //$scope.buscarReservas();
}





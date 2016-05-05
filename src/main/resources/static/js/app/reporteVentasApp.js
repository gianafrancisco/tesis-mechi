//angular.module('BebidaApp', [])
//        .controller('RegistrarBebidaCtrl',RegistrarBebidaCtrl );


function ReportesVentasCtrl($scope, $http, $log) {
    $scope.pedidos = [];
    $scope.mozos = {};
    function initDataset() {

        $scope.mozosDatos = {
            labels: [],
            series: [],
            data: [[]]
        };
        $scope.platosDatos = {
            labels: [],
            series: ["Cantidad"],
            data: [[]]
        };
        $scope.bebidasDatos = {
            labels: [],
            series: ["Cantidad"],
            data: [[]]
        };
        $scope.promocionesDatos = {
            labels: [],
            series: ["Cantidad"],
            data: [[]]
        };

        $scope.platosVentasDatos = {
            labels: [],
            series: ["Ventas"],
            data: [[]]
        };
        $scope.bebidasVentasDatos = {
            labels: [],
            series: ["Ventas"],
            data: [[]]
        };
        $scope.promocionesVentasDatos = {
            labels: [],
            series: ["Ventas"],
            data: [[]]
        };
    }
    $scope.generarReporte = function () {

        initDataset();

        var filtro = {
            fechaDesde: $scope.fechaDesde,
            fechaHasta: $scope.fechaHasta,
            idMozo: ($scope.mozo === undefined || $scope.mozo === null) ? "" : $scope.mozo.idUsuario
        };
        if ($scope.fechaDesde !== undefined && $scope.fechaHasta !== undefined) {
            $http.post("/pedido/pedidos", filtro).success(function (data, status, headers, config) {
                $scope.pedidos = data;
                $scope.procesarDatos($scope.pedidos);
            }).error(function (data, status, headers, config) {
                $log.error(status + " " + data);
            });
        }
    };
    function pedidos(element) {
        var index = $scope.mozosDatos.labels.indexOf(element.usuario.usuario);
        if (index !== -1) {
            $scope.mozosDatos.data[0][index]++;
        } else {
            $scope.mozosDatos.labels.push(element.usuario.usuario);
            $scope.mozosDatos.data[0].push(1);
        }
        element.platos.forEach(platos);
        element.bebidas.forEach(bebidas);
        element.promociones.forEach(promociones);
    }
    ;

    function platos(element) {
        var index = $scope.platosDatos.labels.indexOf(element.descripcion);
        if (index !== -1) {
            $scope.platosDatos.data[0][index]++;
            $scope.platosVentasDatos.data[0][index] = element.precio * $scope.platosDatos.data[0][index];
        } else {
            $scope.platosDatos.labels.push(element.descripcion);
            $scope.platosDatos.data[0].push(1);
            $scope.platosVentasDatos.labels.push(element.descripcion);
            $scope.platosVentasDatos.data[0].push(element.precio);

        }
    };
    
    function bebidas(element) {
        var nombre = element.nombre;
        var index = $scope.bebidasDatos.labels.indexOf(nombre);
        if (index !== -1) {
            $scope.bebidasDatos.data[0][index]++;
            $scope.bebidasVentasDatos.data[0][index] = element.precio * $scope.bebidasDatos.data[0][index];
        } else {
            $scope.bebidasDatos.labels.push(nombre);
            $scope.bebidasDatos.data[0].push(1);
            $scope.bebidasVentasDatos.labels.push(nombre);
            $scope.bebidasVentasDatos.data[0].push(element.precio);
        }
    }
    ;
    function promociones(element) {
        var index = $scope.promocionesDatos.labels.indexOf(element.descripcion);
        if (index !== -1) {
            $scope.promocionesDatos.data[0][index]++;
            $scope.promocionesVentasDatos.data[0][index] = element.precio * $scope.promocionesDatos.data[0][index];
        } else {
            $scope.promocionesDatos.labels.push(element.descripcion);
            $scope.promocionesDatos.data[0].push(1);
            $scope.promocionesVentasDatos.labels.push(element.descripcion);
            $scope.promocionesVentasDatos.data[0].push(element.precio);

        }
        element.platos.forEach(platos);
        element.bebidas.forEach(bebidas);
    }
    ;

    $scope.procesarDatos = function (p) {
        p.forEach(pedidos);
    };

    $scope.buscarMozos = function () {
        $http.get('/mozo/mozos').success(function (data, status, headers, config) {
            $scope.mozos = data.usuarios;
        });
    };

    $scope.getNombreMozo = function (mozo) {
        return mozo.apellido + ", " + mozo.nombre;
    };

    $scope.buscarMozos();

}





//angular.module('BebidaApp', [])
//        .controller('RegistrarBebidaCtrl',RegistrarBebidaCtrl );


function AsignacionCtrl($scope, $http, $log) {

    $scope.messajes = {
        agregar: "La bebida ha sido creada correctamente.",
        modificar: "La bebida ha sido modificada correctamente."
    };
    $scope.mensaje = "";
    $scope.mozos = [];
    $scope.mesas = [];
    $scope.asignaciones = [];

    $scope.totalItems = 0;
    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    $scope.maxSize = 10;

    $scope.buscarMozos = function () {
        $http.get('/mozo/mozos').success(function (data, status, headers, config) {
            $scope.mozos = data.usuarios;
        });
    }

    $scope.buscarMesas = function () {
        $http.get('/mesa/mesas').success(function (data, status, headers, config) {
            $scope.mesas = data.items;
        });
    };
    $scope.buscarAsignaciones = function () {
        var pagination = "?p=" + $scope.currentPage + "&ipp=" + $scope.itemsPerPage;
        $http.get('/asignacion/asignaciones'+pagination).success(function (data, status, headers, config) {
            $scope.asignaciones = data.items;
            $scope.totalItems = data.totalItems;
        });
    };

    $scope.asignar = function () {
        var asignacion = {
            turno: $scope.turno,
            usuario: $scope.mozo,
            mesa: $scope.mesa
        };
        $http.post("/asignacion/agregar", asignacion).success(function (data) {
            $scope.buscarAsignaciones();
        });
    };

    //TODO: Validar funcion save
    $scope.guardar = function () {
        //ésta es una peticion Rest
        $scope.mostrarRegistro = true;//oculta el campo si hay algún error a continuación
        if ($scope.bebida.tiposPlatos != "") {
            if ($scope.agregar) {
                $http.post('/bebida/agregar', $scope.bebida).success(function (data, status, headers, config) {
                    console.log("La bebida ha sido agregada");
                    $scope.mostrarRegistro = false;
                    $scope.mensaje = $scope.messajes["agregar"];
                    $scope.cancelar();
                });
            } else {
                $http.post('/bebida/modificar/' + $scope.bebida.idBebida, $scope.bebida).success(function (data, status, headers, config) {
                    console.log("La bebida ha sido modificada");
                    $scope.mostrarRegistro = false;
                    $scope.mensaje = $scope.messajes["modificar"];
                    $scope.cancelar();
                });
            }
        } else {
            alert("Seleccione el tipo de plato");
        }
    };
    $scope.buscarMozos();
    $scope.buscarMesas();
    $scope.buscarAsignaciones();

    $scope.getNombreMozo = function (mozo) {
        return mozo.apellido + ", " + mozo.nombre;
    };
    $scope.seleccionar = function (id) {
        $scope.agregar = false;
        $scope.agregarModificar = true;
        angular.forEach($scope.bebidas, function (be) {
            if (be.idBebida === id) {
                $scope.bebida = be;
                return;
            }
        });
    };

    $scope.eliminar = function (asignacion) {
        $http.put('/asignacion/borrar/' + asignacion.idAsignacion).success(function (data, status, headers, config) {
            $scope.buscarAsignaciones();
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };
    $scope.getDescripcionMesa = function (mesa){
        return "#"+mesa.numero+" "+mesa.descripcion;
    };

}





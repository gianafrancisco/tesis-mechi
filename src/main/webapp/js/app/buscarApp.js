//angular.module('BuscarApp', ['ui.bootstrap'])
//        .controller('BuscarCtrl', BuscarCtrl);


function BuscarCtrl($scope, $http, $log) {
    $scope.usuarios = [];
    $scope.usuario;
    $scope.modificar = false;
    $scope.rol = "";
    $scope.filtro ="";

    $scope.totalItems = 0;
    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    $scope.maxSize = 10;

    $scope.urlsListado = {
        "administrador": "/usuario/usuarios",
        "mozo": "/mozo/mozos",
        "cliente": "/cliente/clientes"
    };
    $scope.urlsModificar = {
        "administrador": "/usuario/modificar/",
        "mozo": "/mozo/modificar/",
        "cliente": "/cliente/modificar/"
    };
    $scope.urlsBorrar = {
        "administrador": "/usuario/borrar/",
        "mozo": "/mozo/borrar/",
        "cliente": "/cliente/borrar/"
    };

    $scope.expresionesRegulares = {
        name: /^([A-Za-záéíóúñ\s]{1,})+$/,
        text: /^[a-zA-Z\s]*$/,
        email: /^([\da-z_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/,
        pass: /(^(?=.*[a-z])(?=.*[A-Z])(?=.*\d){6,20}.+$)/,
        tel: /(^[0-9]|-)*$/,
        number: /^\d*$/
    };

    $scope.errores = {
        campoObligatorio: "El campo es obligatorio.",
        contrasena: "Debe ingresar por lo menos una letra en mayúscula, una letra en minúscula y un número y que su longitud sea entre 6 y 20 caracteres.",
        nombre: "Debe ingresar sólo letras mayúsculas y minúsculas y hasta 3 espacios.",
        numero: "Debe ingresar solo números.",
        tel: "Debe ingresar sólo números y guiones",
    };

    $scope.listaDeUsuarios = function () {
        if ($scope.urlsListado[$scope.rol] != undefined) {
            var pagination = "?p=" + $scope.currentPage + "&ipp=" + $scope.itemsPerPage+"&filter="+$scope.filtro;
            $http.get($scope.urlsListado[$scope.rol] + pagination).success(function (data, status, headers, config) {
                $scope.usuarios = data.usuarios;
                $scope.totalItems = data.totalItems;
            }).error(function (data, status, headers, config) {
                $log.error(status + " " + data);
            });
        }
    };
    $scope.cancelar = function () {
        $scope.modificar = false;
    };
    $scope.actualizarCuenta = function () {
        $log.info("Guardando los cambios");
        if ($scope.urlsModificar[$scope.rol] != undefined) {
            $http.put($scope.urlsModificar[$scope.rol] + $scope.usuario.idUsuario, $scope.usuario).success(function (data, status, headers, config) {
                $log.info("El usuario ha sido actualizado correctamente");
                $scope.usuario = data;
                $scope.modificar = false;
            }).error(function (data, status, headers, config) {
                $log.error(status + " " + data);
            });
        }
    };
    $scope.eliminarCuenta = function (index) {
        if ($scope.urlsBorrar[$scope.rol] != undefined) {
            //$scope.usuario = $scope.usuarios[index];
            angular.forEach($scope.usuarios, function (u) {
                if (u.idUsuario === index) {
                    $scope.usuario = u;
                    return;
                }
            });
            $http.put($scope.urlsBorrar[$scope.rol] + $scope.usuario.idUsuario).success(function (data, status, headers, config) {
                $scope.listaDeUsuarios();
            }).error(function (data, status, headers, config) {
                $log.error(status + " " + data);
            });
        }
    };
    $scope.seleccionar = function (index) {
        angular.forEach($scope.usuarios, function (u) {
            if (u.idUsuario === index) {
                $scope.usuario = u;
                return;
            }
        });
        $scope.modificar = true;
    };
    //$scope.listaDeUsuarios();
    //TODO Traer desde REST los usuarios filtrador, es decir hacer una funcion en el DAO que filtre por un valor pasado por parametros.
}
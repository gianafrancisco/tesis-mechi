//angular.module('MesaApp', [])
//        .controller('RegistrarMesaCtrl', RegistrarMesaCtrl);


function RegistrarMesaCtrl($rootScope, $scope, $http, $log) {
    $scope.mostrarRegistro = true;
    $scope.agregarModificar = false;
    $scope.mesa = new Mesa();
    $scope.agregar = true;
    $scope.messajes = {
        agregar: "La mesa ha sido creada correctamente.",
        modificar: "La mesa ha sido modificada correctamente."
    };
    $scope.mensaje = "";
    $scope.ubicaciones = [];
    
    $scope.totalItems = 0;
    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    $scope.maxSize = 10;

    $scope.expresionesRegulares = {
        name: /^([A-Za-záéíóúñ\s]{1,})+$/,
        text: /^[a-zA-Z\s]*$/,
        email: /^([\da-z_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/,
        pass: /(^(?=.*[a-z])(?=.*[A-Z])(?=.*\d){6,20}.+$)/,
        tel: /(^[0-9]|-)*$/,
        number: /^\d*$/,
        things: /^([A-Za-záéíóúñ\s\d*]{1,})+$/
    };

    $scope.errores = {
        campoObligatorio: "El campo es obligatorio.",
        mail: "Debe ingresar caracteres alfanuméricos  guión, guión bajo, puntos y signo @",
        contrasena: "Debe ingresar por lo menos una letra en mayúscula, una letra en minúscula y un número y que su longitud sea entre 6 y 20 caracteres.",
        nombre: "Debe ingresar sólo letras mayúsculas y minúsculas y hasta 3 espacios.",
        numero: "Debe ingresar solo números.",
        tel: "Debe ingresar sólo números y guiones",
        mailExiste: "El correo ya se encuentra registrado en el servidor",
        cosas: "Debe ingresar letras, números y espacios"
    };

    //Mapeo desde html los elementos por ID a una variable interna de java
    $scope.btnLimpiar = document.getElementById("borrar");
    $scope.btnGuardar = document.getElementById("guardar");

    $scope.consultarUbicaciones = function () {
        $http.get('/ubicacion/ubicaciones').success(function (data, status, headers, config) {
            $scope.ubicaciones = data.items;
            $scope.mesa.ubicacion = $scope.ubicaciones[0];
        });
    }

    $scope.limpiar = function () {
        $scope.mesa = new Mesa();
        $scope.registrarMesaForm.$setPristine();
        $scope.registrarMesaForm.$setUntouched();
    };

    $scope.cancelar = function () {
        $scope.agregarModificar = false;
        $scope.limpiar();
    };

    //TODO: Validar funcion save
    $scope.guardar = function () {
        //ésta es una peticion Rest
        $scope.mostrarRegistro = true;//oculta el campo si hay algún error a continuación
        //$scope.mesa.ubicacion = $scope.ubicacion;
        if ($scope.mesa.ubicacion != "") {
            if ($scope.agregar) {
                $http.post('/mesa/agregar', $scope.mesa).success(function (data, status, headers, config) {
                    console.log("La mesa ha sido agregada");
                    $scope.mostrarRegistro = false;
                    $scope.mensaje = $scope.messajes["agregar"];
                    $scope.cancelar();
                });
            } else {
                $http.post('/mesa/modificar/' + $scope.mesa.idMesa, $scope.mesa).success(function (data, status, headers, config) {
                    console.log("La mesa ha sido Modificada");
                    $scope.mostrarRegistro = false;
                    $scope.mensaje = $scope.messajes["modificar"];
                    $scope.cancelar();
                });
            }
        } else {
            alert("Seleccione la ubicación");
        }
    };
    $scope.consultarUbicaciones();

    $scope.nuevaMesa = function () {
        $scope.mesa = new Mesa();
        $scope.agregar = true;
        $scope.agregarModificar = true;
    };

    $scope.seleccionar = function (id) {
        $scope.agregar = false;
        $scope.agregarModificar = true;
        angular.forEach($scope.mesas, function (m) {
            if (m.idMesa === id) {
                $scope.mesa = m;
                
                return;
            }
        });
    };

    $scope.eliminar = function (id) {
        $scope.agregar = false;
        $scope.agregarModificar = false;
        angular.forEach($scope.mesas, function (m) {
            if (m.idMesa === id) {
                $scope.mesa = m;
                return;
            }
        });
        $http.put('/mesa/borrar/' + $scope.mesa.idMesa).success(function (data, status, headers, config) {
            $scope.buscarMesas();
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };

    $scope.buscarMesas = function () {
        var pagination = "?p=" + $scope.currentPage + "&ipp=" + $scope.itemsPerPage;
        $http.get("/mesa/mesas"+pagination).success(function (data, status, headers, config) {
            $scope.mesas = data.items;
            $scope.totalItems = data.totalItems;
            $scope.mesa = $scope.mesas[0];
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    }

    $scope.cargarUbicaciones = function () {
        $http.get("/ubicacion/ubicaciones").success(function (data, status, headers, config) {
            $scope.ubicaciones = data;
            $scope.ubicacionSeleccionado = $scope.ubicaciones[0];
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };

    $scope.agregarUbicacionAMesa = function () {
        $log.debug($scope.mesa.ubicacion.length);
        $scope.mesa.ubicaciones[$scope.mesa.ubicaciones.length] = $scope.ubicacionSeleccionado;
    };
}
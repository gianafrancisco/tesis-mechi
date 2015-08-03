//angular.module('UbicacionApp', [])
//        .controller('RegistrarUbicacionCtrl', RegistrarUbicacionCtrl);

function RegistrarUbicacionCtrl($rootScope, $scope, $http, $log) {
    $scope.mostrarRegistro = true;
    $scope.agregarModificar = false;
    $scope.ubicacionExiste = false;
    $scope.ubicacion = new Ubicacion();
    $scope.agregar = true;
    $scope.messajes = {
        agregar: "La ubicación ha sido creada correctamente.",
        modificar: "La ubicación ha sido modificada correctamente.",
        eliminar: "La ubicación ha sido eliminada correctamente",
        eliminarError: "La ubicación no ha podido ser eliminada porque tiene mesas asignadas."
    };
    $scope.mensaje = "";

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
        ubicacionExiste: "La ubicación ya se encuentra registrada en el servidor",
        cosas: "Debe ingresar letras, números y espacios"
    };

    //Mapeo desde html los elementos por ID a una variable interna de java
    $scope.btnLimpiar = document.getElementById("borrar");
    $scope.btnGuardar = document.getElementById("guardar");

    $scope.limpiar = function () {
        $scope.ubicacion = new Ubicacion();
        $scope.registrarUbicacionForm.$setPristine();
        $scope.registrarUbicacionForm.$setUntouched();
        $scope.ubicacionExiste = false;
    };

    $scope.cancelar = function () {
        $scope.agregarModificar = false;
        $scope.limpiar();
    };

    //TODO: Validar funcion save
    $scope.guardar = function () {
        //ésta es una peticion Rest
        $scope.mostrarRegistro = true;//oculta el campo si hay algún error a continuación
        if ($scope.ubicacion.descripcion != "") {

            if ($scope.agregar) {
                $http.post('/ubicacion/agregar', $scope.ubicacion).success(function (data, status, headers, config) {
                    console.log("La ubicación ha sido agregada");
                    $scope.mostrarRegistro = false;
                    $scope.mensaje = $scope.messajes["agregar"];
                    $scope.cancelar();
                });
            } else {
                $http.post('/ubicacion/modificar/' + $scope.ubicacion.idUbicacion, $scope.ubicacion).success(function (data, status, headers, config) {
                    console.log("La ubicación ha sido Modificada");
                    $scope.mostrarRegistro = false;
                    $scope.mensaje = $scope.messajes["modificar"];
                    $scope.cancelar();
                });
            }
        }
    };

    $scope.nuevaUbicacion = function () {
        $scope.mostrarRegistro = true;
        $scope.ubicacion = new Ubicacion();
        $scope.agregar = true;
        $scope.agregarModificar = true;
    };

    $scope.seleccionar = function (id) {
        $scope.mostrarRegistro = true;
        $scope.agregar = false;
        $scope.agregarModificar = true;
        angular.forEach($scope.ubicaciones, function (u) {
            if (u.idUbicacion === id) {
                $scope.ubicacion = u;
                return;
            }
        });
    };

    $scope.eliminar = function (id) {
        $scope.mostrarRegistro = true;
        $scope.agregar = false;
        $scope.agregarModificar = false;
        angular.forEach($scope.ubicaciones, function (u) {
            if (u.idUbicacion === id) {
                $scope.ubicacion = u;
                return;
            }
        });
        $http.put('/ubicacion/borrar/' + $scope.ubicacion.idUbicacion).success(function (data, status, headers, config) {
            $scope.mostrarRegistro = false;
            if (data === true) {
                $scope.buscarUbicaciones();
                $scope.mensaje = $scope.messajes["eliminar"];
            } else {
                $scope.mensaje = $scope.messajes["eliminarError"];
            }
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };

    $scope.verificarUbicacion = function (descripcion) {
        $http.put("/validar/ubicacion", {descripcion: descripcion}).success(function (data, status, headers, config) {
            $scope.ubicacionExiste = data;
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    }

    $scope.buscarUbicaciones = function () {
        
        var pagination = "?p=" + $scope.currentPage + "&ipp=" + $scope.itemsPerPage;
        $http.get("/ubicacion/ubicaciones"+pagination).success(function (data, status, headers, config) {
            $scope.ubicaciones = data.items;
            $scope.totalItems = data.totalItems;
            $scope.ubicacion = $scope.ubicaciones[0];
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    }
}



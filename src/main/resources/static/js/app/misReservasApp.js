/*angular.module('MisReservasApp', ['ui.bootstrap'])
        .controller('MisReservasCtrl', MisReservasCtrl);*/


function MisReservasCtrl($scope, $http, $log) {
    $scope.usuario = new Usuario();
    $scope.usuario.idUsuario = 2;
    $scope.totalItems = 100;
    $scope.currentPage = 1;
    $scope.itemsPerPage = 5;
    $scope.maxSize = 10;

    $scope.mensajes = {
        agregar: "La reserva ha sido creada correctamente.",
        modificar: "La reserva ha sido modificada correctamente.",
        cancelar: "La reserva ha sido cancelada correctamente.",
        cancelarError: "No se pudo cancelar la reserva debido a un error del sistema."
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
        mail: "Debe ingresar caracteres alfanuméricos  guión, guión bajo, puntos y signo @",
        contrasena: "Debe ingresar por lo menos una letra en mayúscula, una letra en minúscula y un número y que su longitud sea entre 6 y 20 caracteres.",
        nombre: "Debe ingresar sólo letras mayúsculas y minúsculas y hasta 3 espacios.",
        numero: "Debe ingresar solo números.",
        tel: "Debe ingresar sólo números y guiones",
        mailExiste: "El correo ya se encuentra registrado en el servidor"
    };

    $scope.guardar = function () {
        //ésta es una peticion Rest
        $scope.mostrarRegistro = true;
        if ($scope.reserva.fecha != "") {
            var hora = $scope.hora.getHours() + ":" + $scope.hora.getMinutes() + ":00";
            $scope.reserva.hora = hora;
            if ($scope.agregar) {
                $http.post('/reserva/agregar', $scope.reserva).success(function (data, status, headers, config) {
                    console.log("La reserva ha sido agregada");
                    $scope.mostrarRegistro = false;
                    $scope.mensaje = $scope.messajes["agregar"];
                    $scope.limpiar();
                });
            } else {
                $http.post('/reserva/modificar/' + $scope.reserva.nroReserva, $scope.reserva).success(function (data, status, headers, config) {
                    console.log("La reserva ha sido Modificada");
                    $scope.mostrarRegistro = false;
                    $scope.mensaje = $scope.messajes["modificar"];
                    $scope.limpiar();
                });
            }
        }
    };
    $scope.seleccionar = function (id) {
        $scope.mostrarRegistro = true;
        $scope.agregar = false;
        $scope.agregarModificar = true;
        angular.forEach($scope.reservas, function (r) {
            if (r.nroReserva === id) {
                $scope.reserva = r;
                return;
            }
        });
    };

    $scope.cancelar = function (id) {
        $scope.mostrarMensaje = false;
        $scope.agregar = false;
        $scope.agregarModificar = false;
        angular.forEach($scope.reservas, function (r) {
            if (r.nroReserva === id) {
                $scope.reserva = r;
                return;
            }
        });
        $http.put('/reserva/cancelar/' + $scope.reserva.nroReserva).success(function (data, status, headers, config) {
            $scope.mostrarMensaje = true;
            if (data === true) {
                $scope.buscarReservas();
                $scope.mensaje = $scope.mensajes["cancelar"];
            } else {
                $scope.mensaje = $scope.mensajes["cancelarError"];
            }
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };
    $scope.buscarReservas = function () {
        var pagination = "?p=" + $scope.currentPage + "&ipp=" + $scope.itemsPerPage;
        $http.get("/reserva/reservas/" + $scope.usuario.idUsuario + pagination).success(function (data, status, headers, config) {
            $scope.reservas = data.reservas;
            $scope.totalItems = data.totalItems;
            $scope.reserva = $scope.reservas[0];
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };
    $scope.consultarSession = function () {
        $http.get("/validar/session/usuario").success(function (data, status, headers, config) {
            $scope.usuario = data;
            $scope.buscarReservas();
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });

    };

    $scope.consultarSession();
}



/*angular.module('ReservaApp', ['ui.bootstrap'])
 .controller('RegistrarReservaCtrl', RegistrarReservaCtrl);*/

function RegistrarReservaCtrl($scope, $http, $log) {
    $scope.usuario = new Usuario();
    $scope.usuario.idUsuario = 2;
    $scope.mostrarRegistro = true;
    $scope.agregarModificar = true;
    $scope.reserva = new Reserva();
    $scope.agregar = true;
    $scope.messajes = {
        agregar: "La reserva ha sido creada correctamente.",
        modificar: "La reserva ha sido modificada correctamente.",
    };
    $scope.mensaje = "";
    $scope.hora = new Date();
    $scope.hora.setMinutes(0);


    $scope.hstep = 1;
    $scope.mstep = 30;


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

    //Mapeo desde html los elementos por ID a una variable interna de java
    $scope.btnLimpiar = document.getElementById("borrar");
    $scope.btnGuardar = document.getElementById("guardar");

    $scope.limpiar = function () {
        $scope.reserva = new Reserva();
        $scope.hora = new Date();
        $scope.hora.setMinutes(0);
        $scope.registrarReservaForm.$setPristine();
        $scope.registrarReservaForm.$setUntouched();
    };

    $scope.guardar = function () {
        //ésta es una peticion Rest
        $scope.mostrarRegistro = true;
        if ($scope.reserva.fecha != "") {
            var hora = $scope.hora.getHours() + ":" + $scope.hora.getMinutes() + ":00";
            $scope.reserva.hora = hora;
            $scope.reserva.fechaReserva = new Date($scope.reserva.fecha +" "+$scope.reserva.hora); 
            if ($scope.agregar) {
                $http.post('/reserva/agregar?u=' + $scope.usuario.idUsuario, $scope.reserva).success(function (data, status, headers, config) {
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

    $scope.nuevaReserva = function () {
        $scope.mostrarRegistro = true;
        $scope.reserva = new Reserva();
        $scope.agregar = true;
        $scope.agregarModificar = true;
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

    $scope.eliminar = function (id) {
        $scope.mostrarRegistro = true;
        $scope.agregar = false;
        $scope.agregarModificar = false;
        angular.forEach($scope.reservas, function (r) {
            if (r.nroReserva === id) {
                $scope.reserva = r;
                return;
            }
        });
        $http.put('/reserva/borrar/' + $scope.reserva.nroReserva).success(function (data, status, headers, config) {
            $scope.mostrarRegistro = false;
            if (data === true) {
                $scope.buscarReservas();
                $scope.mensaje = $scope.messajes["eliminar"];
            } else {
                $scope.mensaje = $scope.messajes["eliminarError"];
            }
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };

    $scope.verificarReserva = function (fecha) {
        $http.put("/validar/reserva", {fecha: fecha}).success(function (data, status, headers, config) {
            $scope.reservaExiste = data;
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    }

    $scope.buscarReservas = function () {
        $http.get("/reserva/reservas").success(function (data, status, headers, config) {
            $scope.reservas = data;
            $scope.reserva = $scope.reservas[0];
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };
    $scope.buscarMesas = function () {
        var fecha = $scope.reserva.fecha;
        var hora = $scope.hora.getHours() + ":" + $scope.hora.getMinutes() + ":00";
        if (hora != undefined && fecha != undefined) {
            $http.get("/mesa/disponibles/" + fecha + "/" + hora).success(function (data, status, headers, config) {
                $scope.mesasDisponibles = data;
                $scope.reserva.mesa = $scope.mesasDisponibles[0];
            }).error(function (data, status, headers, config) {
                $log.error(status + " " + data);
            });
        }
    };
    $scope.getMesaLabel = function (mesa) {
        return "Mesa " + mesa.numero + " ( " + mesa.descripcion + " )";
    };
    /*
     $scope.consultarSession = function () {
     $http.get("/validar/session/usuario").success(function (data, status, headers, config) {
     $scope.usuario = data;
     $scope.buscarMesas();
     }).error(function (data, status, headers, config) {
     $log.error(status + " " + data);
     });
     
     };
     */
    //$scope.consultarSession();
}



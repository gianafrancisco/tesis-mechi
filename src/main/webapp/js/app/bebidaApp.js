//angular.module('BebidaApp', [])
//        .controller('RegistrarBebidaCtrl',RegistrarBebidaCtrl );


function RegistrarBebidaCtrl($scope, $http, $log) {
    $scope.mostrarRegistro = true;
    $scope.agregarModificar = false;
    $scope.bebida = new Bebida();
    $scope.agregar = true;
    $scope.messajes = {
        agregar: "La bebida ha sido creada correctamente.",
        modificar: "La bebida ha sido modificada correctamente."
    };
    $scope.mensaje = "";
    $scope.totalItems = 0;
    $scope.currentPage = 1;
    $scope.itemsPerPage = 10;
    $scope.maxSize = 10;
    
    $scope.tiposPlatosArray = [];
    $scope.expresionesRegulares = {
        name: /^([A-Za-záéíóúñ\s]{1,})+$/,
        text: /^[a-zA-Z\s]*$/,
        email: /^([\da-z_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/,
        pass: /(^(?=.*[a-z])(?=.*[A-Z])(?=.*\d){6,20}.+$)/,
        tel: /(^[0-9]|-)*$/,
        number: /^\d*$/,
        things: /^([A-Za-záéíóúñ\s\d*]{1,})+$/,
        price: /(^\d*\.?\d*[0-9]+\d*$)|(^[0-9]+\d*\.\d*$)/};

    $scope.errores = {
        campoObligatorio: "El campo es obligatorio.",
        mail: "Debe ingresar caracteres alfanuméricos  guión, guión bajo, puntos y signo @",
        contrasena: "Debe ingresar por lo menos una letra en mayúscula, una letra en minúscula y un número y que su longitud sea entre 6 y 20 caracteres.",
        nombre: "Debe ingresar sólo letras mayúsculas y minúsculas y hasta 3 espacios.",
        numero: "Debe ingresar solo números.",
        tel: "Debe ingresar sólo números y guiones",
        mailExiste: "El correo ya se encuentra registrado en el servidor",
        precio: "Debe ingresar sólo números y un punto."
    };

    //Mapeo desde html los elementos por ID a una variable interna de java
    $scope.btnLimpiar = document.getElementById("borrar");
    $scope.btnGuardar = document.getElementById("guardar");
    $scope.consultarTiposPlatos = function () {
        $http.get('/tipoPlato/tiposPlatos').success(function (data, status, headers, config) {
            $scope.tiposPlatosArray = data.items;
            $scope.bebida.tiposPlatos = $scope.tiposPlatosArray[0];
        });
    }

    $scope.limpiar = function () {
        $scope.bebida = new Bebida();
        $scope.registrarBebidaForm.$setPristine();
        $scope.registrarBebidaForm.$setUntouched();
    };
    $scope.cancelar = function () {
        $scope.agregarModificar = false;
        $scope.limpiar();
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
    $scope.consultarTiposPlatos();

    $scope.nuevaBebida = function () {
        $scope.bebida = new Bebida();
        $scope.agregar = true;
        $scope.agregarModificar = true;
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

    $scope.eliminar = function (id) {
        $scope.agregar = false;
        $scope.agregarModificar = false;
        angular.forEach($scope.bebidas, function (be) {
            if (be.idBebida === id) {
                $scope.bebida = be;
                return;
            }
        });
        $http.put('/bebida/borrar/' + $scope.bebida.idBebida).success(function (data, status, headers, config) {
            $scope.buscarBebidas();
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };

    $scope.buscarBebidas = function () {
        var pagination = "?p=" + $scope.currentPage + "&ipp=" + $scope.itemsPerPage;
        $http.get("/bebida/bebidas"+pagination).success(function (data, status, headers, config) {
            $scope.bebidas = data.items;
            $scope.totalItems = data.totalItems;
            $scope.bebida = $scope.bebidas[0];
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    }

    $scope.cargarTiposPlatos = function () {
        $http.get("/tipoPlato/tiposPLatos").success(function (data, status, headers, config) {
            $scope.tiposPlatos = data;
            $scope.tipoPlatoSeleccionado = $scope.tiposPlatos[0];
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };
    $scope.agregarTipoPlatoABebida = function () {
        $log.debug($scope.bebida.tipoPlato.length);
        $scope.bebida.tiposPlatos[$scope.bebida.tiposPlatos.length] = $scope.tipoPlatoSeleccionado;
    };
}





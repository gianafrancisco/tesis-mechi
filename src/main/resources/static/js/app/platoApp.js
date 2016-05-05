//angular.module('PlatoApp', [])
//        .controller('RegistrarPlatoCtrl', RegistrarPlatoCtrl);

function RegistrarPlatoCtrl($rootScope,$scope, $http, $log) {
    $scope.mostrarRegistro = true;
    $scope.agregarModificar = false;
    $scope.plato = new Plato();
    $scope.agregar = true;
    $scope.messajes = {
        agregar: "El plato ha sido creado correctamente.",
        modificar: "El plato ha sido modificado correctamente."
    };
    $scope.mensaje = "";
    $scope.tiposPlatos = [];

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
        things: /^([A-Za-záéíóúñ\s\d*]{1,})+$/,
        price: /(^\d*\.?\d*[0-9]+\d*$)|(^[0-9]+\d*\.\d*$)/
    };

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
            $scope.tiposPlatos = data.items;
            $scope.plato.tipoPlato = $scope.tiposPlatos[0];
        });
    }

    $scope.limpiar = function () {
        $scope.plato = new Plato();
        $scope.registrarPlatoForm.$setPristine();
        $scope.registrarPlatoForm.$setUntouched();
    };

    $scope.cancelar = function () {
        $scope.agregarModificar = false;
        $scope.limpiar();
    };

    //TODO: Validar funcion save
    $scope.guardar = function () {
        //ésta es una peticion Rest
        $scope.mostrarRegistro = true;//oculta el campo si hay algún error a continuación
        if ($scope.plato.tipoPlato != "") {
            if ($scope.agregar) {
                $http.post('/plato/agregar', $scope.plato).success(function (data, status, headers, config) {
                    console.log("El plato ha sido agregado");
                    $scope.mostrarRegistro = false;
                    $scope.mensaje = $scope.messajes["agregar"];
                    $scope.cancelar();
                });
            } else {
                $http.post('/plato/modificar/' + $scope.plato.idPlato, $scope.plato).success(function (data, status, headers, config) {
                    console.log("El plato ha sido modificado");
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

    $scope.nuevaPlato = function () {
        $scope.plato = new Plato();
        $scope.agregar = true;
        $scope.agregarModificar = true;
    };

    $scope.seleccionar = function (id) {
        $scope.agregar = false;
        $scope.agregarModificar = true;
        angular.forEach($scope.platos, function (pl) {
            if (pl.idPlato === id) {
                $scope.plato = pl;
                return;
            }
        });
    };

    $scope.eliminar = function (id) {
        $scope.agregar = false;
        $scope.agregarModificar = false;
        angular.forEach($scope.platos, function (pl) {
            if (pl.idPlato === id) {
                $scope.plato = pl;
                return;
            }
        });
        $http.put('/plato/borrar/' + $scope.plato.idPlato).success(function (data, status, headers, config) {
            $scope.buscarPlatos();
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };

    $scope.buscarPlatos = function () {
        var pagination = "?p=" + $scope.currentPage + "&ipp=" + $scope.itemsPerPage;
        $http.get("/plato/platos"+pagination).success(function (data, status, headers, config) {
            $scope.platos = data.items;
            $scope.totalItems = data.totalItems;
            $scope.plato = $scope.platos[0];
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

    $scope.agregarTipoPlatoAPlato = function () {
        $log.debug($scope.plato.tipoPlato.length);
        $scope.plato.tiposPlatos[$scope.plato.tiposPlatos.length] = $scope.tipoPlatoSeleccionado;
    };
}



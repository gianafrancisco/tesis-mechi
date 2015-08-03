//angular.module('PromocionApp', [])
//        .controller('RegistrarPromocionCtrl', RegistrarPromocionCtrl);

function RegistrarPromocionCtrl($rootScope, $scope, $http, $log, $filter) {
    $scope.mostrarRegistro = true;
    $scope.agregarModificar = false;
    $scope.tipoPromocionExiste = false;
    $scope.promocion = new Promocion();
    $scope.bebidas = [];
    $scope.platos = [];
    $scope.agregar = true;
    $scope.messajes = {
        agregar: "La promoción ha sido creada correctamente.",
        modificar: "La promoción ha sido modificada correctamente.",
        eliminar: "La promoción ha sido eliminada correctamente",
        eliminarError: "La promoción no ha podido ser eliminada porque tiene platos o bebidas asignadas."
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
        tipoPlatoExiste: "El tipo de plato ya se encuentra registrado en el servidor",
        promocionExiste: "La promoción ya se encuentra registrada en el servidor",
        cosas: "Debe ingresar letras, números y espacios",
        precio: "Debe ingresar sólo números y un punto."
    };

    //Mapeo desde html los elementos por ID a una variable interna de java
    $scope.btnLimpiar = document.getElementById("borrar");
    $scope.btnGuardar = document.getElementById("guardar");

    $scope.limpiar = function () {
        $scope.promocion = new Promocion();
        $scope.registrarPromocionForm.$setPristine();
        $scope.registrarPromocionForm.$setUntouched();
        $scope.promocionExiste = false;
    };

    $scope.cancelar = function () {
        $scope.agregarModificar = false;
        $scope.limpiar();
    };

    //TODO: Validar funcion save
    $scope.guardar = function () {
        //ésta es una peticion Rest
        $scope.mostrarRegistro = true;//oculta el campo si hay algún error a continuación
        if ($scope.promocion.descripcion != "") {
            //$scope.fechaDesde += 3600 * 3;
            //$scope.fechaHasta += 3600 * 3;
            if ($scope.agregar) {
                $http.post('/promocion/agregar', $scope.promocion).success(function (data, status, headers, config) {
                    console.log("La promoción ha sido agregada");
                    $scope.mostrarRegistro = false;
                    $scope.mensaje = $scope.messajes["agregar"];
                    $scope.cancelar();
                });
            } else {
                $http.post('/promocion/modificar/' + $scope.promocion.idPromocion, $scope.promocion).success(function (data, status, headers, config) {
                    console.log("La promoción ha sido Modificada");
                    $scope.mostrarRegistro = false;
                    $scope.mensaje = $scope.messajes["modificar"];
                    $scope.cancelar();
                });
            }
        }
    };

    $scope.nuevaPromocion = function () {
        $scope.promocion = new Promocion();
        $scope.agregar = true;
        $scope.agregarModificar = true;
        $scope.mostrarRegistro = true;
    };

    $scope.seleccionar = function (id) {
        $scope.mostrarRegistro = true;
        $scope.agregar = false;
        $scope.agregarModificar = true;
        angular.forEach($scope.promociones, function (p) {
            if (p.idPromocion === id) {
                $scope.promocion = p;
                return;
            }
        });
    };

    $scope.eliminar = function (id) {
        $scope.mostrarRegistro = true;
        $scope.agregar = false;
        $scope.agregarModificar = false;
        angular.forEach($scope.promociones, function (p) {
            if (p.idPromocion === id) {
                $scope.promocion = p;
                return;
            }
        });
        $http.put('/promocion/borrar/' + $scope.promocion.idPromocion).success(function (data, status, headers, config) {
            $scope.buscarPromociones();
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };
    $scope.verificarPromocion = function (descripcion) {
        $http.put("/validar/promocion", {descripcion: descripcion}).success(function (data, status, headers, config) {
            $scope.promocionExiste = data;
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };

    $scope.cargarPlatos = function () {
        $http.get("/plato/platos").success(function (data, status, headers, config) {
            $scope.platos = data.items;
            $scope.platoSeleccionado = $scope.platos[0];
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };

    $scope.cargarBebidas = function () {
        $http.get("/bebida/bebidas").success(function (data, status, headers, config) {
            $scope.bebidas = data.items;
            $scope.bebidaSeleccionado = $scope.bebidas[0];
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };

    $scope.agregarPlatoAPromocion = function () {
        $log.debug($scope.promocion.platos.length);
        $scope.promocion.platos[$scope.promocion.platos.length] = $scope.platoSeleccionado;
    };
    $scope.agregarBebidaAPromocion = function () {
        $log.debug($scope.promocion.bebidas.length);
        $scope.promocion.bebidas[$scope.promocion.bebidas.length] = $scope.bebidaSeleccionado;
    };

    $scope.quitarPlato = function (index) {
        $scope.promocion.platos.splice(index, 1);
    }
    $scope.quitarBebida = function (index) {
        $scope.promocion.bebidas.splice(index, 1);
    }

    $scope.buscarPromociones = function () {
        $scope.mostrarRegistro = true;
        var pagination = "?p=" + $scope.currentPage + "&ipp=" + $scope.itemsPerPage;
        $http.get("/promocion/promociones" + pagination).success(function (data, status, headers, config) {
            $scope.promociones = data.items;
            $scope.totalItems = data.totalItems;
            $scope.promocion = $scope.promociones[0];
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };
    $scope.$watch('fechaDesde', function (newValue) {
        $scope.promocion.fechaDesde = $filter('date')(newValue, 'yyyy-MM-dd');
    });
    $scope.$watch('fechaHasta', function (newValue) {
        $scope.promocion.fechaHasta = $filter('date')(newValue, 'yyyy-MM-dd');
    });
    $scope.$watch('promocion.fechaDesde', function (newValue) {
        $scope.fechaDesde = $filter('date')(newValue, 'yyyy-MM-dd');
    });
    $scope.$watch('promocion.fechaHasta', function (newValue) {
        $scope.fechaHasta = $filter('date')(newValue, 'yyyy-MM-dd');
    });

    $scope.cargarBebidas();
    $scope.cargarPlatos();
}



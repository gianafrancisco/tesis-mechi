angular.module('PedidoApp', ['ngRoute']).config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {

        $routeProvider
                .when('/pedido', {templateUrl: '/mozo/main.html', controller: 'PedidoCtrl'})
                .when('/tabMenu', {templateUrl: '/mozo/main.html'})
                .when('/tabPedido', {templateUrl: '/mozo/main.html'})
                .when('/tabConfirmar', {templateUrl: '/mozo/main.html'})
                .when('/home', {templateUrl: '/mozo/home.html'})
                .when('/pedidoListo', {templateUrl: '/mozo/pedidoListo.html'});
        //.otherwise({redirectTo: '/main'});
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }])
        .controller('PedidoCtrl', PedidoCtrl).controller('NavegacionMozoCtrl', NavegacionMozoCtrl)
        .controller('PedidoListoCtrl', PedidoListoCtrl);


function NavegacionMozoCtrl($rootScope, $scope, $http, $log, $location, $window) {

    $rootScope.credentials = {};
    $scope.error = false;
    $scope.errores = {
        passwordIncorrecto: "El password o el nombre de usuario no es correcto, intente nuevamente"
    };
    $scope.clienteLogin = function () {
        //$scope.login = true;
        //$scope.indexForm.$setPristine();
        //$scope.indexForm.$setUntouched();

        authenticate($rootScope.credentials, function () {
            $log.debug($rootScope.authenticated);
            if ($rootScope.authenticated) {
                $location.path("/pedido");
                $scope.error = false;
                //setTimeout($rootScope.buscarCliente, '500');
            } else {
                $location.path("/main");
                $scope.error = true;
            }
        });

    };

    $scope.clienteLogout = function () {
        $rootScope.credentials = {};
        $http.post('/logout', {}).success(function () {
            $rootScope.authenticated = false;
            $location.path("#/home");
            //$window.location.href="/index.html";
        }).error(function (data) {
            $rootScope.authenticated = false;
            $location.path("#/home");
            //$window.location.href="/index.html";
        });
        //$window.location.href="/index.html";
    };

    var authenticate = function (credentials, callback) {

        var headers = credentials ? {authorization: "Basic "
                    + btoa(credentials.username + ":" + credentials.password)
        } : {};

        $http.get('/validar/user', {headers: headers}).success(function (data) {
            if (data.authorities[0].authority === "ADMIN") {
                $log.debug(data.authorities[0].authority);
                //$location.path("/administrador");
                $window.location.href = "/admin.html";
            } else if (data.authorities[0].authority === "CLIENTE") {
                //$location.path("/mozo");
                $window.location.href = "/index.html";
            }
            if (data.name) {
                $rootScope.authenticated = true;
            } else {
                $rootScope.authenticated = false;
                $scope.clienteLogout();
            }
            callback && callback();
        }).error(function () {
            $rootScope.authenticated = false;
            callback && callback();
        });

    };
    authenticate();
}

function PedidoCtrl($rootScope, $scope, $http, $log) {

    $scope.pedidoEnviadoConExito = false;

    $scope.limpiarCampos = function () {
        $scope.tipoPlatos = [];
        $scope.productos = [];
        $scope.todosProductos = [];
        $scope.bebidas = [];
        $scope.platos = [];
        $scope.promociones = [];
        $scope.items = [];
        $scope.reservas = [];
        $scope.mesa = {};
        $scope.mesas = [];
        $scope.cantidadPersonas = 0;
    };

    $scope.buscarTipoPlatos = function () {
        $http.get('/tipoPlato/tiposPlatos').success(function (data, status, headers, config) {
            $scope.tipoPlatos = data.items;
            $scope.buscarProductos();
        });
    };
    $scope.buscarProductos = function () {
        $http.get('/plato/platos').success(function (data, status, headers, config) {
            $scope.platos = data.items;
            $scope.crearColeccionProductos();
        });
        $http.get('/bebida/bebidas').success(function (data, status, headers, config) {
            $scope.bebidas = data.items;
            $scope.crearColeccionProductos();
        });
        $http.get('/promocion/disponibles').success(function (data, status, headers, config) {
            $scope.promociones = data.items;
            $scope.crearColeccionProductos();
        });
    };

    $scope.buscarReservas = function () {
        $http.get('/reserva/activas').success(function (data, status, headers, config) {
            $scope.reservas = data;
        });
    };

    $scope.buscarMesas = function () {
        $http.get('/asignacion/mesas').success(function (data, status, headers, config) {
            $scope.mesas = data;
        });
    };

    $scope.crearColeccionProductos = function () {
        $scope.todosProductos = [];
        $scope.todosProductos = $scope.bebidas.concat($scope.platos.concat($scope.promociones));
        $scope.productos = $scope.todosProductos.filter($scope.filtroTipo);
    };

    $scope.filtrarPor = function (tipo) {
        //$log.debug(tipo);
        $scope.productos = $scope.todosProductos.filter($scope.filtroTipo, tipo);
    };
    $scope.filtrarPorPromociones = function () {
        //$log.debug(tipo);
        $scope.productos = $scope.todosProductos.filter($scope.filtroTipoPromocion);
    };
    $scope.filtroTipo = function (element) {
        if (this.idTipoPlato === undefined) {
            return true;
        }
        else {
            if (element.tiposPlatos !== undefined) {
                return this.idTipoPlato === element.tiposPlatos.idTipoPlato;
            }
            return false;
        }
    };
    $scope.filtroTipoPromocion = function (element) {
        if (element.idPromocion !== undefined) {
            return true;
        }
        return false;
    };
    $scope.agregarProducto = function (producto) {
        var flag = false;
        $scope.items.forEach(function (value, index) {
            if (flag === false) {
                flag = $scope.buscarProducto(value.producto, producto);
                if (flag === true) {
                    value.cantidad++;

                }
            }
        });
        if (flag === false) {
            var item = {
                cantidad: 1,
                producto: producto
            };
            $scope.items.push(item);
        }
    };
    $scope.quitarProducto = function (index) {
        //var index = $scope.productosPedidos.indexOf($scope.buscarProducto, producto);
        $scope.items[index].cantidad--;
        if ($scope.items[index].cantidad == 0) {
            $scope.items.splice(index, 1);
        }
    };
    $scope.incrementarProducto = function (index) {
        //var index = $scope.productosPedidos.indexOf($scope.buscarProducto, producto);
        $scope.items[index].cantidad++;

    };
    $scope.eliminarProducto = function (index) {
        //var index = $scope.productosPedidos.indexOf($scope.buscarProducto, producto);
        $scope.items.splice(index, 1);
    };
    $scope.buscarProducto = function (element, producto) {
        if (producto.idPlato !== undefined) {
            if (element.idPlato !== undefined && producto.idPlato === element.idPlato) {
                return true;
            }
        } else if (producto.idBebida !== undefined) {
            if (element.idBebida !== undefined && producto.idBebida === element.idBebida) {
                return true;
            }
        }
        return false;
    };
    $scope.getDescripcion = function (producto) {
        var title = "";
        if (producto.idBebida !== undefined) {
            title = producto.nombre + " ( " + producto.descripcion + " )";
        } else if (producto.idPlato !== undefined) {
            title = producto.descripcion;
        } else {
            title = producto.descripcion;
        }
        return title;
    };
    $scope.getReservaLabel = function (reserva) {
        return reserva.nroReserva + " ( " + reserva.usuario.apellido + "," + reserva.usuario.nombre + " )";
    };
    $scope.getMesaLabel = function (mesa) {
        return "Mesa " + mesa.numero + " ( " + mesa.descripcion + " )";
    };
    $scope.refrescarDatosReserva = function () {
        if ($scope.reserva !== null) {
            var flag = false;
            $scope.cantidadPersonas = $scope.reserva.cantidadPersonas;
            $scope.mesas.forEach(function (mesa) {
                //$log.debug(mesa);
                //$log.debug($scope.reserva.mesa);
                if (!flag) {
                    if (mesa.mesa.idMesa === $scope.reserva.mesa.idMesa) {
                        $scope.mesa = mesa;
                        flag = true;
                        //return true;
                    } else {
                        $scope.mesa = undefined;
                    }
                }
            });
        } else {
            $scope.cantidadPersonas = 0;
            $scope.mesa = undefined;
        }
    };
    $scope.enviarPedido = function () {
        var pedido = new Pedido();
        pedido.estado = "NUEVO";
        $scope.items.forEach(function (value, index) {
            if (value.producto.idBebida !== undefined) {
                for (var i = 0; i < value.cantidad; i++) {
                    pedido.bebidas.push(value.producto);
                }
            }
            if (value.producto.idPlato !== undefined) {
                for (var i = 0; i < value.cantidad; i++) {
                    pedido.platos.push(value.producto);
                }
            }
            if (value.producto.idPromocion !== undefined) {
                for (var i = 0; i < value.cantidad; i++) {
                    pedido.promociones.push(value.producto);
                }
            }
        });
        var fecha = new Date();
        $log.debug($scope.reserva);
        if ($scope.reserva !== undefined) {
            pedido.reserva = $scope.reserva;
        } else {
            pedido.reserva = new Reserva();
            pedido.reserva.estado = "EJECUCION";
            pedido.reserva.cantidadPersonas = $scope.cantidadPersonas;
            pedido.reserva.mesa = $scope.mesa.mesa;
            pedido.reserva.fecha = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate();
            pedido.reserva.hora = fecha.getHours() + ":" + fecha.getMinutes() + ":" + fecha.getSeconds();
            pedido.reserva.fechaReserva = fecha.toISOString();
            pedido.reserva.cliente = null;
        }
        pedido.precio = 0;
        //pedido.fecha = fecha.getFullYear() + "-" + (fecha.getMonth() + 1) + "-" + fecha.getDate() + " " + fecha.getHours() + ":" + fecha.getMinutes() + ":" + fecha.getSeconds();
        pedido.fecha = fecha.toISOString();
        pedido.enviado = true;
        pedido.usuario = null;
        pedido.usuario = null;
        $http.put('/pedido/agregar', pedido).success(function (data, status, headers, config) {
            $scope.pedidoEnviadoConExito = true;
            $scope.limpiarCampos();
            //$scope.consultarSession();
            $scope.buscarTipoPlatos();
            $scope.buscarReservas();
            $scope.buscarMesas();
        });

        //pedido.usuario.usuario = $rootScope.credentials.username;

    };

    $scope.getImagen = function (producto) {
        var imagen = "";
        /*
         if (producto.idBebida !== undefined) {
         imagen = "/images/bebidas/bebida2.png";
         } else if (producto.idPlato !== undefined) {
         imagen = "/images/platosPrincipales/plato3.png";
         } else {
         imagen = "/images/promociones/combo3.png";
         }*/
        if (producto.tiposPlatos !== undefined) {
            imagen = "/images/" + producto.tiposPlatos.imagen;
        }
        else {
            imagen = "/images/promociones/combo3.png";
        }
        return imagen;
    };

    $scope.agrupar = function (asignacion) {
        return asignacion.turno + " - " + asignacion.mesa.ubicacion.descripcion;
    }


    /*
     $scope.consultarSession = function () {
     $http.get("/validar/session/mozo").success(function (data, status, headers, config) {
     $scope.usuario = data;
     }).error(function (data, status, headers, config) {
     $log.error(status + " " + data);
     });
     
     };*/
    $scope.limpiarCampos();
    //$scope.consultarSession();
    $scope.buscarTipoPlatos();
    $scope.buscarReservas();
    $scope.buscarMesas();
}
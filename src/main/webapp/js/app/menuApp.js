/* angular.module('MenuApp', [])
 .controller('MenuCtrl', MenuCtrl);*/

function MenuCtrl($scope, $http, $log) {
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
            title = producto.nombre + " (" + producto.descripcion+")";
        } else if (producto.idPlato !== undefined) {
            title = producto.descripcion;
        } else {
            title = producto.descripcion;
        }
        return title;
    };

    $scope.getImagen = function (producto) {
        var imagen = "";
        if(producto.tiposPlatos !== undefined){
            imagen="/images/"+producto.tiposPlatos.imagen;
        }else{
            imagen="/images/promociones/combo3.png";
        }
        
        /*
        if (producto.idBebida !== undefined) {
            imagen="/images/bebidas/bebida2.png";
        } else if (producto.idPlato !== undefined) {
            imagen="/images/platosPrincipales/plato3.png";
        } else {
            imagen="/images/promociones/combo3.png";
        }*/
        return imagen;
    };

    $scope.buscarTipoPlatos();

}



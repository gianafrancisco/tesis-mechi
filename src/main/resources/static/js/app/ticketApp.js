//angular.module('TicketApp', ['ui.bootstrap'])
//        .controller('TicketCtrl', TicketCtrl);


function TicketCtrl($rootScope, $scope, $http, $log, $filter) {
    $scope.pedidos;
    $scope.ticketGenerado = false;
    $scope.ticket = {
        pedidos: [],
        fecha: ""
    };
    $scope.total = 0;
    $scope.buscarPedidos = function () {
        $http.get("/pedido/entregados").success(function (data) {
            $scope.pedidos = data;
        });
    };
    $scope.getPedidoTitulo = function (pedido) {
        return $filter('date')(pedido.fecha, 'yyyy-MM-dd HH:mm:ss') + ": #" + pedido.idPedido + " / " + pedido.reserva.mesa.ubicacion.descripcion + " / " + pedido.reserva.mesa.numero;
    };
    $scope.pedidoListo = function (pedido) {
        $http.post("/pedido/entregado/" + pedido.idPedido).success(function (data) {
            $scope.buscarPedidos();
        });
    };
    $scope.getDescripcion = function (producto) {
        var title = "";
        if (producto.idBebida !== undefined) {
            title = producto.nombre + " " + producto.descripcion;
        } else if (producto.idPlato !== undefined) {
            title = producto.descripcion;
        } else {
            title = producto.descripcion;
        }
        return title;
    };
    $scope.getMozo = function (pedido) {
        return pedido.usuario.apellido + ", " + pedido.usuario.nombre;
    };
    $scope.agregar = function (pedido) {
        $scope.ticket.pedidos.push(pedido);
        $scope.actualizarTotal();
    };
    $scope.actualizarTotal = function () {
        $scope.total = 0;
        $scope.ticket.pedidos.forEach(function (value, index) {
            value.platos.forEach(function (value, index) {
                $scope.total += value.precio;
            });
            value.bebidas.forEach(function (value, index) {
                $scope.total += value.precio;
            });
            value.promociones.forEach(function (value, index) {
                $scope.total += value.precio;
            });
        });
    };
    $scope.generarTicket = function () {
        var fecha = new Date();
        $scope.ticket.fecha = fecha.toISOString();
        $http.post("/ticket/agregar", $scope.ticket).success(function (data) {
            $scope.buscarPedidos();
            $scope.limpiarLista();
            $scope.ticketGenerado = true;
        });
    };
    $scope.limpiarLista = function () {
        $scope.ticket = {
            pedidos: [],
            fecha: ""
        };
        $scope.total = 0;
    };
    $scope.buscarPedidos();
}
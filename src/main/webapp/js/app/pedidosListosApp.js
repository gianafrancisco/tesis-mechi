//angular.module('PedidoListoApp', ['ui.bootstrap'])
//        .controller('PedidoListoCtrl', PedidoListoCtrl);


function PedidoListoCtrl($rootScope, $scope, $http, $log) {
    $scope.pedidos;
    $scope.buscarPedidos = function () {
        $http.get("/pedido/listos").success(function (data) {
            $scope.pedidos = data;
        });
    };
    $scope.getPedidoTitulo = function (pedido) {
        return ": #" + pedido.idPedido + " / " + pedido.reserva.mesa.ubicacion.descripcion + " / " + pedido.reserva.mesa.numero;
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
    $scope.buscarPedidos();
}
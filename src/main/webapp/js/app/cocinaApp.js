angular.module('CocinaApp', ['ui.bootstrap'])
        .controller('CocinaCtrl', function ($scope, $http, $log) {
            $scope.pedidos;
            $scope.buscarPedidos = function () {
                $http.get("/pedido/cocina").success(function (data) {
                    $scope.pedidos = data;
                });
            };
            $scope.getPedidoTitulo = function (pedido) {
                return ": #"+pedido.idPedido + " / " + pedido.reserva.mesa.ubicacion.descripcion + " / " + pedido.reserva.mesa.numero;
            };
            $scope.pedidoListo = function(pedido){
                $http.post("/pedido/listo/"+pedido.idPedido).success(function(data){
                    $scope.buscarPedidos();
                });
            };
            $scope.buscarPedidos();
        });



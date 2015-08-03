angular.module('UsuarioApp', ['ui.bootstrap', 'ngRoute', 'chart.js', 'angularFileUpload']).config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {

        $routeProvider
                .when('/usuario', {templateUrl: '/admin/usuario.html', controller: 'UsuarioCtrl'})
                .when('/buscar', {templateUrl: '/admin/buscar.html', controller: 'BuscarCtrl'})
                .when('/ubicacion', {templateUrl: '/admin/ubicacion.html', controller: 'RegistrarUbicacionCtrl'})
                .when('/mesa', {templateUrl: '/admin/mesa.html', controller: 'RegistrarMesaCtrl'})
                .when('/tipoPlato', {templateUrl: '/admin/tipoPlato.html', controller: 'RegistrarTipoPlatoCtrl'})
                .when('/plato', {templateUrl: '/admin/plato.html', controller: 'RegistrarPlatoCtrl'})
                .when('/bebida', {templateUrl: '/admin/bebida.html', controller: 'RegistrarBebidaCtrl'})
                .when('/promocion', {templateUrl: '/admin/promocion.html', controller: 'RegistrarPromocionCtrl'})
                .when('/ticket', {templateUrl: '/admin/ticket.html', controller: 'TicketCtrl'})
                .when('/asignacion', {templateUrl: '/admin/asignacion.html', controller: 'AsignacionCtrl'})
                .when('/reporteReservas', {templateUrl: '/admin/reporteReservas.html', controller: 'ReportesReservasCtrl'})
                .when('/reporteVentas', {templateUrl: '/admin/reporteVentas.html', controller: 'ReportesVentasCtrl'})
                .when('/main', {templateUrl: '/admin/main.html'})
                .otherwise({redirectTo: '/main'});
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }]).controller('NavegacionAdminCtrl', NavegacionAdminCtrl)
        .controller('UsuarioCtrl', UsuarioCtrl).controller('BuscarCtrl', BuscarCtrl)
        .controller('RegistrarUbicacionCtrl', RegistrarUbicacionCtrl)
        .controller('RegistrarMesaCtrl', RegistrarMesaCtrl)
        .controller('RegistrarTipoPlatoCtrl', RegistrarTipoPlatoCtrl)
        .controller('RegistrarPlatoCtrl', RegistrarPlatoCtrl)
        .controller('RegistrarBebidaCtrl', RegistrarBebidaCtrl)
        .controller('RegistrarPromocionCtrl', RegistrarPromocionCtrl)
        .controller('AsignacionCtrl', AsignacionCtrl)
        .controller('ReportesReservasCtrl', ReportesReservasCtrl)
        .controller('ReportesVentasCtrl', ReportesVentasCtrl)
        .controller('TicketCtrl', TicketCtrl);

function NavegacionAdminCtrl($rootScope, $scope, $http, $log, $location, $window) {

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
            $location.path("#/main");
            //$window.location.href="/index.html";
        }).error(function (data) {
            $rootScope.authenticated = false;
            $location.path("#/main");
            //$window.location.href="/index.html";
        });
    };

    var authenticate = function (credentials, callback) {

        var headers = credentials ? {authorization: "Basic "
                    + btoa(credentials.username + ":" + credentials.password)
        } : {};

        $http.get('/validar/user', {headers: headers}).success(function (data) {
            if (data.authorities[0].authority === "MOZO") {
                $log.debug(data.authorities[0].authority);
                //$location.path("/administrador");
                $window.location.href = "/mozo.html";
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
            $location.path("#/main");
            callback && callback();
        });

    };
    authenticate();

}

function UsuarioCtrl($scope, $http, $log) {
    $scope.mostrarRegistro = true;
    $scope.mailExiste = false;
    $scope.usuario = new Usuario();
    $scope.urls = {
        administrador: "/usuario/agregar",
        mozo: "/mozo/agregar",
        cliente: "/cliente/agregar"
    }

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
        $scope.usuario = new Usuario();
        $scope.registrarUsuarioForm.$setPristine();
        $scope.registrarUsuarioForm.$setUntouched();
        $scope.mailExiste = false;
    };
    //TODO: Validar funcion save
    $scope.guardar = function () {
        //ésta es una peticion Rest
        //Seleccionamos entre las distintas URL en funcion del ROL
        $scope.mostrarRegistro = true;//oculta el campo si hay algún error a continuación
        if ($scope.usuario.rol != "") {
            console.log($scope.urls[$scope.usuario.rol]);
            $http.post($scope.urls[$scope.usuario.rol], $scope.usuario).success(function (data, status, headers, config) {
                console.log("El usuario ha sido agregado");
                $scope.mostrarRegistro = false;
                $scope.limpiar();
            });
        } else {
            alert("Seleccione el rol del usuario");
        }
    };

    $scope.verificarEmail = function (mail) {
        $http.put("/validar/email", {email: mail}).success(function (data, status, headers, config) {
            $scope.mailExiste = data;
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    }
}
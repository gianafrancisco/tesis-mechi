angular.module('InicioApp', ['ui.bootstrap', 'ngRoute']).config(['$routeProvider', '$httpProvider', function ($routeProvider, $httpProvider) {

        $routeProvider
                .when('/main', {templateUrl: '/cliente/main.html', controller: 'RegistrarClienteCtrl'})
                .when('/menu', {templateUrl: '/cliente/menu.html', controller: 'MenuCtrl'})
                .when('/misReservas', {templateUrl: '/cliente/misReservas.html', controller: 'MisReservasCtrl'})
                .when('/reserva', {templateUrl: '/cliente/reserva.html', controller: 'RegistrarReservaCtrl'})
                .otherwise({redirectTo: '/main'});
        $httpProvider.defaults.headers.common["X-Requested-With"] = 'XMLHttpRequest';
    }]).controller('NavegacionCtrl', NavegacionCtrl)
        .controller('RegistrarClienteCtrl', RegistrarClienteCtrl).controller('MenuCtrl', MenuCtrl)
        .controller('MisReservasCtrl', MisReservasCtrl).controller('RegistrarReservaCtrl', RegistrarReservaCtrl);


function RegistrarClienteCtrl($rootScope, $scope, $http, $log) {
    $rootScope.cliente = new Cliente();
    $scope.mostrarRegistro = true;
    $scope.datosModificados = true;
    $scope.mailExiste = false;
    $scope.login = false;
    //Mapeo desde html los elementos por ID a una variable interna de java
    $scope.crearCuenta = function () {
        $log.info("Creando nueva cuenta en el sistema");
        $http.put("/cliente/agregar", $rootScope.cliente).success(function (data, status, headers, config) {
            $log.info("El usuario ha sido creado correctamente");
            $scope.mostrarRegistro = false;
            $scope.login = false;
            $rootScope.cliente = data;
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    }
    $scope.actualizarCuenta = function () {
        $scope.datosModificados = true;
        $log.info("Guardando los cambios");
        $http.put("/cliente/modificar/" + $rootScope.cliente.idUsuario, $rootScope.cliente).success(function (data, status, headers, config) {
            $log.info("El usuario ha sido actualizado correctamente");
            $scope.datosModificados = false;
            $rootScope.cliente = data;
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };

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

    $scope.verificarEmail = function (mail) {
        $http.put("/validar/email", {email: mail}).success(function (data, status, headers, config) {
            $scope.mailExiste = data;
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };
    /*
     $rootScope.buscarCliente = function () {
     $log.debug($rootScope.credentials.username);
     $http.post('/validar/session/cliente', {username: $rootScope.credentials.username}).success(function (data) {
     $rootScope.cliente = data;
     });
     };
     */
}

function NavegacionCtrl($rootScope, $scope, $http, $log, $location, $window) {

    $rootScope.credentials = {};
    $scope.error = false;
    $scope.errores = {
        passwordIncorrecto: "El password o el nombre de usuario no es correcto, intente nuevamente"
    };

    $scope.buscarCliente = function () {
        //$log.debug($rootScope.credentials.username);
        $http.post('/validar/session/cliente', {username: $rootScope.credentials.username}).success(function (data) {
            $rootScope.cliente = data;
        });
    };

    $scope.clienteLogin = function () {
        authenticate($rootScope.credentials, function () {
            if ($rootScope.authenticated) {
                $location.path("/menu");
                $scope.error = false;
            } else {
                $location.path("/main");
                $scope.error = true;
            }
        });
    };

    $scope.clienteLogout = function () {
        $rootScope.credentials = {};
        $rootScope.cliente = new Cliente();
        $http.post('/logout', {}).success(function () {
            $rootScope.authenticated = false;
            $location.path("/main");
            $rootScope.credentials = {};
        }).error(function (data) {
            $rootScope.authenticated = false;
            $location.path("/main");
        });
    };

    var authenticate = function (credentials, callback) {

        var headers = credentials ? {authorization: "Basic "
                    + btoa(credentials.username + ":" + credentials.password)
        } : {};

        $http.get('/validar/user', {headers: headers}).success(function (data) {
            if (data.authorities[0].authority === "ADMIN") {
                $log.debug(data.authorities[0].authority);
                $window.location.href = "/admin.html";
            } else if (data.authorities[0].authority === "MOZO") {
                $window.location.href = "/mozo.html";
            }   
            if (data.name) {
                $rootScope.credentials.username = data.principal.username;
                $rootScope.authenticated = true;
                $scope.buscarCliente();
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
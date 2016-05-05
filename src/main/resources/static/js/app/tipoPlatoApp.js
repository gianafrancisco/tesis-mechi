//angular.module('TipoPlatoApp', [])
//        .controller('RegistrarTipoPlatoCtrl', RegistrarTipoPlatoCtrl);

function RegistrarTipoPlatoCtrl($rootScope, $scope, $http, $log, FileUploader) {
    $scope.uploader = new FileUploader({url: "/tipoPlato/upload", removeAfterUpload: true});
    $scope.mostrarRegistro = true;
    $scope.agregarModificar = false;
    $scope.tipoPlatoExiste = false;
    $scope.tipoPlato = new TipoPlato();
    $scope.agregar = true;
    $scope.messajes = {
        agregar: "El tipo de plato ha sido creado correctamente.",
        modificar: "El tipo de plato ha sido modificado correctamente.",
        eliminar: "El tipo de plato ha sido eliminado correctamente",
        eliminarError: "El tipo de plato no ha podido ser eliminado porque tiene platos o bebidas asignados."
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
        things: /^([A-Za-záéíóúñ\s\d*]{1,})+$/
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
        cosas: "Debe ingresar letras, números y espacios"
    };

    //Mapeo desde html los elementos por ID a una variable interna de java
    $scope.btnLimpiar = document.getElementById("borrar");
    $scope.btnGuardar = document.getElementById("guardar");

    $scope.limpiar = function () {
        $scope.tipoPlato = new TipoPlato();
        $scope.registrarTipoPlatoForm.$setPristine();
        $scope.registrarTipoPlatoForm.$setUntouched();
        $scope.tipoPlatoExiste = false;

    };

    $scope.cancelar = function () {
        $scope.agregarModificar = false;
        $scope.limpiar();
    };

    //TODO: Validar funcion save
    $scope.guardar = function () {
        //ésta es una peticion Rest
        //$scope.mostrarRegistro = true;
        if ($scope.tipoPlato.descripcion !== "") {

            var filename;

            if ($scope.uploader.queue[0] !== undefined) {
                filename = $scope.uploader.queue[0].file.name;
                $scope.uploader.uploadAll();
                $scope.tipoPlato.imagen = filename;
            }
            //$scope.uploader = new FileUploader({url: "/tipoPlato/upload"});

            if ($scope.agregar) {
                $http.post('/tipoPlato/agregar', $scope.tipoPlato).success(function (data, status, headers, config) {
                    console.log("El tipo de plato ha sido agregado");
                    $scope.mostrarRegistro = false;
                    $scope.mensaje = $scope.messajes["agregar"];
                    $scope.cancelar();
                });
            } else {
                $http.post('/tipoPlato/modificar/' + $scope.tipoPlato.idTipoPlato, $scope.tipoPlato).success(function (data, status, headers, config) {
                    console.log("El tipo de plato ha sido modificado");
                    $scope.mostrarRegistro = false;
                    $scope.mensaje = $scope.messajes["modificar"];
                    $scope.cancelar();
                });
            }
        }
    };

    $scope.verificarTipoPlato = function (descripcion) {
        $http.put("/validar/tipoPlato", {descripcion: descripcion}).success(function (data, status, headers, config) {
            $scope.tipoPlatoExiste = data;
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    }

    $scope.nuevaTipoPlato = function () {
        $scope.mostrarRegistro = true;
        $scope.tipoPlato = new TipoPlato();
        $scope.agregar = true;
        $scope.agregarModificar = true;
    };

    $scope.seleccionar = function (id) {
        $scope.mostrarRegistro = true;
        $scope.agregar = false;
        $scope.agregarModificar = true;
        angular.forEach($scope.tiposPlatos, function (tp) {
            if (tp.idTipoPlato === id) {
                $scope.tipoPlato = tp;
                return;
            }
        });
    };

    $scope.eliminar = function (id) {
        $scope.mostrarRegistro = true;
        $scope.agregar = false;
        $scope.agregarModificar = false;
        angular.forEach($scope.tiposPlatos, function (tp) {
            if (tp.idTipoPlato === id) {
                $scope.tipoPlato = tp;
                return;
            }
        });
        $http.put('/tipoPlato/borrar/' + $scope.tipoPlato.idTipoPlato).success(function (data, status, headers, config) {
            $scope.mostrarRegistro = false;
            if (data === true) {
                $scope.buscarTiposPlatos();
                $scope.mensaje = $scope.messajes["eliminar"];
            } else {
                $scope.mensaje = $scope.messajes["eliminarError"];
            }
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };

    $scope.buscarTiposPlatos = function () {
        var pagination = "?p=" + $scope.currentPage + "&ipp=" + $scope.itemsPerPage;
        $http.get("/tipoPlato/tiposPlatos"+pagination).success(function (data, status, headers, config) {
            $scope.tiposPlatos = data.items;
            $scope.totalItems = data.totalItems;
            $scope.tipoPlato = $scope.tiposPlatos[0];
        }).error(function (data, status, headers, config) {
            $log.error(status + " " + data);
        });
    };

    $scope.getImagen = function (tipoPlato) {
        return "/images/" + tipoPlato.imagen;
    };

}



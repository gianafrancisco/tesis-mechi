var Usuario = function (usuario, password,nombre, apellido, dni, telefono, calle, altura, fechaNacimiento, email){
	this.idUsuario = 0;
        this.usuario = usuario;
        this.password = password;
        this.rol = "";
        this.nombre = nombre;
        this.apellido = apellido;
        this.dni = dni;
        this.telefono = telefono;
        this.calle = calle;
        this.altura = altura;
        this.fechaNacimiento = fechaNacimiento;
        this.email = "";
        this.pedidos = [];
}
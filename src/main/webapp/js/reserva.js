var Reserva = function (estado, fecha, hora, cliente, mesa){
    this.estado = estado;
    this.fecha = fecha;
    this.hora = hora;
    this.cantidadPersonas = 1;
    this.cliente = cliente;
    this.mesa = mesa;
}
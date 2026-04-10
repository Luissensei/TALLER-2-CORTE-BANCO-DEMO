package modelo.excepciones;

// Se lanza cuando se intenta agregar algo pero ya no hay espacio
public class CapacidadExcedidaException extends SistemaBancarioException {
    
    private int capacidadMaxima;  // Cuál es el límite
    
    // Constructor que recibe el mensaje y el límite
    public CapacidadExcedidaException(String mensaje, int capacidadMaxima) {
        super(mensaje, "ERR_CAPACIDAD_EXCEDIDA");
        this.capacidadMaxima = capacidadMaxima;
    }
    
    // Getter para la capacidad máxima
    public int getCapacidadMaxima() {
        return capacidadMaxima;
    }
}
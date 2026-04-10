package modelo.excepciones;

// Se lanza cuando se busca un cliente que no existe en el sistema
public class ClienteNoEncontradoException extends SistemaBancarioException {
    
    private String idCliente;  // El ID del cliente que no se encontró
    
    // Constructor que recibe el mensaje y el ID
    public ClienteNoEncontradoException(String mensaje, String idCliente) {
        super(mensaje, "ERR_CLIENTE_NO_ENCONTRADO");
        this.idCliente = idCliente;
    }
    
    // Getter para el ID
    public String getIdCliente() {
        return idCliente;
    }
}
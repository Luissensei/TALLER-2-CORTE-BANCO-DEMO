package modelo.excepciones;

// Excepción unchecked: se lanza cuando se intenta una transición de estado inválida
public class EstadoTransaccionInvalidoException extends BancoRuntimeException {
    
    // Constructor que recibe el mensaje del error
    public EstadoTransaccionInvalidoException(String mensaje) {
        super(mensaje);
    }
}
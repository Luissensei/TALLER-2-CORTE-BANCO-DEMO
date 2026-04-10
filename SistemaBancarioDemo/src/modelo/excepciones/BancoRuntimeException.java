package modelo.excepciones;

// Excepción base (unchecked) = el compilador NO nos obliga a manejarla
// Se usa para errores que no son recuperables
public class BancoRuntimeException extends RuntimeException {
    
    // Constructor que recibe el mensaje de error
    public BancoRuntimeException(String mensaje) {
        super(mensaje);
    }
}
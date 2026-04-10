package modelo.excepciones;

import java.time.LocalDateTime;

// Excepción base (checked) = el compilador nos obliga a manejarla
// Se lanza cuando hay un problema en el sistema bancario
public class SistemaBancarioException extends Exception {
    
    private String codigoError;           // Código único del error
    private LocalDateTime timestamp;       // Cuándo ocurrió el error
    
    // Constructor que recibe el mensaje de error
    public SistemaBancarioException(String mensaje, String codigoError) {
        super(mensaje);
        this.codigoError = codigoError;
        this.timestamp = LocalDateTime.now(); // Registra la hora actual
    }
    
    // Getter para el código de error
    public String getCodigoError() {
        return codigoError;
    }
    
    // Getter para la fecha y hora del error
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    
    // Muestra toda la información del error
    @Override
    public String toString() {
        return "SistemaBancarioException{" +
                "mensaje='" + getMessage() + '\'' +
                ", codigoError='" + codigoError + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
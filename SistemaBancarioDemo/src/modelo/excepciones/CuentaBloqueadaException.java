package modelo.excepciones;

// Se lanza cuando alguien intenta operar en una cuenta que está bloqueada
public class CuentaBloqueadaException extends SistemaBancarioException {
    
    // Constructor que recibe el mensaje de error
    public CuentaBloqueadaException(String mensaje) {
        super(mensaje, "ERR_CUENTA_BLOQUEADA");
    }
}
package modelo.excepciones;

// Excepción unchecked: se lanza cuando alguien intenta hacer algo que no tiene permiso
public class PermisoInsuficienteException extends BancoRuntimeException {
    
    // Constructor que recibe el mensaje del error
    public PermisoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
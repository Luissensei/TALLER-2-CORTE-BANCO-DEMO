package modelo.excepciones;

// Se lanza cuando alguien intenta guardar datos que no son válidos
public class DatoInvalidoException extends BancoRuntimeException {
    
    private String campo;        // Cuál es el dato que está mal (ej: "email", "edad")
    private Object valorRecibido; // Qué valor inválido recibimos
    
    // Constructor que recibe el mensaje, el campo y el valor
    public DatoInvalidoException(String mensaje, String campo, Object valorRecibido) {
        super(mensaje);
        this.campo = campo;
        this.valorRecibido = valorRecibido;
    }
    
    // Getters para obtener los valores
    public String getCampo() {
        return campo;
    }
    
    public Object getValorRecibido() {
        return valorRecibido;
    }
}
package modelo.interfaces;

// Interface Notificable: para clientes que pueden recibir avisos del banco
public interface Notificable {
    
    // Enviar un mensaje al cliente
    void notificar(String mensaje);
    
    // Obtener cómo contactar al cliente
    String obtenerContacto();
    
    // Saber si el cliente acepta notificaciones
    boolean aceptaNotificaciones();
}
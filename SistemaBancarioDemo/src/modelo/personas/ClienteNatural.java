package modelo.personas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import modelo.enums.TipoDocumento;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;
import modelo.interfaces.Notificable;

// Clase ClienteNatural: un cliente que es una persona natural
// Implementa 3 interfaces: Consultable, Notificable, Auditable
public class ClienteNatural extends Cliente implements Consultable, Notificable, Auditable {
    
    // Atributos específicos de cliente natural
    private TipoDocumento tipoDocumento;  // CEDULA, PASAPORTE, etc
    private String numeroDocumento;       // El número del documento
    private boolean aceptaNotificaciones; // Si quiere recibir mensajes
    
    // Para Auditable: registrar cambios
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    
    // Constructor
    public ClienteNatural(String id, String nombre, String apellido, LocalDate fechaNacimiento,
                         String email, TipoDocumento tipoDocumento, String numeroDocumento) {
        // Llamar al constructor de Cliente con super()
        super(id, nombre, apellido, fechaNacimiento, email);
        
        this.tipoDocumento = tipoDocumento;
        this.numeroDocumento = numeroDocumento;
        this.aceptaNotificaciones = true; // Por defecto acepta notificaciones
        
        // Inicializar auditoría
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }
    
    // Getter de tipo documento
    public TipoDocumento getTipoDocumento() {
        return tipoDocumento;
    }
    
    // Setter de tipo documento
    public void setTipoDocumento(TipoDocumento tipoDocumento) {
        this.tipoDocumento = tipoDocumento;
    }
    
    // Getter de número documento
    public String getNumeroDocumento() {
        return numeroDocumento;
    }
    
    // Setter de número documento
    public void setNumeroDocumento(String numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }
    
    // Getter de acepta notificaciones
    public boolean isAceptaNotificaciones() {
        return aceptaNotificaciones;
    }
    
    // Setter de acepta notificaciones
    public void setAceptaNotificaciones(boolean aceptaNotificaciones) {
        this.aceptaNotificaciones = aceptaNotificaciones;
    }
    
    // ============= MÉTODOS DE PERSONA (abstractos) =============
    
    // Implementar: calcular edad
    @Override
    public int calcularEdad() {
        //retorna la edad
        return calcularEdad();
    }
    
    // Implementar: obtener tipo
    @Override
    public String obtenerTipo() {
        // Retorna que es un cliente natural
        return "ClienteNatural";
    }
    
    // Implementar: obtener documento
    @Override
    public String obtenerDocumentoIdentidad() {
        // Retorna el tipo de documento y el número
        return tipoDocumento + ": " + numeroDocumento;
    }
    
    // ============= MÉTODOS DE CONSULTABLE =============
    
    // Retorna un resumen de los datos principales
    @Override
    public String obtenerResumen() {
        return "ClienteNatural{" +
                "nombre='" + this.getNombreCompleto() + '\'' +
                ", documento='" + obtenerDocumentoIdentidad() + '\'' +
                ", cuentas=" + this.getCantidadCuentas() +
                '}';
    }
    
    // Indicar si está activo
    @Override
    public boolean estaActivo() {
        // Un cliente natural siempre está activo
        return true;
    }
    
    // ============= MÉTODOS DE NOTIFICABLE =============
    
    // Enviar una notificación al cliente (imprimirla en consola)
    @Override
    public void notificar(String mensaje) {
        // Solo si acepta notificaciones
        if (aceptaNotificaciones) {
            System.out.println("[NOTIFICACIÓN para " + this.getNombreCompleto() + "] " + mensaje);
        }
    }
    
    // Obtener el contacto (email)
    @Override
    public String obtenerContacto() {
        return this.getEmail();
    }
    
    // ============= MÉTODOS DE AUDITABLE =============
    
    // Retorna cuándo se creó
    @Override
    public LocalDateTime obtenerFechaCreacion() {
        return fechaCreacion;
    }
    
    // Retorna cuándo se modificó por última vez
    @Override
    public LocalDateTime obtenerUltimaModificacion() {
        return ultimaModificacion;
    }
    
    // Retorna quién hizo el último cambio
    @Override
    public String obtenerUsuarioModificacion() {
        return usuarioModificacion;
    }
    
    // Registrar una nueva modificación
    @Override
    public void registrarModificacion(String usuario) {
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuario;
    }

    @Override
    public boolean aceptaNotificaciones() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
package modelo.personas;

import java.time.LocalDate;
import java.time.LocalDateTime;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;
import modelo.interfaces.Notificable;

// Clase ClienteEmpresarial: un cliente que es una empresa
// Implementa las mismas 3 interfaces que ClienteNatural
public class ClienteEmpresarial extends Cliente implements Consultable, Notificable, Auditable {
    
    // Atributos específicos de cliente empresarial
    private String nit;                  // Número de identificación tributaria
    private String razonSocial;          // Nombre de la empresa
    private String representanteLegal;   // Quién firma en nombre de la empresa
    private boolean aceptaNotificaciones; // Si quiere recibir mensajes
    
    // Para Auditable: registrar cambios
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    
    // Constructor
    public ClienteEmpresarial(String id, String nombre, String apellido, LocalDate fechaNacimiento,
                             String email, String nit, String razonSocial, String representanteLegal) {
        // Llamar al constructor de Cliente con super()
        super(id, nombre, apellido, fechaNacimiento, email);
        
        this.nit = nit;
        this.razonSocial = razonSocial;
        this.representanteLegal = representanteLegal;
        this.aceptaNotificaciones = true; // Por defecto acepta notificaciones
        
        // Inicializar auditoría
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }
    
    // Getter de NIT
    public String getNit() {
        return nit;
    }
    
    // Setter de NIT
    public void setNit(String nit) {
        this.nit = nit;
    }
    
    // Getter de razón social
    public String getRazonSocial() {
        return razonSocial;
    }
    
    // Setter de razón social
    public void setRazonSocial(String razonSocial) {
        this.razonSocial = razonSocial;
    }
    
    // Getter de representante legal
    public String getRepresentanteLegal() {
        return representanteLegal;
    }
    
    // Setter de representante legal
    public void setRepresentanteLegal(String representanteLegal) {
        this.representanteLegal = representanteLegal;
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
    
    // Implementar: calcular edad (para una empresa, retorna 0)
    @Override
    public int calcularEdad() {
        return 0; // Una empresa no tiene "edad" como persona
    }
    
    // Implementar: obtener tipo
    @Override
    public String obtenerTipo() {
        // Retorna que es cliente empresarial
        return "ClienteEmpresarial";
    }
    
    // Implementar: obtener documento
    @Override
    public String obtenerDocumentoIdentidad() {
        // Retorna el NIT
        return "NIT: " + nit;
    }
    
    // ============= MÉTODOS DE CONSULTABLE =============
    
    // Retorna un resumen de los datos principales
    @Override
    public String obtenerResumen() {
        return "ClienteEmpresarial{" +
                "razonSocial='" + razonSocial + '\'' +
                ", nit='" + nit + '\'' +
                ", representante='" + representanteLegal + '\'' +
                ", cuentas=" + this.getCantidadCuentas() +
                '}';
    }
    
    // Indicar si está activo
    @Override
    public boolean estaActivo() {
        // Una empresa siempre está activa
        return true;
    }
    
    // ============= MÉTODOS DE NOTIFICABLE =============
    
    // Enviar una notificación a la empresa
    @Override
    public void notificar(String mensaje) {
        // Solo si acepta notificaciones
        if (aceptaNotificaciones) {
            System.out.println("[NOTIFICACIÓN para " + razonSocial + "] " + mensaje);
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
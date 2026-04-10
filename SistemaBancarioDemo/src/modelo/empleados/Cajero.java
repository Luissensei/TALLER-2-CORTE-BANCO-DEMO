package modelo.empleados;

import java.time.LocalDate;
import modelo.abstractas.Empleado;
import modelo.enums.Turno;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;
import java.time.LocalDateTime;

// Clase Cajero: un tipo específico de empleado
// Implementa Consultable y Auditable
public class Cajero extends Empleado implements Consultable, Auditable {
    
    // Atributos específicos del cajero
    private Turno turno;                // MAÑANA, TARDE, NOCHE
    private String sucursalAsignada;    // En cuál sucursal trabaja
    private int transaccionesDia;       // Cuántas transacciones hizo hoy
    
    // Para Auditable
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    
    // Constructor
    public Cajero(String id, String nombre, String apellido, LocalDate fechaNacimiento,
                  String email, String legajo, LocalDate fechaContratacion, 
                  double salarioBase, Turno turno, String sucursalAsignada) {
        // Llamar al constructor de Empleado con super()
        super(id, nombre, apellido, fechaNacimiento, email, legajo, fechaContratacion, salarioBase);
        
        this.turno = turno;
        this.sucursalAsignada = sucursalAsignada;
        this.transaccionesDia = 0; // Al principio no ha hecho ninguna transacción
        
        // Inicializar auditoría
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }
    
    // Getter de turno
    public Turno getTurno() {
        return turno;
    }
    
    // Setter de turno
    public void setTurno(Turno turno) {
        this.turno = turno;
    }
    
    // Getter de sucursal asignada
    public String getSucursalAsignada() {
        return sucursalAsignada;
    }
    
    // Setter de sucursal asignada
    public void setSucursalAsignada(String sucursalAsignada) {
        this.sucursalAsignada = sucursalAsignada;
    }
    
    // Getter de transacciones del día
    public int getTransaccionesDia() {
        return transaccionesDia;
    }
    
    // Setter de transacciones del día
    public void setTransaccionesDia(int transaccionesDia) {
        this.transaccionesDia = transaccionesDia;
    }
    
    // ============= MÉTODOS ABSTRACTOS DE EMPLEADO =============
    
    // Calcular salario del cajero
    // Formula: salarioBase + bono (100 pesos por cada transacción)
    @Override
    public double calcularSalario() {
        return this.getSalarioBase() + this.calcularBono();
    }
    
    // Calcular bono del cajero
    // Formula: 100 pesos por cada transacción hecha en el día
    @Override
    public double calcularBono() {
        return transaccionesDia * 100;
    }
    
    // ============= MÉTODOS DE CONSULTABLE =============
    
    // Retorna un resumen del cajero
    @Override
    public String obtenerResumen() {
        return "Cajero{" +
                "nombre='" + this.getNombreCompleto() + '\'' +
                ", turno=" + turno +
                ", sucursal='" + sucursalAsignada + '\'' +
                ", transacciones=" + transaccionesDia +
                '}';
    }
    
    // Indicar si está activo
    @Override
    public boolean estaActivo() {
        // Un cajero está activo si el empleado está activo
        return this.isActivo();
    }
    
    // Obtener el tipo
    @Override
    public String obtenerTipo() {
        return "Cajero";
    }
    
    // ============= MÉTODOS DE AUDITABLE =============
    
    @Override
    public LocalDateTime obtenerFechaCreacion() {
        return fechaCreacion;
    }
    
    @Override
    public LocalDateTime obtenerUltimaModificacion() {
        return ultimaModificacion;
    }
    
    @Override
    public String obtenerUsuarioModificacion() {
        return usuarioModificacion;
    }
    
    @Override
    public void registrarModificacion(String usuario) {
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuario;
    }
}
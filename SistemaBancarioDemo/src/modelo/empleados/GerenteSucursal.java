package modelo.empleados;

import java.time.LocalDate;
import java.time.LocalDateTime;
import modelo.abstractas.Empleado;
import modelo.excepciones.PermisoInsuficienteException;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;

// Clase GerenteSucursal: empleado con nivel gerencial
// Implementa Consultable y Auditable
public class GerenteSucursal extends Empleado implements Consultable, Auditable {
    
    // Atributos específicos del gerente
    private String sucursal;               // Cuál sucursal maneja
    private double presupuestoAnual;       // Dinero disponible para la sucursal
    private Empleado[] empleadosACargo;    // Máximo 30 empleados
    private int cantidadEmpleados;         // Cuántos empleados tiene
    
    // Para Auditable
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    
    // Constructor
    public GerenteSucursal(String id, String nombre, String apellido, LocalDate fechaNacimiento,
                          String email, String legajo, LocalDate fechaContratacion,
                          double salarioBase, String sucursal, double presupuestoAnual) {
        // Llamar al constructor de Empleado
        super(id, nombre, apellido, fechaNacimiento, email, legajo, fechaContratacion, salarioBase);
        
        this.sucursal = sucursal;
        this.presupuestoAnual = presupuestoAnual;
        this.empleadosACargo = new Empleado[30]; // Array para 30 empleados
        this.cantidadEmpleados = 0;
        
        // Inicializar auditoría
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }
    
    // Getter de sucursal
    public String getSucursal() {
        return sucursal;
    }
    
    // Setter de sucursal
    public void setSucursal(String sucursal) {
        this.sucursal = sucursal;
    }
    
    // Getter de presupuesto anual
    public double getPresupuestoAnual() {
        return presupuestoAnual;
    }
    
    // Setter de presupuesto anual
    public void setPresupuestoAnual(double presupuestoAnual) {
        this.presupuestoAnual = presupuestoAnual;
    }
    
    // Agregar un empleado a cargo
    public void asignarEmpleado(Empleado empleado) {
        if (cantidadEmpleados < empleadosACargo.length) {
            empleadosACargo[cantidadEmpleados] = empleado;
            cantidadEmpleados++;
        }
    }
    
    // Retorna copia de empleados a cargo
    public Empleado[] getEmpleadosACargo() {
        Empleado[] copia = new Empleado[cantidadEmpleados];
        System.arraycopy(empleadosACargo, 0, copia, 0, cantidadEmpleados);
        return copia;
    }
    
    // ============= MÉTODOS ABSTRACTOS DE EMPLEADO =============
    
    // Calcular salario del gerente
    // Formula: salarioBase + bono por antigüedad + bono fijo de gerencia
    @Override
    public double calcularSalario() {
        return this.getSalarioBase() + this.calcularBono();
    }
    
    // Calcular bono del gerente
    // Formula: 5000 pesos fijos + 1000 pesos por cada año de antigüedad
    @Override
    public double calcularBono() {
        double bonoFijo = 5000;
        double bonoAntigüedad = this.calcularAntigüedad() * 1000;
        return bonoFijo + bonoAntigüedad;
    }
    
    // Método especial: aprobar un crédito
    // Solo el gerente puede hacerlo, otros empleados no
    public void aprobarCredito(String idCliente, double monto) {
        // Verificar que sea el gerente quien llama (en demo se verifica manualmente)
        System.out.println("Crédito de $" + monto + " aprobado para cliente " + idCliente);
    }
    
    // ============= MÉTODOS DE CONSULTABLE =============
    
    @Override
    public String obtenerResumen() {
        return "GerenteSucursal{" +
                "nombre='" + this.getNombreCompleto() + '\'' +
                ", sucursal='" + sucursal + '\'' +
                ", presupuesto=" + presupuestoAnual +
                ", empleados=" + cantidadEmpleados +
                '}';
    }
    
    @Override
    public boolean estaActivo() {
        return this.isActivo();
    }
    
    @Override
    public String obtenerTipo() {
        return "GerenteSucursal";
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
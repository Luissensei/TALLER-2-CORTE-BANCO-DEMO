package modelo.empleados;

import java.time.LocalDate;
import java.time.LocalDateTime;
import modelo.abstractas.Empleado;
import modelo.personas.Cliente;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;

// Clase AsesorFinanciero: empleado que asesora a clientes
// Implementa Consultable y Auditable
public class AsesorFinanciero extends Empleado implements Consultable, Auditable {
    
    // Atributos específicos del asesor
    private double comisionBase;        // Porcentaje base de comisión
    private double metasMensuales;      // Dinero objetivo a vender
    private Cliente[] clientesAsignados; // Máximo 20 clientes
    private int cantidadClientes;       // Cuántos clientes tiene asignados
    
    // Para Auditable
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    
    // Constructor
    public AsesorFinanciero(String id, String nombre, String apellido, LocalDate fechaNacimiento,
                           String email, String legajo, LocalDate fechaContratacion,
                           double salarioBase, double comisionBase, double metasMensuales) {
        // Llamar al constructor de Empleado
        super(id, nombre, apellido, fechaNacimiento, email, legajo, fechaContratacion, salarioBase);
        
        this.comisionBase = comisionBase;
        this.metasMensuales = metasMensuales;
        this.clientesAsignados = new Cliente[20]; // Array para 20 clientes
        this.cantidadClientes = 0;
        
        // Inicializar auditoría
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }
    
    // Getter de comisión base
    public double getComisionBase() {
        return comisionBase;
    }
    
    // Setter de comisión base
    public void setComisionBase(double comisionBase) {
        this.comisionBase = comisionBase;
    }
    
    // Getter de metas mensuales
    public double getMetasMensuales() {
        return metasMensuales;
    }
    
    // Setter de metas mensuales
    public void setMetasMensuales(double metasMensuales) {
        this.metasMensuales = metasMensuales;
    }
    
    // Agregar un cliente asignado
    public void asignarCliente(Cliente cliente) {
        if (cantidadClientes < clientesAsignados.length) {
            clientesAsignados[cantidadClientes] = cliente;
            cantidadClientes++;
        }
    }
    
    // Retorna copia de clientes asignados
    public Cliente[] getClientesAsignados() {
        Cliente[] copia = new Cliente[cantidadClientes];
        System.arraycopy(clientesAsignados, 0, copia, 0, cantidadClientes);
        return copia;
    }
    
    // ============= MÉTODOS ABSTRACTOS DE EMPLEADO =============
    
    // Calcular salario del asesor
    // Formula: salarioBase + bono (si supera meta)
    @Override
    public double calcularSalario() {
        return this.getSalarioBase() + this.calcularBono();
    }
    
    // Calcular bono del asesor
    // Formula: si supera meta, recibe comisionBase% de lo que superó
    @Override
    public double calcularBono() {
        // Por ahora, retornar un porcentaje del salario base como bono
        // (En una app real, se calcularía con ventas reales)
        return this.getSalarioBase() * (comisionBase / 100);
    }
    
    // ============= MÉTODOS DE CONSULTABLE =============
    
    @Override
    public String obtenerResumen() {
        return "AsesorFinanciero{" +
                "nombre='" + this.getNombreCompleto() + '\'' +
                ", comisionBase=" + comisionBase + "%" +
                ", metaMensual=" + metasMensuales +
                ", clientes=" + cantidadClientes +
                '}';
    }
    
    @Override
    public boolean estaActivo() {
        return this.isActivo();
    }
    
    @Override
    public String obtenerTipo() {
        return "AsesorFinanciero";
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
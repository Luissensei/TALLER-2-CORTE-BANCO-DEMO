package modelo.abstractas;

import java.time.LocalDate;
import java.time.Period;
import modelo.excepciones.DatoInvalidoException;

// Clase abstracta Empleado: hereda de Persona y agrega info de empleado
// No se puede instanciar directamente (es abstracta)
public abstract class Empleado extends Persona {
    
    // Atributos adicionales específicos de empleados
    private String legajo;              // Número de empleado
    private LocalDate fechaContratacion; // Cuándo fue contratado
    private double salarioBase;         // Sueldo base mensual
    private boolean activo;             // Si está trabajando actualmente
    
    // Constructor
    public Empleado(String id, String nombre, String apellido, LocalDate fechaNacimiento,
                    String email, String legajo, LocalDate fechaContratacion, 
                    double salarioBase) {
        // Llamar al constructor de la clase padre (Persona)
        // Esto es obligatorio con super()
        super(id, nombre, apellido, fechaNacimiento, email);
        
        // Validar datos específicos de empleado
        this.legajo = legajo;
        setFechaContratacion(fechaContratacion);
        setSalarioBase(salarioBase);
        this.activo = true; // Por defecto, al crear un empleado, está activo
    }
    
    // Getter de legajo
    public String getLegajo() {
        return legajo;
    }
    
    // Setter de legajo
    public void setLegajo(String legajo) {
        this.legajo = legajo;
    }
    
    // Getter de fecha de contratación
    public LocalDate getFechaContratacion() {
        return fechaContratacion;
    }
    
    // Setter de fecha de contratación
    public void setFechaContratacion(LocalDate fechaContratacion) {
        // Validar que no sea una fecha futura
        if (fechaContratacion != null && fechaContratacion.isAfter(LocalDate.now())) {
            throw new DatoInvalidoException("La fecha de contratación no puede ser en el futuro", 
                                          "fechaContratacion", fechaContratacion);
        }
        this.fechaContratacion = fechaContratacion;
    }
    
    // Getter de salario base
    public double getSalarioBase() {
        return salarioBase;
    }
    
    // Setter de salario base
    public void setSalarioBase(double salarioBase) {
        // Validar que el salario sea mayor a 0
        if (salarioBase <= 0) {
            throw new DatoInvalidoException("El salario debe ser mayor a 0", "salarioBase", salarioBase);
        }
        this.salarioBase = salarioBase;
    }
    
    // Getter de activo
    public boolean isActivo() {
        return activo;
    }
    
    // Setter de activo
    public void setActivo(boolean activo) {
        this.activo = activo;
    }
    
    // Método concreto (implementado): calcula cuántos años lleva trabajando
    public int calcularAntigüedad() {
        // Calcula la diferencia entre ahora y la fecha de contratación
        Period periodo = Period.between(fechaContratacion, LocalDate.now());
        return periodo.getYears(); // Retorna solo los años
    }
    
    // Implementar el método abstracto calcularEdad() de Persona
    @Override
    public int calcularEdad() {
        // Calcular la diferencia entre ahora y la fecha de nacimiento
        Period periodo = Period.between(this.getFechaNacimiento(), LocalDate.now());
        return periodo.getYears(); // Retorna solo los años
    }
    
    // Implementar el método abstracto obtenerTipo() de Persona
    @Override
    public String obtenerTipo() {
        // Retorna "Empleado" por defecto (las subclases pueden cambiar esto)
        return "Empleado";
    }
    
    // Implementar el método abstracto obtenerDocumentoIdentidad() de Persona
    @Override
    public String obtenerDocumentoIdentidad() {
        // Los empleados solo tienen su ID/legajo
        return this.getId();
    }
    
    // ============= MÉTODOS ABSTRACTOS DE EMPLEADO =============
    // Estos deben ser implementados por los tipos específicos de empleados
    
    // Cada tipo de empleado calcula el salario diferente
    // (Cajero, AsesorFinanciero, GerenteSucursal)
    public abstract double calcularSalario();
    
    // Cada tipo de empleado tiene un bono diferente
    public abstract double calcularBono();
}
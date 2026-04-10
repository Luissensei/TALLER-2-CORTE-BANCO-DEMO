package modelo.abstractas;

import java.time.LocalDate;
import java.time.Period;
import modelo.excepciones.DatoInvalidoException;

// Clase abstracta Persona: no se puede instanciar directamente
// Es la clase padre de empleados y clientes
public abstract class Persona {
    
    // Atributos privados = no se pueden acceder directamente desde fuera
    private String id;                  // ID único de la persona
    private String nombre;              // Primer nombre
    private String apellido;            // Apellido
    private LocalDate fechaNacimiento;  // Fecha de nacimiento
    private String email;               // Correo electrónico
    
    // Constructor: se ejecuta cuando creamos una nueva persona
    public Persona(String id, String nombre, String apellido, LocalDate fechaNacimiento, String email) {
        // Validar los datos antes de guardarlos
        setId(id);                           // Usa el setter para validar
        this.nombre = nombre;
        this.apellido = apellido;
        setFechaNacimiento(fechaNacimiento); // Usa el setter para validar
        setEmail(email);                     // Usa el setter para validar
    }
    
    // Getter de id: permite leer el id
    public String getId() {
        return id;
    }
    
    // Setter de id: permite cambiar el id pero lo valida primero
    public void setId(String id) {
        // Si es nulo (null) o está vacío (sin caracteres), lanzar excepción
        if (id == null || id.trim().isEmpty()) {
            // Lanza una excepción unchecked (no es obligatorio manejarla)
            throw new DatoInvalidoException("El ID no puede estar vacío", "id", id);
        }
        this.id = id;
    }
    
    // Getter de nombre
    public String getNombre() {
        return nombre;
    }
    
    // Setter de nombre
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    
    // Getter de apellido
    public String getApellido() {
        return apellido;
    }
    
    // Setter de apellido
    public void setApellido(String apellido) {
        this.apellido = apellido;
    }
    
    // Getter de fecha de nacimiento
    public LocalDate getFechaNacimiento() {
        return fechaNacimiento;
    }
    
    // Setter de fecha de nacimiento
    public void setFechaNacimiento(LocalDate fechaNacimiento) {
        // Validar que no sea una fecha futura
        if (fechaNacimiento != null && fechaNacimiento.isAfter(LocalDate.now())) {
            throw new DatoInvalidoException("La fecha de nacimiento no puede ser en el futuro", "fechaNacimiento", fechaNacimiento);
        }
        this.fechaNacimiento = fechaNacimiento;
    }
    
    // Getter de email
    public String getEmail() {
        return email;
    }
    
    // Setter de email
    public void setEmail(String email) {
        // Validar que el email contenga @
        if (email != null && !email.contains("@")) {
            throw new DatoInvalidoException("El email debe contener @", "email", email);
        }
        this.email = email;
    }
    
    // Método concreto (implementado): todos heredan esto
    // Retorna el nombre completo en formato "Nombre Apellido"
    public String getNombreCompleto() {
        return nombre + " " + apellido;
    }
    
    // ============= MÉTODOS ABSTRACTOS =============
    // Estos métodos DEBEN ser implementados por las subclases
    
    // Cada tipo de persona calcula su edad de forma distinta
    // (por eso es abstracto)
    public abstract int calcularEdad();
    
    // Cada tipo de persona retorna un tipo distinto
    // (ej: "ClienteNatural", "Cajero", etc)
    public abstract String obtenerTipo();
    
    // Cada tipo de persona tiene un documento diferente
    // (ej: cédula, pasaporte, etc)
    public abstract String obtenerDocumentoIdentidad();
}
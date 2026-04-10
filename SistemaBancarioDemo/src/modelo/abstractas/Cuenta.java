package modelo.abstractas;

import java.time.LocalDateTime;
import modelo.banco.Transaccion;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.CuentaBloqueadaException;

// Clase abstracta Cuenta: representa una cuenta bancaria
// No se puede instanciar directamente
public abstract class Cuenta {
    
    // Atributos privados
    private String numeroCuenta;              // Número único de la cuenta
    private double saldo;                     // Dinero disponible
    private boolean bloqueada;                // Si está bloqueada o no
    private final LocalDateTime fechaCreacion;      // Cuándo se creó
    private LocalDateTime ultimaModificacion; // Cuándo fue el ultimo cambio
    private String usuarioModificacion;       // Quién hizo el ultimo cambio
    
    // Array estático: guarda el historial de transacciones (máx 20)
    private final Transaccion[] historial = new Transaccion[20];
    private int cantidadTransacciones = 0; // Cuántas transacciones hay registradas
    
    // Constructor
    public Cuenta(String numeroCuenta, double saldo) {
        this.numeroCuenta = numeroCuenta;
        this.saldo = saldo;
        this.bloqueada = false; // Por defecto, la cuenta no está bloqueada
        this.fechaCreacion = LocalDateTime.now(); // Se crea ahora
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA"; // Lo crea el sistema
    }
    
    // Getter de número de cuenta
    public String getNumeroCuenta() {
        return numeroCuenta;
    }
    
    // Setter de número de cuenta
    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
    
    // Getter de saldo
    public double getSaldo() {
        return saldo;
    }
    
    // Setter de saldo (con cuidado, no se usa directamente)
    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }
    
    // Getter de bloqueada
    public boolean isBloqueada() {
        return bloqueada;
    }
    
    // Setter de bloqueada
    public void setBloqueada(boolean bloqueada) {
        this.bloqueada = bloqueada;
    }
    
    // Getter de fecha de creación
    public LocalDateTime getFechaCreacion() {
        return fechaCreacion;
    }
    
    // Getter de última modificación
    public LocalDateTime getUltimaModificacion() {
        return ultimaModificacion;
    }
    
    // Getter de usuario que modificó
    public String getUsuarioModificacion() {
        return usuarioModificacion;
    }
    
    // Método que registra una modificación
    public void registrarModificacion(String usuario) {
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuario;
    }
    
    // ============= MÉTODOS CONCRETOS HEREDABLES =============
    
    // Verifica si la cuenta está bloqueada, si sí, lanza excepción
    public void verificarBloqueada() throws CuentaBloqueadaException {
        if (this.bloqueada) {
            throw new CuentaBloqueadaException("La cuenta " + numeroCuenta + " está bloqueada");
        }
    }
    
    // Agrega una transacción al historial
    public void agregarAlHistorial(Transaccion transaccion) throws CapacidadExcedidaException {
        // Si ya hay 20 transacciones, no cabe más
        if (cantidadTransacciones >= historial.length) {
            throw new CapacidadExcedidaException("El historial está lleno (máximo 20 transacciones)", 20);
        }
        // Guardar la transacción en el array
        historial[cantidadTransacciones] = transaccion;
        cantidadTransacciones++;
    }
    
    // Retorna una COPIA del historial (no el original)
    public Transaccion[] getHistorial() {
        // Crear un array nuevo con solo las transacciones que hay
        Transaccion[] copia = new Transaccion[cantidadTransacciones];
        // Copiar los datos usando System.arraycopy
        System.arraycopy(historial, 0, copia, 0, cantidadTransacciones);
        return copia; // Retornar la copia, no el original
    }
    
    // ============= MÉTODOS ABSTRACTOS =============
    
    // Cada tipo de cuenta calcula intereses de forma diferente
    public abstract double calcularInteres();
    
    // Cada tipo de cuenta tiene un límite de retiro diferente
    public abstract double getLimiteRetiro();
    
    // Cada tipo retorna su nombre
    public abstract String getTipoCuenta();
}
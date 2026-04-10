package modelo.banco;

import java.time.LocalDateTime;
import modelo.abstractas.Cuenta;
import modelo.enums.EstadoTransaccion;
import modelo.excepciones.EstadoTransaccionInvalidoException;

// Clase Transaccion: representa un movimiento de dinero
// Puede ser depósito, retiro o transferencia
public class Transaccion {
    
    // Atributos
    private String id;                    // ID único de la transacción
    private Cuenta cuentaOrigen;          // De dónde sale el dinero
    private Cuenta cuentaDestino;         // A dónde va el dinero (null en depósitos/retiros)
    private double monto;                 // Cuánto dinero se mueve
    private EstadoTransaccion estado;     // PENDIENTE, PROCESANDO, COMPLETADA, etc
    private LocalDateTime fecha;          // Cuándo se hizo
    private String descripcion;           // Qué es la transacción
    
    // Constructor
    public Transaccion(String id, Cuenta cuentaOrigen, Cuenta cuentaDestino, 
                      double monto, String descripcion) {
        this.id = id;
        this.cuentaOrigen = cuentaOrigen;
        this.cuentaDestino = cuentaDestino;
        this.monto = monto;
        this.estado = EstadoTransaccion.PENDIENTE; // Comienza en PENDIENTE
        this.fecha = LocalDateTime.now();
        this.descripcion = descripcion;
    }
    
    // Getter de id
    public String getId() {
        return id;
    }
    
    // Getter de cuenta origen
    public Cuenta getCuentaOrigen() {
        return cuentaOrigen;
    }
    
    // Getter de cuenta destino
    public Cuenta getCuentaDestino() {
        return cuentaDestino;
    }
    
    // Getter de monto
    public double getMonto() {
        return monto;
    }
    
    // Getter de estado
    public EstadoTransaccion getEstado() {
        return estado;
    }
    
    // Getter de fecha
    public LocalDateTime getFecha() {
        return fecha;
    }
    
    // Getter de descripción
    public String getDescripcion() {
        return descripcion;
    }
    
    // ============= MÉTODO CRÍTICO: cambiarEstado =============
    
    // Cambiar el estado de la transacción
    // Valida que la transición sea válida según las reglas
    public void cambiarEstado(EstadoTransaccion nuevoEstado) {
        // Obtener el estado actual
        EstadoTransaccion estadoActual = this.estado;
        
        // Validar si la transición es permitida
        boolean transicionValida = false;
        
        // Según el estado actual, verificar qué estados son válidos
        switch (estadoActual) {
            case PENDIENTE:
                // Desde PENDIENTE se puede ir a PROCESANDO o RECHAZADA
                if (nuevoEstado == EstadoTransaccion.PROCESANDO || 
                    nuevoEstado == EstadoTransaccion.RECHAZADA) {
                    transicionValida = true;
                }
                break;
                
            case PROCESANDO:
                // Desde PROCESANDO se puede ir a COMPLETADA o RECHAZADA
                if (nuevoEstado == EstadoTransaccion.COMPLETADA || 
                    nuevoEstado == EstadoTransaccion.RECHAZADA) {
                    transicionValida = true;
                }
                break;
                
            case COMPLETADA:
                // Desde COMPLETADA se puede ir a REVERTIDA
                if (nuevoEstado == EstadoTransaccion.REVERTIDA) {
                    transicionValida = true;
                }
                break;
                
            case RECHAZADA:
            case REVERTIDA:
                // Son estados finales, no se puede transicionar
                transicionValida = false;
                break;
        }
        
        // Si la transición no es válida, lanzar excepción
        if (!transicionValida) {
            throw new EstadoTransaccionInvalidoException(
                "No se puede cambiar de " + estadoActual + " a " + nuevoEstado
            );
        }
        
        // Si llegamos aquí, la transición es válida
        this.estado = nuevoEstado;
        System.out.println("✓ Estado cambiado a: " + nuevoEstado);
    }
    
    // ============= MÉTODO: generarComprobante =============
    
    // Retorna un texto con los datos formateados de la transacción
    public String generarComprobante() {
        String comprobante = "=== COMPROBANTE DE TRANSACCIÓN ===\n";
        comprobante += "ID: " + id + "\n";
        comprobante += "Fecha: " + fecha + "\n";
        comprobante += "Descripción: " + descripcion + "\n";
        
        // Si hay cuenta origen
        if (cuentaOrigen != null) {
            comprobante += "Cuenta Origen: " + cuentaOrigen.getNumeroCuenta() + "\n";
        }
        
        // Si hay cuenta destino
        if (cuentaDestino != null) {
            comprobante += "Cuenta Destino: " + cuentaDestino.getNumeroCuenta() + "\n";
        }
        
        comprobante += "Monto: $" + monto + "\n";
        comprobante += "Estado: " + estado + "\n";
        comprobante += "===================================";
        
        return comprobante;
    }
}
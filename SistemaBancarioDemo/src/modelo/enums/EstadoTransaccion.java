package modelo.enums;

// Enum que define los posibles estados por los que pasa una transacción
// Una transacción es un movimiento de dinero (depósito, retiro, etc.)
public enum EstadoTransaccion {
    PENDIENTE,      // La transacción acaba de crearse, no se ha procesado
    PROCESANDO,     // El banco está verificando la transacción
    COMPLETADA,     // La transacción fue exitosa
    RECHAZADA,      // La transacción fue rechazada (sin más cambios)
    REVERTIDA       // La transacción fue cancelada después de estar completada
}
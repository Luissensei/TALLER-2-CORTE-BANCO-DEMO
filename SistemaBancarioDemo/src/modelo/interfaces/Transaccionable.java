package modelo.interfaces;

// Interface Transaccionable: solo las cuentas pueden hacer transacciones
public interface Transaccionable {
    
    // Meter dinero en la cuenta
    void depositar(double monto) throws Exception;
    
    // Sacar dinero de la cuenta
    void retirar(double monto) throws Exception;
    
    // Calcular cuánto cobra de comisión
    double calcularComision(double monto);
    
    // Ver cuánto dinero hay en la cuenta
    double consultarSaldo();
}
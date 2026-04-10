package modelo.excepciones;

// Se lanza cuando alguien intenta retirar más dinero del que tiene
public class SaldoInsuficienteException extends SistemaBancarioException {
    
    private double saldoActual;      // Cuánto dinero hay realmente
    private double montoSolicitado;  // Cuánto dinero se quiso sacar
    
    // Constructor que recibe info del error
    public SaldoInsuficienteException(String mensaje, double saldoActual, double montoSolicitado) {
        super(mensaje, "ERR_SALDO_INSUFICIENTE");
        this.saldoActual = saldoActual;
        this.montoSolicitado = montoSolicitado;
    }
    
    // Getters para obtener los valores
    public double getSaldoActual() {
        return saldoActual;
    }
    
    public double getMontoSolicitado() {
        return montoSolicitado;
    }
}
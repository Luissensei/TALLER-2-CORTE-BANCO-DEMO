package modelo.cuentas;

import java.time.LocalDateTime;
import modelo.abstractas.Cuenta;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;
import modelo.interfaces.Transaccionable;
import modelo.excepciones.CuentaBloqueadaException;
import modelo.excepciones.SaldoInsuficienteException;

// Clase CuentaCredito: cuenta de crédito (es como una tarjeta de crédito)
// Implementa Consultable, Transaccionable y Auditable
public class CuentaCredito extends Cuenta implements Consultable, Transaccionable, Auditable {
    
    // Atributos específicos de cuenta de crédito
    private double limiteCredito;       // Cuánto dinero se puede "pedir prestado"
    private double tasaInteres;         // Porcentaje de interés (ej: 20% anual)
    private double deudaActual;         // Cuánto se debe ahora
    
    // Para Auditable
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    
    // Constructor
    public CuentaCredito(String numeroCuenta, double limiteCredito, double tasaInteres) {
        // Llamar al constructor de Cuenta con saldo inicial 0
        super(numeroCuenta, 0);
        
        this.limiteCredito = limiteCredito;
        this.tasaInteres = tasaInteres;
        this.deudaActual = 0; // Al principio no hay deuda
        
        // Inicializar auditoría
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }
    
    // Getter de límite de crédito
    public double getLimiteCredito() {
        return limiteCredito;
    }
    
    // Setter de límite de crédito
    public void setLimiteCredito(double limiteCredito) {
        this.limiteCredito = limiteCredito;
    }
    
    // Getter de tasa de interés
    public double getTasaInteres() {
        return tasaInteres;
    }
    
    // Setter de tasa de interés
    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }
    
    // Getter de deuda actual
    public double getDeudaActual() {
        return deudaActual;
    }
    
    // Setter de deuda actual
    public void setDeudaActual(double deudaActual) {
        this.deudaActual = deudaActual;
    }
    
    // ============= MÉTODOS ABSTRACTOS DE CUENTA =============
    
    // Calcular el interés mensual
    // Formula: deudaActual * tasaInteres / 12
    @Override
    public double calcularInteres() {
        return deudaActual * (tasaInteres / 100) / 12;
    }
    
    // Obtener el límite de retiro diario
    @Override
    public double getLimiteRetiro() {
        // En crédito, el límite es cuánto dinero disponible queda
        return limiteCredito - deudaActual;
    }
    
    // Obtener el tipo de cuenta
    @Override
    public String getTipoCuenta() {
        return "CuentaCredito";
    }
    
    // ============= MÉTODOS DE TRANSACCIONABLE =============
    
    // Depositar dinero (pagar la deuda)
    @Override
    public void depositar(double monto) throws CuentaBloqueadaException {
        // Verificar que la cuenta no esté bloqueada
        this.verificarBloqueada();
        
        // Restar del dinero de la deuda (es un pago)
        deudaActual = Math.max(0, deudaActual - monto); // No puede ser negativa
        
        // Agregar al saldo disponible (dinero pagado)
        this.setSaldo(this.getSaldo() + monto);
        
        // Registrar el cambio
        this.registrarModificacion("PAGO_DEUDA");
        
        System.out.println("✓ Pago de $" + monto + " realizado en " + this.getNumeroCuenta());
    }
    
    // Retirar dinero (usar el crédito)
    // En crédito, "retirar" significa usar crédito
    @Override
    public void retirar(double monto) throws CuentaBloqueadaException {
        // Verificar que la cuenta no esté bloqueada
        this.verificarBloqueada();
        
        // Verificar que no se exceda el límite de crédito
        double creditoDisponible = limiteCredito - deudaActual;
        if (creditoDisponible < monto) {
            throw new CuentaBloqueadaException("No hay crédito disponible para realizar la operación");
        }
        
        // Aumentar la deuda
        deudaActual += monto;
        
        // Registrar el cambio
        this.registrarModificacion("USO_CREDITO");
        
        System.out.println("✓ Crédito de $" + monto + " utilizado en " + this.getNumeroCuenta());
    }
    
    // Calcular la comisión
    // En crédito: no hay comisión por operación
    @Override
    public double calcularComision(double monto) {
        return 0; // Sin comisión
    }
    
    // Consultar el saldo disponible
    @Override
    public double consultarSaldo() {
        // El saldo es lo que queda disponible del límite
        return limiteCredito - deudaActual;
    }
    
    // ============= MÉTODOS DE CONSULTABLE =============
    
    @Override
    public String obtenerResumen() {
        return "CuentaCredito{" +
                "numero='" + this.getNumeroCuenta() + '\'' +
                ", limiteCredito=" + limiteCredito +
                ", deudaActual=" + deudaActual +
                ", disponible=" + consultarSaldo() +
                ", tasaInteres=" + tasaInteres + "%" +
                '}';
    }
    
    @Override
    public boolean estaActivo() {
        return !this.isBloqueada();
    }
    
    @Override
    public String obtenerTipo() {
        return "CuentaCredito";
    }
    
    // ============= MÉTODOS DE AUDITABLE =============
    
    @Override
    public LocalDateTime obtenerFechaCreacion() {
        return fechaCreacion;
    }
    
    @Override
    public LocalDateTime obtenerUltimaModificacion() {
        return this.getUltimaModificacion();
    }
    
    @Override
    public String obtenerUsuarioModificacion() {
        return this.getUsuarioModificacion();
    }
    
    @Override
    public void registrarModificacion(String usuario) {
        super.registrarModificacion(usuario);
    }
}
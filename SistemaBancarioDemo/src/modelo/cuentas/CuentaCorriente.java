package modelo.cuentas;

import java.time.LocalDateTime;
import modelo.abstractas.Cuenta;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;
import modelo.interfaces.Transaccionable;
import modelo.excepciones.CuentaBloqueadaException;
import modelo.excepciones.SaldoInsuficienteException;

// Clase CuentaCorriente: cuenta para operaciones diarias
// Implementa Consultable, Transaccionable y Auditable
public class CuentaCorriente extends Cuenta implements Consultable, Transaccionable, Auditable {
    
    // Atributos específicos de cuenta corriente
    private double montoSobregiro;      // Dinero que se puede "prestar" (como overdraft)
    private double comisionMantenimiento; // Comisión mensual fija
    
    // Para Auditable
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    
    // Constructor
    public CuentaCorriente(String numeroCuenta, double saldoInicial, double montoSobregiro, double comisionMantenimiento) {
        // Llamar al constructor de Cuenta
        super(numeroCuenta, saldoInicial);
        
        this.montoSobregiro = montoSobregiro;
        this.comisionMantenimiento = comisionMantenimiento;
        
        // Inicializar auditoría
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }
    
    // Getter de monto de sobregiro
    public double getMontoSobregiro() {
        return montoSobregiro;
    }
    
    // Setter de monto de sobregiro
    public void setMontoSobregiro(double montoSobregiro) {
        this.montoSobregiro = montoSobregiro;
    }
    
    // Getter de comisión de mantenimiento
    public double getComisionMantenimiento() {
        return comisionMantenimiento;
    }
    
    // Setter de comisión de mantenimiento
    public void setComisionMantenimiento(double comisionMantenimiento) {
        this.comisionMantenimiento = comisionMantenimiento;
    }
    
    // ============= MÉTODOS ABSTRACTOS DE CUENTA =============
    
    // Calcular el interés mensual
    // En cuentas corrientes: NO genera interés (es 0)
    @Override
    public double calcularInteres() {
        return 0; // Las cuentas corrientes no generan interés
    }
    
    // Obtener el límite de retiro diario
    @Override
    public double getLimiteRetiro() {
        // Límite de 100,000 pesos por día en cuenta corriente
        return 100000;
    }
    
    // Obtener el tipo de cuenta
    @Override
    public String getTipoCuenta() {
        return "CuentaCorriente";
    }
    
    // ============= MÉTODOS DE TRANSACCIONABLE =============
    
    // Depositar dinero en la cuenta
    @Override
    public void depositar(double monto) throws CuentaBloqueadaException {
        // Verificar que la cuenta no esté bloqueada
        this.verificarBloqueada();
        
        // Agregar el dinero al saldo
        double nuevoSaldo = this.getSaldo() + monto;
        this.setSaldo(nuevoSaldo);
        
        // Registrar el cambio
        this.registrarModificacion("DEPOSITO");
        
        System.out.println("✓ Depósito de $" + monto + " realizado en " + this.getNumeroCuenta());
    }
    
    // Retirar dinero de la cuenta
    // Permite sobregiro (dinero prestado)
    @Override
    public void retirar(double monto) throws SaldoInsuficienteException, CuentaBloqueadaException {
        // Verificar que la cuenta no esté bloqueada
        this.verificarBloqueada();
        
        // Calcular si hay dinero disponible considerando el sobregiro
        double dineroDisponible = this.getSaldo() + montoSobregiro;
        
        // Si no hay dinero ni con sobregiro
        if (dineroDisponible < monto) {
            throw new SaldoInsuficienteException(
                "No hay saldo suficiente para retirar $" + monto + " (disponible: $" + dineroDisponible + ")",
                dineroDisponible,
                monto
            );
        }
        
        // Restar el dinero del saldo
        double nuevoSaldo = this.getSaldo() - monto;
        this.setSaldo(nuevoSaldo);
        
        // Si el saldo es negativo, está usando sobregiro
        if (nuevoSaldo < 0) {
            System.out.println("⚠ Advertencia: Usando sobregiro de $" + Math.abs(nuevoSaldo));
        }
        
        // Registrar el cambio
        this.registrarModificacion("RETIRO");
        
        System.out.println("✓ Retiro de $" + monto + " realizado en " + this.getNumeroCuenta());
    }
    
    // Calcular la comisión
    // En cuentas corrientes: comisión fija mensual
    @Override
    public double calcularComision(double monto) {
        return comisionMantenimiento; // Retorna la comisión mensual fija
    }
    
    // Consultar el saldo actual
    @Override
    public double consultarSaldo() {
        return this.getSaldo();
    }
    
    // ============= MÉTODOS DE CONSULTABLE =============
    
    @Override
    public String obtenerResumen() {
        return "CuentaCorriente{" +
                "numero='" + this.getNumeroCuenta() + '\'' +
                ", saldo=" + this.getSaldo() +
                ", sobregiro=" + montoSobregiro +
                ", comision=" + comisionMantenimiento +
                '}';
    }
    
    @Override
    public boolean estaActivo() {
        return !this.isBloqueada();
    }
    
    @Override
    public String obtenerTipo() {
        return "CuentaCorriente";
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
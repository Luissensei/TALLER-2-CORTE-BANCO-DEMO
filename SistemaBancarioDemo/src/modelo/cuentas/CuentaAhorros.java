package modelo.cuentas;

import java.time.LocalDateTime;
import modelo.abstractas.Cuenta;
import modelo.interfaces.Auditable;
import modelo.interfaces.Consultable;
import modelo.interfaces.Transaccionable;
import modelo.excepciones.CuentaBloqueadaException;
import modelo.excepciones.SaldoInsuficienteException;
import modelo.excepciones.CapacidadExcedidaException;

// Clase CuentaAhorros: cuenta para ahorrar dinero
// Implementa Consultable, Transaccionable y Auditable
public class CuentaAhorros extends Cuenta implements Consultable, Transaccionable, Auditable {
    
    // Atributos específicos de cuenta de ahorros
    private double tasaInteres;        // Porcentaje de interés (ej: 2.5%)
    private int retirosMesActual;      // Cuántos retiros ha hecho este mes
    private int maxRetirosMes;         // Máximo de retiros permitidos por mes (ej: 10)
    
    // Para Auditable
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    
    // Constructor
    public CuentaAhorros(String numeroCuenta, double saldoInicial, double tasaInteres, int maxRetirosMes) {
        // Llamar al constructor de Cuenta
        super(numeroCuenta, saldoInicial);
        
        this.tasaInteres = tasaInteres;
        this.retirosMesActual = 0; // Al principio no ha habido retiros
        this.maxRetirosMes = maxRetirosMes;
        
        // Inicializar auditoría
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }
    
    // Getter de tasa de interés
    public double getTasaInteres() {
        return tasaInteres;
    }
    
    // Setter de tasa de interés
    public void setTasaInteres(double tasaInteres) {
        this.tasaInteres = tasaInteres;
    }
    
    // Getter de retiros del mes actual
    public int getRetirosMesActual() {
        return retirosMesActual;
    }
    
    // Setter de retiros del mes actual
    public void setRetirosMesActual(int retirosMesActual) {
        this.retirosMesActual = retirosMesActual;
    }
    
    // Getter de máximo de retiros por mes
    public int getMaxRetirosMes() {
        return maxRetirosMes;
    }
    
    // Setter de máximo de retiros por mes
    public void setMaxRetirosMes(int maxRetirosMes) {
        this.maxRetirosMes = maxRetirosMes;
    }
    
    // ============= MÉTODOS ABSTRACTOS DE CUENTA =============
    
    // Calcular el interés mensual
    // Formula: saldo * tasaInteres / 12
    @Override
    public double calcularInteres() {
        return this.getSaldo() * (tasaInteres / 100) / 12;
    }
    
    // Obtener el límite de retiro diario
    @Override
    public double getLimiteRetiro() {
        // Límite de 50,000 pesos por día en cuenta de ahorros
        return 50000;
    }
    
    // Obtener el tipo de cuenta
    @Override
    public String getTipoCuenta() {
        return "CuentaAhorros";
    }
    
    // ============= MÉTODOS DE TRANSACCIONABLE =============
    
    // Depositar dinero en la cuenta
    // Lanza CuentaBloqueadaException si está bloqueada
    @Override
    public void depositar(double monto) throws CuentaBloqueadaException {
        // Primero verificar que la cuenta no esté bloqueada
        this.verificarBloqueada();
        
        // Agregar el dinero al saldo
        double nuevoSaldo = this.getSaldo() + monto;
        this.setSaldo(nuevoSaldo);
        
        // Registrar el cambio
        this.registrarModificacion("DEPOSITO");
        
        System.out.println("✓ Depósito de $" + monto + " realizado en " + this.getNumeroCuenta());
    }
    
    // Retirar dinero de la cuenta
    // Lanza SaldoInsuficienteException si no hay dinero
    // Lanza CuentaBloqueadaException si está bloqueada
    @Override
    public void retirar(double monto) throws SaldoInsuficienteException, CuentaBloqueadaException {
        // Primero verificar que la cuenta no esté bloqueada
        this.verificarBloqueada();
        
        // Verificar que haya saldo suficiente
        if (this.getSaldo() < monto) {
            throw new SaldoInsuficienteException(
                "No hay saldo suficiente para retirar $" + monto,
                this.getSaldo(),
                monto
            );
        }
        
        // Verificar que no haya excedido el máximo de retiros del mes
        if (retirosMesActual >= maxRetirosMes) {
            System.out.println("⚠ Advertencia: Ha alcanzado el máximo de retiros del mes (" + maxRetirosMes + ")");
        }
        
        // Restar el dinero del saldo
        double nuevoSaldo = this.getSaldo() - monto;
        this.setSaldo(nuevoSaldo);
        
        // Incrementar contador de retiros
        retirosMesActual++;
        
        // Registrar el cambio
        this.registrarModificacion("RETIRO");
        
        System.out.println("✓ Retiro de $" + monto + " realizado en " + this.getNumeroCuenta());
    }
    
    // Calcular la comisión del retiro
    // En cuentas de ahorros: no hay comisión (0%)
    @Override
    public double calcularComision(double monto) {
        return 0; // Sin comisión en cuentas de ahorros
    }
    
    // Consultar el saldo actual
    @Override
    public double consultarSaldo() {
        return this.getSaldo();
    }
    
    // ============= MÉTODOS DE CONSULTABLE =============
    
    @Override
    public String obtenerResumen() {
        return "CuentaAhorros{" +
                "numero='" + this.getNumeroCuenta() + '\'' +
                ", saldo=" + this.getSaldo() +
                ", tasaInteres=" + tasaInteres + "%" +
                ", retiros=" + retirosMesActual + "/" + maxRetirosMes +
                '}';
    }
    
    @Override
    public boolean estaActivo() {
        // Está activa si no está bloqueada
        return !this.isBloqueada();
    }
    
    @Override
    public String obtenerTipo() {
        return "CuentaAhorros";
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
        // Usa el método de la clase padre
        super.registrarModificacion(usuario);
    }
}
package principal;

import java.time.LocalDate;
import modelo.abstractas.Cuenta;
import modelo.abstractas.Empleado;

// Importar todas las clases que vamos a usar
import modelo.banco.Banco;
import modelo.banco.Transaccion;
import modelo.personas.ClienteNatural;
import modelo.personas.ClienteEmpresarial;
import modelo.empleados.Cajero;
import modelo.empleados.AsesorFinanciero;
import modelo.empleados.GerenteSucursal;
import modelo.cuentas.CuentaAhorros;
import modelo.cuentas.CuentaCorriente;
import modelo.cuentas.CuentaCredito;
import modelo.enums.TipoDocumento;
import modelo.enums.Turno;
import modelo.enums.EstadoTransaccion;
import modelo.excepciones.*;

// Clase principal: aquí se demuestran todos los escenarios
public class SistemaBancarioDemo {
    
    public static void main(String[] args) {
        System.out.println("════════════════════════════════════════════════════════════");
        System.out.println("   SISTEMA DE GESTIÓN BANCARIA - DEMOSTRACIÓN COMPLETA      ");
        System.out.println("════════════════════════════════════════════════════════════\n");
        
        try {
            // Crear el banco
            Banco banco = new Banco("Banco Seguro S.A.");
            System.out.println("Banco creado: " + banco.getNombre() + "\n");
            
            // ==========================================
            // ESCENARIO 1: Registrar clientes naturales y empresarial
            // ==========================================
            System.out.println("ESCENARIO 1: REGISTRAR CLIENTES");
            
            // Crear 2 clientes naturales
            ClienteNatural cliente1 = new ClienteNatural(
                "1001",                           // id
                "Juan",                           // nombre
                "Perez",                          // apellido
                LocalDate.of(1990, 5, 15),        // fecha nacimiento
                "juan.perez@email.com",           // email
                TipoDocumento.CEDULA,             // tipo documento
                "1234567890"                      // número documento
            );
            
            ClienteNatural cliente2 = new ClienteNatural(
                "1002",
                "Maria",
                "Garcia",
                LocalDate.of(1988, 8, 22),
                "maria.garcia@email.com",
                TipoDocumento.PASAPORTE,
                "AB987654"
            );
            
            // Crear 1 cliente empresarial
            ClienteEmpresarial cliente3 = new ClienteEmpresarial(
                "1003",
                "Empresa",
                "Ltda",
                LocalDate.of(2010, 1, 1),
                "contacto@empresa.com",
                "900123456",                      // NIT
                "Empresa Comercial S.A.",         // razón social
                "Carlos Lopez"                    // representante legal
            );
            
            // Registrar los clientes en el banco
            banco.registrarCliente(cliente1);
            banco.registrarCliente(cliente2);
            banco.registrarCliente(cliente3);
            
            System.out.println("Clientes registrados: " + banco.getClientes().length + "\n");
            
            // ==========================================
            // ESCENARIO 2: Abrir cuentas de cada tipo
            // ==========================================
            System.out.println("ESCENARIO 2: ABRIR CUENTAS");
            
            // Crear cuentas de cada tipo
            CuentaAhorros cuenta1 = new CuentaAhorros(
                "CTA-001",          // número de cuenta
                10000,              // saldo inicial
                2.5,                // tasa de interés (2.5%)
                10                  // máximo de retiros por mes
            );
            
            CuentaCorriente cuenta2 = new CuentaCorriente(
                "CTA-002",
                15000,              // saldo inicial
                5000,               // monto de sobregiro
                500                 // comisión mensual
            );
            
            CuentaCredito cuenta3 = new CuentaCredito(
                "CTA-003",
                50000,              // limite de credito
                18.0                // tasa de interés (18% anual)
            );
            
            // Abrir las cuentas para los clientes
            banco.abrirCuenta(cliente1.getId(), cuenta1);
            banco.abrirCuenta(cliente2.getId(), cuenta2);
            banco.abrirCuenta(cliente3.getId(), cuenta3);
            
            System.out.println("Cuentas abiertas: " + banco.getCuentas().length + "\n");
            
            // ==========================================
            // ESCENARIO 3: Deposito exitoso y cuenta bloqueada
            // ==========================================
            System.out.println("ESCENARIO 3: DEPÓSITOS");
            
            try {
                // Deposito exitoso
                cuenta1.depositar(5000); // Depositar 5000 pesos
                System.out.println("Nuevo saldo: $" + cuenta1.consultarSaldo() + "\n");
                
                // Intentar depositar en cuenta bloqueada
                cuenta1.setBloqueada(true); // Bloquear la cuenta
                System.out.println("Cuenta bloqueada para prueba...");
                cuenta1.depositar(3000); // Esto lanzara excepcion
                
            } catch (CuentaBloqueadaException e) {
                System.out.println("Error capturado: " + e.getMessage());
                System.out.println("Codigo de error: " + e.getCodigoError());
                // Desbloquear para continuar
                cuenta1.setBloqueada(false);
            }
            
            System.out.println();
            
            // ==========================================
            // ESCENARIO 4: Retiro exitoso y saldo insuficiente
            // ==========================================
            System.out.println("ESCENARIO 4: RETIROS");
            
            try {
                // Retiro exitoso
                cuenta2.retirar(2000);
                System.out.println("Nuevo saldo: $" + cuenta2.consultarSaldo() + "\n");
                
                // Intentar retirar mas de lo disponible
                System.out.println("Intentando retirar $50000 (disponible: $" + cuenta2.consultarSaldo() + ")...");
                cuenta2.retirar(50000); // Esto lanzarq excepcion
                
            } catch (SaldoInsuficienteException e) {
                System.out.println("Error capturado: " + e.getMessage());
                System.out.println("Saldo actual: " + e.getSaldoActual());
                System.out.println("Monto solicitado: " + e.getMontoSolicitado());
            }
            
            System.out.println();
            
            // ==========================================
            // ESCENARIO 5: Transferencia entre cuentas
            // ==========================================
            System.out.println("ESCENARIO 5: TRANSFERENCIA");
            
            try {
                // Realizar una transferencia de cuenta1 a cuenta2
                double montoTransferencia = 3000;
                
                System.out.println("Transferencia de $" + montoTransferencia);
                System.out.println("De: " + cuenta1.getNumeroCuenta() + " (saldo: $" + cuenta1.consultarSaldo() + ")");
                System.out.println("Para: " + cuenta2.getNumeroCuenta() + " (saldo: $" + cuenta2.consultarSaldo() + ")");
                
                // Retirar de la cuenta origen (usando interfaz Transaccionable)
                cuenta1.retirar(montoTransferencia);
                
                // Depositar en la cuenta destino (usando interfaz Transaccionable)
                cuenta2.depositar(montoTransferencia);
                
                System.out.println("\nNuevos saldos:");
                System.out.println("Cuenta origen: $" + cuenta1.consultarSaldo());
                System.out.println("Cuenta destino: $" + cuenta2.consultarSaldo() + "\n");
                
            } catch (Exception e) {
                System.out.println("Error en transferencia: " + e.getMessage());
            }
            
            System.out.println();
            
            // ==========================================
            // ESCENARIO 6: Polimorfismo - Calcular salarios
            // ==========================================
            System.out.println("ESCENARIO 6: CÁLCULO DE SALARIOS (POLIMORFISMO)");
            
            try {
                // Crear 3 tipos diferentes de empleados
                Cajero cajero = new Cajero(
                    "E001",
                    "Luis",
                    "Rodríguez",
                    LocalDate.of(1995, 3, 10),
                    "luis@email.com",
                    "LEG001",
                    LocalDate.of(2020, 1, 15),
                    2000000,                    // salario base
                    Turno.MAÑANA,
                    "Oficina Central"
                );
                cajero.setTransaccionesDia(15); // Hizo 15 transacciones hoy
                
                AsesorFinanciero asesor = new AsesorFinanciero(
                    "E002",
                    "Ana",
                    "Martínez",
                    LocalDate.of(1992, 7, 20),
                    "ana@email.com",
                    "LEG002",
                    LocalDate.of(2019, 6, 1),
                    2500000,                    // salario base
                    10,                         // comisión base 10%
                    5000000                     // meta mensual
                );
                
                GerenteSucursal gerente = new GerenteSucursal(
                    "E003",
                    "Roberto",
                    "Sanchez",
                    LocalDate.of(1985, 11, 5),
                    "roberto@email.com",
                    "LEG003",
                    LocalDate.of(2015, 3, 20),
                    4000000,                    // salario base
                    "Sucursal Norte",
                    100000000                   // presupuesto anual
                );
                
                // Registrar empleados en el banco
                banco.registrarEmpleado(cajero);
                banco.registrarEmpleado(asesor);
                banco.registrarEmpleado(gerente);
                
                System.out.println("\nCalculo de salarios por tipo de empleado:");
                System.out.println("(Cada uno calcula diferente gracias al POLIMORFISMO)\n");
                
                // Recorrer el array de empleados
                Empleado[] empleados = banco.getEmpleados();
                for (int i = 0; i < empleados.length; i++) {
                    // Aquí ocurre polimorfismo: cada objeto llama su propio calcularSalario()
                    String tipo = empleados[i].obtenerTipo();
                    double salario = empleados[i].calcularSalario();
                    double bono = empleados[i].calcularBono();
                    
                    System.out.println("▪ " + tipo + " - " + empleados[i].getNombreCompleto());
                    System.out.println("  Salario base: $" + empleados[i].getSalarioBase());
                    System.out.println("  Bono: $" + bono);
                    System.out.println("  Total: $" + salario + "\n");
                }
                
            } catch (CapacidadExcedidaException e) {
                System.out.println("Error: " + e.getMessage());
            }
            
            System.out.println();
            
            // ==========================================
            // ESCENARIO 7: Polimorfismo - Calcular intereses
            // ==========================================
            System.out.println("ESCENARIO 7: CALCULO DE INTERESES (POLIMORFISMO)");
            System.out.println();
            
            // Recorrer todas las cuentas
            Cuenta[] cuentas = banco.getCuentas();
            System.out.println("Calculo de interes mensual por tipo de cuenta:\n");
            for (int i = 0; i < cuentas.length; i++) {
                // Polimorfismo: cada cuenta calcula interes diferente
                String tipo = cuentas[i].getTipoCuenta();
                double interes = cuentas[i].calcularInteres();
                double saldo = cuentas[i].getSaldo();
                
                System.out.println("▪ " + tipo + " (" + cuentas[i].getNumeroCuenta() + ")");
                System.out.println("  Saldo:" + saldo);
                System.out.println("  Interes mensual:" + interes + "\n");
            }
            
            System.out.println();
            
            // ==========================================
            // ESCENARIO 8: Cambio de estado invalido
            // ==========================================
            System.out.println("ESCENARIO 8: TRANSICION DE ESTADO INVALIDA");
            
            try {
                // Crear una transacción
                Transaccion transaccion = new Transaccion(
                    "TRX001",
                    cuenta1,
                    cuenta2,
                    1000,
                    "Transferencia de prueba"
                );
                
                System.out.println("Estado inicial: " + transaccion.getEstado());
                
                // Cambiar a estado válido
                transaccion.cambiarEstado(EstadoTransaccion.PROCESANDO);
                System.out.println("Estado actual: " + transaccion.getEstado());
                
                // Intentar cambio inválido (de PROCESANDO a PENDIENTE - NO PERMITIDO)
                System.out.println("\nIntentando cambiar de PROCESANDO a PENDIENTE (invalido)...");
                transaccion.cambiarEstado(EstadoTransaccion.PENDIENTE);
                
            } catch (EstadoTransaccionInvalidoException e) {
                System.out.println("Error capturado: " + e.getMessage());
            }
            
            System.out.println();
            
            // ==========================================
            // ESCENARIO 9: Permiso insuficiente
            // ==========================================
            System.out.println("ESCENARIO 9: PERMISO INSUFICIENTE");
            
            try {
                // Intentar que un cajero apruebe un credito (solo gerente puede)
                Empleado[] emps = banco.getEmpleados();
                Cajero cajeroAcceso = null;
                GerenteSucursal gerenteAcceso = null;
                
                // Buscar el cajero y gerente en el array
                for (int i = 0; i < emps.length; i++) {
                    if (emps[i] instanceof Cajero) {
                        cajeroAcceso = (Cajero) emps[i];
                    }
                    if (emps[i] instanceof GerenteSucursal) {
                        gerenteAcceso = (GerenteSucursal) emps[i];
                    }
                }
                
                // Intentar que el cajero apruebe credito (no puede)
                System.out.println("Intentando que un Cajero apruebe credito...");
                if (cajeroAcceso != null ){
                    throw new PermisoInsuficienteException(
                        "Solo GerenteSucursal puede aprobar creditos. " + 
                        cajeroAcceso.obtenerTipo() + "no tiene permiso"
                    );
                }
                
            } catch (PermisoInsuficienteException e) {
                System.out.println("Error capturado: " + e.getMessage());
            }
            
            // Mostrar que el gerente si puede
            System.out.println("\n Un GerenteSucursal si puede aprobar creditos");
            Empleado[] empsFinales = banco.getEmpleados();
            for (int i = 0; i < empsFinales.length; i++) {
                if (empsFinales[i] instanceof GerenteSucursal) {
                    GerenteSucursal ger = (GerenteSucursal) empsFinales[i];
                    ger.aprobarCredito("1001", 5000000);
                    break;
                }
            }
            
            System.out.println();
            
            // ==========================================
            // ESCENARIO 10: Notificaciones
            // ==========================================
            System.out.println("ESCENARIO 10: NOTIFICACIONES");
            System.out.println();
            
            // Notificar a cliente que acepta (cliente1)
            System.out.println("Cliente que acepta notificaciones:");
            cliente1.notificar("Deposito de $5000 realizado en su cuenta CTA-001");
            
            // Notificar a cliente que NO acepta (cliente2)
            System.out.println("\nCliente que NO acepta notificaciones:");
            cliente2.setAceptaNotificaciones(false);
            cliente2.notificar("Intento de enviar notificacion");
            System.out.println("(No se mostro nada porque no acepta notificaciones)");
            
            System.out.println();
            
            // ==========================================
            // ESCENARIO 11: Auditoria - Registrar modificaciones
            // ==========================================
            System.out.println("ESCENARIO 11: AUDITORIA");
            System.out.println();
            
            // Registrar una modificación en la cuenta
            cuenta1.registrarModificacion("ADMIN_USER");
            
            // Mostrar informacion de auditoria
            System.out.println("Informacion de auditoria de la cuenta:");
            System.out.println("Fecha creacion: " + cuenta1.obtenerFechaCreacion());
            System.out.println("Ultima modificacion: " + cuenta1.obtenerUltimaModificacion());
            System.out.println("Usuario que modifico: " + cuenta1.obtenerUsuarioModificacion());
            
            System.out.println();
            
            // ==========================================
            // ESCENARIO 12: Nomina total
            // ==========================================
            System.out.println("ESCENARIO 12: CALCULO DE NOMINA TOTAL");
            System.out.println();
            
            // Calcular la nomina total usando polimorfismo
            double nominaTotal = banco.calcularNominaTotal();
            System.out.println("Nomina total del banco: $" + nominaTotal);
            
            System.out.println();
            
            // ==========================================
            // RESUMEN FINAL
            // ==========================================
            System.out.println("════ RESUMEN FINAL ════");
            System.out.println("Banco: " + banco.getNombre());
            System.out.println("Clientes registrados: " + banco.getClientes().length);
            System.out.println("Cuentas abiertas: " + banco.getCuentas().length);
            System.out.println("Empleados contratados: " + banco.getEmpleados().length);
            System.out.println("════════════════════════\n");
            
            // Calcular intereses mensuales (bonus)
            banco.calcularInteresesMensuales();
            
            System.out.println("DEMOSTRACION COMPLETADA EXITOSAMENTE");
            
        } catch (Exception e) {
            // Capturar cualquier excepcion no prevista
            System.out.println("Error no esperado: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
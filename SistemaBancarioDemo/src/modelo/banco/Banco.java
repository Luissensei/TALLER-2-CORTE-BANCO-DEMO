package modelo.banco;

import java.time.LocalDateTime;
import modelo.abstractas.Cuenta;
import modelo.abstractas.Empleado;
import modelo.personas.Cliente;
import modelo.interfaces.Auditable;
import modelo.excepciones.CapacidadExcedidaException;
import modelo.excepciones.ClienteNoEncontradoException;

// Clase Banco: representa todo el banco
// Implementa Auditable para registrar modificaciones
public class Banco implements Auditable {
    
    // Atributos del banco
    private String nombre;  // Nombre del banco
    
    // Arrays estáticos para guardar datos
    private Empleado[] empleados = new Empleado[50];        // Máximo 50 empleados
    private int cantidadEmpleados = 0;                      // Cuántos empleados hay
    
    private Cliente[] clientes = new Cliente[200];          // Máximo 200 clientes
    private int cantidadClientes = 0;                       // Cuántos clientes hay
    
    private Cuenta[] cuentas = new Cuenta[500];             // Máximo 500 cuentas
    private int cantidadCuentas = 0;                        // Cuántas cuentas hay
    
    // Para Auditable
    private LocalDateTime fechaCreacion;
    private LocalDateTime ultimaModificacion;
    private String usuarioModificacion;
    
    // Constructor
    public Banco(String nombre) {
        this.nombre = nombre;
        
        // Inicializar auditoría
        this.fechaCreacion = LocalDateTime.now();
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = "SISTEMA";
    }
    
    // Getter de nombre
    public String getNombre() {
        return nombre;
    }
    
    // ============= GESTIÓN DE EMPLEADOS =============
    
    // Registrar un nuevo empleado en el banco
    public void registrarEmpleado(Empleado empleado) throws CapacidadExcedidaException {
        // Verificar que haya espacio
        if (cantidadEmpleados >= empleados.length) {
            throw new CapacidadExcedidaException(
                "No hay espacio para más empleados (máximo 50)",
                50
            );
        }
        
        // Agregar el empleado al array
        empleados[cantidadEmpleados] = empleado;
        cantidadEmpleados++;
        
        System.out.println("✓ Empleado " + empleado.getNombreCompleto() + " registrado");
    }
    
    // Retorna una copia del array de empleados
    public Empleado[] getEmpleados() {
        Empleado[] copia = new Empleado[cantidadEmpleados];
        System.arraycopy(empleados, 0, copia, 0, cantidadEmpleados);
        return copia;
    }
    
    // ============= GESTIÓN DE CLIENTES =============
    
    // Registrar un nuevo cliente en el banco
    public void registrarCliente(Cliente cliente) throws CapacidadExcedidaException {
        // Verificar que haya espacio
        if (cantidadClientes >= clientes.length) {
            throw new CapacidadExcedidaException(
                "No hay espacio para más clientes (máximo 200)",
                200
            );
        }
        
        // Agregar el cliente al array
        clientes[cantidadClientes] = cliente;
        cantidadClientes++;
        
        System.out.println("✓ Cliente " + cliente.getNombreCompleto() + " registrado");
    }
    
    // Buscar un cliente por su ID
    public Cliente buscarCliente(String id) throws ClienteNoEncontradoException {
        // Recorrer el array de clientes
        for (int i = 0; i < cantidadClientes; i++) {
            // Si encuentra el cliente con este ID
            if (clientes[i].getId().equals(id)) {
                return clientes[i]; // Retornarlo
            }
        }
        
        // Si no lo encontró, lanzar excepción
        throw new ClienteNoEncontradoException(
            "No se encontró cliente con ID: " + id,
            id
        );
    }
    
    // Retorna una copia del array de clientes
    public Cliente[] getClientes() {
        Cliente[] copia = new Cliente[cantidadClientes];
        System.arraycopy(clientes, 0, copia, 0, cantidadClientes);
        return copia;
    }
    
    // ============= GESTIÓN DE CUENTAS =============
    
    // Abrir una nueva cuenta para un cliente
    public void abrirCuenta(String idCliente, Cuenta cuenta) 
            throws ClienteNoEncontradoException, CapacidadExcedidaException {
        
        // Primero buscar el cliente
        Cliente cliente = this.buscarCliente(idCliente); // Lanza ClienteNoEncontradoException si no existe
        
        // Verificar que el banco tenga espacio para cuentas
        if (cantidadCuentas >= cuentas.length) {
            throw new CapacidadExcedidaException(
                "No hay espacio para más cuentas en el banco (máximo 500)",
                500
            );
        }
        
        // Agregar la cuenta al banco
        cuentas[cantidadCuentas] = cuenta;
        cantidadCuentas++;
        
        // Agregar la cuenta al cliente
        cliente.agregarCuenta(cuenta);
        
        System.out.println("✓ Cuenta " + cuenta.getNumeroCuenta() + " abierta para " + cliente.getNombreCompleto());
    }
    
    // Retorna una copia del array de cuentas
    public Cuenta[] getCuentas() {
        Cuenta[] copia = new Cuenta[cantidadCuentas];
        System.arraycopy(cuentas, 0, copia, 0, cantidadCuentas);
        return copia;
    }
    
    // ============= CÁLCULOS FINANCIEROS =============
    
    // Calcular la nómina total (salario de todos los empleados)
    // POLIMORFISMO: cada tipo de empleado calcula su salario diferente
    public double calcularNominaTotal() {
        double nominaTotal = 0;
        
        // Recorrer todos los empleados
        for (int i = 0; i < cantidadEmpleados; i++) {
            // Llamar a calcularSalario()
            // El método correcto se ejecuta según el tipo real del empleado
            // (Polimorfismo de sobrescritura)
            nominaTotal += empleados[i].calcularSalario();
        }
        
        return nominaTotal;
    }
    
    // Calcular los intereses mensuales de todas las cuentas
    // POLIMORFISMO: cada tipo de cuenta calcula interés diferente
    public void calcularInteresesMensuales() {
        System.out.println("\n=== CÁLCULO DE INTERESES MENSUALES ===");
        
        // Recorrer todas las cuentas
        for (int i = 0; i < cantidadCuentas; i++) {
            // Calcular el interés
            double interes = cuentas[i].calcularInteres();
            
            // Mostrar información
            System.out.println("Cuenta " + cuentas[i].getNumeroCuenta() + 
                             " (" + cuentas[i].getTipoCuenta() + "): " +
                             "Interés = $" + interes);
            
            // Agregar el interés al saldo
            cuentas[i].setSaldo(cuentas[i].getSaldo() + interes);
        }
        
        System.out.println("=====================================\n");
    }
    
    // ============= MÉTODOS DE AUDITABLE =============
    
    @Override
    public LocalDateTime obtenerFechaCreacion() {
        return fechaCreacion;
    }
    
    @Override
    public LocalDateTime obtenerUltimaModificacion() {
        return ultimaModificacion;
    }
    
    @Override
    public String obtenerUsuarioModificacion() {
        return usuarioModificacion;
    }
    
    @Override
    public void registrarModificacion(String usuario) {
        this.ultimaModificacion = LocalDateTime.now();
        this.usuarioModificacion = usuario;
    }
}
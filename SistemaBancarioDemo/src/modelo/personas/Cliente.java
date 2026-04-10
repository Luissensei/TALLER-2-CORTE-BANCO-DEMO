package modelo.personas;

import java.time.LocalDate;
import modelo.abstractas.Cuenta;
import modelo.abstractas.Persona;

// Clase abstracta Cliente: hereda de Persona
// Los clientes pueden ser naturales o empresariales
public abstract class Cliente extends Persona {
    
    // Array estático: un cliente puede tener máximo 5 cuentas
    private final Cuenta[] cuentas = new Cuenta[5];
    private int cantidadCuentas = 0; // Cuántas cuentas tiene registradas
    
    // Constructor
    public Cliente(String id, String nombre, String apellido, LocalDate fechaNacimiento, String email) {
        // Llamar al constructor de Persona con super()
        super(id, nombre, apellido, fechaNacimiento, email);
    }
    
    // Método para agregar una cuenta al cliente
    public void agregarCuenta(Cuenta cuenta) {
        // Si ya tiene 5 cuentas, no puede agregar más
        if (cantidadCuentas >= cuentas.length) {
            System.out.println("Error: El cliente ya tiene el máximo de 5 cuentas");
            return;
        }
        // Guardar la cuenta en el array
        cuentas[cantidadCuentas] = cuenta;
        cantidadCuentas++;
    }
    
    // Retorna las cuentas (copia del array, no el original)
    public Cuenta[] getCuentas() {
        Cuenta[] copia = new Cuenta[cantidadCuentas];
        System.arraycopy(cuentas, 0, copia, 0, cantidadCuentas);
        return copia;
    }
    
    // Retorna cuántas cuentas tiene
    public int getCantidadCuentas() {
        return cantidadCuentas;
    }
}
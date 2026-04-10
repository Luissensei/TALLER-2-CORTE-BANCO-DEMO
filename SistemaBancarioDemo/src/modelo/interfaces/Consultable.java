package modelo.interfaces;

// Interface Consultable: cualquier cosa que se pueda consultar en el banco debe implementar esto
public interface Consultable {
    
    // Retorna un resumen con los datos principales
    String obtenerResumen();
    
    // Dice si está activo o no
    boolean estaActivo();
    
    // Retorna qué tipo de cosa es (ej: "ClienteNatural", "CuentaAhorros", etc)
    String obtenerTipo();
}
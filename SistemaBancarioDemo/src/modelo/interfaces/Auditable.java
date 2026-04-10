package modelo.interfaces;

import java.time.LocalDateTime;

// Interface Auditable: para entidades que registran cambios (quién y cuándo)
public interface Auditable {
    
    // Cuándo se creó el objeto
    LocalDateTime obtenerFechaCreacion();
    
    // Cuándo se modificó por última vez
    LocalDateTime obtenerUltimaModificacion();
    
    // Quién hizo el último cambio
    String obtenerUsuarioModificacion();
    
    // Registrar un cambio nuevo
    void registrarModificacion(String usuario);
}
package pe.edu.unsch.banco.aplicacion;

/** Puerto: el servicio no necesita saber cómo se guardan los movimientos. */
public interface MovimientoRepository {
    void guardar(String movimiento);
}

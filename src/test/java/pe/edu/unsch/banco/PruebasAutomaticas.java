package pe.edu.unsch.banco;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import pe.edu.unsch.banco.aplicacion.CuentaService;
import pe.edu.unsch.banco.aplicacion.MovimientoRepository;

/** Sin librerías externas: ejecutar con java pe.edu.unsch.banco.PruebasAutomaticas */
public final class PruebasAutomaticas {
    private PruebasAutomaticas() { }

    public static void main(String[] args) {
        MemoriaRepository repo = new MemoriaRepository();
        CuentaService s = new CuentaService(repo);
        s.registrar("Ana", new BigDecimal("1000"));
        comprobarSaldo(s, "1000.00", "P01");
        comprobar(repo.historial.size() == 1, "P01: debe guardar alta");
        imprimir("P01", "Cuenta registrada. Saldo 1000.00");

        s.depositar(new BigDecimal("200"));
        comprobarSaldo(s, "1200.00", "P02");
        comprobar(repo.historial.size() == 2, "P02: falta movimiento");
        imprimir("P02", "Depósito 200. Saldo 1200.00");

        s.retirar(new BigDecimal("150"));
        comprobarSaldo(s, "1050.00", "P03");
        comprobar(repo.historial.size() == 3, "P03: falta movimiento");
        imprimir("P03", "Retiro 150. Saldo 1050.00");

        comprobarError(() -> s.retirar(new BigDecimal("2000")), "P04");
        comprobarSaldo(s, "1050.00", "P04");
        comprobar(repo.historial.size() == 3, "P04: no debe registrar retiro fallido");
        imprimir("P04", "Retiro rechazado. Saldo 1050.00");

        comprobarError(() -> s.depositar(new BigDecimal("-10")), "P05");
        comprobarSaldo(s, "1050.00", "P05");
        comprobar(repo.historial.size() == 3, "P05: no debe registrar depósito fallido");
        imprimir("P05", "Depósito rechazado. Saldo 1050.00");

        comprobarError(() -> s.registrar("Luis", new BigDecimal("50")), "P06");
        comprobar(s.consultarTitular().equals("Ana"), "P06: se reemplazó a Ana");
        comprobarSaldo(s, "1050.00", "P06");
        imprimir("P06", "Nueva cuenta rechazada. Ana sigue activa");

        CuentaService reiniciado = new CuentaService(repo);
        comprobar(!reiniciado.tieneCuentaActiva(), "P07: no debe restaurar cuenta automáticamente");
        comprobarError(reiniciado::consultarSaldo, "P07");
        imprimir("P07", "Nuevo servicio sin cuenta activa: no hay restauración");

        System.out.println("RESULTADO: 7/7 CASOS SUPERADOS (pruebas de lógica). ");
        System.out.println("Completar P07 con prueba manual cerrando y abriendo el programa.");
    }

    private static void comprobarSaldo(CuentaService s, String esperado, String caso) {
        comprobar(s.consultarSaldo().compareTo(new BigDecimal(esperado)) == 0,
                caso + ": saldo inesperado: " + s.consultarSaldo());
    }

    private static void comprobarError(Runnable operacion, String caso) {
        try {
            operacion.run();
            throw new AssertionError(caso + ": se esperaba una excepción.");
        } catch (IllegalArgumentException | IllegalStateException correcto) {
            // El rechazo es el resultado esperado.
        }
    }

    private static void comprobar(boolean condicion, String error) {
        if (!condicion) throw new AssertionError(error);
    }

    private static void imprimir(String caso, String resultado) {
        System.out.println(caso + " OK - " + resultado);
    }

    private static final class MemoriaRepository implements MovimientoRepository {
        private final List<String> historial = new ArrayList<>();
        @Override public void guardar(String movimiento) { historial.add(movimiento); }
    }
}

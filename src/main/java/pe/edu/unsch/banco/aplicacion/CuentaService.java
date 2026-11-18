package pe.edu.unsch.banco.aplicacion;

import java.math.BigDecimal;
import java.util.Objects;
import pe.edu.unsch.banco.dominio.CuentaBancaria;

/** Casos de uso del sistema bancario. Depende del contrato, no del archivo. */
public final class CuentaService {
    private final MovimientoRepository movimientoRepository;
    private CuentaBancaria cuenta;

    public CuentaService(MovimientoRepository movimientoRepository) {
        this.movimientoRepository = Objects.requireNonNull(movimientoRepository,
                "El repositorio es obligatorio.");
    }

    public void registrar(String titular, BigDecimal saldoInicial) {
        if (cuenta != null) {
            throw new IllegalStateException("Ya existe una cuenta activa. No se reemplazará.");
        }
        CuentaBancaria nuevaCuenta = new CuentaBancaria(titular, saldoInicial);
        movimientoRepository.guardar("Cuenta creada | Titular: " + nuevaCuenta.getTitular()
                + " | Saldo inicial: S/ " + nuevaCuenta.getSaldo().toPlainString());
        cuenta = nuevaCuenta;
    }

    public void depositar(BigDecimal monto) {
        CuentaBancaria actual = obtenerCuenta();
        actual.depositar(monto);
        movimientoRepository.guardar("Titular: " + actual.getTitular()
                + " | Depósito: S/ " + monto.toPlainString()
                + " | Saldo actual: S/ " + actual.getSaldo().toPlainString());
    }

    public void retirar(BigDecimal monto) {
        CuentaBancaria actual = obtenerCuenta();
        actual.retirar(monto);
        movimientoRepository.guardar("Titular: " + actual.getTitular()
                + " | Retiro: S/ " + monto.toPlainString()
                + " | Saldo actual: S/ " + actual.getSaldo().toPlainString());
    }

    public String consultarTitular() {
        return obtenerCuenta().getTitular();
    }

    public BigDecimal consultarSaldo() {
        return obtenerCuenta().getSaldo();
    }

    public boolean tieneCuentaActiva() {
        return cuenta != null;
    }

    private CuentaBancaria obtenerCuenta() {
        if (cuenta == null) {
            throw new IllegalStateException("Primero debe registrar una cuenta.");
        }
        return cuenta;
    }
}

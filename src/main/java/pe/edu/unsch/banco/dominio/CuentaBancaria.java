package pe.edu.unsch.banco.dominio;

import java.math.BigDecimal;
import java.math.RoundingMode;

/** Entidad de dominio: no conoce el menú ni la forma de persistencia. */
public final class CuentaBancaria {
    private final String titular;
    private BigDecimal saldo;

    public CuentaBancaria(String titular, BigDecimal saldoInicial) {
        if (titular == null || titular.isBlank()) {
            throw new IllegalArgumentException("El titular no puede estar vacío.");
        }
        this.titular = titular.trim();
        this.saldo = validarMonto(saldoInicial, true);
    }

    public void depositar(BigDecimal monto) {
        BigDecimal importe = validarMonto(monto, false);
        saldo = saldo.add(importe);
    }

    public void retirar(BigDecimal monto) {
        BigDecimal importe = validarMonto(monto, false);
        if (importe.compareTo(saldo) > 0) {
            throw new IllegalArgumentException("Fondos insuficientes.");
        }
        saldo = saldo.subtract(importe);
    }

    public String getTitular() {
        return titular;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    private static BigDecimal validarMonto(BigDecimal monto, boolean permiteCero) {
        if (monto == null) {
            throw new IllegalArgumentException("Debe ingresar un monto.");
        }
        BigDecimal normalizado;
        try {
            normalizado = monto.setScale(2, RoundingMode.UNNECESSARY);
        } catch (ArithmeticException ex) {
            throw new IllegalArgumentException("El monto admite como máximo dos decimales.", ex);
        }
        int signo = normalizado.signum();
        if ((permiteCero && signo < 0) || (!permiteCero && signo <= 0)) {
            throw new IllegalArgumentException(permiteCero
                    ? "El saldo inicial no puede ser negativo."
                    : "El monto debe ser mayor que cero.");
        }
        return normalizado;
    }
}

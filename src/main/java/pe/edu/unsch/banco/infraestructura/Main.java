package pe.edu.unsch.banco.infraestructura;

import java.io.UncheckedIOException;
import java.math.BigDecimal;
import java.util.Scanner;
import pe.edu.unsch.banco.aplicacion.CuentaService;
import pe.edu.unsch.banco.aplicacion.MovimientoRepository;

/** Presentación por consola: menú, lectura de datos e inyección de dependencias. */
public final class Main {
    private Main() { }

    public static void main(String[] args) {
        MovimientoRepository repositorio = new ArchivoMovimientoRepository();
        CuentaService servicio = new CuentaService(repositorio);

        System.out.println("ADVERTENCIA: movimientos.txt es solo un historial.");
        System.out.println("La cuenta activa NO se restaura al reiniciar el programa.");
        try (Scanner scanner = new Scanner(System.in)) {
            boolean continuar = true;
            while (continuar) {
                mostrarMenu();
                String opcion = scanner.nextLine().trim();
                try {
                    switch (opcion) {
                        case "1": {
                            System.out.print("Ingrese titular: ");
                            String titular = scanner.nextLine();
                            BigDecimal saldoInicial = leerMonto(scanner, "Ingrese saldo inicial: ");
                            servicio.registrar(titular, saldoInicial);
                            System.out.println("Cuenta registrada. Saldo: S/ " + servicio.consultarSaldo());
                            break;
                        }
                        case "2": {
                            BigDecimal monto = leerMonto(scanner, "Monto a depositar: ");
                            servicio.depositar(monto);
                            System.out.println("Depósito exitoso. Saldo: S/ " + servicio.consultarSaldo());
                            break;
                        }
                        case "3": {
                            BigDecimal monto = leerMonto(scanner, "Monto a retirar: ");
                            servicio.retirar(monto);
                            System.out.println("Retiro exitoso. Saldo: S/ " + servicio.consultarSaldo());
                            break;
                        }
                        case "4":
                            System.out.println("Titular: " + servicio.consultarTitular());
                            System.out.println("Saldo: S/ " + servicio.consultarSaldo());
                            break;
                        case "5":
                            System.out.println("Saliendo del sistema...");
                            continuar = false;
                            break;
                        default:
                            System.out.println("Opción inválida. Intente de nuevo.");
                    }
                } catch (IllegalArgumentException | IllegalStateException | UncheckedIOException ex) {
                    System.out.println("Operación rechazada: " + ex.getMessage());
                }
            }
        }
    }

    private static BigDecimal leerMonto(Scanner scanner, String indicacion) {
        System.out.print(indicacion);
        return new BigDecimal(scanner.nextLine().trim());
    }

    private static void mostrarMenu() {
        System.out.println("\n=== SISTEMA BANCARIO ===");
        System.out.println("1. Registrar cuenta");
        System.out.println("2. Depositar dinero");
        System.out.println("3. Retirar dinero");
        System.out.println("4. Consultar saldo");
        System.out.println("5. Salir");
        System.out.print("Seleccione una opción: ");
    }
}

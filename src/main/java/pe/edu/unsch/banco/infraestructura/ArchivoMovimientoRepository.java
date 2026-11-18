package pe.edu.unsch.banco.infraestructura;

import java.io.FileWriter;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.Objects;
import pe.edu.unsch.banco.aplicacion.MovimientoRepository;

/** Adaptador de infraestructura que agrega entradas al historial de texto. */
public final class ArchivoMovimientoRepository implements MovimientoRepository {
    private final Path rutaArchivo;

    public ArchivoMovimientoRepository() {
        this(Path.of("movimientos.txt"));
    }

    public ArchivoMovimientoRepository(Path rutaArchivo) {
        this.rutaArchivo = Objects.requireNonNull(rutaArchivo);
    }

    @Override
    public void guardar(String movimiento) {
        try (FileWriter escritor = new FileWriter(rutaArchivo.toFile(), StandardCharsets.UTF_8, true)) {
            escritor.write(LocalDateTime.now() + " | " + movimiento + System.lineSeparator());
        } catch (IOException ex) {
            throw new UncheckedIOException("No se pudo guardar el historial de movimientos.", ex);
        }
    }
}

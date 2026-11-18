# Preguntas de sustentación - LABORATORIO 07-A

1. **¿Qué problema resolvieron al separar CuentaBancaria de Main?** Antes una sola clase tenía interfaz, reglas de negocio y archivos. Ahora `CuentaBancaria` maneja reglas bancarias y `Main` solo entradas/salidas, de modo que se pueden modificar o probar por separado.
2. **¿Por qué CuentaService recibe MovimientoRepository por constructor?** Porque necesita registrar movimientos, pero no debe depender directamente de `FileWriter`. Recibe un contrato que puede implementar un archivo, una base de datos o un repositorio de pruebas.
3. **¿Qué cambia si se reemplaza FileWriter por JDBC/H2?** Crear o reemplazar un adaptador en infraestructura y cambiar la instancia inyectada en `Main`; las clases `CuentaBancaria`, `CuentaService` y la interfaz no necesitan cambiar si el contrato sigue siendo el mismo.
4. **¿En qué capa se valida que el retiro no exceda el saldo?** En el dominio, dentro de `CuentaBancaria.retirar()`; no es responsabilidad del menú.
5. **¿Cuál es la diferencia entre registrar movimientos y persistir cuentas?** El historial registra que ocurrieron acciones; la persistencia completa guarda el estado de la cuenta para recuperarlo al reiniciar. En este laboratorio solo se implementa el historial.

## Respuestas a las preguntas orientadoras

- **¿Qué pasa al cambiar archivo por base de datos?** Solo se cambia el adaptador de persistencia y su configuración en la capa externa; la lógica de negocio sigue igual.
- **¿Por qué la entidad no usa Scanner o FileWriter?** Porque son detalles de consola y tecnología de almacenamiento, no reglas del banco.
- **¿Qué sucede al registrar una segunda cuenta?** La solución informa que ya existe una cuenta activa y mantiene la primera sin sobrescribirla.

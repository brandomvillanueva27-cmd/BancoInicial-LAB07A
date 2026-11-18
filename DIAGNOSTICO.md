# DIAGNÓSTICO – LABORATORIO 07-A

## 1. Revisión del código inicial

Para comenzar el laboratorio, se revisó el archivo `SistemaBancario.java`, que venía en el código proporcionado por el docente.

El programa permite registrar una cuenta, depositar, retirar dinero y consultar el saldo. Sin embargo, encontré que casi todo se realiza dentro de una sola clase.

Por ejemplo, la misma clase pide los datos al usuario, comprueba los montos, modifica el saldo, muestra los resultados y guarda los movimientos en un archivo de texto.

Esto puede funcionar para un programa pequeño, pero sería un problema si más adelante necesitamos agregar nuevas funciones. Un cambio en el sistema podría obligarnos a modificar varias partes de la misma clase.

## 2. Responsabilidades encontradas

Al revisar el código identifiqué seis responsabilidades que estaban juntas.

| Responsabilidad | Dónde se encuentra | Qué pasaría si cambia el sistema |
|---|---|---|
| Leer datos | Se utiliza `Scanner` para pedir el titular y los montos. | Tendríamos que cambiar el código si usamos una interfaz gráfica. |
| Validar operaciones | Se comprueba que los montos sean mayores que cero y que exista saldo suficiente. | Habría que modificar las condiciones para agregar límites o comisiones. |
| Actualizar el saldo | Se utiliza una variable `double` y operaciones de suma y resta. | Tendríamos que cambiar esta parte si necesitamos mayor precisión. |
| Guardar movimientos | Se utiliza `FileWriter` para escribir en `movimientos.txt`. | Si usamos una base de datos, habría que modificar el almacenamiento. |
| Mostrar resultados | Los mensajes se imprimen mediante `System.out.println`. | Habría que cambiar los mensajes si el programa tuviera una interfaz diferente. |
| Controlar el menú | El método `main` utiliza un `switch` para seleccionar las opciones. | Tendríamos que modificarlo para agregar otras formas de interacción. |

El principal problema es que todas estas tareas dependen de `SistemaBancario.java`. Por eso decidí separarlas en clases con responsabilidades específicas.

## 3. Problemas encontrados en el programa original

Durante la revisión encontré los siguientes problemas:

1. **Reemplazo de cuentas:** cuando se registra una segunda cuenta, los datos de la primera se pierden porque el programa reemplaza el titular y el saldo.

2. **Uso de double:** el saldo se maneja con `double`, que puede generar pequeñas diferencias de precisión al trabajar con valores decimales.

3. **Operaciones sin registrar una cuenta:** el código original no comprueba correctamente si existe una cuenta antes de realizar determinadas operaciones.

4. **Saldo inicial negativo:** al registrar una cuenta, no se valida que el saldo inicial sea correcto.

5. **Lectura de datos:** se utilizan dos objetos `Scanner` para leer desde `System.in`, lo que puede complicar el manejo de las entradas.

6. **Datos incorrectos:** si el usuario escribe letras cuando se espera un número, el programa puede terminar con un error.

7. **Historial de movimientos:** el archivo `movimientos.txt` registra operaciones, pero no permite recuperar automáticamente una cuenta después de cerrar el programa.

8. **Errores al guardar archivos:** cuando ocurre un problema de escritura, el código original muestra un mensaje, pero no comunica ese fallo de forma adecuada a la lógica de las operaciones.

Estos problemas muestran que el programa necesita una mejor organización, especialmente para separar las reglas bancarias de la entrada de datos y del almacenamiento.

## 4. Cambios realizados con Clean Architecture

Para mejorar la organización del programa, se separó el código en tres paquetes principales: dominio, aplicación e infraestructura.

### 4.1. Dominio

En este paquete se encuentra `CuentaBancaria.java`.

Esta clase se encarga de manejar los datos de la cuenta y las reglas necesarias para depositar o retirar dinero.

Por ejemplo, antes de retirar un monto comprueba que sea válido y que exista suficiente saldo.

La ventaja es que estas reglas no dependen del menú ni del archivo donde se guardan los movimientos.

### 4.2. Aplicación

En este paquete se encuentran `CuentaService.java` y `MovimientoRepository.java`.

`CuentaService` organiza las operaciones bancarias y controla que no se registre otra cuenta cuando ya existe una activa.

Por su parte, `MovimientoRepository` es una interfaz que establece cómo se solicita guardar un movimiento, sin depender directamente de un archivo de texto.

Esto permite cambiar posteriormente la forma de almacenamiento sin modificar las reglas principales del banco.

### 4.3. Infraestructura

En este paquete se encuentran `ArchivoMovimientoRepository.java` y `Main.java`.

`ArchivoMovimientoRepository` permite guardar los movimientos dentro de `movimientos.txt`.

`Main` muestra el menú, recibe los datos del usuario y conecta las clases necesarias para ejecutar las operaciones.

De esta manera, el menú ya no necesita realizar directamente las operaciones sobre el saldo.

## 5. Regla de dependencias

Una parte importante de Clean Architecture es evitar que las reglas principales del programa dependan de detalles externos.

En este proyecto, `CuentaBancaria` no necesita conocer cómo funciona el menú ni cómo se guardan los archivos.

De igual manera, `CuentaService` trabaja con la interfaz `MovimientoRepository`, sin depender directamente de `ArchivoMovimientoRepository`.

Por eso, si después queremos guardar los movimientos en una base de datos, principalmente tendríamos que cambiar el adaptador de almacenamiento y la forma de conectarlo en `Main`.

Las reglas bancarias podrían mantenerse.

## 6. Uso de BigDecimal

En el código original se utilizaba `double` para representar el dinero.

En la versión organizada se utiliza `BigDecimal`, porque permite trabajar con valores decimales de forma más precisa.

Esto es importante en operaciones bancarias, donde una pequeña diferencia en los cálculos puede afectar el saldo.

Para este laboratorio se trabajan los importes con dos decimales.

## 7. Corrección del registro de cuentas

Uno de los problemas principales del código original era que se podía registrar otra cuenta y reemplazar los datos anteriores.

En la nueva versión se controla que solo exista una cuenta activa.

Esto lo comprobé durante la prueba P06. Primero registré a Ana y después intenté registrar a Luis sin cerrar el programa.

El sistema rechazó la segunda operación e indicó que ya existía una cuenta activa.

Así se evitó que los datos de Ana fueran reemplazados.

## 8. Alcance del almacenamiento

El programa guarda los movimientos realizados en el archivo `movimientos.txt`.

Durante las pruebas comprobé que se registraron las creaciones de cuenta, los depósitos y los retiros.

Sin embargo, este archivo solo funciona como un historial. No permite recuperar automáticamente una cuenta cuando se vuelve a abrir el programa.

Esto también se comprobó en la prueba P07, cuando cerré el sistema, volví a ejecutarlo e intenté consultar el saldo.

El sistema indicó que primero debía registrar una cuenta.

No se implementó una base de datos ni la recuperación automática de cuentas porque esas funciones no forman parte de los objetivos de este laboratorio.

## 9. Conclusión del diagnóstico

Después de revisar el programa original y la versión reorganizada, considero que el cambio principal fue separar las tareas que antes estaban juntas en una sola clase.

Ahora cada parte del programa tiene una responsabilidad más clara. Esto facilita entender el código, realizar pruebas y modificar algunas funciones sin afectar innecesariamente las demás.

También se corrigió el problema del reemplazo de cuentas y se mejoraron las validaciones de las operaciones bancarias.

Con estos cambios, el programa conserva las cinco opciones originales y presenta una estructura más ordenada siguiendo los principios de Clean Architecture.
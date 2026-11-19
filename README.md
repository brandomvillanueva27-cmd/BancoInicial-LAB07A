# BancoInicial – Laboratorio 07-A

## 1. Descripción del proyecto

En este laboratorio se trabajó con un sistema bancario sencillo que permite registrar una cuenta, depositar dinero, realizar retiros y consultar el saldo.

El código inicial funcionaba, pero tenía un problema de organización: la mayor parte de las operaciones se encontraba dentro de una sola clase llamada `SistemaBancario`.

El objetivo del trabajo fue mejorar esa estructura utilizando Clean Architecture, separando las reglas bancarias del menú y del almacenamiento de movimientos.

También se corrigieron algunos problemas del programa original, como el reemplazo de cuentas y la falta de determinadas validaciones.

## 2. Funciones del sistema

El programa mantiene las cinco opciones solicitadas en la guía:

1. **Registrar cuenta:** permite registrar un titular y su saldo inicial.
2. **Depositar dinero:** aumenta el saldo de la cuenta activa.
3. **Retirar dinero:** permite retirar un monto cuando existen fondos suficientes.
4. **Consultar saldo:** muestra el titular y el saldo disponible.
5. **Salir:** termina la ejecución del programa.

Además, el sistema rechaza montos negativos y evita registrar una segunda cuenta mientras existe otra activa.

## 3. Herramientas utilizadas

Para desarrollar y ejecutar el proyecto se utilizaron:

- Java con JDK 27 en las ejecuciones realizadas.
- IntelliJ IDEA.
- PowerShell como alternativa para ejecutar el programa.
- Archivos Markdown para la documentación.
- Un archivo de texto para registrar los movimientos.

El proyecto requiere JDK 17 o superior.

## 4. Organización del proyecto

El código se encuentra distribuido en carpetas y clases según su responsabilidad.

```text
BancoInicial_LAB07A/
|
|-- codigo-inicial/
|   |-- SistemaBancario.java
|
|-- src/
|   |-- main/
|   |   |-- java/pe/edu/unsch/banco/
|   |       |-- dominio/
|   |       |   |-- CuentaBancaria.java
|   |       |
|   |       |-- aplicacion/
|   |       |   |-- CuentaService.java
|   |       |   |-- MovimientoRepository.java
|   |       |
|   |       |-- infraestructura/
|   |           |-- ArchivoMovimientoRepository.java
|   |           |-- Main.java
|   |
|   |-- test/
|       |-- java/pe/edu/unsch/banco/
|           |-- PruebasAutomaticas.java
|
|-- evidencias/
|   |-- P01.png
|   |-- P02.png
|   |-- P03.png
|   |-- P04.png
|   |-- P05.png
|   |-- P06.png
|   |-- P07.png
|   |-- consultar_saldo.png
|   |-- movimientos.png
|   |-- arquitectura_paquetes.png
|   |-- pruebas_automaticas.png
|
|-- movimientos.txt
|-- DIAGNOSTICO.md
|-- PRUEBAS.md
|-- PREGUNTAS_SUSTENTACION.md
|-- DIFICULTADES_Y_CONCLUSIONES.md
|-- README.md
|-- PASO_A_PASO.md
|-- run.ps1
|-- pruebas.ps1
|-- run.sh
|-- pruebas.sh
```

La carpeta `evidencias` contiene las capturas utilizadas para documentar las pruebas. El archivo `movimientos.txt` se genera o actualiza durante la ejecución del programa.

## 5. ¿Cómo ejecutar el programa?

### Desde IntelliJ IDEA

1. Abrir IntelliJ IDEA.
2. Seleccionar la opción **Open**.
3. Buscar la carpeta `BancoInicial_LAB07A`.
4. Abrir el archivo `Main.java`, ubicado dentro del paquete `infraestructura`.
5. Ejecutar el método `main` utilizando el botón verde.
6. Ingresar las opciones del menú desde la consola.

Al iniciar el programa se muestran las cinco opciones del sistema bancario.

### Desde PowerShell

También se puede ejecutar desde la carpeta principal del proyecto utilizando:

```powershell
.\run.ps1
```

Si PowerShell no permite ejecutar el archivo, se pueden utilizar directamente estos comandos:

```powershell
$archivos = @(Get-ChildItem .\src\main\java -Recurse -Filter *.java | ForEach-Object FullName)
New-Item -ItemType Directory -Force out | Out-Null
javac -encoding UTF-8 -d out $archivos
java -cp out pe.edu.unsch.banco.infraestructura.Main
```

## 6. Pruebas realizadas

Para comprobar el funcionamiento del sistema ejecuté las siete pruebas indicadas en la guía.

| Caso | Prueba realizada | Resultado |
|---|---|---|
| P01 | Registrar a Ana con S/ 1000 | Aprobado |
| P02 | Depositar S/ 200 | Aprobado |
| P03 | Retirar S/ 150 | Aprobado |
| P04 | Intentar retirar S/ 2000 | Aprobado |
| P05 | Intentar depositar S/ -10 | Aprobado |
| P06 | Intentar registrar a Luis | Aprobado |
| P07 | Cerrar, volver a abrir y consultar saldo | Aprobado |

Las operaciones se realizaron desde la consola de IntelliJ IDEA.

Después del depósito y del retiro, el saldo de Ana quedó en S/ 1050.

También comprobé que los intentos de realizar operaciones incorrectas fueron rechazados.

Las capturas y los resultados se encuentran documentados en `PRUEBAS.md`.

## 7. Pruebas automáticas

Además de las pruebas manuales, ejecuté la clase `PruebasAutomaticas.java` desde IntelliJ IDEA.

El programa mostró el siguiente resultado:

```text
P01 OK - Cuenta registrada. Saldo 1000.00
P02 OK - Depósito 200. Saldo 1200.00
P03 OK - Retiro 150. Saldo 1050.00
P04 OK - Retiro rechazado. Saldo 1050.00
P05 OK - Depósito rechazado. Saldo 1050.00
P06 OK - Nueva cuenta rechazada. Ana sigue activa
P07 OK - Nuevo servicio sin cuenta activa. No hay restauración

RESULTADO: 7/7 CASOS SUPERADOS (pruebas de lógica).

Process finished with exit code 0
```

En total se superaron los siete casos de lógica.

La prueba automática P07 comprueba que una nueva instancia del servicio empieza sin una cuenta activa. El cierre y reinicio real del programa se verificó por separado durante las pruebas manuales.

Para ejecutar las pruebas automáticas en Windows se puede utilizar:

```powershell
.\pruebas.ps1
```

También se pueden ejecutar directamente desde `PruebasAutomaticas.java` en IntelliJ IDEA.

## 8. Historial de movimientos

El sistema utiliza el archivo `movimientos.txt` para guardar las operaciones realizadas.

Durante mis pruebas encontré seis registros correspondientes a dos ejecuciones de las operaciones bancarias.

En cada ejecución se registraron tres movimientos:

1. Creación de la cuenta de Ana.
2. Depósito de S/ 200.
3. Retiro de S/ 150.

Los registros contienen información de las operaciones y la fecha y hora en que se realizaron.

El archivo no permite recuperar automáticamente una cuenta cuando se cierra el programa.

Esto quedó comprobado durante la prueba P07, cuando reinicié el sistema e intenté consultar el saldo sin registrar nuevamente una cuenta.

## 9. Aplicación de Clean Architecture

Para organizar el programa se trabajó con tres paquetes principales.

### Dominio

Contiene `CuentaBancaria.java`.

Aquí se encuentran los datos de la cuenta y las reglas para realizar depósitos y retiros.

Esta clase no necesita utilizar `Scanner`, mostrar mensajes en consola ni guardar archivos.

### Aplicación

Contiene `CuentaService.java` y `MovimientoRepository.java`.

`CuentaService` coordina las operaciones y controla la existencia de una cuenta activa.

`MovimientoRepository` establece un contrato para guardar los movimientos.

Gracias a esta interfaz, el servicio no depende directamente de la forma en que se almacenan los datos.

### Infraestructura

Contiene `ArchivoMovimientoRepository.java` y `Main.java`.

La primera clase se encarga de guardar los movimientos en el archivo de texto.

La segunda permite que el usuario interactúe con el sistema mediante el menú y conecta los componentes necesarios.

Esta separación facilita entender el programa y realizar cambios sin modificar todas las clases.

## 10. Regla de dependencias

En Clean Architecture, las reglas principales no deben depender de detalles externos como el menú o los archivos.

Por eso, la clase `CuentaBancaria` no utiliza clases de infraestructura.

De igual manera, `CuentaService` trabaja con la interfaz `MovimientoRepository` y no directamente con `ArchivoMovimientoRepository`.

Si más adelante se reemplaza el archivo de texto por una base de datos utilizando JDBC o H2, se tendría que implementar otro adaptador y modificar la forma en que se conecta desde `Main`.

No sería necesario cambiar las reglas principales de la cuenta bancaria por ese motivo.

## 11. Desarrollo individual

Este laboratorio fue desarrollado de manera individual, realizando las actividades correspondientes a los cuatro roles de la guía.

El trabajo incluyó el análisis del código original, la separación de clases, las validaciones bancarias, el almacenamiento de movimientos, las pruebas y la documentación.

Para presentar el proyecto en GitHub, la guía solicita trabajar con una rama `feature/`, realizar un Pull Request hacia `develop` y crear la etiqueta `lab-7a` sobre la versión integrada.

Estas acciones forman parte del cierre del laboratorio y deben verificarse en el repositorio antes de considerar completada la entrega.

## 12. Limitaciones del programa

Aunque el sistema cumple las operaciones solicitadas, todavía tiene algunas limitaciones.

Solo permite manejar una cuenta activa en memoria y no recupera sus datos al reiniciar.

Tampoco cuenta con autenticación, una base de datos ni mecanismos de concurrencia.

Además, el cambio del saldo y la escritura del historial no forman una única transacción atómica. Por ello, si ocurre un error al guardar el archivo, podría quedar una diferencia entre el saldo en memoria y el historial.

Estas funciones no se implementaron porque exceden el alcance del laboratorio.

## 13. Resultado final

Se consiguió reorganizar el programa bancario utilizando Clean Architecture sin perder las cinco opciones originales.

Las siete pruebas manuales y las siete pruebas automáticas de lógica se ejecutaron correctamente.

También se comprobó que el sistema rechaza operaciones inválidas, mantiene una sola cuenta activa y guarda los movimientos realizados.

El principal resultado fue obtener un código mejor organizado, donde las reglas bancarias, los servicios y el almacenamiento tienen responsabilidades separadas.
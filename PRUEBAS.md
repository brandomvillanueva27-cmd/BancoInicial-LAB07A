
# PRUEBAS - LABORATORIO 07-A

## ARQUITECTURA LIMPIA (CLEAN ARCHITECTURE)

**Curso:** IS-388 - Construcción y Evolución de Software  
**Laboratorio:** 07-A  
**Proyecto:** BancoInicial  
**Fecha:** 18/11/2026  
**Modalidad de desarrollo:** Individual  
**Herramientas:** Java, IntelliJ IDEA y Git

---

## 1. Objetivo de las pruebas

Comprobar que el sistema bancario refactorizado mediante
Clean Architecture conserva las cinco operaciones del
programa original y corrige los problemas identificados.

Se verificaron el registro de cuentas, los depósitos,
los retiros, la consulta de saldo, las validaciones y
el almacenamiento de movimientos.

Las pruebas funcionales se ejecutaron manualmente
en IntelliJ IDEA.

---

## 2. Resultados de las pruebas funcionales

| Caso | Entrada / acción | Resultado esperado | Resultado observado | Estado |
|---|---|---|---|---|
| P01 | Registrar a Ana con S/ 1000 | Cuenta registrada y saldo S/ 1000 | Cuenta registrada. Saldo S/ 1000.00 | APROBADO |
| P02 | Depositar S/ 200 | Saldo S/ 1200 y movimiento registrado | Depósito exitoso. Saldo S/ 1200.00 | APROBADO |
| P03 | Retirar S/ 150 | Saldo S/ 1050 y movimiento registrado | Retiro exitoso. Saldo S/ 1050.00 | APROBADO |
| P04 | Retirar S/ 2000 | Rechazar por fondos insuficientes | Operación rechazada: Fondos insuficientes | APROBADO |
| P05 | Depositar S/ -10 | Rechazar monto negativo | Operación rechazada: El monto debe ser mayor que cero | APROBADO |
| P06 | Registrar a Luis sin reiniciar | No reemplazar la cuenta activa | Operación rechazada porque ya existe una cuenta activa | APROBADO |
| P07 | Salir, reiniciar y consultar saldo | Informar que no se restaura la cuenta | Se mostró la advertencia y se rechazó la consulta | APROBADO |

**Resultado:** 7 de 7 pruebas funcionales ejecutadas
satisfactoriamente.

---

## 3. Evidencias de ejecución

### P01 - Registro de cuenta

Se seleccionó la opción 1 para registrar a Ana con un
saldo inicial de S/ 1000.

El sistema registró la cuenta correctamente y mostró:

- Titular: Ana
- Saldo inicial: S/ 1000.00
- Resultado: Cuenta registrada.

**Estado: APROBADO**

![P01 - Registro de cuenta](evidencia/P01.png)

### P02 - Depósito de dinero

Se seleccionó la opción 2 y se ingresó un depósito
de S/ 200.

El saldo aumentó de S/ 1000 a S/ 1200.

El sistema mostró que el depósito fue exitoso.

**Estado: APROBADO**

![P02 - Deposito](evidencia/P02.png)

### P03 - Retiro de dinero

Se seleccionó la opción 3 para retirar S/ 150.

El sistema descontó correctamente el monto.

Saldo anterior: S/ 1200.00  
Monto retirado: S/ 150.00  
Saldo final: S/ 1050.00

**Estado: APROBADO**

![P03 - Retiro](evidencia/P03.png)

### P04 - Retiro con fondos insuficientes

Se intentó retirar S/ 2000 cuando el saldo
disponible era de S/ 1050.

El sistema mostró:

`Operacion rechazada: Fondos insuficientes.`

La operación fue rechazada correctamente.

**Estado: APROBADO**

![P04 - Fondos insuficientes](evidencia/P04.png)

### P05 - Depósito con monto negativo

Se intentó depositar S/ -10.

El sistema mostró:

`Operacion rechazada: El monto debe ser mayor que cero.`

Después de la operación se consultó el saldo,
comprobando que permanecía en S/ 1050.00.

**Estado: APROBADO**

![P05 - Monto negativo](evidencia/P05.png)

### P06 - Registro de segunda cuenta

Se intentó registrar una nueva cuenta a nombre
de Luis con S/ 1500, sin cerrar la sesión anterior.

El sistema mostró:

`Operacion rechazada: Ya existe una cuenta activa. No se reemplazara.`

De esta manera se corrigió el problema del código
inicial, que permitía sobrescribir los datos de
la cuenta anterior.

**Estado: APROBADO**

![P06 - Segunda cuenta](evidencia/P06.png)

### P07 - Reinicio del sistema

Primero se seleccionó la opción 5 para salir.

El programa terminó normalmente con:

`Process finished with exit code 0`

Después se volvió a ejecutar Main.java.

Al iniciar nuevamente, el programa mostró una
advertencia indicando que movimientos.txt es
únicamente un historial.

Se seleccionó la opción 4 para consultar saldo.

El sistema respondió:

`Operacion rechazada: Primero debe registrar una cuenta.`

Esto demuestra que la cuenta no se recupera
automáticamente después de reiniciar.

**Estado: APROBADO**

![P07 - Reinicio del sistema](evidencia/P07.png)

---

## 4. Evidencia de consulta de saldo

Después de realizar el depósito y el retiro,
se utilizó la opción 4 del menú principal.

El programa mostró:

- Titular: Ana
- Saldo: S/ 1050.00

El saldo coincide con las operaciones realizadas:

Saldo inicial: S/ 1000  
Depósito: + S/ 200  
Retiro: - S/ 150  
Saldo final: S/ 1050

**Resultado: CORRECTO**

![Consulta de saldo](evidencia/consultar_saldo.png)

---

## 5. Verificación de movimientos.txt

Se abrió el archivo movimientos.txt desde
el explorador del proyecto en IntelliJ IDEA.

Se encontraron seis registros correspondientes
a dos ejecuciones de las operaciones bancarias.

| Tipo de movimiento | Cantidad |
|---|---|
| Creación de cuenta | 2 |
| Depósito de dinero | 2 |
| Retiro de dinero | 2 |
| TOTAL | 6 |

En cada ejecución se realizaron las siguientes
operaciones:

1. Registro de Ana con S/ 1000.
2. Depósito de S/ 200.
3. Retiro de S/ 150.

Los registros contienen la fecha, la hora,
el titular y los detalles de las operaciones.

Las operaciones rechazadas no generaron
movimientos exitosos en el historial.

**Resultado: CORRECTO**

![Historial de movimientos](evidencia/movimientos.png)

---

## 6. Historial versus persistencia

Durante las pruebas se comprobó que movimientos.txt
guarda información sobre las operaciones realizadas.

Sin embargo, este archivo no permite recuperar
automáticamente el saldo y los datos de una cuenta
cuando se vuelve a ejecutar el programa.

Por lo tanto, existe una diferencia entre:

**Historial de movimientos:**
Guarda registros de depósitos, retiros y creación
de cuentas.

**Persistencia completa de cuentas:**
Permitiría guardar y recuperar los datos de las
cuentas incluso después de cerrar el sistema.

En este laboratorio únicamente se implementó
el historial de movimientos, conforme a la guía.

---

## 7. Verificación de Clean Architecture

Se revisó la organización del código Java en
tres paquetes principales.

### Dominio

Archivo:

- CuentaBancaria.java

Responsabilidad:

Representar la cuenta bancaria y aplicar las reglas
de depósitos, retiros y validación de montos.

### Aplicación

Archivos:

- CuentaService.java
- MovimientoRepository.java

Responsabilidad:

Coordinar las operaciones bancarias y definir
el contrato para guardar movimientos.

### Infraestructura

Archivos:

- ArchivoMovimientoRepository.java
- Main.java

Responsabilidad:

Implementar el almacenamiento en archivo,
mostrar el menú y permitir la interacción
con el usuario.

La separación permite mantener las reglas
bancarias independientes del menú y del
mecanismo concreto de almacenamiento.

![Estructura de Clean Architecture](evidencia/arquitectura_paquetes.png)

---

## 8. Pruebas automáticas

Se ejecutó la clase PruebasAutomaticas.java
desde IntelliJ IDEA para verificar el correcto
funcionamiento de las operaciones bancarias.

### Resultados obtenidos

| Caso | Verificación | Estado |
|---|---|---|
| P01 | Registro de cuenta con S/ 1000 | APROBADO |
| P02 | Depósito de S/ 200 | APROBADO |
| P03 | Retiro de S/ 150 | APROBADO |
| P04 | Rechazo de retiro de S/ 2000 | APROBADO |
| P05 | Rechazo de depósito de S/ -10 | APROBADO |
| P06 | Rechazo de segunda cuenta | APROBADO |
| P07 | Nuevo servicio sin cuenta activa | APROBADO |

### Resultado de ejecución

RESULTADO: 7/7 CASOS SUPERADOS (pruebas de lógica).

Process finished with exit code 0

La prueba P07 automatizada verifica que una nueva
instancia del servicio no contiene una cuenta activa.
Además, se comprobó manualmente el cierre y reinicio
real del programa en IntelliJ IDEA.

### Evidencia

![Pruebas automáticas](evidencia/pruebas_automaticas.png)

**Estado final: APROBADO**

## 9. Dificultades encontradas

Durante el desarrollo se identificaron
las siguientes dificultades:

1. Comprender la separación entre dominio,
   aplicación e infraestructura.
2. Identificar las responsabilidades mezcladas
   dentro de SistemaBancario.java.
3. Evitar que una nueva cuenta reemplazara
   a la cuenta activa.
4. Validar correctamente los montos de
   depósitos y retiros.
5. Diferenciar el historial de movimientos
   de la persistencia completa de cuentas.

La organización del código en paquetes permitió
separar estas responsabilidades y facilitar
la comprensión del funcionamiento del programa.

---

## 10. Conclusiones

1. Se logró ejecutar correctamente las cinco
   opciones principales del sistema bancario.

2. Las siete pruebas manuales solicitadas en
   el laboratorio fueron satisfactorias.

3. Se comprobó que los depósitos y retiros
   modifican correctamente el saldo disponible.

4. Se verificó que el programa rechaza montos
   negativos y retiros superiores al saldo.

5. El sistema impide registrar una segunda
   cuenta mientras existe otra cuenta activa.

6. Se comprobó que movimientos.txt almacena
   el historial, pero no restaura las cuentas
   después de reiniciar el programa.

7. La aplicación de Clean Architecture permitió
   separar las responsabilidades del sistema
   y mejorar su organización y mantenimiento.

---

## 11. Resultado final

**PRUEBAS MANUALES: 7/7 APROBADAS**

**CONSULTA DE SALDO: CORRECTA**

**HISTORIAL DE MOVIMIENTOS: VERIFICADO**

**ESTRUCTURA DE PAQUETES: VERIFICADA**

**PRUEBAS AUTOMÁTICAS: 7/7 APROBADAS**

El sistema bancario refactorizado conserva
las funcionalidades solicitadas y cumple
las comprobaciones funcionales realizadas
durante el Laboratorio 07-A.

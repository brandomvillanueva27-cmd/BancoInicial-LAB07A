# DIFICULTADES Y CONCLUSIONES – LABORATORIO 07-A

## 1. Dificultades identificadas

Durante el desarrollo del laboratorio, uno de los principales aspectos que tuve que resolver fue la organización del código original, porque todas las operaciones se encontraban dentro de una misma clase.

Para mejorar esto, separé las responsabilidades en dominio, aplicación e infraestructura, siguiendo la estructura de Clean Architecture.

Otro problema importante era que el sistema permitía registrar una segunda cuenta y reemplazar los datos anteriores. En la nueva versión se corrigió ese comportamiento para mantener una sola cuenta activa.

También fue necesario comprobar las validaciones de los depósitos y retiros, especialmente cuando se ingresaban montos negativos o superiores al saldo disponible.

Finalmente, verifiqué que `movimientos.txt` guardaba correctamente las operaciones realizadas. Sin embargo, comprobé que este archivo no recupera automáticamente las cuentas después de reiniciar el programa.

## 2. Resultados obtenidos

El programa conservó sus cinco opciones principales y las pruebas manuales P01–P07 se ejecutaron correctamente.

También ejecuté `PruebasAutomaticas.java`, obteniendo siete casos de lógica superados y una finalización sin errores.

Durante las ejecuciones se registraron seis movimientos en el archivo de historial: dos creaciones de cuenta, dos depósitos y dos retiros.

Las operaciones rechazadas no modificaron el saldo.

## 3. Conclusiones

1. Aprendí cómo organizar un programa utilizando Clean Architecture, separando las reglas bancarias de la entrada de datos y del almacenamiento.

2. Se corrigió el problema del registro de cuentas, evitando que una segunda cuenta reemplace la información de la primera.

3. Las pruebas permitieron comprobar que los depósitos, retiros y consultas funcionan correctamente, incluyendo las validaciones de operaciones incorrectas.

4. Comprendí la importancia de utilizar una interfaz como `MovimientoRepository`, ya que permite cambiar la forma de guardar los movimientos sin modificar las reglas principales del sistema.

5. El programa quedó mejor organizado y es más sencillo identificar qué clase se debe modificar cuando se necesita realizar algún cambio.

En conclusión, el laboratorio permitió mejorar la estructura del sistema bancario sin perder las funciones originales. Además, pude comprobar su funcionamiento mediante pruebas manuales y automáticas.
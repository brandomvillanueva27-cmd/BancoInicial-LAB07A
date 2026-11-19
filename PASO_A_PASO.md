# Guía práctica paso a paso - Laboratorio 07-A

## 0. Preparar el entorno

Abrir PowerShell y comprobar:

```powershell
java -version
javac -version
git --version
```

Se necesita JDK 17 o posterior, IntelliJ IDEA (recomendado), Git y GitHub.

## 1. Reproducir el sistema inicial (actividad A)

1. Crea un proyecto **Java** llamado `BancoInicial` en IntelliJ IDEA.
2. Coloca una copia de `codigo-inicial/SistemaBancario.java` en una carpeta temporal del proyecto, fuera de las nuevas clases por paquetes.
3. Ábrelo y ejecuta el `main` de la clase `SistemaBancario`.
4. Toma una captura del menú original.
5. Identifica en ese único archivo el uso de `Scanner`, validación, cambio del saldo, `FileWriter`, `System.out` y `switch`.
6. Completa `DIAGNOSTICO.md`. El código original usa dos `Scanner` con la misma entrada y puede fallar; si sucede, descríbelo como hallazgo, no lo ocultes.

Para compilar de forma aislada el original desde la carpeta raíz:

```powershell
javac -encoding UTF-8 -d .\out-original .\codigo-inicial\SistemaBancario.java
java -cp .\out-original SistemaBancario
```

## 2. Entender la nueva estructura (actividad B)

Abre `src/main/java/pe/edu/unsch/banco/` y revisa cada paquete:

- `dominio`: entidad `CuentaBancaria` y validaciones monetarias.
- `aplicacion`: servicio `CuentaService` y contrato `MovimientoRepository`.
- `infraestructura`: implementación `ArchivoMovimientoRepository` y consola `Main`.

El orden de trabajo recomendado: diseñar el contrato; implementar dominio; construir servicio; implementar archivo; conectar el menú.

## 3. Abrir y ejecutar el programa refactorizado

Desde la carpeta raíz, en PowerShell:

```powershell
.\run.ps1
```

O, si la ejecución de scripts está restringida:

```powershell
$archivos = @(Get-ChildItem .\src\main\java -Recurse -Filter *.java | ForEach-Object FullName)
New-Item -ItemType Directory -Force out | Out-Null
javac -encoding UTF-8 -d out $archivos
java -cp out pe.edu.unsch.banco.infraestructura.Main
```

En IntelliJ, ejecuta el método `main()` de `infraestructura/Main.java`.

## 4. Pruebas P01 a P07 (actividad C)

Dentro de la **misma ejecución**, introduce lo siguiente en orden:

1. **Opción 1**, titular `Ana`, saldo `1000` -> saldo `1000.00`.
2. **Opción 2**, depósito `200` -> saldo `1200.00`.
3. **Opción 3**, retiro `150` -> saldo `1050.00`.
4. **Opción 3**, retiro `2000` -> rechazo; saldo sigue `1050.00`.
5. **Opción 2**, depósito `-10` -> rechazo; saldo sigue `1050.00`.
6. **Opción 1**, titular `Luis`, saldo `300` -> rechazo; Ana continúa activa.
7. **Opción 4** -> capturar titular Ana y saldo `1050.00`. **Opción 5** para salir. Reabrir `Main`, elegir **opción 4** sin registrar y verificar advertencia y rechazo.

Verifica que `movimientos.txt` contenga **solo tres movimientos correctos**: registro, depósito y retiro; los rechazados no crean registros.

La ubicación de `movimientos.txt` es el *working directory* de la aplicación, por defecto la raíz desde donde ejecutes `java`, que IntelliJ puede configurar en su Run Configuration.

## 5. Registrar evidencias

1. Captura del funcionamiento inicial y tabla de diagnóstico.
2. Captura del árbol de paquetes final en IntelliJ.
3. Capturas P01–P07 (o registro textual suficiente para P01–P06, según la guía).
4. Captura del archivo `movimientos.txt`.
5. Captura de consultar saldo con Ana/S/1050.
6. Actualizar la columna de resultados reales en `PRUEBAS.md` y colocar archivos PNG en `evidencias/`.
7. Ejecutar también `pruebas.ps1` para verificar automáticamente las reglas (no reemplaza evidencia manual de P07).

## 6. Git y GitHub en equipo

Para un repositorio ya creado, con `develop` existente:

```powershell
git fetch origin
git switch develop
git pull origin develop
git switch -c feature/lab7a-aplicacion
# Copiar o editar SOLO los archivos asignados a tu rol.
git add .
git commit -m "feat(lab7a): implementar servicio y contrato de movimientos"
git push -u origin feature/lab7a-aplicacion
```

Después, GitHub -> **Compare & pull request** -> **base: develop**. No hagas merge de una rama de equipo sin la revisión o la coordinación indicada por el líder.

Otros roles pueden usar ramas distintas:

- `feature/lab7a-dominio`
- `feature/lab7a-aplicacion`
- `feature/lab7a-infraestructura`
- `feature/lab7a-menu-pruebas`

Cuando los PR estén integrados y el equipo haya verificado la rama `develop`, el responsable debe crear la etiqueta:

```powershell
git switch develop
git pull origin develop
git tag lab-7a
git push origin lab-7a
```

## 7. Entregar

- Enlace a GitHub con paquetes y PR integrados.
- `README.md` (ejecución y arquitectura).
- `DIAGNOSTICO.md` (mínimo 5 responsabilidades).
- `PRUEBAS.md` con los 7 resultados y evidencias reales.
- Capturas de historial y consulta de saldo.
- `PREGUNTAS_SUSTENTACION.md`.
- `DIFICULTADES_Y_CONCLUSIONES.md` con dificultades realmente experimentadas.
- Etiqueta `lab-7a` sobre el commit final integrado.

**Importante:** la persistencia de cuentas no forma parte de este laboratorio; el texto de movimientos no se debe utilizar para afirmar que la cuenta se recupera tras reiniciar.

$ErrorActionPreference = "Stop"
$fuentes = @(Get-ChildItem -Path ".\src\main\java" -Recurse -Filter "*.java" | ForEach-Object { $_.FullName })
New-Item -ItemType Directory -Force -Path ".\out" | Out-Null
& javac -encoding UTF-8 -d ".\out" $fuentes
if ($LASTEXITCODE -ne 0) { throw "La compilación falló" }
& java -cp ".\out" pe.edu.unsch.banco.infraestructura.Main

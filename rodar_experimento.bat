@echo off
echo ============================
echo Preparando ambiente...
echo ============================

:: Cria diretório de classes se não existir
if not exist "target\classes" mkdir target\classes

:: Cria diretório de resultados se não existir
if not exist "resultados\tempos" mkdir resultados\tempos

:: Limpa arquivos CSV antigos
del /q resultados\tempos\*.csv 2>nul

echo ============================
echo Compilando o projeto...
echo ============================

:: Compila os arquivos .java
javac -d target\classes src\main\java\univille\br\AlgoritmosOrdenacao.java src\main\java\univille\br\ExperimentoOrdenacao.java

if %errorlevel% neq 0 (
    echo.
    echo ERRO: Falha na compilação!
    pause
    exit /b
)

echo ============================
echo Executando o Experimento...
echo ============================

:: Executa o Experimento
java -cp target\classes univille.br.ExperimentoOrdenacao

echo ============================
echo Experimento Finalizado!
echo ============================
pause

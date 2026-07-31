@echo off
REM ===========================================================================
REM run-jacoco.bat - Executa testes com JaCoCo e abre relatorio de cobertura
REM Projeto: API Nutricao (Spring Boot + Maven)
REM ===========================================================================
REM
REM Pre-requisito: Maven (mvn) disponivel no PATH
REM
REM Como usar:
REM   run-jacoco.bat              roda com Maven online
REM   run-jacoco.bat -o           roda com Maven offline (--offline)
REM   run-jacoco.bat -h           exibe esta ajuda
REM
REM Resultado: target\site\jacoco\index.html
REM ===========================================================================
setlocal

set "PROJECT_DIR=%~dp0"
set "OFFLINE="

REM ---- Processa argumentos ----
:PARSE
if not "%1"=="" (
    if "%1"=="-o" (
        set OFFLINE=--offline
    )
    if "%1"=="-h" (
        goto :HELP
    )
    shift
    goto :PARSE
)

echo.
echo ======================================================================
echo  JaCoCo Coverage Runner - API Nutricao
echo ======================================================================
echo.

REM ---- 1) Executa testes com Maven e JaCoCo ----
echo [1/2] Executando testes com Maven...
echo.

mvn clean test jacoco:report %OFFLINE% -f "%PROJECT_DIR%pom.xml"
if errorlevel 1 (
    echo.
    echo [ERRO] Falha ao executar testes ou gerar relatorio.
    echo        Verifique os erros acima antes de continuar.
    pause
    exit /b 1
)

echo.
echo [OK] Testes executados com sucesso.

REM ---- 2) Abre relatorio HTML no navegador padrao ----
set "REPORT_FILE=%PROJECT_DIR%target\site\jacoco\index.html"

echo.
echo [2/2] Abrindo relatorio HTML no navegador padrao...
echo.
echo    %REPORT_FILE%
echo.

if exist "%REPORT_FILE%" (
    start "" "%REPORT_FILE%"
) else (
    echo [AVISO] Relatorio nao encontrado em %REPORT_FILE%
    echo         Execute este script na raiz do projeto.
    pause
    exit /b 1
)

echo.
echo ======================================================================
echo  Relatorio de cobertura aberto no navegador.
echo  Para exportar CSV: target\site\jacoco\jacoco.csv
echo ======================================================================
echo.
pause
exit /b 0

:HELP
echo.
echo Uso: %~nx0 [-o] [-h]
echo.
echo Opcoes:
echo   -o   Modo offline (mvn --offline, sem baixar dependencias)
echo   -h   Exibe esta ajuda
echo.
echo Exemplos:
echo   %~nx0        Roda testes online
echo   %~nx0 -o     Roda testes offline
echo.
exit /b 0

endlocal

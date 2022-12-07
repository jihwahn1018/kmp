@rem
@rem Copyright 2015 the original author or authors.
@rem
@rem Licensed under the Apache License, Version 2.0 (the "License");
@rem you may not use this file except in compliance with the License.
@rem You may obtain a copy of the License at
@rem
@rem      https://www.apache.org/licenses/LICENSE-2.0
@rem
@rem Unless required by applicable law or agreed to in writing, software
@rem distributed under the License is distributed on an "AS IS" BASIS,
@rem WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
@rem See the License for the specific language governing permissions and
@rem limitations under the License.
@rem

@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  k8s-api-service startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Resolve any "." and ".." in APP_HOME to make it shorter.
for %%i in ("%APP_HOME%") do set APP_HOME=%%~fi

@rem Add default JVM options here. You can also use JAVA_OPTS and K8S_API_SERVICE_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto execute

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto execute

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\KubernetesApiService-0.0.1.jar;%APP_HOME%\lib\super-app-server-0.0.7.jar;%APP_HOME%\lib\netty-all-4.1.78.Final.jar;%APP_HOME%\lib\log4j-over-slf4j-1.7.32.jar;%APP_HOME%\lib\client-java-16.0.0.jar;%APP_HOME%\lib\jose4j-0.7.12.jar;%APP_HOME%\lib\slf4j-api-1.7.36.jar;%APP_HOME%\lib\client-java-api-16.0.0.jar;%APP_HOME%\lib\gson-fire-1.8.5.jar;%APP_HOME%\lib\gson-2.9.0.jar;%APP_HOME%\lib\commons-dbcp2-2.9.0.jar;%APP_HOME%\lib\json-20220924.jar;%APP_HOME%\lib\netty-transport-native-epoll-4.1.78.Final-linux-x86_64.jar;%APP_HOME%\lib\netty-transport-native-epoll-4.1.78.Final-linux-aarch_64.jar;%APP_HOME%\lib\netty-transport-native-kqueue-4.1.78.Final-osx-x86_64.jar;%APP_HOME%\lib\netty-transport-native-kqueue-4.1.78.Final-osx-aarch_64.jar;%APP_HOME%\lib\netty-transport-classes-epoll-4.1.78.Final.jar;%APP_HOME%\lib\netty-transport-classes-kqueue-4.1.78.Final.jar;%APP_HOME%\lib\netty-resolver-dns-native-macos-4.1.78.Final-osx-x86_64.jar;%APP_HOME%\lib\netty-resolver-dns-native-macos-4.1.78.Final-osx-aarch_64.jar;%APP_HOME%\lib\netty-resolver-dns-classes-macos-4.1.78.Final.jar;%APP_HOME%\lib\netty-resolver-dns-4.1.78.Final.jar;%APP_HOME%\lib\netty-handler-4.1.78.Final.jar;%APP_HOME%\lib\netty-transport-native-unix-common-4.1.78.Final.jar;%APP_HOME%\lib\netty-codec-dns-4.1.78.Final.jar;%APP_HOME%\lib\netty-codec-4.1.78.Final.jar;%APP_HOME%\lib\netty-transport-4.1.78.Final.jar;%APP_HOME%\lib\netty-buffer-4.1.78.Final.jar;%APP_HOME%\lib\netty-codec-haproxy-4.1.78.Final.jar;%APP_HOME%\lib\netty-codec-http-4.1.78.Final.jar;%APP_HOME%\lib\netty-codec-http2-4.1.78.Final.jar;%APP_HOME%\lib\netty-codec-memcache-4.1.78.Final.jar;%APP_HOME%\lib\netty-codec-mqtt-4.1.78.Final.jar;%APP_HOME%\lib\netty-codec-redis-4.1.78.Final.jar;%APP_HOME%\lib\netty-codec-smtp-4.1.78.Final.jar;%APP_HOME%\lib\netty-codec-socks-4.1.78.Final.jar;%APP_HOME%\lib\netty-codec-stomp-4.1.78.Final.jar;%APP_HOME%\lib\netty-codec-xml-4.1.78.Final.jar;%APP_HOME%\lib\netty-resolver-4.1.78.Final.jar;%APP_HOME%\lib\netty-common-4.1.78.Final.jar;%APP_HOME%\lib\netty-handler-proxy-4.1.78.Final.jar;%APP_HOME%\lib\netty-transport-rxtx-4.1.78.Final.jar;%APP_HOME%\lib\netty-transport-sctp-4.1.78.Final.jar;%APP_HOME%\lib\netty-transport-udt-4.1.78.Final.jar;%APP_HOME%\lib\commons-pool2-2.10.0.jar;%APP_HOME%\lib\commons-logging-1.2.jar;%APP_HOME%\lib\simpleclient_httpserver-0.15.0.jar;%APP_HOME%\lib\simpleclient_common-0.15.0.jar;%APP_HOME%\lib\simpleclient-0.15.0.jar;%APP_HOME%\lib\client-java-proto-16.0.0.jar;%APP_HOME%\lib\snakeyaml-1.30.jar;%APP_HOME%\lib\commons-codec-1.15.jar;%APP_HOME%\lib\commons-compress-1.21.jar;%APP_HOME%\lib\commons-lang3-3.12.0.jar;%APP_HOME%\lib\commons-io-2.11.0.jar;%APP_HOME%\lib\bcpkix-jdk18on-1.71.jar;%APP_HOME%\lib\protobuf-java-3.21.2.jar;%APP_HOME%\lib\commons-collections4-4.4.jar;%APP_HOME%\lib\simpleclient_tracer_otel-0.15.0.jar;%APP_HOME%\lib\simpleclient_tracer_otel_agent-0.15.0.jar;%APP_HOME%\lib\javax.annotation-api-1.3.2.jar;%APP_HOME%\lib\swagger-annotations-1.6.6.jar;%APP_HOME%\lib\logging-interceptor-4.9.2.jar;%APP_HOME%\lib\okhttp-4.9.2.jar;%APP_HOME%\lib\jsr305-3.0.2.jar;%APP_HOME%\lib\bcutil-jdk18on-1.71.jar;%APP_HOME%\lib\bcprov-jdk18on-1.71.jar;%APP_HOME%\lib\simpleclient_tracer_common-0.15.0.jar;%APP_HOME%\lib\okio-jvm-2.8.0.jar;%APP_HOME%\lib\kotlin-stdlib-jdk8-1.4.10.jar;%APP_HOME%\lib\kotlin-stdlib-jdk7-1.4.10.jar;%APP_HOME%\lib\kotlin-stdlib-1.4.10.jar;%APP_HOME%\lib\kotlin-stdlib-common-1.4.10.jar;%APP_HOME%\lib\annotations-13.0.jar


@rem Execute k8s-api-service
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %K8S_API_SERVICE_OPTS%  -classpath "%CLASSPATH%" com.tmax.supervm.Version %*

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable K8S_API_SERVICE_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%K8S_API_SERVICE_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega

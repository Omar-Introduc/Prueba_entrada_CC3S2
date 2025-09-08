#!/usr/bin/env bash
set -euo pipefail
trap 'echo "[ERROR] Falló en línea $LINENO" >&2' ERR

mkdir -p reports

# TODO: HTTP — guarda headers y explica código en 2–3 líneas al final del archivo
{
  echo "== curl -I example.com =="
  curl -Is https://example.com | sed '/^\r$/d'
  echo
  echo "Explicación: Código HTTP 200 significa OK, indica que la petición fue exitosa
y se recibieron los encabezados de example.com, HTTP/2 versión del protocolo"
} > reports/http.txt

# TODO: DNS — muestra A/AAAA/MX y comenta TTL
{
  echo "== A ==";    dig A example.com +noall +answer
  echo "== AAAA =="; dig AAAA example.com +noall +answer
  echo "== MX ==";   dig MX example.com +noall +answer
  echo
  echo "Nota: TTL alto vs bajo impacta en la duración en que una respuesta DNS
queda almacenada en el caché, para esta caso 86400=24horas útil para estabilidad
pero mucha demora en cambios de servidor"
} > reports/dns.txt

# TODO: TLS — registra versión TLS
{
  echo "== TLS via curl -Iv =="
  curl -Iv https://example.com 2>&1 | sed -n '1,20p'
  echo "versión: TLSv1.3"
} > reports/tls.txt

# TODO: Puertos locales — lista y comenta riesgos
{
  echo "== ss -tuln =="
  ss -tuln || true
  echo
  echo "Riesgos: Puertos abiertos innecesarios pueden aumentar el ataque automatizado a un sistema
,cada puerto genera entrada a atacantes ganando acceso no autorizado, robar datos o ejecutar Código
malicioso."
} > reports/sockets.txt

echo "Reportes generados en ./reports"

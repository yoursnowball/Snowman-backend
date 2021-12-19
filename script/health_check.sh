# health_check.sh
# !/bin/bash #

CURRENT_PORT=$(cat /etc/nginx/service_url.inc | grep -Po '[0-9]+' | tail -1)
TARGET_PORT=0
TARGET_URL="swagger-ui/index.html"

if [ ${CURRENT_PORT} -eq 8081 ]; then
  TARGET_PORT=8082
elif [ ${CURRENT_PORT} -eq 8082 ]; then
  TARGET_PORT=8081
else echo "> No WAS is connected to nginx" exit 1
fi

for RETRY_COUNT in `seq 1 10`
do
  echo "> #${RETRY_COUNT} trying..."
  RESPONSE_CODE=$(curl -s -o /dev/null -w "%{http_code}"http://127.0.0.1:${TARGET_PORT}/${TARGET_URL})
  if [ ${RESPONSE_CODE} -eq 200 ]; then echo "> New WAS successfully running"
  exit 0
  elif [ ${RETRY_COUNT} -eq 10 ]; then
    echo "> Health check failed."
    exit 1
  fi
    sleep 10
done


while true; do

  runnable_services=("portal-ui" "auth-server" "portal-service")

  echo "$(date) checking running docker services";

  running_services=($(docker ps --format "{{.Names}}"))

  stopped_services=($(echo "${runnable_services[@]}" "${running_services[@]}" | tr ' ' '\n' | sort | uniq -u))

  # clear all stopped containers
  if [ ${#stopped_services[@]} -gt 0  ]; then
    docker rm $(docker ps -qa --no-trunc --filter "status=exited")
  fi

  for item in "${stopped_services[@]}"; do
    echo "$item service is stopped.. triggering start.."

    if [ "$item" = "portal-ui" ]; then
      docker run -d -p 80:80 --name portal-ui bhoomitech/portal-ui nginx -g 'daemon off;'

    elif [ "$item" = "auth-server" ]; then
      docker run -p 12002:12002 -d --name auth-server bhoomitech/auth-server java -jar -Dspring.profiles.active=prod auth-service-2021.1-1.jar

    elif [ "$item" = "portal-service" ]; then
      docker run -p 12003:12003 -d --name portal-service bhoomitech/portal-service java -jar portal-service-2021.1-1.jar -Dspring.profiles.active=dev
    fi
  done

  echo "$(date) I'm done sleeping for 10 mins \n"
  sleep 600
done

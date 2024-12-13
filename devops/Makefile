build-jar:
	@ ./gradlew bootJar

build-image:
	@ docker build -f Dockerfile -t biblioteca-app:1 .

deploy:
	@ docker stack deploy --with-registry-auth -c stack.yml biblioteca_app_stack

rm:
	@ docker stack rm biblioteca_app_stack

crear-red:
	@ docker network create --driver overlay --scope swarm biblioteca_net || true

borrar-red:
	@ docker network rm biblioteca_net

crear-volumen:
	@ docker volume create pg_biblioteca_data

borrar-volumen:
	@ docker volume rm pg_biblioteca_data

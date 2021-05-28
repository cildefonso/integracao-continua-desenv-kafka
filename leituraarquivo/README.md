# Para inciar o container, siga os passos abaixo

docker ps -a
docker start id_container
docker logs -f id_container
dpkg -l | grep -i docker

#Versão do Linux
cat /etc/*-release

#Install vim in the red hat
microdnf install vim
or
microdnf install yum

# Projeto Leitor-FTP

# Rodar o servidor de ftp
Na pasta "docker"
```
docker-compose -f docker-server-ftp.yml up
```

# Rodar o S3ninja
Na pasta "docker"
```
docker-compose -f docker-s3ninja.yml up
```

# Rodar o postgres
Na pasta "docker"
```
docker-compose -f docker-postgres.yml up

#Entrar no container do Postgres

docker exec -it 3cb5f64c94e9 /bin/bash

#Entrar no Postgres

psql -U postgres

DROP DATABASE gnus;

CREATE DATABASE gnus;

#Para sair do prompt, digitamos 
\q
Listar os bancos de dados
\l
#Para entrar no banco
\c gnus
#Listar todas as tabelas
SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE';

#Para obter os atributos de uma tabela, use o comando abaixo:
SELECT column_name, data_type, character_maximum_length FROM INFORMATION_SCHEMA.COLUMNS WHERE table_name = 'information_files';


# Rodar o kafka
Na pasta "docker"
_JAVA_OPTIONS == -Xms512m -Xmx1024m
/etc/kafka/
producer.properties
buffer.memory=1024M
´``
docker-compose -f docker-kafka.yml up
```
# Executar o Sonar
Na pasta "docker"

docker-compose -f docker-sonar.yml up
#Instalar os plugins do Sonar
==>Administration  ==> Marketplace ==> All
#Criar token no Sonar
==>Administration ==> security ==> users ==>
#Criar o root para instalações do container
docker exec -u root -t -i jenkins /bin/bash
#Atualização do sistema operacional
apt-get update
#Instalar telnet
apt-get install telnet
#Instalar netstat
apt install net-tools
#comandos
netstat -v
netstat -tl - lista as conexões abertas de tcp em modo de escuta
netstat -t - lista as conexões tcp estabelecidas
netstat -p - lista os programas que estão usando a conexão
netstat --numeric-ports - não converte o número da porta para ser listado
netstat --numeric-hosts - não converte o número de ip para nome do host 
#Instalar ping
apt-get install iputils-ping
sonarqube
7fb7860bcb08d00613fa181a0365120c015a9b23
# Executar o Jenkins
Na pasta "docker"

#Instalar Docker linha comando
docker run -u root --rm -d -p 8080:8080 -p 5000:5000 -v jenkins-data:/var/jenkins_home -v /var/run/docker.sock:/var/run/docker.sock jenkinsci/blueocean
#Use o seguinte comando no prompt de comando:
java -jar jenkins.war --httpPort=8181
#Se você deseja usar https, use o seguinte comando:
java -jar jenkins.war --httpsPort=8143
#Para o Jenkisn do Ubuntu ter autorização precisa executar o coamndo abaixo
sudo chmod 777 /var/run/docker.sock
docker-compose -f docker-jenkins.yml up

#Iniciar Jenkins Ubuntu
systemctl start jenkins
#Verificar situação do Jenkins
systemctl status jenkins
#FireWall do Ubuntu
ufw status

#Criar o root para instalações do container
-H fd:// $DOCKER_OPTS
docker exec -u root -t -i 27e6f1721c1c /bin/bash


#Atualização do sistema operacional
sudo apt-get update

Senha Inicial  44efc8accabc42ebacbd75faee74dad1
#Clicar em Novo JOB
#Entrar nas configurações do "Global Tool Configuration"
1. Maven3
2. Instalar automaticamente Maven
3. Versão do Maven
4. Atualização da JDK apt-get install openjdk-11-jre-headless
5. diretório do linux do container /usr/lib/jvm/java-11-openjdk-amd64/

#Instalando o Docker dentro da máquina do Jenkins.
curl -fsSL https://get.docker.com -o get-docker.sh
sudo sh apt-get-docker.sh
cat /var/lib/jenkins/hudson.tasks.Maven.xml
Sonar   admin   cami8521
#Verificar a network
docker network ls
docker network inspect nome_da_network_criada
#Como obter o endereço IP de um contêiner Docker do host
docker inspect -f '{{range .NetworkSettings.Networks}}{{.IPAddress}}{{end}}' container_name_or_id
#Adicionando Contêiner na REDE
docker network connect (nome da rede) (nome ou id do container)
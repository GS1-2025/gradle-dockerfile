# ☀ Sensolux

## 👨‍💻 INTEGRANTES
- RM558763 • Eric Issamu de Lima Yoshida
- RM555010 • Gustavo Matias Teixeira
- RM555010 • Gustavo Monção

## 💬 Vídeo Pitch
[Link](https://youtu.be/WJmfimRwF8w)

## 💬 Vídeo Demonstrando a Aplicação
[Link](https://youtu.be/D6uLlUSuCcI)

## ⚠ Requisitos mínimos e recomendados para rodar a aplicação completa
- RAM: 3gb - Recomendado: 4gb
- CPU: 1vCPU - Recomendado: 2vCPU
- Disco: 20gb - Recomendado: 30gb

---
## 🖥 1º - Criação da VM

### Grupo de Recursos

```
az group create --name rg-vm-sensolux --location brazilsouth
```

### Criação da VM
```
az vm create \
   --resource-group rg-vm-ssx \
   --name vm-ssx \
   --image almalinux:almalinux-x86_64:9-gen2:9.5.202411260 \
   --size Standard_B1ms \
   --vnet-name nnet-ssx \
   --nsg nsgsr-ssx \
   --public-ip-address pip-almalinux \
   --authentication-type password \
   --admin-username admssx \
   --admin-password Sensolux@2025
```

```
az vm create --resource-group rg-vm-ssx --name vm-ssx --image almalinux:almalinux-x86_64:9-gen2:9.5.202411260 --size Standard_B1ms --vnet-name nnet-ssx --nsg nsgsr-ssx --public-ip-address pip-almalinux --authentication-type password --admin-username admssx --admin-password Sensolux@2025
```
---
## 🚪 2º - Expondo portas

### Porta 5432 - Postgre

- Múltiplas linhas
```
az network nsg rule create \
   --resource-group rg-vm-ssx \
   --nsg-name nsgsr-ssx \
   --name port_5432 \
   --protocol tcp \
   --priority 1010 \
   --destination-port-range 5432
```

- Uma linha
```
az network nsg rule create --resource-group rg-vm-ssx --nsg-name nsgsr-ssx --name port_5432 --protocol tcp --priority 1010 --destination-port-range 22
```

### Porta 8080 - Java

- Múltiplas linhas
```
az network nsg rule create \
   --resource-group rg-vm-ssx \
   --nsg-name nsgsr-ssx \
   --name port_8080 \
   --protocol tcp \
   --priority 1020 \
   --destination-port-range 8080
```

- Uma linha
```
az network nsg rule create --resource-group rg-vm-ssx --nsg-name nsgsr-ssx --name port_8080 --protocol tcp --priority 1020 --destination-port-range 8080
```

### Porta 8081 - Adminer

- Múltiplas linhas
```
az network nsg rule create \
   --resource-group rg-vm-ssx \
   --nsg-name nsgsr-ssx \
   --name port_8081 \
   --protocol tcp \
   --priority 1030 \
   --destination-port-range 8081
```

- Uma linha
```
az network nsg rule create --resource-group rg-vm-ssx --nsg-name nsgsr-ssx --name port_8081 --protocol tcp --priority 1030 --destination-port-range 8081
```
---
## ♻ 3º - Instalação e configuração do Docker e Git na VM

- Faça a conexão via SSH com sua VM.
### Instalação puxando o repositório do docker

```
sudo yum install -y yum-utils -y

```

```
sudo yum-config-manager --add-repo https://download.docker.com/linux/rhel/docker-ce.repo
```

```
sudo yum install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin -y
```
```
sudo systemctl start docker
```
### Teste para ver se a instalação foi feita com sucesso

```
sudo docker ps -a
```
- Se não retornar nada, repita o processo
### Configuração para poder usar o docker sem o sudo

```
sudo usermod -aG docker admmottu
```
- Reinicie a conexão com a VM após usar o comando

### Instalação do Git
```
sudo yum install git -y
```
---
## 📦 4º - Containers

### Criação da rede
```
docker network create ssx-net
```
### Criação do Banco
```
docker run -d --name ssx-db --network ssx-net -e POSTGRES_DB=sensolux -e POSTGRES_USER=postgres -e POSTGRES_PASSWORD=sensolux -v /var/lib/postgresql/data -p 5432:5432 postgres:16
```
- Leva em torno de 10 minutos para a criação completa do banco, para saber se já foi criado, use o comendo `docker ps -a`, se o container estiver em status "UP (Healty)", já pode ser usado.

### Puxando e rodando o APP
- Clona o repositório e já entra da pasta do app
```
git clone https://github.com/GS1-2025/gradle-dockerfile.git && cd gradle-dockerfile/backend-java
```
- Builda o Dockerfile e rode o container: [Dockerfile](backend-java/Dockerfile)
```
docker build -t ssx-gradle .
```
```
docker run -d --name ssx-java --network ssx-net -p 8080:8080 ssx-gradle
```
### Container Adminer
```
docker run -d --name ssx-adminer --network ssx-net -p 8081:8080 adminer
```
---
## ▶ 5º - CRUD

- GET

- POST

- PUT

- DELETE

---
## 🗑 6º - Remoção da VM
```
az group delete --name rg-vm-ssx -y
```
---

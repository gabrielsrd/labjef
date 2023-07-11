## EP - MAC0350
Gabriel da Silva Alves NUSP: 11208109
Andre Gustavo Nakagomi Lopez NUSP: 11796465

### Pré-requisitos

- O PostgreSQL está instalado em seu sistema.

### Instruções

- Para iniciar o postgres em modo interativo:

```bash
sudo -i -u postgres
psql
```

- Para criar o usuario e o banco a ser usado:

```sql
CREATE USER admin WITH ENCRYPTED PASSWORD 'admin';
CREATE DATABASE labjefdb WITH OWNER admin;
GRANT ALL PRIVILEGES ON DATABASE labjefdb TO admin;
exit
exit
```

- Para executar os comandos de criar as tabelas:

```bash
psql -U admin labjefdb -f createdatabase.sql
```

- Para executar os comandos de criar as functions:

```bash
psql -U admin labjefdb -f procedures.sql
```

- Para importar o backup do banco de dados, utilize o seguinte comando:

```bash
psql -U seu_usuario -h localhost -d seu_banco_de_dados < backup.sql
```
Há também o arquivo `backup_sem_dados.sql`, um backup sem os dados.

Lembre-se de substituir `seu_usuario` e `seu_banco_de_dados` pelos seus respectivos nome de usuário e banco de dados do PostgreSQL.

## SISTEMA
- Para executar o sistema, deve estar dentro do diretório `demo` e executar:
```bash
gradle bootRun
```

O sistema deve rodar em localhost:8080.

Em `demo/src/resources`, talvez seja necessário alterar `aplication.properties` e colocar os dados correto do banco.

## EP - MAC0350
Gabriel da Silva Alves NUSP: 11208109
André Gustavo Nakagomi Lopez NUSP: 11796465

### Pré-requisitos

- O PostgreSQL está instalado em seu sistema.

### Instruções

- Para iniciar o postgres em modo interativo:

```bash
sudo -i -u postgres
psql
```

- Se quiser criar um novo usuário para o psql:
```sql
CREATE USER admin WITH ENCRYPTED PASSWORD 'admin';
```

- Para criar o banco e colocar o dono como o usuário criado:
```sql
CREATE DATABASE labjefdb WITH OWNER admin;
GRANT ALL PRIVILEGES ON DATABASE labjefdb TO admin;
\q
exit
```

- Para usar o banco com os dados já inseridos e importar o backup do banco de dados, utilize o seguinte comando:

```bash
psql -U seu_usuario -d seu_banco_de_dados < backup.sql
```
Há também o arquivo `backup_sem_dados.sql`, um backup sem os dados.

Lembre-se de substituir `seu_usuario` e `seu_banco_de_dados` pelos seus respectivos nome de usuário e banco de dados do PostgreSQL.

- Se quiser criar tabelas vazias:

```bash
psql -U admin labjefdb -f createdatabase.sql
```

- Para executar os comandos de criar as functions (apenas necessário se não foi utilizado o arquivo `backup.sql` para popular o banco):

```bash
psql -U admin labjefdb -f procedures.sql
```

## SISTEMA
- Para executar o sistema, deve estar dentro do diretório `demo` e executar:
```bash
gradle bootRun
```

O sistema deve rodar em localhost:8080.

Em `demo/src/main/resources`, talvez seja necessário alterar `aplication.properties` e colocar os dados correto do banco, como nome do usuário criado, senha do usuário criado, e nome do banco de dados criado.

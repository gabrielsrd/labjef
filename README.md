## EP - MAC0350
Gabriel da Silva Alves NUSP: 11208109
Andre Gustavo Nakagomi Lopez NUSP: 11796465

### Pré-requisitos

- O PostgreSQL está instalado em seu sistema.

### Instruções

1. Para iniciar o postgres em modo interativo:

```bash
sudo -i -u postgres
psql
```

2. Para criar o usuario e o banco a ser usado:

```sql
CREATE USER admin WITH ENCRYPTED PASSWORD 'admin';
CREATE DATABASE labjefdb WITH OWNER admin;
GRANT ALL PRIVILEGES ON DATABASE labjefdb TO admin;
exit
exit
```

3. Para executar os comandos de criar as tabelas:

```bash
psql -U admin labjefdb -f createdatabase.sql
```

4. Para executar os comandos de criar as functions:

```bash
psql -U admin labjefdb -f procedures.sql
```

5. Para importar o backup do banco de dados, utilize o seguinte comando:

```bash
psql -U seu_usuario -h localhost -d seu_banco_de_dados < backup.sql
```

Lembre-se de substituir `seu_usuario` e `seu_banco_de_dados` pelos seus respectivos nome de usuário e banco de dados do PostgreSQL.

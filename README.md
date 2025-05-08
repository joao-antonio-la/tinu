# 📏 Tinu
Aplicativo Android para conversão de unidades de medida.

## 📝 Descrição e Especificações
Este projeto utiliza Android Studio (Java) para o desenvolvimento
do aplicativo cliente, Flask (Python) para a criação da API, e
PostgreSQL como sistema de banco de dados.

## 🔷 Componentes

### 📱 App
Contém o código da aplicação Android, incluindo interfaces
gráficas, lógica de navegação e regras de negócio básicas.

### ⚒️ Backend
Responsável por intermediar a conexão entre o app e o banco de
dados de forma segura e controlada. Gerencia operações de login e
registro de usuários.

### 📂 Database
Inclui os scripts SQL necessários para criação das tabelas e
inserção de dados iniciais no banco de dados.

## 🐳 Docker
Docker foi utilizado para facilitar o desenvolvimento, executando
o backend e o banco de dados em ambientes isolados e
independentes. Isso garante que o sistema funcione da mesma forma
em qualquer máquina, sem depender de configurações locais
específicas.

Para iniciar os containers (com o Docker Engine em execução),
utilize:

```bash
docker-compose up --build -d
```

Esse comando baixa as imagens necessárias e executa os containers
com base nas instruções dos arquivos Dockerfile localizados nos
diretórios de backend e database.

---

⚠️ O aplicativo Android não está contido em um container e deve
ser executado via Android Studio.
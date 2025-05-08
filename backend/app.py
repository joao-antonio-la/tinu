from flask import Flask, request, jsonify
import psycopg2
import os
from psycopg2 import sql

app = Flask(__name__)

# conexão com banco de dados
def get_db_connection():
    return psycopg2.connect(
        host=os.environ['DB_HOST'],
        dbname=os.environ['DB_NAME'],
        user=os.environ['DB_USER'],
        password=os.environ['DB_PASSWORD']
    )

# rota de registro
@app.route('/register', methods=['POST'])
def register():
    #0
    data = request.get_json()
    email = data.get('email')
    senha = data.get('senha')

    # retorna erro se algum campo estiver vazio
    if not email or not senha:
        return jsonify({"error": "Email e senha são necessários"}), 400

    conn = get_db_connection()
    cur = conn.cursor()

    # verifica se email está disponível
    cur.execute("SELECT * FROM users WHERE user_email = %s", (email,))
    existing_user = cur.fetchone()
    if existing_user:
        return jsonify({"error": "Email já cadastrado"}), 400

    # tenta inserção no banco de dados
    try:
        insert_query = sql.SQL("""
            INSERT INTO users (user_email, user_password_hash)
            VALUES (%s, crypt(%s, gen_salt('bf', 12)))
        """)
        cur.execute(insert_query, (email, senha))
        conn.commit()
        return jsonify({"message": "Usuário cadastrado com sucesso"}), 201
    # retorna erros, caso haja
    except Exception as e:
        conn.rollback()
        print(e)
        return jsonify({"error": str(e)}), 500
    # fecha a conexão com o bd
    finally:
        cur.close()
        conn.close()

# rota de login
@app.route("/login", methods=["POST"])
def login():
    data = request.get_json()
    email = data.get("email")
    senha = data.get("senha")

    # retorna erro se algum campo estiver vazio
    if not email or not senha:
        return jsonify({"error": "Email e senha são necessários"}), 400

    conn = get_db_connection()
    cur = conn.cursor()    
    
    # tenta inserção no banco de dados
    try:
        search_query = sql.SQL("""
            SELECT *
            FROM users
            WHERE
                user_email = %s
                AND user_password_hash = crypt(%s, user_password_hash)
        """)
        cur.execute(search_query, (email, senha))
        user = cur.fetchone()
        if user:
            return jsonify({"status": "success"}), 200 # retorna sucesso se login for bem sucedido
        else:
            return jsonify({"error": "Usuário não é cadastrado"}), 401 # caso contrário, retorna falha

    # retorna erros, caso haja
    except Exception as e:
        conn.rollback()
        print(e)
        return jsonify({"error": str(e)}), 500
    
    # fecha a conexão com o bd
    finally:
        cur.close()
        conn.close()


if __name__ == "__main__":
    app.run(host="0.0.0.0", port=5000)

package com.example.tinu;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class AcessActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // inicialização padrão
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_acess);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // elementos interativos na tela
        Button criarContaBtn = findViewById(R.id.criar_conta_btn);
        Button entrarBtn = findViewById(R.id.entrar_btn);
        EditText iptEmail = findViewById(R.id.ipt_email);
        EditText iptSenha = findViewById(R.id.ipt_senha);
        TextView resultText = findViewById(R.id.result);

        // ação de redirecionamento para criar conta
        criarContaBtn.setOnClickListener(view -> {
                Intent intent = new Intent(AcessActivity.this, RegisterActivity.class);
                startActivity(intent);
        });

        // tentativa de login
        entrarBtn.setOnClickListener(view -> {

            // coleta de dados
            String email = iptEmail.getText().toString().trim();
            String senha = iptSenha.getText().toString().trim();

            // checagem de input
            if (email.isEmpty() || senha.isEmpty()) {
                runOnUiThread(() -> resultText.setText("Insira todas as credenciais necessárias."));
            } else {
                // nova thread para conexão externa
                new Thread(() -> {
                    try {
                        // conexão com o servidor backend
                        URL url = new URL("http://10.0.2.2:5000/login");
                        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                        conn.setRequestMethod("POST");
                        conn.setRequestProperty("Content-Type", "application/json");
                        conn.setDoOutput(true);

                        // formatação de string json para envio ao servidor
                        String jsonInput = "{\"email\":\"" + email + "\", \"senha\":\"" + senha + "\"}";

                        // coleta da resposta
                        OutputStream os = conn.getOutputStream();
                        os.write(jsonInput.getBytes());
                        os.flush();
                        os.close();
                        int responseCode = conn.getResponseCode();
                        String responseMessage = conn.getResponseMessage();

                        // redirecionamento para função princiapal do app
                        if (responseCode == 200) {
                            Intent intent = new Intent(AcessActivity.this, MainFunctionActivity.class);
                            startActivity(intent);
                        } else {
                            // exibição do erro do servidor, caso haja
                            runOnUiThread(() -> resultText.setText("Erro ao fazer login.\n" + responseMessage));
                        }
                    } catch (Exception e) {
                        // exibição do erro do app, caso haja
                        runOnUiThread(() -> resultText.setText("Erro: " + e.getMessage()));
                    }
                }).start();
            }
        });
    }
}
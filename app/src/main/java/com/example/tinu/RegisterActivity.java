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

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // inicialização padrão
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // elementos interativos na tela
        Button possuoContaBtn = findViewById(R.id.ja_possuo_conta_btn); // redirecionar para login
        Button registrarBtn = findViewById(R.id.registrar_btn); // tentar registro no banco de dados
        EditText iptEmail = findViewById(R.id.ipt_email); // input de email
        EditText iptSenha = findViewById(R.id.ipt_senha); // input de senha
        EditText iptConfirmSenha = findViewById(R.id.ipt_confirm_senha); // input de confirmação de senha
        TextView warningText = findViewById(R.id.warning); // campo de texto para exibir aviso de erros

        // ação de redirecionamento para login
        possuoContaBtn.setOnClickListener(view -> {
                Intent intent = new Intent(RegisterActivity.this, AcessActivity.class);
                startActivity(intent);
        });

        // tentativa de registro
        registrarBtn.setOnClickListener(view -> {

            // coleta de dados
            String email = iptEmail.getText().toString().trim();
            String senha = iptSenha.getText().toString().trim();
            String confirmSenha = iptConfirmSenha.getText().toString().trim();

            // checagem de input
            if (email.isEmpty() || senha.isEmpty() || confirmSenha.isEmpty()) {
                runOnUiThread(() -> warningText.setText("Insira todas as credenciais necessárias."));
            } else if (!confirmSenha.equals(senha)) {
                runOnUiThread(() -> warningText.setText("As senhas precisam ser iguais."));
            } else {
                // nova thread para conexão externa
                new Thread(() -> {
                    try {
                        // conexão com o servidor backend
                        URL url = new URL("http://10.0.2.2:5000/register"); // 10.0.2.2. = localhost
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

                        // redirecionamento para função principal do app
                        if (responseCode == 200 || responseCode == 201) {
                            Intent intent = new Intent(RegisterActivity.this, MainFunctionActivity.class);
                            startActivity(intent);
                        } else {
                            // exibição do erro do servidor, caso haja
                            runOnUiThread(() -> warningText.setText("Erro ao registrar.\n" + responseMessage));
                        }
                    } catch (Exception e) {
                        // exibição do erro do app, caso haja
                        runOnUiThread(() -> warningText.setText("Erro: " + e.getMessage()));
                    }
                }).start();
            }
        });
    }
}
package com.aula.gridpedido;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Pagamento extends AppCompatActivity {

    // Seções de pagamento
    private LinearLayout secaoCartao;
    private LinearLayout secaoPix;

    // Botões de seleção de método
    private Button btnMetodoCartao;
    private Button btnMetodoPix;

    // Campos do cartão
    private EditText editNumeroCartao;
    private EditText editNomeTitular;
    private EditText editValidade;
    private EditText editCvv;

    // Total recebido via Intent
    private double totalPedido;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_pagamento);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        // Recebe o total do carrinho
        totalPedido = getIntent().getDoubleExtra("total", 0.0);

        TextView textTotalPagamento = findViewById(R.id.textTotalPagamento);
        textTotalPagamento.setText(String.format("Total a pagar: R$ %.2f", totalPedido));

        // Vincula os layouts de cada método
        secaoCartao      = findViewById(R.id.secaoCartao);
        secaoPix         = findViewById(R.id.secaoPix);
        btnMetodoCartao  = findViewById(R.id.btnMetodoCartao);
        btnMetodoPix     = findViewById(R.id.btnMetodoPix);

        // Campos do formulário de cartão
        editNumeroCartao = findViewById(R.id.editNumeroCartao);
        editNomeTitular  = findViewById(R.id.editNomeTitular);
        editValidade     = findViewById(R.id.editValidade);
        editCvv          = findViewById(R.id.editCvv);

        // Chave PIX exibida na tela
        TextView textChavePix = findViewById(R.id.textChavePix);
        textChavePix.setText("mercadofacil@pagamentos.com.br");

        // Define cartão como método padrão ao abrir
        selecionarCartao();

        btnMetodoCartao.setOnClickListener(v -> selecionarCartao());
        btnMetodoPix.setOnClickListener(v -> selecionarPix());

        // Botão de confirmar pagamento com cartão
        Button btnPagarCartao = findViewById(R.id.btnPagarCartao);
        btnPagarCartao.setOnClickListener(v -> confirmarPagamentoCartao());

        // Botão de confirmar pagamento PIX
        Button btnPagarPix = findViewById(R.id.btnConfirmarPix);
        btnPagarPix.setOnClickListener(v -> confirmarPagamentoPix());
    }

    private void selecionarCartao() {
        secaoCartao.setVisibility(View.VISIBLE);
        secaoPix.setVisibility(View.GONE);
        btnMetodoCartao.setBackground(Drawable.createFromPath("#4CAF50"));
        btnMetodoPix.setBackgroundColor(Color.parseColor("#9E9E9E"));
    }

    private void selecionarPix() {
        secaoCartao.setVisibility(View.GONE);
        secaoPix.setVisibility(View.VISIBLE);
        btnMetodoPix.setBackground(Drawable.createFromPath("#4CAF50"));
        btnMetodoCartao.setBackground(Drawable.createFromPath("#9E9E9E"));
    }

    private void confirmarPagamentoCartao() {
        String numero  = editNumeroCartao.getText().toString().trim();
        String nome    = editNomeTitular.getText().toString().trim();
        String validade = editValidade.getText().toString().trim();
        String cvv     = editCvv.getText().toString().trim();

        // Validação simples dos campos
        if (numero.isEmpty() || nome.isEmpty() || validade.isEmpty() || cvv.isEmpty()) {
            Toast.makeText(this, "⚠️ Preencha todos os campos!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (numero.length() < 16) {
            Toast.makeText(this, "⚠️ Número do cartão inválido!", Toast.LENGTH_SHORT).show();
            return;
        }
        if (cvv.length() < 3) {
            Toast.makeText(this, "⚠️ CVV inválido!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Simula aprovação do pagamento
        Toast.makeText(this,
                "✅ Pagamento aprovado! Obrigado, " + nome + "!",
                Toast.LENGTH_LONG).show();

        // Aguarda e volta ao início
        new android.os.Handler().postDelayed(this::finish, 2000);
    }

    private void confirmarPagamentoPix() {
        Toast.makeText(this,
                "✅ PIX recebido! Pedido confirmado!",
                Toast.LENGTH_LONG).show();

        new android.os.Handler().postDelayed(this::finish, 2000);
    }
}
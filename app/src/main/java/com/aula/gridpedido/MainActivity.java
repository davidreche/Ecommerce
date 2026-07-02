package com.aula.gridpedido;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.gridpedido.adapter.ProdutoAdapter;
import com.aula.gridpedido.adapter.ProdutoAdapterJson;
import com.aula.gridpedido.api.ProdutoAPI;
import com.aula.gridpedido.model.ProdutoJson;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textTotal;
    private double totalAtual = 0.0;
    private List<Produto> listaProdutos;
    private List<ProdutoJson> listaJson;
    private ProdutoAdapterJson adapterJson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        textTotal = findViewById(R.id.textTotal);
        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        Button btnFinalizar = findViewById(R.id.btnFinalizar);
        int  tipo = getIntent().getIntExtra("opc",0);

        listaProdutos = new ArrayList<>();
        listaJson = new ArrayList<>();

        if (tipo == 0) {
            // Criando a lista de produtos com dados fixos

            listaProdutos.add(new Produto("Arroz 5kg",     25.90));
            listaProdutos.add(new Produto("Feijão 1kg",     9.50));
            listaProdutos.add(new Produto("Macarrão 500g",  4.75));
            listaProdutos.add(new Produto("Azeite 500ml",  32.00));
            listaProdutos.add(new Produto("Café 500g",     18.90));

            // Configurando o Adapter com o callback para atualizar o total
            ProdutoAdapter adapter = new ProdutoAdapter(listaProdutos, outListener -> {
                totalAtual = outListener;
                textTotal.setText(String.format("Total: R$ %.2f", totalAtual));
            });
            recyclerView.setAdapter(adapter);
        } else {
            chamaProdutoAPI();
            // Configurando o Adapter com o callback para atualizar o total
            adapterJson = new ProdutoAdapterJson(listaJson, outListener -> {
                totalAtual = outListener;
                textTotal.setText(String.format("Total: R$ %.2f", totalAtual));
            });
            recyclerView.setAdapter(adapterJson);
        }

        // Configurando o RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Botão Finalizar Pedido
        btnFinalizar.setOnClickListener(v -> {
            if (totalAtual <= 0) {
                Toast.makeText(this,
                        "⚠️ Adicione pelo menos um produto ao carrinho!",
                        Toast.LENGTH_SHORT).show();
                return;
            }
            // Navega para a tela de pagamento passando o total
            Intent intent = new Intent(this, Pagamento.class);
            intent.putExtra("total", totalAtual);
            startActivity(intent);
        });
    }

    private void chamaProdutoAPI() {

        // Definir a URL da API
        String url = "https://fakestoreapi.com";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // chamar a interface da API
        ProdutoAPI produtoAPI = retrofit.create(ProdutoAPI.class);
        Call<List<ProdutoJson>> call = produtoAPI.getProdutos();

        // controlar o retorno da chamada
        call.enqueue(new Callback<List<ProdutoJson>>() {
            @Override
            public void onResponse(Call<List<ProdutoJson>> call, Response<List<ProdutoJson>> response) {
                listaJson.clear();
                listaJson.addAll(response.body());
                adapterJson.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ProdutoJson>> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Erro ao carrregar a lista de produto",
                        Toast.LENGTH_LONG).show();
            }
        })    ;
    }
}
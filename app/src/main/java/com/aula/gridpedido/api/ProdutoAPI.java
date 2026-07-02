package com.aula.gridpedido.api;
import com.aula.gridpedido.model.ProdutoJson;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ProdutoAPI {

    // recuperar lista de produtos

    @GET("/products")

    Call<List<ProdutoJson>> getProdutos();

}

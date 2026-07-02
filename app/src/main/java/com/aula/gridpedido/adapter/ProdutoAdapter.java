package com.aula.gridpedido.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.aula.gridpedido.Produto;
import com.aula.gridpedido.R;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutoViewHolder> {

    // Interface para comunicar mudanças ao Activity
    public interface OnTotalAlteradoListener {
        void onTotalAlterado(double novoTotal);
    }

    private List<Produto> listaProdutos;
    private OnTotalAlteradoListener listener;

    public ProdutoAdapter(List<Produto> listaProdutos, OnTotalAlteradoListener listener) {
        this.listaProdutos = listaProdutos;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ProdutoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_produto, parent, false);
        return new ProdutoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoViewHolder holder, int position) {
        Produto produto = listaProdutos.get(position);

        holder.textNome.setText(produto.getNome());
        holder.textPreco.setText(String.format("R$ %.2f", produto.getPreco()));
        holder.textQuantidade.setText(String.valueOf(produto.getQuantidade()));

        // Desabilita o botão "-" se a quantidade for zero
        holder.btnMenos.setEnabled(produto.getQuantidade() > 0);

        holder.btnMais.setOnClickListener(v -> {
            produto.setQuantidade(produto.getQuantidade() + 1);
            notifyItemChanged(holder.getBindingAdapterPosition());
            notificarTotalAlterado();
        });

        holder.btnMenos.setOnClickListener(v -> {
            produto.setQuantidade(produto.getQuantidade() - 1);
            notifyItemChanged(holder.getBindingAdapterPosition());
            notificarTotalAlterado();

        });
    }

    @Override
    public int getItemCount() {
        return listaProdutos.size();
    }

    // Calcula e envia o total atualizado para o Activity
    private void notificarTotalAlterado() {
        double total = 0;
        for (Produto p : listaProdutos) {
            total += p.getSubtotal();
        }
        listener.onTotalAlterado(total);
    }

    // ViewHolder: representa cada item da lista
    public static class ProdutoViewHolder extends RecyclerView.ViewHolder {

        TextView textNome;
        TextView textPreco;
        TextView textQuantidade;
        Button btnMais;
        Button btnMenos;

        public ProdutoViewHolder(@NonNull View itemView) {
            super(itemView);
            textNome      = itemView.findViewById(R.id.textNome);
            textPreco     = itemView.findViewById(R.id.textPreco);
            textQuantidade = itemView.findViewById(R.id.textQuantidade);
            btnMais       = itemView.findViewById(R.id.btnMais);
            btnMenos      = itemView.findViewById(R.id.btnMenos);
        }
    }
}

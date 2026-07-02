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
import com.aula.gridpedido.model.ProdutoJson;

import java.util.List;

public class ProdutoAdapterJson extends RecyclerView.Adapter<ProdutoAdapterJson.ProdutoViewHolder> {

        // Interface para comunicar mudanças ao Activity
        public interface OnTotalAlteradoListener {
            void onTotalAlterado(double novoTotal);
        }

        private List<ProdutoJson> listaProdutos;
        private OnTotalAlteradoListener listener;

        public ProdutoAdapterJson(List<ProdutoJson> listaProdutos, OnTotalAlteradoListener listener) {
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
            ProdutoJson produto = listaProdutos.get(position);

            holder.textNome.setText(produto.getTitle());
            holder.textPreco.setText(String.format("R$ %.2f", produto.getPrice()));
            holder.textQuantidade.setText(String.valueOf(produto.getQuantidade()));

            // Desabilita o botão "-" se a quantidade for zero
            holder.btnMenos.setEnabled(produto.getQuantidade() > 0);

            holder.btnMais.setOnClickListener(v -> {
                produto.setQuantidade(produto.getQuantidade() + 1);
                notifyItemChanged(holder.getBindingAdapterPosition());
                notificarTotalAlterado();
            });

            holder.btnMenos.setOnClickListener(v -> {
                if (produto.getQuantidade() > 0) {
                    produto.setQuantidade(produto.getQuantidade() - 1);
                    notifyItemChanged(holder.getBindingAdapterPosition());
                    notificarTotalAlterado();
                }
            });
        }

        @Override
        public int getItemCount() {
            return listaProdutos.size();
        }

        // Calcula e envia o total atualizado para o Activity
        private void notificarTotalAlterado() {
            double total = 0;
            for (ProdutoJson p : listaProdutos) {
                total += p.getQuantidade() * p.getPrice();
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



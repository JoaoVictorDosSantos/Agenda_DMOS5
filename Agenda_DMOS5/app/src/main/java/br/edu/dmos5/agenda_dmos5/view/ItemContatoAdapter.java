package br.edu.dmos5.agenda_dmos5.view;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.model.Contato;

public class ItemContatoAdapter extends RecyclerView.Adapter<ItemContatoAdapter.ContatoViewHolder> {

    private List<Contato> contatoList;
    private static RecyclerItemClickListener clickListener;

    public ItemContatoAdapter(@NonNull List<Contato> contatos){
        this.contatoList = contatos;
    }

    @NonNull
    @Override
    public ItemContatoAdapter.ContatoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recyclerview, parent, false);
        ContatoViewHolder contatoViewHolder = new ContatoViewHolder(view);
        return contatoViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemContatoAdapter.ContatoViewHolder holder, int position) {
        holder.textViewContatoNome.setText(contatoList.get(position).getNome());
    }

    @Override
    public int getItemCount() {
        return contatoList.size();
    }

    public void setClickListener(RecyclerItemClickListener clickListener) {
        ItemContatoAdapter.clickListener = clickListener;
    }

    public class ContatoViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView textViewContatoNome;

        public ContatoViewHolder(@NonNull View itemView){
            super(itemView);
            textViewContatoNome = itemView.findViewById(R.id.textview_contato_nome);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null)
                clickListener.onItemClick(getAdapterPosition());
        }
    }
}

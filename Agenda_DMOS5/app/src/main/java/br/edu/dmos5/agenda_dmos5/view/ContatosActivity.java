package br.edu.dmos5.agenda_dmos5.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Arrays;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.model.Contato;

public class ContatosActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUESTCODE_NOVO_CONTATO = 99;
    public static final int DETALHE_ITEM_CONTATO = 98;

    private ListView listViewContatos;
    private FloatingActionButton fabAdicionarContato;


    private ContatoDao contatoDao;
    private List<Contato> contatos;

    private ArrayAdapter<Contato> arrayAdapterContato;

    public static final String KEY_NOME = "nome";
    public static final String KEY_TELEFONE = "telefone";
    public static final String KEY_CELULAR = "celular";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        listViewContatos = findViewById(R.id.list_contatos);
        fabAdicionarContato = findViewById(R.id.fab_adicionar_contato);
        fabAdicionarContato.setOnClickListener(this);

        contatoDao = new ContatoDao(this);
        contatos = contatoDao.recuperateAll();

        arrayAdapterContato = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contatos);
        listViewContatos.setAdapter(arrayAdapterContato);

        listViewContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Bundle args = new Bundle();
                args.putString(KEY_NOME, contatos.get(i).getNome());
                args.putString(KEY_TELEFONE, contatos.get(i).getTelefone());
                args.putString(KEY_CELULAR, contatos.get(i).getCelular());
                Intent intent = new Intent(getApplicationContext(), DetalheContatoActivity.class);
                intent.putExtras(args);
                startActivityForResult(intent, DETALHE_ITEM_CONTATO);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.fab_adicionar_contato:
                Intent in = new Intent(this, NovoContatoActivity.class);
                startActivityForResult(in, REQUESTCODE_NOVO_CONTATO);
                break;
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case REQUESTCODE_NOVO_CONTATO:
                if (resultCode == RESULT_OK) {
                    atualizaListView(contatoDao.recuperateAll());
                }
                break;
        }
    }

    private void atualizaListView(List<Contato> contatos){
        arrayAdapterContato.clear();
        arrayAdapterContato.addAll(contatos);
        arrayAdapterContato.notifyDataSetChanged();
    }
}

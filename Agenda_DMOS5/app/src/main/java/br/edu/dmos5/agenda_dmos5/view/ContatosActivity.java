package br.edu.dmos5.agenda_dmos5.view;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.util.UsuarioUtil;

public class ContatosActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int REQUESTCODE_NOVO_CONTATO = 99;
    public static final int DETALHE_ITEM_CONTATO = 98;

    private RecyclerView recyclerViewContatos;
    private FloatingActionButton fabAdicionarContato;
    private ItemContatoAdapter itemContatoAdapter;

    private ContatoDao contatoDao;
    private List<Contato> contatos;

    public static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contatos);

        recyclerViewContatos = findViewById(R.id.recylerview_contatos);
        fabAdicionarContato = findViewById(R.id.fab_adicionar_contato);
        fabAdicionarContato.setOnClickListener(this);

        contatoDao = new ContatoDao(this);
        contatos = contatoDao.recuperatePorIdUsuario(UsuarioUtil.getInstance().getIdUsuarioLogado());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        itemContatoAdapter = new ItemContatoAdapter(contatos);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerViewContatos.setLayoutManager(layoutManager);
        recyclerViewContatos.setAdapter(itemContatoAdapter);

        itemContatoAdapter.setClickListener(new RecyclerItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Bundle args = new Bundle();
                args.putLong(KEY_ID, contatos.get(position).getId());
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
                    atualizaListView();
                }
                break;
        }
    }

    private void atualizaListView(){
        contatos.clear();
        contatos.addAll(contatoDao.recuperatePorIdUsuario(UsuarioUtil.getInstance().getIdUsuarioLogado()));
        recyclerViewContatos.getAdapter().notifyDataSetChanged();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                setResult(Activity.RESULT_OK);
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

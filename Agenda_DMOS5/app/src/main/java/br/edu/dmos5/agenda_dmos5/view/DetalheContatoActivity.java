package br.edu.dmos5.agenda_dmos5.view;


import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;


import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.dao.EmailDao;
import br.edu.dmos5.agenda_dmos5.dao.TelefoneDao;
import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.model.Telefone;


public class DetalheContatoActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView textViewNome;
    private TextView textViewTelefone;
    private TextView textViewCelular;
    private Button btnRemoverContato;
    private FloatingActionButton btnEditarContato;

    private Contato contato;
    private ContatoDao contatoDao;

    public static final String KEY_ID = "id";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_contato);

        textViewNome = findViewById(R.id.textview_detalhe_nome);
        textViewTelefone = findViewById(R.id.textview_detalhe_telefone);
        textViewCelular = findViewById(R.id.textview_detalhe_celular);
        btnRemoverContato = findViewById(R.id.button_remover_contato);
        btnEditarContato = findViewById(R.id.button_editar_contato);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnRemoverContato.setOnClickListener(this);
        btnEditarContato.setOnClickListener(this);

        contatoDao = new ContatoDao(getApplicationContext());
        extrairDados();
        exibeDados();
    }

    @Override
    public void onClick(View view) {
        if(view == btnRemoverContato){
            removerContato();
        }

        if(view == btnEditarContato){
            Bundle args = new Bundle();
            args.putLong(KEY_ID, contato.getId());
            Intent intent = new Intent(getApplicationContext(), NovoContatoActivity.class);
            intent.putExtras(args);
            startActivity(intent);
        }
    }

    private void removerContato(){
        try {
            contatoDao.remover(contato.getId());
            finish();
        }catch (Exception e){
            showSnackbar(e.getMessage());
        }
    }

    private void showSnackbar(String mensagem){
        Snackbar snackbar;
        ConstraintLayout constraintLayout = findViewById(R.id.layout_detalhe_contato);
        snackbar = Snackbar.make(constraintLayout, mensagem, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
        }
        return super.onOptionsItemSelected(item);
    }

    private void extrairDados(){
        Intent intent = getIntent();
        Bundle embrulho = intent.getExtras();

        if(embrulho != null){
            Long id = embrulho.getLong(ContatosActivity.KEY_ID);
            contato = contatoDao.buscaPorId(id);
            if(contato != null){
                EmailDao emailDao = new EmailDao(getApplicationContext());
                contato.setEmails(emailDao.buscaPorIdContato(id));

                TelefoneDao telefoneDao = new TelefoneDao(getApplicationContext());
                contato.setCelulares(telefoneDao.buscaCelularPorIdContato(id));
                contato.setCelulares(telefoneDao.buscaFixoPorIdContato(id));
            }
        }
    }

    private void exibeDados(){
        textViewNome.setText(contato.getNome());
        textViewTelefone.setText(preparaTelefone(contato.getTelefones()));
        textViewCelular.setText(preparaTelefone(contato.getCelulares()));
    }

    private String preparaTelefone(List<Telefone> list){
        String retorno = "";
        for (Telefone t: list) {
            retorno += t.getNumero() + ";";
        }
        return retorno;
    }

    @Override
    public void finish() {
        super.finish();
    }
}

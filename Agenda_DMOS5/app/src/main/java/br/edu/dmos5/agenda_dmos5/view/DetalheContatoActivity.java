package br.edu.dmos5.agenda_dmos5.view;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.List;
import java.util.stream.Collectors;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.dao.EmailDao;
import br.edu.dmos5.agenda_dmos5.dao.TelefoneDao;
import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.model.Telefone;

public class DetalheContatoActivity extends AppCompatActivity {

    private TextView textViewNome;
    private TextView textViewTelefone;
    private TextView textViewCelular;

    private Contato contato;
    private ContatoDao contatoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_contato);

        textViewNome = findViewById(R.id.textview_detalhe_nome);
        textViewTelefone = findViewById(R.id.textview_detalhe_telefone);
        textViewCelular = findViewById(R.id.textview_detalhe_celular);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        contatoDao = new ContatoDao(getApplicationContext());
        extrairDados();
        exibeDados();
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

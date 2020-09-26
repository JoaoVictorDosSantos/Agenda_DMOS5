package br.edu.dmos5.agenda_dmos5.view;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.model.Contato;

public class DetalheContatoActivity extends AppCompatActivity {

    private TextView textViewNome;
    private TextView textViewTelefone;
    private TextView textViewCelular;

    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_contato);

        textViewNome = findViewById(R.id.textview_detalhe_nome);
        textViewTelefone = findViewById(R.id.textview_detalhe_telefone);
        textViewCelular = findViewById(R.id.textview_detalhe_celular);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
            String nome = embrulho.getString(ContatosActivity.KEY_NOME);
            String telefone = embrulho.getString(ContatosActivity.KEY_TELEFONE);
            String celular  = embrulho.getString(ContatosActivity.KEY_CELULAR);
            contato = new Contato(nome,telefone,celular);
        }
    }

    private void exibeDados(){
        textViewNome.setText(contato.getNome());
        textViewTelefone.setText(contato.getTelefone());
        textViewCelular.setText(contato.getCelular());
    }

    @Override
    public void finish() {
        super.finish();
    }
}

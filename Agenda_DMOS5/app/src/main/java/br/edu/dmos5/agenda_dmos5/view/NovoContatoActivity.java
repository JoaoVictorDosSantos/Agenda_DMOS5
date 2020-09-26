package br.edu.dmos5.agenda_dmos5.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.database.SQLException;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.snackbar.Snackbar;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.model.Contato;

public class NovoContatoActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextNome;
    private EditText editTextTelefone;
    private EditText editTextCelular;
    private Button btnSalvar;

    private ContatoDao contatoDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_contato);

        contatoDao = new ContatoDao(getApplicationContext());

        editTextNome = findViewById(R.id.edittext_novo_contato_nome);
        editTextTelefone = findViewById(R.id.edittext_novo_contato_telefone);
        editTextCelular = findViewById(R.id.edittext_novo_contato_celular);
        btnSalvar = findViewById(R.id.btn_novo_contato_salvar);

        btnSalvar.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_novo_contato_salvar:
                salvarContato();
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finalizar(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void salvarContato() {
        String nome, telefone, celular;
        nome = editTextNome.getText().toString();
        telefone = editTextTelefone.getText().toString();
        celular = editTextCelular.getText().toString();

        if(nome.isEmpty() || telefone.isEmpty() || celular.isEmpty()){
            showSnackbar(getString(R.string.erro_campo_obrigatorio));
        }else{
            try {
                contatoDao.add(new Contato(nome, telefone, celular));
                finalizar(true);
            }catch (SQLException e){
                showSnackbar(e.getMessage());
            }catch (NullPointerException e){
                showSnackbar(getString(R.string.erro_contato));
            }catch (Exception e){
                showSnackbar("Erro ao cadastrar o contato!");
            }
        }

    }

    private void finalizar(boolean sucesso){
        if(sucesso){
            setResult(Activity.RESULT_OK);
        }else{
            setResult(Activity.RESULT_CANCELED);
        }
        finish();
    }

    private void showSnackbar(String mensagem){
        Snackbar snackbar;
        ConstraintLayout constraintLayout = findViewById(R.id.layout_novo_contato);
        snackbar = Snackbar.make(constraintLayout, mensagem, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }
}

package br.edu.dmos5.agenda_dmos5.view;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.Activity;
import android.content.Intent;
import android.database.SQLException;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.model.Email;
import br.edu.dmos5.agenda_dmos5.model.Telefone;
import br.edu.dmos5.agenda_dmos5.util.EmailUtil;
import br.edu.dmos5.agenda_dmos5.util.MesagemUtil;
import br.edu.dmos5.agenda_dmos5.util.TelefoneUtil;

public class NovoContatoActivity extends AppCompatActivity implements View.OnClickListener{

    private EditText editTextNome;
    private EditText editTextTelefone;
    private EditText editTextCelular;
    private EditText editTextEmail;
    private Button btnSalvar;

    private ContatoDao contatoDao;
    private Contato contato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_contato);

        contatoDao = new ContatoDao(getApplicationContext());
        contato = null;
        editTextNome = findViewById(R.id.edittext_novo_contato_nome);
        editTextTelefone = findViewById(R.id.edittext_novo_contato_telefone);
        editTextCelular = findViewById(R.id.edittext_novo_contato_celular);
        editTextEmail = findViewById(R.id.edittext_novo_contato_email);
        btnSalvar = findViewById(R.id.btn_novo_contato_salvar);

        btnSalvar.setOnClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        extrairDados();
    }

    @Override
    public void onClick(View view) {
        if(view == btnSalvar){
            if(contato == null){
                salvarContato();
            }else{
                atualizaContato();
            }
        }
    }

    private void atualizaContato() {
        String nome;
        nome = editTextNome.getText().toString();
        if(nome.isEmpty()){
            showSnackbar("Informa o campo obrigatorio");
        }else{
            try {
                this.contato.setNome(nome);
                contatoDao.atualizar(contato);
                abrirContatos();
            }catch (Exception e){
                showSnackbar(e.getMessage());
            }

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
        String nome, telefone, celular, email;
        nome = editTextNome.getText().toString();
        telefone = editTextTelefone.getText().toString();
        celular = editTextCelular.getText().toString();
        email = editTextEmail.getText().toString();
        if(nome.isEmpty() || telefone.isEmpty() || celular.isEmpty() || email.isEmpty()){
            showSnackbar(getString(R.string.erro_campo_obrigatorio));
        }else{
            try {
                contatoDao.add(new Contato(nome, montarTelefone(telefone, false),
                        montarTelefone(celular, true), montaEmail(email)));
                finalizar(true);
            }catch (SQLException e){
                showSnackbar(e.getMessage());
            }catch (NullPointerException e){
                showSnackbar(getString(R.string.erro_contato));
            }catch (RuntimeException e){
                showSnackbar(e.getMessage());
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

    private List<Telefone> montarTelefone(String numeros, boolean isCelular){
      String[] arrayNumeros = numeros.split(";");
      List<Telefone> list = new ArrayList<>();
      if(arrayNumeros.length == 0){
        if(isCelular && !TelefoneUtil.isValidCelular(numeros)){
            throw new RuntimeException(MesagemUtil.CELULAR_FORA_PADRAO);
        }else if(!isCelular && !TelefoneUtil.isValidFixo(numeros)){
            throw new RuntimeException(MesagemUtil.FIXO_FORA_PADRAO);
        }
        list.add(new Telefone(numeros, isCelular));
      }else{
        for (int i = 0; i < arrayNumeros.length; i++){
            if(isCelular && !TelefoneUtil.isValidCelular(arrayNumeros[i])){
                throw new RuntimeException(MesagemUtil.CELULAR_FORA_PADRAO);
            }else if(!isCelular && !TelefoneUtil.isValidFixo(arrayNumeros[i])){
                throw new RuntimeException(MesagemUtil.FIXO_FORA_PADRAO);
            }
            list.add(new Telefone(arrayNumeros[i], isCelular));
        }
      }
      return list;
    }

    private List<Email> montaEmail(String listEmail){
        List<Email> list = new ArrayList<>();
        String[] emails = listEmail.split(";");
        try {
            if(emails.length == 0){
                list.add(validaEmail(listEmail));
            }else{
                for (int i = 0; i < emails.length; i++){
                    list.add(validaEmail(emails[i]));
                }
            }
            return list;
        }catch (RuntimeException e){
            throw new RuntimeException(e.getMessage());
        }
    }

    private Email validaEmail(String email){
        Email emailObjt = null;
        if(EmailUtil.isValidEmail(email)){
            emailObjt = new Email(email);
        }else{
            throw new RuntimeException(MesagemUtil.EMAIL_FORA_PADRAO);
        }
        return emailObjt;
    }

    private void extrairDados(){
        Intent intent = getIntent();
        Bundle embrulho = intent.getExtras();

        if(embrulho != null){
            Long id = embrulho.getLong(DetalheContatoActivity.KEY_ID);
            if(id != null){
                this.contato = contatoDao.buscaPorId(id);
                editTextNome.setText(contato.getNome());
            }
        }
    }

    private void abrirContatos() {
        Intent in = new Intent(this, ContatosActivity.class);
        startActivity(in);
    }
}

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

import com.google.android.material.snackbar.Snackbar;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.dao.UsuarioDao;
import br.edu.dmos5.agenda_dmos5.model.Usuario;
import br.edu.dmos5.agenda_dmos5.util.MesagemUtil;

public class NovoUsuarioActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextSenha;
    private EditText editTextConfirmaSenha;
    private Button btnSalvar;

    private Usuario usuario;
    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_novo_usuario);

        editTextEmail = findViewById(R.id.edittext_novo_email);
        editTextSenha = findViewById(R.id.edittext_nova_senha);
        editTextConfirmaSenha = findViewById(R.id.edittext_confirme_senha);
        btnSalvar = findViewById(R.id.button_salvar);

        usuarioDao = new UsuarioDao(getApplicationContext());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btnSalvar.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == btnSalvar){
            String email, senha, confirma;
            email = editTextEmail.getText().toString();
            senha = editTextSenha.getText().toString();
            confirma = editTextConfirmaSenha.getText().toString();

            if(email.isEmpty() || senha.isEmpty() || confirma.isEmpty()){
                showSnackbar(getString(R.string.erro_usuario));
                return;
            }

            if(!senha.equals(confirma)){
                showSnackbar(getString(R.string.erro_confirma_senha));
                editTextSenha.setText("");
                editTextConfirmaSenha.setText("");
                return;
            }

            usuario = new Usuario(email, senha);
            adicionarUsuario();
        }
    }

    private void showSnackbar(String mensagem){
        ConstraintLayout constraintLayout = findViewById(R.id.layout_novo_usuario);
        MesagemUtil.exibirSnackbar(mensagem, constraintLayout);
    }

    private void adicionarUsuario(){
        try {
            usuarioDao.add(usuario);
            if(usuarioDao.isPrimeiroUsuario()){
                ContatoDao contatoDao = new ContatoDao(getApplicationContext());
                contatoDao.atualizaContatosExistente();
            }
            showSnackbar(MesagemUtil.CADASTRO_USUARIO_SUCESSO);
            finalizar(true);
        }catch (SQLException e){
            showSnackbar(e.getMessage());
        }catch (Exception e){
            showSnackbar("Erro ao cadastrar o usuario!");
        }
    }

    private void abrirLogin() {
        Intent in = new Intent(this, LoginActivity.class);
        startActivity(in);
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

    private void finalizar(boolean sucesso){
        if(sucesso){
            setResult(Activity.RESULT_OK);
        }else{
            setResult(Activity.RESULT_CANCELED);
        }
        finish();
    }
}

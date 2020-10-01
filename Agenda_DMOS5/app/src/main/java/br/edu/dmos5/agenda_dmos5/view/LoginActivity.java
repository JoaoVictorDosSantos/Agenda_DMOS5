package br.edu.dmos5.agenda_dmos5.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

import br.edu.dmos5.agenda_dmos5.R;
import br.edu.dmos5.agenda_dmos5.dao.ContatoDao;
import br.edu.dmos5.agenda_dmos5.dao.UsuarioDao;
import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.model.Usuario;
import br.edu.dmos5.agenda_dmos5.util.MesagemUtil;
import br.edu.dmos5.agenda_dmos5.util.UsuarioUtil;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText editTextEmail;
    private EditText editTextSenha;
    private Button btnLogar;
    private CheckBox checkBoxLembrar;
    private Button btnNovoUsuario;
    private String email;
    private String senha;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private UsuarioDao usuarioDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextEmail = findViewById(R.id.edittext_usuario_email);
        editTextSenha = findViewById(R.id.edittext_usuario_senha);
        btnLogar = findViewById(R.id.button_logar);
        checkBoxLembrar = findViewById(R.id.checkbox_lembrar);
        btnNovoUsuario = findViewById(R.id.button_novo_usuario);

        btnLogar.setOnClickListener(this);
        btnNovoUsuario.setOnClickListener(this);

        usuarioDao = new UsuarioDao(getApplicationContext());

        sharedPreferences = this.getPreferences(MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    @Override
    protected void onResume() {
        verificarPreferencias();
        super.onResume();
    }

    @Override
    public void onClick(View view) {
        if(view == btnLogar){
            email = editTextEmail.getText().toString();
            senha = editTextSenha.getText().toString();
            if(email.isEmpty() || senha.isEmpty()){
                showSnackbar(getString(R.string.erro_entrada_msg));
                return;
            }

            Usuario usuario = usuarioDao.login(email, senha);
            if(usuario == null){
                showSnackbar(getString(R.string.erro_login_msg));
                return;
            }
            UsuarioUtil.finalizarInstancia();
            setUsuarioUtil(usuario);
            salvaPreferencias();
            abrirContatos();
            return;
        }

        if(view == btnNovoUsuario){
            Intent in = new Intent(this, NovoUsuarioActivity.class);
            startActivity(in);
            return;
        }
    }

    private void setUsuarioUtil(Usuario usuario){
        UsuarioUtil.getInstance().setIdUsuarioLogado(usuario.getId());
        UsuarioUtil.getInstance().setEmailUsuarioLogado(usuario.getEmail());
    }

    private void showSnackbar(String mensagem){
        ConstraintLayout constraintLayout = findViewById(R.id.layout_login);
        MesagemUtil.exibirSnackbar(mensagem, constraintLayout);
    }

    private void abrirContatos() {
        Intent in = new Intent(this, ContatosActivity.class);
        startActivity(in);
    }

    private void salvaPreferencias(){
        if(checkBoxLembrar.isChecked()){
            editor.putString(getString(R.string.key_email), email);
            editor.commit();
            editor.putString(getString(R.string.key_senha), senha);
            editor.commit();
            editor.putBoolean(getString(R.string.key_lembrar), true);
            editor.commit();
        }else{
            editor.putString(getString(R.string.key_email), "");
            editor.commit();
            editor.putString(getString(R.string.key_senha), "");
            editor.commit();
            editor.putBoolean(getString(R.string.key_lembrar), false);
            editor.commit();
        }
    }


    private void verificarPreferencias() {
        email = sharedPreferences.getString(getString(R.string.key_email), "");
        senha = sharedPreferences.getString(getString(R.string.key_senha), "");
        boolean lembrar = sharedPreferences.getBoolean(getString(R.string.key_lembrar), false);
        if(lembrar){
            editTextEmail.setText(senha);
            editTextSenha.setText(senha);
            checkBoxLembrar.setChecked(true);
        }
    }
}

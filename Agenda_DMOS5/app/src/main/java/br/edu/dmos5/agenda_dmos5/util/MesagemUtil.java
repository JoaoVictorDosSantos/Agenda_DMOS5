package br.edu.dmos5.agenda_dmos5.util;

import android.content.Context;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.google.android.material.snackbar.Snackbar;

public class MesagemUtil {

    public static final String CADASTRO_USUARIO_SUCESSO = "Usuário cadastrado com sucesso!";
    public static void exibirSnackbar(String mensagem, ConstraintLayout constraintLayout) {
        Snackbar snackbar = Snackbar.make(constraintLayout, mensagem, Snackbar.LENGTH_SHORT);
        snackbar.show();
    }

    public static void exibirToast(String mensagem, Context context){
        Toast.makeText(context, mensagem, Toast.LENGTH_SHORT).show();
    }
}

package br.edu.dmos5.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.model.Usuario;
import br.edu.dmos5.agenda_dmos5.script_inicializacao.UsuarioScriptSQL;

public class UsuarioDao {

    private SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper  sqlLiteHelper;

    public UsuarioDao(Context contex){
        sqlLiteHelper = new SQLiteHelper(contex);
    }

    public void add(Usuario usuario){
        if (usuario == null) throw new NullPointerException();

        ContentValues valores = new ContentValues();
        valores.put(UsuarioScriptSQL.COLUMN_EMAIL, usuario.getEmail());
        valores.put(UsuarioScriptSQL.COLUMN_SENHA, usuario.getSenha());

        sqLiteDatabase = sqlLiteHelper.getWritableDatabase();
        if(sqLiteDatabase.insert(UsuarioScriptSQL.TABLE_USUARIO, null, valores) == -1){
            throw new SQLException("Erro ao adicionar usuario");
        }
        sqLiteDatabase.close();
    }

    public Usuario login(String email, String senha) {
        Usuario usuario = null;
        sqLiteDatabase = sqlLiteHelper.getReadableDatabase();

        String columns[] = new String[]{
                UsuarioScriptSQL.COLUMN_ID,
                UsuarioScriptSQL.COLUMN_EMAIL
        };

        String where = UsuarioScriptSQL.COLUMN_EMAIL + " = ?";
               where += " AND " + UsuarioScriptSQL.COLUMN_SENHA + " = ?";
        String[] argumentos = {email, senha};

        Cursor cursor = sqLiteDatabase.query(
                UsuarioScriptSQL.TABLE_USUARIO,
                columns,
                where,
                argumentos,
                null,
                null,
                null
        );

        if (cursor.moveToFirst()) {
            usuario = new Usuario(
                    cursor.getLong(0),
                    cursor.getString(1)
            );
        }
        cursor.close();
        sqLiteDatabase.close();
        return usuario;
    }

    public boolean isPrimeiroUsuario() {
        SQLiteDatabase db = sqlLiteHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * from " + UsuarioScriptSQL.TABLE_USUARIO ,
                null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count == 1;
    }
}

package br.edu.dmos5.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.model.Email;
import br.edu.dmos5.agenda_dmos5.model.Telefone;
import br.edu.dmos5.agenda_dmos5.model.Usuario;
import br.edu.dmos5.agenda_dmos5.script_inicializacao.EmailScriptSQL;
import br.edu.dmos5.agenda_dmos5.script_inicializacao.TelefoneScriptSQL;
import br.edu.dmos5.agenda_dmos5.script_inicializacao.UsuarioScriptSQL;

public class EmailDao {

    private SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper  sqlLiteHelper;

    public EmailDao(Context context) {
        sqlLiteHelper = new SQLiteHelper(context);
    }

    public void add(Email email) {
        if (email == null) throw new NullPointerException();

        ContentValues valores = new ContentValues();
        valores.put(EmailScriptSQL.COLUMN_ID_CONTATO, email.getContato().getId());
        valores.put(EmailScriptSQL.COLUMN_EMAIL, email.getEmail());

        sqLiteDatabase = sqlLiteHelper.getWritableDatabase();
        if(sqLiteDatabase.insert(EmailScriptSQL.TABLE_EMAIL, null, valores) == -1){
            throw new SQLException("Erro ao adicionar email");
        }
        sqLiteDatabase.close();
    }

    //quem charmar esse metodo é responsavel, por abrir e fechar uma conexão;
    public void addList(List<Email> emails, SQLiteDatabase db, Long idContato) {
        if (emails == null) throw new NullPointerException();
        for (Email email: emails) {
            ContentValues valores = new ContentValues();
            valores.put(EmailScriptSQL.COLUMN_ID_CONTATO, idContato);
            valores.put(EmailScriptSQL.COLUMN_EMAIL, email.getEmail());

            if(db.insert(TelefoneScriptSQL.TABLE_TELEFONE, null, valores) == -1){
                throw new SQLException("Erro ao adicionar email");
            }
        }
    }

    public List<Email> buscaPorIdContato(Long idContato) {
        List<Email> emails = new ArrayList<>();
        sqLiteDatabase = sqlLiteHelper.getReadableDatabase();

        String columns[] = new String[]{
                EmailScriptSQL.COLUMN_ID,
                EmailScriptSQL.COLUMN_EMAIL
        };

        String where = EmailScriptSQL.COLUMN_ID_CONTATO + " = ?";
        String[] argumentos = {String.valueOf(idContato)};

        Cursor cursor = sqLiteDatabase.query(
                UsuarioScriptSQL.TABLE_USUARIO,
                columns,
                where,
                argumentos,
                null,
                null,
                null
        );

        if (cursor.moveToNext()) {
            Email email = new Email(
                    cursor.getLong(0),
                    cursor.getString(1)
            );
            emails.add(email);
        }
        cursor.close();
        sqLiteDatabase.close();
        return emails;
    }
}

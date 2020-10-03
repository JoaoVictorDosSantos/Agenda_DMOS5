package br.edu.dmos5.agenda_dmos5.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import br.edu.dmos5.agenda_dmos5.model.Contato;
import br.edu.dmos5.agenda_dmos5.model.Email;
import br.edu.dmos5.agenda_dmos5.model.Telefone;
import br.edu.dmos5.agenda_dmos5.script_inicializacao.ContatoScriptSQL;
import br.edu.dmos5.agenda_dmos5.script_inicializacao.EmailScriptSQL;
import br.edu.dmos5.agenda_dmos5.script_inicializacao.TelefoneScriptSQL;
import br.edu.dmos5.agenda_dmos5.script_inicializacao.UsuarioScriptSQL;
import br.edu.dmos5.agenda_dmos5.util.UsuarioUtil;

public class TelefoneDao {

    private SQLiteDatabase sqLiteDatabase;
    private SQLiteHelper  sqlLiteHelper;

    public TelefoneDao(Context context){
        sqlLiteHelper = new SQLiteHelper(context);
    }

    public void add(Telefone telefone) {
        if (telefone == null) throw new NullPointerException();

        ContentValues valores = new ContentValues();
        valores.put(TelefoneScriptSQL.COLUMN_ID_CONTATO, telefone.getContato().getId());
        valores.put(TelefoneScriptSQL.COLUMN_NUMERO, telefone.getNumero());
        valores.put(TelefoneScriptSQL.COLUMN_IS_CELULAR, telefone.isCelular());

        sqLiteDatabase = sqlLiteHelper.getWritableDatabase();
        if(sqLiteDatabase.insert(TelefoneScriptSQL.TABLE_TELEFONE, null, valores) == -1){
            throw new SQLException("Erro ao adicionar telefone");
        }
        sqLiteDatabase.close();
    }

    //quem charmar esse metodo é responsavel, por abrir e fechar uma conexão;
    public void addList(List<Telefone> telefones, SQLiteDatabase db, Long idContato) {
        if (telefones == null) throw new NullPointerException();
        for (Telefone telefone: telefones) {
            ContentValues valores = new ContentValues();
            valores.put(TelefoneScriptSQL.COLUMN_ID_CONTATO, idContato);
            valores.put(TelefoneScriptSQL.COLUMN_NUMERO, telefone.getNumero());
            valores.put(TelefoneScriptSQL.COLUMN_IS_CELULAR, telefone.isCelular());

            if(db.insert(TelefoneScriptSQL.TABLE_TELEFONE, null, valores) == -1){
                throw new SQLException("Erro ao adicionar telefone");
            }
        }
    }

    public List<Telefone> buscaCelularPorIdContato(Long idContato) {
        List<Telefone> celulares = new ArrayList<>();
        sqLiteDatabase = sqlLiteHelper.getReadableDatabase();

        String columns[] = new String[]{
                TelefoneScriptSQL.COLUMN_ID,
                TelefoneScriptSQL.COLUMN_NUMERO
        };

        String where = TelefoneScriptSQL.COLUMN_ID_CONTATO + " = ? " +
        " AND " + TelefoneScriptSQL.COLUMN_IS_CELULAR + " = ?;";

        String[] argumentos = {String.valueOf(idContato), "1"};

        Cursor cursor = sqLiteDatabase.query(
                TelefoneScriptSQL.TABLE_TELEFONE,
                columns,
                where,
                argumentos,
                null,
                null,
                null
        );

        if (cursor.moveToNext()) {
            Telefone telefone = new Telefone(
                    cursor.getLong(0),
                    cursor.getString(1)
            );
            celulares.add(telefone);
        }
        cursor.close();
        sqLiteDatabase.close();
        return celulares;
    }

    public List<Telefone> buscaFixoPorIdContato(Long idContato) {
        List<Telefone> fixos = new ArrayList<>();
        sqLiteDatabase = sqlLiteHelper.getReadableDatabase();

        String columns[] = new String[]{
                TelefoneScriptSQL.COLUMN_ID,
                TelefoneScriptSQL.COLUMN_NUMERO
        };

        String where = TelefoneScriptSQL.COLUMN_ID_CONTATO + " = ? " +
                " AND " + TelefoneScriptSQL.COLUMN_IS_CELULAR + " = ?;";

        String[] argumentos = {String.valueOf(idContato), "0"};

        Cursor cursor = sqLiteDatabase.query(
                TelefoneScriptSQL.TABLE_TELEFONE,
                columns,
                where,
                argumentos,
                null,
                null,
                null
        );

        if (cursor.moveToNext()) {
            Telefone telefone = new Telefone(
                    cursor.getLong(0),
                    cursor.getString(1)
            );
            fixos.add(telefone);
        }
        cursor.close();
        sqLiteDatabase.close();
        return fixos;
    }
}

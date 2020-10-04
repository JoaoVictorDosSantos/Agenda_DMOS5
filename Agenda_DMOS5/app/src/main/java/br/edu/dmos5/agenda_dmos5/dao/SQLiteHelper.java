package br.edu.dmos5.agenda_dmos5.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import br.edu.dmos5.agenda_dmos5.model.Telefone;
import br.edu.dmos5.agenda_dmos5.script_inicializacao.ContatoScriptSQL;
import br.edu.dmos5.agenda_dmos5.script_inicializacao.EmailScriptSQL;
import br.edu.dmos5.agenda_dmos5.script_inicializacao.TelefoneScriptSQL;
import br.edu.dmos5.agenda_dmos5.script_inicializacao.UsuarioScriptSQL;

public class SQLiteHelper extends SQLiteOpenHelper {

    //Constantes do Banco de Dados
    public static final int DATABASE_VERSION = 3;
    public static final String DATABASE_NAME = "agenda.db";

    //Contexto
    private Context context;

    public SQLiteHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(ContatoScriptSQL.CREATE_TABLE);
        sqLiteDatabase.execSQL(TelefoneScriptSQL.CREATE_TABLE);
        sqLiteDatabase.execSQL(EmailScriptSQL.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        switch (oldVersion){
            case 1 : db.execSQL(UsuarioScriptSQL.CREATE_TABLE);
                     db.execSQL(contatoVersaoUm());

            case 2 : db.execSQL(ContatoScriptSQL.RENOMEANDO_TABELA);
                     db.execSQL(ContatoScriptSQL.CREATE_TABLE);
                     db.execSQL(ContatoScriptSQL.ATUALIZANDO_CONTATOS);
                     db.execSQL(ContatoScriptSQL.ATUALIZANDO_TELEFONE_FIXO);
                     db.execSQL(ContatoScriptSQL.ATUALIZANDO_TELEFONE_CELULAR);
                     db.execSQL(ContatoScriptSQL.EXCLUIR_TABELA_RENOMEADA);
        }
    }

    private String contatoVersaoUm(){
        String sql = "ALTER TABLE " + ContatoScriptSQL.TABLE_CONTATO;
               sql += " ADD " + ContatoScriptSQL.COLUMN_ID_USUARIO + " INTEGER REFERENCES ";
               sql += UsuarioScriptSQL.TABLE_USUARIO + "(id);";
        return sql;
    }
}

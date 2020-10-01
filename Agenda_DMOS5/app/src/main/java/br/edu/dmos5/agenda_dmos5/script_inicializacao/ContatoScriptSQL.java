package br.edu.dmos5.agenda_dmos5.script_inicializacao;

public class ContatoScriptSQL {

    //Constantes da tabela Contato
    public static final String TABLE_CONTATO = "contato";
    public static final String COLUMN_NOME = "nome";
    public static final String COLUMN_TELEFONE = "telefone";
    public static final String COLUMN_CELULAR = "celular";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_ID_USUARIO = "id_usuario";

    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_CONTATO + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY,"
                    + COLUMN_NOME + " TEXT NOT NULL,"
                    + COLUMN_TELEFONE + " TEXT,"
                    + COLUMN_CELULAR + " TEXT NOT NULL, "
                    + COLUMN_ID_USUARIO + " INTEGER, "
                    + " FOREIGN KEY(" + COLUMN_ID_USUARIO + ") REFERENCES "
                    + UsuarioScriptSQL.TABLE_USUARIO + "(" + UsuarioScriptSQL.COLUMN_ID + "));";
}

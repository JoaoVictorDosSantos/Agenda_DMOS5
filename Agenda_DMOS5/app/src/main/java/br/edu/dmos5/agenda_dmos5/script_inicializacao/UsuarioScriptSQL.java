package br.edu.dmos5.agenda_dmos5.script_inicializacao;

public class UsuarioScriptSQL {

    //Constantes da tabela Contato
    public static final String TABLE_USUARIO = "usuario";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_SENHA = "senha";

    //Criando a tabela de usuário
    public static final String  CREATE_TABLE = "CREATE TABLE " + TABLE_USUARIO + " ("
        + COLUMN_ID + " INTEGER PRIMARY KEY, "
        + COLUMN_EMAIL + " TEXT NOT NULL UNIQUE, "
        + COLUMN_SENHA + " TEXT NOT NULL ); ";
}

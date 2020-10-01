package br.edu.dmos5.agenda_dmos5.util;

public class UsuarioUtil {

    private static UsuarioUtil instance;

    private Long idUsuarioLogado;
    private String emailUsuarioLogado;

    public static synchronized UsuarioUtil getInstance() {
        if (instance == null) {
            instance = new UsuarioUtil();
        }
        return instance;
    }

    public static void finalizarInstancia() {
        instance = null;
    }

    public Long getIdUsuarioLogado() {
        return idUsuarioLogado;
    }

    public void setIdUsuarioLogado(Long idUsuarioLogado) {
        this.idUsuarioLogado = idUsuarioLogado;
    }

    public String getEmailUsuarioLogado() {
        return emailUsuarioLogado;
    }

    public void setEmailUsuarioLogado(String emailUsuarioLogado) {
        this.emailUsuarioLogado = emailUsuarioLogado;
    }
}

package br.edu.dmos5.agenda_dmos5.model;

public class Email {

    private Long id;
    private String email;
    private Contato contato;

    public Email(Long id, String email) {
        this.id = id;
        this.email = email;
    }

    public Email(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }
}

package br.edu.dmos5.agenda_dmos5.model;

public class Telefone {

    private Long id;
    private String numero;
    private boolean isCelular;
    private Contato contato;

    public Telefone(Long id, String numero, boolean isCelular) {
        this.id = id;
        this.numero = numero;
        this.isCelular = isCelular;
    }

    public Telefone(Long id, String numero) {
        this.id = id;
        this.numero = numero;
    }

    public Telefone(String numero, boolean isCelular) {
        this.numero = numero;
        this.isCelular = isCelular;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Contato getContato() {
        return contato;
    }

    public void setContato(Contato contato) {
        this.contato = contato;
    }

    public boolean isCelular() {
        return isCelular;
    }

    public void setCelular(boolean celular) {
        isCelular = celular;
    }
}

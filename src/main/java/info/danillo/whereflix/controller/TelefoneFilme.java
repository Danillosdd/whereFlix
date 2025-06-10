package info.danillo.whereflix.controller;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

/**
 * Classe que representa a entidade TelefoneFilme no sistema.
 * Contém informações sobre o telefone de um filme, como número,
 * se é WhatsApp e a relação com o filme.
 */
@Entity
public class TelefoneFilme {

    /**
     * Identificador único do telefone, gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue
    private Long id;

    /**
     * Número do telefone do filme.
     */
    @Column(nullable = false)
    private String numero;

    /**
     * Indica se o telefone é WhatsApp.
     */
    @Column(nullable = false)
    private boolean whatsapp;

    /**
     * Relacionamento Many-to-One com a entidade Filme.
     * Um telefone pertence a um único filme.
     */
    @ManyToOne
    @JoinColumn(name = "filme_id", nullable = false)
    private Filme filme;

    // Getters e Setters

    /**
     * Obtém o identificador único do telefone.
     *
     * @return ID do telefone.
     */
    public Long getId() {
        return id;
    }

    /**
     * Define o identificador único do telefone.
     *
     * @param id ID do telefone.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtém o número do telefone.
     *
     * @return Número do telefone.
     */
    public String getNumero() {
        return numero;
    }

    /**
     * Define o número do telefone.
     *
     * @param numero Número do telefone.
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }

    /**
     * Verifica se o telefone é WhatsApp.
     *
     * @return {@code true} se for WhatsApp, caso contrário {@code false}.
     */
    public boolean isWhatsapp() {
        return whatsapp;
    }

    /**
     * Define se o telefone é WhatsApp.
     *
     * @param whatsapp {@code true} se for WhatsApp, caso contrário {@code false}.
     */
    public void setWhatsapp(boolean whatsapp) {
        this.whatsapp = whatsapp;
    }

    /**
     * Obtém o filme associado ao telefone.
     *
     * @return Filme associado.
     */
    public Filme getFilme() {
        return filme;
    }

    /**
     * Define o filme associado ao telefone.
     *
     * @param filme Filme associado.
     */
    public void setFilme(Filme filme) {
        this.filme = filme;
    }
}

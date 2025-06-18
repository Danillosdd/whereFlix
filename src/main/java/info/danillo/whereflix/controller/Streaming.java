package info.danillo.whereflix.controller;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Classe que representa a entidade Streaming no sistema.
 * Contém informações básicas sobre a streaming, como nome.
 */
@Entity
public class Streaming {

    /**
     * Identificador único da streaming, gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Nome da streaming.
     */
    @Basic
    private String nome;



    @Basic
    private String foto;

    /**
     * Construtor padrão necessário para o JPA.
     */
    public Streaming() {
    }


    // Getters e Setters

    /**
     * Obtém o identificador único da streaming.
     *
     * @return ID da streaming.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o identificador único da streaming.
     *
     * @param id ID da streaming.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtém o nome da streaming.
     *
     * @return Nome da streaming.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da streaming.
     *
     * @param nome Nome da streaming.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * Retorna uma representação textual da streaming.
     *
     * @return String representando a streaming.
     */
    @Override
    public String toString() {
        return "Streaming{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                '}';
    }
}

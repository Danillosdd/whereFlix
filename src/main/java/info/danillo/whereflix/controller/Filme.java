package info.danillo.whereflix.controller;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

/**
 * Classe que representa a entidade Filme no sistema.
 * Contém informações básicas do filme, como título, tipo, qualidade, categoria,
 * duração, avaliação e ano de lançamento.
 * além de relacionamentos com  streamings.
 */
@Entity
public class Filme {

    // Identificador único do filme, gerado automaticamente pelo banco de dados.
    @Id
    @GeneratedValue
    private Integer id;

    // Título completo do filme.
    private String titulo;

   // Relacionamento Many-to-Many com a entidade Streaming.
    // Um filme pode estar matriculado em várias streamings.
    @ManyToMany
    private List<Streaming> streamings;

    @ManyToOne
    private Tipo tipo;

    @ManyToOne
    private Qualidade qualidade;

    @ManyToOne
    private Categoria categoria;

    private Integer duracao; // duração em minutos
    private Double avaliacao; // ex: 6.5
    private Integer ano; // ex: 2024

    private String foto;

    private String sinopse;

    /**
     * Construtor padrão necessário para o JPA.
     */
    public Filme() {
    }

    /**
     * Construtor para inicializar um filme com o título fornecido.
     *
     * @param titulo Título completo do filme.
     */
    public Filme(String titulo) {
        this.titulo = titulo;
    }

    // Getters e Setters

    /**
     * Obtém o identificador único do filme.
     *
     * @return ID do filme.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o identificador único do filme.
     *
     * @param id ID do filme.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtém o titulo completo do filme.
     *
     * @return Título do filme.
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Define o título completo do filme.
     *
     * @param titulo Título do filme.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }


    /**
     * Obtém a lista de streamings em que o filme está matriculado.
     *
     * @return Lista de streamings.
     */
    public List<Streaming> getStreamings() {
        return streamings;
    }

    /**
     * Define a lista de streamings em que o filme está matriculado.
     *
     * @param streamings Lista de streamings.
     */
    public void setStreamings(List<Streaming> streamings) {
        this.streamings = streamings;
    }

    public Tipo getTipo() {
        return tipo;
    }

    public void setTipo(Tipo tipo) {
        this.tipo = tipo;
    }

    public Qualidade getQualidade() {
        return qualidade;
    }

    public void setQualidade(Qualidade qualidade) {
        this.qualidade = qualidade;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public void setAvaliacao(Double avaliacao) {
        this.avaliacao = avaliacao;
    }

    public Integer getAno() {
        return ano;
    }

    public void setAno(Integer ano) {
        this.ano = ano;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }
}

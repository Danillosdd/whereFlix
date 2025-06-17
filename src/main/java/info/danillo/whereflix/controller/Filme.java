package info.danillo.whereflix.controller;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;

/**
 * Classe que representa a entidade Filme no sistema.
 * Contém informações básicas do filme, como título, tipo, qualidade, categoria,
 * duração, avaliação e ano de lançamento.
 * além de relacionamentos com telefones e streamings.
 */
@Entity
public class Filme {

    // Identificador único do filme, gerado automaticamente pelo banco de dados.
    @Id
    @GeneratedValue
    private Integer id;

    // Título completo do filme.
    private String titulo;

    // Relacionamento One-to-Many com a entidade TelefoneFilmes.
    // Um filme pode ter vários telefones.
    @OneToMany(mappedBy = "filme", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TelefoneFilme> telefones;

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

    @NotNull(message = "A avaliação é obrigatória")
    private Double avaliacao; // ex: 6.5
    private Integer ano; // ex: 2024

    private String foto;

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
     * Obtém a lista de telefones associados ao filme.
     *
     * @return Lista de telefones.
     */
    public List<TelefoneFilme> getTelefones() {
        return telefones;
    }

    /**
     * Define a lista de telefones associados ao filme.
     *
     * @param telefones Lista de telefones.
     */
    public void setTelefones(List<TelefoneFilme> telefones) {
        this.telefones = telefones;
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
}

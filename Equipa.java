import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;


public class Equipa implements Serializable {
    private static int contador = 0;
    private int id;
    private String nome;
    private List<Jogador> jogadores;
    private int numeroVitorias;
    private int numeroDerrotas;

    

    public Equipa(String nome) {
        this.id = ++contador;  // ID gerado automaticamente
        this.nome = nome;
        this.jogadores = new ArrayList<>();
        this.numeroVitorias = 0;
        this.numeroDerrotas = 0;
    }

    public void adicionarJogador(Jogador jogador) {
        if (!jogadores.contains(jogador)) {
            getJogadores().add(jogador);
        }
    }
    public void setNumeroVitorias(int numeroVitorias) {
        this.numeroVitorias = numeroVitorias;
    }
    public void setNumeroDerrotas(int numeroDerrotas) {
        this.numeroDerrotas = numeroDerrotas;
    }
    
    public void removerJogador(Jogador jogador) {
        getJogadores().remove(jogador);
    }

    public void registrarVitoria() {
        numeroVitorias++;
    }

    public void registrarDerrota() {
        numeroDerrotas++;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * @param nome the nome to set
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * @return the jogadores
     */
    public List<Jogador> getJogadores() {
        return jogadores;
    }

    /**
     * @return the numeroVitorias
     */
    public int getNumeroVitorias() {
        return numeroVitorias;
    }

    /**
     * @return the numeroDerrotas
     */
    public int getNumeroDerrotas() {
        return numeroDerrotas;
    }

    @Override
    public String toString() {
        return "Equipa{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", numeroVitorias=" + getNumeroVitorias() +
                ", numeroDerrotas=" + getNumeroDerrotas() +
                ", jogadores=" + getJogadores() +
                '}';
    }

}

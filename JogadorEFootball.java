
public class JogadorEFootball extends Jogador {
    private String posicaoPrincipal; 
    private int golosMarcados; 
    private int assistencias; 
    private int golosDefendidos; 

    // Construtor
    public JogadorEFootball(String nome, String username, String password, String nickname, String posicaoPrincipal, int golosMarcados, int assistencias, int golosDefendidos) {
        super(nome, username, password, nickname); // Chama o construtor correto de Jogador
        this.posicaoPrincipal = posicaoPrincipal;
        this.golosMarcados = golosMarcados;
        this.assistencias = assistencias;
        this.golosDefendidos = golosDefendidos;
    } 

    public String getPosicaoPrincipal() {
        return posicaoPrincipal;
    }

    public void setPosicaoPrincipal(String posicaoPrincipal) {
        this.posicaoPrincipal = posicaoPrincipal;
    }

    public int getGolosMarcados() {
        return golosMarcados;
    }

    public void setGolosMarcados(int golosMarcados) {
        this.golosMarcados = golosMarcados;
    }

    public int getAssistencias() {
        return assistencias;
    }

    public void setAssistencias(int assistencias) {
        this.assistencias = assistencias;
    }

    public int getGolosDefendidos() {
        return golosDefendidos;
    }

    public void setGolsDefendidos(int golosDefendidos) {
        this.golosDefendidos = golosDefendidos;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Posição: %s, Golos Marcados: %d, Assistências: %d, Gols Defendidos: %d",
                                                 posicaoPrincipal, golosMarcados, assistencias, golosDefendidos);
    }
}
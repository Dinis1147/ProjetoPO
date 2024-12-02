
public class JogadorFPS extends Jogador {
    private double precisao;
    private int headshots;

    public JogadorFPS(String nome, String username, String password, String nickname, double precisao, int headshots) {
        super(nome, username, password, nickname); // Chama o construtor correto de Jogador
        this.precisao = precisao;
        this.headshots = headshots;
    }

    public String getTipoJogador() {
        return "FPS";
    }

    public double getPrecisao() {
        return precisao;
    }

    public void setPrecisao(double precisao) {
        this.precisao = precisao;
    }

    public int getHeadshots() {
        return headshots;
    }

    public void setHeadshots(int headshots) {
        this.headshots = headshots;
    }

    @Override
    public String toString() {
        return super.toString() + String.format(", Precisão: %.2f, Headshots: %d", precisao, headshots);
    }
}
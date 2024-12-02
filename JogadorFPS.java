public class JogadorFPS extends Jogador {
    private double precisao;
    private int headshots;

    public JogadorFPS(String nome, String username, String password, String nickname, double precisao, int headshots) {
        super(nome, username, password, nickname);
        this.precisao = precisao;
        this.headshots = headshots;
    }

    public double getPrecisao() {
        return precisao;
    }

    public void setPrecisao(double precisao) {
        if (precisao < 0 || precisao > 100) {
            throw new IllegalArgumentException("Precisão deve estar entre 0 e 100");
        }
        this.precisao = precisao;
    }

    public int getHeadshots() {
        return headshots;
    }

    public void incrementarHeadshots() {
        this.headshots++;
    }

    @Override
    protected String getTipoJogador() {
        return "FPS";
    }

    @Override
    protected void adicionarDadosEspecificos(StringBuilder sb) {
        sb.append(precisao).append(";")
          .append(headshots).append(";");
    }

    @Override
    public void verEstatisticas() {
        super.verEstatisticas();
        System.out.println("Precisão: " + precisao + "%");
        System.out.println("Headshots: " + headshots);
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s;%.2f;%d;%d;%d;%d",
            getTipoJogador(),
            getNome(),
            getUsername(),
            getPassword(),
            getNickname(),
            precisao,
            headshots,
            getPartidasJogadas(),
            getVitorias(),
            getDerrotas()
        );
    }
}
public class JogadorMOBA extends Jogador {
    private String personagemPrincipal;
    private String kda; // Kill/Death/Assist ratio

    public JogadorMOBA(String nome, String username, String password, String nickname, String personagemPrincipal, String kda) {
        super(nome, username, password, nickname);
        this.personagemPrincipal = personagemPrincipal;
        this.kda = kda;
    }

    public String getPersonagemPrincipal() {
        return personagemPrincipal;
    }

    public void setPersonagemPrincipal(String personagemPrincipal) {
        if (personagemPrincipal == null || personagemPrincipal.trim().isEmpty()) {
            throw new IllegalArgumentException("Personagem principal não pode ser vazio");
        }
        this.personagemPrincipal = personagemPrincipal;
    }

    public String getKda() {
        return kda;
    }

    public void setKda(String kda) {
        if (kda == null || !kda.matches("\\d+/\\d+/\\d+")) {
            throw new IllegalArgumentException("KDA deve estar no formato K/D/A (exemplo: 5/2/10)");
        }
        this.kda = kda;
    }

    public void atualizarKda(int kills, int deaths, int assists) {
        if (kills < 0 || deaths < 0 || assists < 0) {
            throw new IllegalArgumentException("Valores de KDA não podem ser negativos");
        }
        this.kda = kills + "/" + deaths + "/" + assists;
    }

    @Override
    protected String getTipoJogador() {
        return "MOBA";
    }

    @Override
    protected void adicionarDadosEspecificos(StringBuilder sb) {
        sb.append(personagemPrincipal).append(";")
          .append(kda).append(";");
    }

    @Override
    public void verEstatisticas() {
        super.verEstatisticas();
        System.out.println("Personagem Principal: " + personagemPrincipal);
        System.out.println("KDA (Kill/Death/Assist): " + kda);
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s;%s;%s;%d;%d;%d",
            getTipoJogador(),
            getNome(),
            getUsername(),
            getPassword(),
            getNickname(),
            personagemPrincipal,
            kda,
            getPartidasJogadas(),
            getVitorias(),
            getDerrotas()
        );
    }
}

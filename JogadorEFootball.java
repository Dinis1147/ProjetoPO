public class JogadorEFootball extends Jogador {
    private String posicaoPrincipal;
    private int golosMarcados;
    private int assistencias;
    private int golosDefendidos; // Apenas para guarda-redes

    public JogadorEFootball(String nome, String username, String password, String nickname, 
                          String posicaoPrincipal, int golosMarcados, int assistencias, int golosDefendidos) {
        super(nome, username, password, nickname);
        setPosicaoPrincipal(posicaoPrincipal);
        this.golosMarcados = golosMarcados;
        this.assistencias = assistencias;
        this.golosDefendidos = golosDefendidos;
    }

    public String getPosicaoPrincipal() {
        return posicaoPrincipal;
    }

    public void setPosicaoPrincipal(String posicaoPrincipal) {
        if (posicaoPrincipal == null || posicaoPrincipal.trim().isEmpty()) {
            throw new IllegalArgumentException("Posição principal não pode ser vazia");
        }
        String posicao = posicaoPrincipal.toUpperCase();
        if (!posicao.equals("GUARDA-REDES") && !posicao.equals("DEFESA") && 
            !posicao.equals("MEDIO") && !posicao.equals("AVANCADO")) {
            throw new IllegalArgumentException("Posição inválida. Deve ser: GUARDA-REDES, DEFESA, MEDIO ou AVANCADO");
        }
        this.posicaoPrincipal = posicao;
    }

    public int getGolosMarcados() {
        return golosMarcados;
    }

    public void incrementarGolosMarcados() {
        this.golosMarcados++;
    }

    public int getAssistencias() {
        return assistencias;
    }

    public void incrementarAssistencias() {
        this.assistencias++;
    }

    public int getGolosDefendidos() {
        return golosDefendidos;
    }

    public void incrementarGolosDefendidos() {
        if (!posicaoPrincipal.equals("GUARDA-REDES")) {
            throw new IllegalStateException("Apenas guarda-redes podem registrar gols defendidos");
        }
        this.golosDefendidos++;
    }

    @Override
    protected String getTipoJogador() {
        return "EFootball";
    }

    @Override
    protected void adicionarDadosEspecificos(StringBuilder sb) {
        sb.append(posicaoPrincipal).append(";")
          .append(golosMarcados).append(";")
          .append(assistencias).append(";")
          .append(golosDefendidos).append(";");
    }

    @Override
    public void verEstatisticas() {
        super.verEstatisticas();
        System.out.println("Posição: " + posicaoPrincipal);
        System.out.println("Gols Marcados: " + golosMarcados);
        System.out.println("Assistências: " + assistencias);
        if (posicaoPrincipal.equals("GUARDA-REDES")) {
            System.out.println("Gols Defendidos: " + golosDefendidos);
        }
    }

    @Override
    public String toString() {
        return String.format("%s;%s;%s;%s;%s;%s;%d;%d;%d;%d;%d;%d",
            getTipoJogador(),
            getNome(),
            getUsername(),
            getPassword(),
            getNickname(),
            posicaoPrincipal,
            golosMarcados,
            assistencias,
            golosDefendidos,
            getPartidasJogadas(),
            getVitorias(),
            getDerrotas()
        );
    }
}
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Jogador extends Entidade implements Serializable {
    private String nickname;
    private int partidasJogadas;
    private int vitorias;
    private int derrotas;
    private Equipa equipa;

    public Jogador(String nome, String username, String password, String nickname) {
        super(nome, username, password, nome); // usando nome como nomeCompleto por enquanto
        this.nickname = nickname;
        this.partidasJogadas = 0;
        this.vitorias = 0;
        this.derrotas = 0;
    }

    public static List<Jogador> carregarTodosJogadores() {
        List<Jogador> jogadores = new ArrayList<>();
        List<String> linhas = Ficheiro.lerArquivo("jogadores.txt");
        
        for (String linha : linhas) {
            String[] partes = linha.split(";");
            if (partes.length < 5) continue;

            String tipoJogador = partes[0];
            String nome = partes[1];
            String username = partes[2];
            String password = partes[3];
            String nickname = partes[4];

            Jogador jogador = null;
            try {
                switch (tipoJogador) {
                    case "MOBA":
                        if (partes.length >= 7) {
                            String personagemPrincipal = partes[5];
                            String kda = partes[6];
                            jogador = new JogadorMOBA(nome, username, password, nickname, personagemPrincipal, kda);
                        }
                        break;
                    case "EFootball":
                        if (partes.length >= 9) {
                            String posicaoPrincipal = partes[5];
                            int golosMarcados = Integer.parseInt(partes[6]);
                            int assistencias = Integer.parseInt(partes[7]);
                            int golosDefendidos = Integer.parseInt(partes[8]);
                            jogador = new JogadorEFootball(nome, username, password, nickname, posicaoPrincipal, golosMarcados, assistencias, golosDefendidos);
                        }
                        break;
                    case "FPS":
                        if (partes.length >= 7) {
                            double precisao = Double.parseDouble(partes[5]);
                            int headshots = Integer.parseInt(partes[6]);
                            jogador = new JogadorFPS(nome, username, password, nickname, precisao, headshots);
                        }
                        break;
                }
                
                if (jogador != null) {
                    if (partes.length >= 11) {
                        jogador.setPartidasJogadas(Integer.parseInt(partes[8]));
                        jogador.setVitorias(Integer.parseInt(partes[9]));
                        jogador.setDerrotas(Integer.parseInt(partes[10]));
                    }
                    jogadores.add(jogador);
                }
            } catch (NumberFormatException e) {
                System.err.println("Erro ao converter dados do jogador: " + nome);
            }
        }
        return jogadores;
    }

    public void salvar() {
        StringBuilder sb = new StringBuilder();
        sb.append(getTipoJogador()).append(";")
          .append(getNome()).append(";")
          .append(getUsername()).append(";")
          .append(getPassword()).append(";")
          .append(getNickname()).append(";");
        
        adicionarDadosEspecificos(sb);
        
        sb.append(getPartidasJogadas()).append(";")
          .append(getVitorias()).append(";")
          .append(getDerrotas());

        Ficheiro.adicionarLinha(sb.toString(), "jogadores.txt");
    }

    protected abstract String getTipoJogador();
    
    protected abstract void adicionarDadosEspecificos(StringBuilder sb);

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        if (nickname == null || nickname.trim().isEmpty()) {
            throw new IllegalArgumentException("Nickname não pode ser vazio");
        }
        this.nickname = nickname;
    }

    public int getPartidasJogadas() {
        return partidasJogadas;
    }

    public void setPartidasJogadas(int partidasJogadas) {
        if (partidasJogadas < 0) {
            throw new IllegalArgumentException("Número de partidas não pode ser negativo");
        }
        this.partidasJogadas = partidasJogadas;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void setVitorias(int vitorias) {
        if (vitorias < 0) {
            throw new IllegalArgumentException("Número de vitórias não pode ser negativo");
        }
        this.vitorias = vitorias;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void setDerrotas(int derrotas) {
        if (derrotas < 0) {
            throw new IllegalArgumentException("Número de derrotas não pode ser negativo");
        }
        this.derrotas = derrotas;
    }

    public Equipa getEquipa() {
        return equipa;
    }

    public void setEquipa(Equipa equipa) {
        this.equipa = equipa;
    }

    public void registrarPartida(boolean vitoria) {
        partidasJogadas++;
        if (vitoria) {
            vitorias++;
        } else {
            derrotas++;
        }
    }

    public void verEstatisticas() {
        System.out.println("Estatísticas do Jogador " + getNickname());
        System.out.println("Partidas Jogadas: " + partidasJogadas);
        System.out.println("Vitórias: " + vitorias);
        System.out.println("Derrotas: " + derrotas);
        if (partidasJogadas > 0) {
            double winRate = (double) vitorias / partidasJogadas * 100;
            System.out.printf("Taxa de Vitória: %.2f%%\n", winRate);
        }
    }
}

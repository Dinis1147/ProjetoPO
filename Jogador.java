import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Jogador extends Entidade implements Serializable {
    private String nickname;
    private int partidasJogadas;
    private int vitorias;
    private int derrotas;

    public Jogador(String nome, String username, String password, String nickname) {
        super(nome, username, password, nickname); 
        this.nickname = nickname;
        this.partidasJogadas = 0;
        this.vitorias = 0;
        this.derrotas = 0;
    }
    public static List<Jogador> carregarTodosJogadoresTxt(String caminho) {
        List<Jogador> jogadores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(caminho))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] partes = linha.split(","); // Ajuste o separador conforme o arquivo
    
                // O arquivo deve ter todas as informações necessárias para criar cada tipo de jogador
                String tipoJogador = partes[0]; // Assumindo que a primeira informação seja o tipo
                String nome = partes[1];
                String username = partes[2];
                String password = partes[3];
                String nickname = partes[4];
    
                Jogador jogador = null;
                switch (tipoJogador) {
                    case "MOBA":
                        String personagemPrincipal = partes[5];
                        String kda = partes[6];
                        jogador = new JogadorMOBA(nome, username, password, nickname, personagemPrincipal, kda);
                        break;
                    case "EFootball":
                        String posicaoPrincipal = partes[5];
                        int golosMarcados = Integer.parseInt(partes[6]);
                        int assistencias = Integer.parseInt(partes[7]);
                        jogador = new JogadorEFootball(nome, username, password, nickname, posicaoPrincipal, golosMarcados, assistencias, golosMarcados);
                        break;
                    case "FPS":
                        double precisao = Double.parseDouble(partes[5]);
                        int headshots = Integer.parseInt(partes[6]);
                        jogador = new JogadorFPS(nome, username, password, nickname, precisao, headshots);
                        break;
                    default:
                        System.out.println("Tipo de jogador desconhecido: " + tipoJogador);
                        continue;
                }
                jogadores.add(jogador);
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar os jogadores: " + e.getMessage());
        }
        return jogadores;
    }
    

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getPartidasJogadas() {
        return partidasJogadas;
    }

    public void incrementarPartidasJogadas() {
        this.partidasJogadas++;
    }

    public int getVitorias() {
        return vitorias;
    }

    public void incrementarVitorias() {
        this.vitorias++;
    }

    public int getDerrotas() {
        return derrotas;
    }

    public void incrementarDerrotas() {
        this.derrotas++;
    }

    public void editarDados(String novoNome, String novoNickname) {
        setNome(novoNome);
        this.nickname = novoNickname;
    }

    public void verTorneios() {
        // Lógica para exibir torneios nos quais o jogador está participando
        System.out.println("Torneios nos quais o jogador " + getNome() + " está participando...");
        // Aqui pode implementar a lógica para buscar e exibir os torneios do jogador
    }

    public void verEstatisticas() {
        System.out.println("Estatísticas do Jogador " + getNome() + ":");
        System.out.println("Partidas Jogadas: " + partidasJogadas);
        System.out.println("Vitórias: " + vitorias);
        System.out.println("Derrotas: " + derrotas);
    }

    public void salvarDadosTxt(String caminho) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho, true))) {
            writer.write(this.toString()); // Aqui se assume que o método toString() tem uma representação útil dos dados do jogador.
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados do jogador: " + e.getMessage());
        }
    }
    
    @Override
    public String toString() {
        return getId() + "," + getNome() + "," + getUsername() + "," + nickname + "," + partidasJogadas + "," + vitorias + "," + derrotas;
    }
}

import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Administrador extends Entidade {
    private String nome;
    private List<Jogador> jogadores;
    private List<Treinador> treinadores;
    private List<Torneio> torneios;
    private List<Equipa> equipas;

    public Administrador(String nomeCompleto, String username, String password) {
        super(nomeCompleto, username, password, password);
        this.jogadores = new ArrayList<>();
        this.treinadores = new ArrayList<>();
        this.torneios = new ArrayList<>();
        this.equipas = new ArrayList<>();
    }

    public void adicionarJogador(Jogador jogador) {
        jogadores.add(jogador);
    }

    public void removerJogador(String nomeJogador) {
        Jogador jogador = procurarJogador(nomeJogador);
        if (jogador != null) {
            jogadores.remove(jogador);
            System.out.println("Jogador removido com sucesso.");
        } else {
            System.out.println("Jogador não encontrado.");
        }
    }

    public void adicionarTreinador(Treinador treinador) {
        treinadores.add(treinador);
    }

    public void removerTreinador(String nomeTreinador) {
        Treinador treinador = procurarTreinador(nomeTreinador);
        if (treinador != null) {
            treinadores.remove(treinador);
            System.out.println("Treinador removido com sucesso.");
        } else {
            System.out.println("Treinador não encontrado.");
        }
    }

    public void criarEquipa(Equipa equipa) {
        equipas.add(equipa);
    }

    public void criarTorneio(Torneio torneio) {
        torneios.add(torneio);
    }

    public void agendarPartida(Torneio torneio, Equipa equipaA, Equipa equipaB, String data) {
        if (torneios.contains(torneio)) {
            torneio.agendarPartida(equipaA, equipaB, data);
            System.out.println("Partida agendada com sucesso entre " + equipaA.getNome() + " e " + equipaB.getNome() + " no torneio " + torneio.getNome());
        } else {
            System.out.println("Torneio não encontrado.");
        }
    }

    public void registrarResultado(Torneio torneio, Partida partida, int pontosEquipaA, int pontosEquipaB) {
        if (torneios.contains(torneio)) {
            torneio.registrarResultado(partida, pontosEquipaA, pontosEquipaB);
            System.out.println("Resultado registrado para a partida entre " + partida.getEquipaA().getNome() + " e " + partida.getEquipaB().getNome());
        } else {
            System.out.println("Torneio não encontrado.");
        }
    }

    public void acompanharEstatisticas(Torneio torneio) {
        if (torneios.contains(torneio)) {
            torneio.exibirEstatisticas();
        } else {
            System.out.println("Torneio não encontrado.");
        }
    }

    public Jogador procurarJogador(String nomeJogador) {
        for (Jogador jogador : jogadores) {
            if (jogador.getNome().equalsIgnoreCase(nomeJogador)) {
                return jogador;
            }
        }
        return null;
    }

    public Treinador procurarTreinador(String nomeTreinador) {
        for (Treinador treinador : treinadores) {
            if (treinador.getNome().equalsIgnoreCase(nomeTreinador)) {
                return treinador;
            }
        }
        return null;
    }

    public Torneio procurarTorneio(String nomeTorneio) {
        for (Torneio torneio : torneios) {
            if (torneio.getNome().equalsIgnoreCase(nomeTorneio)) {
                return torneio;
            }
        }
        return null;
    }

    public Equipa procurarEquipa(String nomeEquipa) {
        for (Equipa equipa : equipas) {
            if (equipa.getNome().equalsIgnoreCase(nomeEquipa)) {
                return equipa;
            }
        }
        return null;
    }

    public static List<Administrador> carregarTodosAdministradoresTxt(String filename) {
        List<Administrador> administradores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Administrador:")) {
                    linha = reader.readLine();
                    String[] partes = linha.split(", ");
                    String nome = partes[0].split("=")[1];
                    String username = partes[1].split("=")[1];
                    String password = partes[2].split("=")[1];
                    Administrador administrador = new Administrador(nome, username, password);
                    administradores.add(administrador);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return administradores;
    }

    public void salvarDadosTxt(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) {
            writer.write("Administrador:");
            writer.newLine();
            writer.write("nome=" + this.getNome() + ", username=" + this.getUsername() + ", password=" + this.getPassword());
            writer.newLine();

            writer.write("Jogadores:");
            writer.newLine();
            for (Jogador jogador : jogadores) {
                writer.write("nome=" + jogador.getNome() + ", username=" + jogador.getUsername() + ", nickname=" + jogador.getNickname());
                writer.newLine();
            }

            writer.write("Treinadores:");
            writer.newLine();
            for (Treinador treinador : treinadores) {
                writer.write("nome=" + treinador.getNome() + ", username=" + treinador.getUsername() + ", email=" + treinador.getEmail());
                writer.newLine();
            }

            writer.write("Torneios:");
            writer.newLine();
            for (Torneio torneio : torneios) {
                writer.write("nome=" + torneio.getNome() + ", jogo=" + torneio.getJogo());
                writer.newLine();
            }

            writer.write("Equipas:");
            writer.newLine();
            for (Equipa equipa : equipas) {
                writer.write("nome=" + equipa.getNome());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Getters e Setters
    public List<Jogador> getJogadores() {
        return jogadores;
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores;
    }

    public List<Treinador> getTreinadores() {
        return treinadores;
    }

    public void setTreinadores(List<Treinador> treinadores) {
        this.treinadores = treinadores;
    }

    public List<Torneio> getTorneios() {
        return torneios;
    }

    public void setTorneios(List<Torneio> torneios) {
        this.torneios = torneios;
    }

    public List<Equipa> getEquipas() {
        return equipas;
    }

    public void setEquipas(List<Equipa> equipas) {
        this.equipas = equipas;
    }
    public String getnome(){
        return nome;
    }
    
    @Override
    public String toString() {
        return "Administrador{" +
                "nome='" + getNome() + '\'' +
                ", username='" + getUsername() + '\'' +
                '}';
    }
}

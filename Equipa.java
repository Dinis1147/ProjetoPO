import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Equipa implements Serializable {
    private static int contador = 0;
    private int id;
    private String nome;
    private List<Jogador> jogadores;
    private Treinador treinador;
    private int numeroVitorias;
    private int numeroDerrotas;
    private int numeroEmpates;
    private int golsMarcados;
    private int golsSofridos;
    private List<Torneio> torneiosParticipando;

    public Equipa(String nome) {
        this.id = ++contador;
        this.nome = nome;
        this.jogadores = new ArrayList<>();
        this.torneiosParticipando = new ArrayList<>();
        this.numeroVitorias = 0;
        this.numeroDerrotas = 0;
        this.numeroEmpates = 0;
        this.golsMarcados = 0;
        this.golsSofridos = 0;
    }

    public void adicionarJogador(Jogador jogador) {
        if (jogador == null) {
            throw new IllegalArgumentException("Jogador não pode ser nulo");
        }
        if (jogadores.contains(jogador)) {
            throw new IllegalArgumentException("Jogador já está na equipa");
        }
        jogadores.add(jogador);
    }

    public void removerJogador(Jogador jogador) {
        if (jogador == null) {
            throw new IllegalArgumentException("Jogador não pode ser nulo");
        }
        if (!jogadores.contains(jogador)) {
            throw new IllegalArgumentException("Jogador não está na equipa");
        }
        jogadores.remove(jogador);
    }

    public void setTreinador(Treinador treinador) {
        if (this.treinador != null && !this.treinador.equals(treinador)) {
            this.treinador.setEquipa(null);
        }
        this.treinador = treinador;
        if (treinador != null) {
            treinador.setEquipa(this);
        }
    }

    public void registrarVitoria() {
        numeroVitorias++;
    }

    public void registrarDerrota() {
        numeroDerrotas++;
    }

    public void registrarEmpate() {
        numeroEmpates++;
    }

    public void registrarGols(int marcados, int sofridos) {
        if (marcados < 0 || sofridos < 0) {
            throw new IllegalArgumentException("Número de gols não pode ser negativo");
        }
        this.golsMarcados += marcados;
        this.golsSofridos += sofridos;
    }

    public void adicionarTorneio(Torneio torneio) {
        if (torneio == null) {
            throw new IllegalArgumentException("Torneio não pode ser nulo");
        }
        if (!torneiosParticipando.contains(torneio)) {
            torneiosParticipando.add(torneio);
        }
    }

    public void removerTorneio(Torneio torneio) {
        torneiosParticipando.remove(torneio);
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome da equipa não pode ser vazio");
        }
        this.nome = nome;
    }

    public List<Jogador> getJogadores() {
        return Collections.unmodifiableList(jogadores);
    }

    public Treinador getTreinador() {
        return treinador;
    }

    public int getNumeroVitorias() {
        return numeroVitorias;
    }

    public int getNumeroDerrotas() {
        return numeroDerrotas;
    }

    public int getNumeroEmpates() {
        return numeroEmpates;
    }

    public int getGolsMarcados() {
        return golsMarcados;
    }

    public int getGolsSofridos() {
        return golsSofridos;
    }

    public int getSaldoGols() {
        return golsMarcados - golsSofridos;
    }

    public List<Torneio> getTorneiosParticipando() {
        return Collections.unmodifiableList(torneiosParticipando);
    }

    public String getEstatisticas() {
        return String.format("""
            Estatísticas da Equipa %s:
            Vitórias: %d
            Derrotas: %d
            Empates: %d
            Gols Marcados: %d
            Gols Sofridos: %d
            Saldo de Gols: %d
            Número de Jogadores: %d
            Treinador: %s
            """,
            nome,
            numeroVitorias,
            numeroDerrotas,
            numeroEmpates,
            golsMarcados,
            golsSofridos,
            getSaldoGols(),
            jogadores.size(),
            treinador != null ? treinador.getNome() : "Sem treinador"
        );
    }

    @Override
    public String toString() {
        return String.format("Equipa: %s (ID: %d)", nome, id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Equipa equipa = (Equipa) o;
        return id == equipa.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    public String toFileString() {
        StringBuilder sb = new StringBuilder();
        sb.append("nome=").append(nome).append(",");
        
        if (treinador != null) {
            sb.append("treinador=").append(treinador.getNome()).append(",");
        }
        
        if (!jogadores.isEmpty()) {
            sb.append("jogadores=");
            for (int i = 0; i < jogadores.size(); i++) {
                sb.append(jogadores.get(i).getNome());
                if (i < jogadores.size() - 1) {
                    sb.append(";");
                }
            }
        }
        
        return sb.toString();
    }

    public void salvarEquipaTxt(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar equipa: " + e.getMessage());
        }
    }

    public static List<Equipa> carregarEquipasTxt(String filename, List<Jogador> jogadores, List<Treinador> treinadores) {
        List<Equipa> equipas = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                String nome = null;
                String treinadorNome = null;
                List<String> jogadoresNomes = new ArrayList<>();

                for (String dado : dados) {
                    String[] partes = dado.split("=");
                    if (partes.length == 2) {
                        switch (partes[0].trim()) {
                            case "nome":
                                nome = partes[1].trim();
                                break;
                            case "treinador":
                                treinadorNome = partes[1].trim();
                                break;
                            case "jogadores":
                                String[] nomes = partes[1].split(";");
                                jogadoresNomes.addAll(Arrays.asList(nomes));
                                break;
                        }
                    }
                }

                if (nome != null) {
                    Equipa equipa = new Equipa(nome);
                    
                    // Adicionar treinador
                    if (treinadorNome != null) {
                        for (Treinador t : treinadores) {
                            if (t.getNome().equals(treinadorNome)) {
                                equipa.setTreinador(t);
                                break;
                            }
                        }
                    }

                    // Adicionar jogadores
                    for (String nomeJogador : jogadoresNomes) {
                        for (Jogador j : jogadores) {
                            if (j.getNome().equals(nomeJogador.trim())) {
                                equipa.adicionarJogador(j);
                                break;
                            }
                        }
                    }

                    equipas.add(equipa);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar equipas: " + e.getMessage());
        }
        return equipas;
    }
}

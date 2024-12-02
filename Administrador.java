import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Administrador extends Entidade {
    private List<Jogador> jogadores;
    private List<Treinador> treinadores;
    private List<Torneio> torneios;
    private List<Equipa> equipas;

    public Administrador(String nomeCompleto, String username, String password) {
        super(nomeCompleto, username, password, "admin@esports.com");
        this.jogadores = new ArrayList<>();
        this.treinadores = new ArrayList<>();
        this.torneios = new ArrayList<>();
        this.equipas = new ArrayList<>();
    }

    public void adicionarJogador(Jogador jogador) {
        if (jogador == null) {
            throw new IllegalArgumentException("Jogador não pode ser nulo");
        }
        if (procurarJogador(jogador.getNome()) != null) {
            throw new IllegalArgumentException("Jogador já existe no sistema");
        }
        jogadores.add(jogador);
    }

    public void removerJogador(String nomeJogador) {
        if (nomeJogador == null || nomeJogador.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do jogador não pode ser vazio");
        }
        Jogador jogador = procurarJogador(nomeJogador);
        if (jogador != null) {
            jogadores.remove(jogador);
            System.out.println("Jogador removido com sucesso.");
        } else {
            System.out.println("Jogador não encontrado.");
        }
    }

    public void adicionarTreinador(Treinador treinador) {
        if (treinador == null) {
            throw new IllegalArgumentException("Treinador não pode ser nulo");
        }
        if (procurarTreinador(treinador.getNome()) != null) {
            throw new IllegalArgumentException("Treinador já existe no sistema");
        }
        treinadores.add(treinador);
    }

    public void removerTreinador(String nomeTreinador) {
        if (nomeTreinador == null || nomeTreinador.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do treinador não pode ser vazio");
        }
        Treinador treinador = procurarTreinador(nomeTreinador);
        if (treinador != null) {
            treinadores.remove(treinador);
            System.out.println("Treinador removido com sucesso.");
        } else {
            System.out.println("Treinador não encontrado.");
        }
    }

    public void criarEquipa(Equipa equipa) {
        if (equipa == null) {
            throw new IllegalArgumentException("Equipa não pode ser nula");
        }
        if (procurarEquipa(equipa.getNome()) != null) {
            throw new IllegalArgumentException("Equipa já existe no sistema");
        }
        equipas.add(equipa);
    }

    public void criarTorneio(Torneio torneio) {
        if (torneio == null) {
            throw new IllegalArgumentException("Torneio não pode ser nulo");
        }
        if (procurarTorneio(torneio.getNome()) != null) {
            throw new IllegalArgumentException("Torneio já existe no sistema");
        }
        torneios.add(torneio);
    }

    public void agendarPartida(Torneio torneio, Equipa equipaA, Equipa equipaB, String data) {
        if (torneio == null || equipaA == null || equipaB == null || data == null) {
            throw new IllegalArgumentException("Parâmetros não podem ser nulos");
        }
        if (!torneios.contains(torneio)) {
            throw new IllegalArgumentException("Torneio não encontrado no sistema");
        }
        torneio.agendarPartida(equipaA, equipaB, data);
        System.out.println("Partida agendada com sucesso entre " + equipaA.getNome() + " e " + equipaB.getNome() + " no torneio " + torneio.getNome());
    }

    public void registrarResultado(Torneio torneio, Partida partida, int pontosEquipaA, int pontosEquipaB) {
        if (torneio == null || partida == null) {
            throw new IllegalArgumentException("Torneio e partida não podem ser nulos");
        }
        if (!torneios.contains(torneio)) {
            throw new IllegalArgumentException("Torneio não encontrado no sistema");
        }
        if (pontosEquipaA < 0 || pontosEquipaB < 0) {
            throw new IllegalArgumentException("Pontuação não pode ser negativa");
        }
        torneio.registrarResultado(partida, pontosEquipaA, pontosEquipaB);
        System.out.println("Resultado registrado para a partida entre " + partida.getEquipaA().getNome() + " e " + partida.getEquipaB().getNome());
    }

    public void acompanharEstatisticas(Torneio torneio) {
        if (torneio == null) {
            throw new IllegalArgumentException("Torneio não pode ser nulo");
        }
        if (!torneios.contains(torneio)) {
            throw new IllegalArgumentException("Torneio não encontrado no sistema");
        }
        
        System.out.println(torneio.toString());
        System.out.println("\nClassificação atual:");
        List<Equipa> classificacao = torneio.getClassificacao();
        for (int i = 0; i < classificacao.size(); i++) {
            System.out.println((i + 1) + "º - " + classificacao.get(i).getNome());
        }
    }

    public void removerEquipa(Equipa equipa) {
        if (equipa == null) {
            throw new IllegalArgumentException("Equipa não pode ser nula");
        }
        if (!equipas.contains(equipa)) {
            throw new IllegalArgumentException("Equipa não encontrada no sistema");
        }
        equipas.remove(equipa);
    }

    public Jogador procurarJogador(String nomeJogador) {
        if (nomeJogador == null || nomeJogador.trim().isEmpty()) {
            return null;
        }
        return jogadores.stream()
                .filter(j -> j.getNome().equalsIgnoreCase(nomeJogador.trim()))
                .findFirst()
                .orElse(null);
    }

    public Treinador procurarTreinador(String nomeTreinador) {
        if (nomeTreinador == null || nomeTreinador.trim().isEmpty()) {
            return null;
        }
        return treinadores.stream()
                .filter(t -> t.getNome().equalsIgnoreCase(nomeTreinador.trim()))
                .findFirst()
                .orElse(null);
    }

    public Torneio procurarTorneio(String nomeTorneio) {
        if (nomeTorneio == null || nomeTorneio.trim().isEmpty()) {
            return null;
        }
        return torneios.stream()
                .filter(t -> t.getNome().equalsIgnoreCase(nomeTorneio.trim()))
                .findFirst()
                .orElse(null);
    }

    public Equipa procurarEquipa(String nomeEquipa) {
        if (nomeEquipa == null || nomeEquipa.trim().isEmpty()) {
            return null;
        }
        return equipas.stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nomeEquipa.trim()))
                .findFirst()
                .orElse(null);
    }

    public void salvarDadosTxt(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do arquivo não pode ser vazio");
        }

        StringBuilder dados = new StringBuilder();
        dados.append("nome=").append(getNome()).append(", ");
        dados.append("username=").append(getUsername()).append(", ");
        dados.append("password=").append(getPassword());

        try {
            if (!Ficheiro.existeArquivo(filename)) {
                // Se o arquivo não existe, cria novo com cabeçalho
                StringBuilder conteudo = new StringBuilder();
                conteudo.append("Administrador:\n");
                conteudo.append(dados.toString()).append("\n");
                Ficheiro.escreverParaArquivo(conteudo.toString(), filename);
            } else {
                // Se existe, adiciona ao final
                Ficheiro.adicionarLinha(dados.toString(), filename);
            }
            System.out.println("DEBUG - Administrador salvo com sucesso: " + getNome());
        } catch (RuntimeException e) {
            System.err.println("Erro ao salvar administrador: " + e.getMessage());
            throw e;
        }
    }

    public static List<Administrador> carregarTodosAdministradoresTxt(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Nome do arquivo não pode ser vazio");
        }

        List<Administrador> administradores = new ArrayList<>();
        List<String> linhas = Ficheiro.lerArquivo(filename);
        
        System.out.println("DEBUG - Número de linhas lidas: " + linhas.size());

        for (String linha : linhas) {
            linha = linha.trim();
            if (linha.isEmpty()) continue;
            
            System.out.println("DEBUG - Lendo linha: [" + linha + "]");
            
            if (linha.startsWith("nome=")) {
                String[] dados = linha.split(",");
                String nome = null;
                String username = null;
                String password = null;

                for (String dado : dados) {
                    dado = dado.trim();
                    String[] partes = dado.split("=");
                    if (partes.length == 2) {
                        System.out.println("DEBUG - Processando campo: " + partes[0].trim() + " = " + partes[1].trim());
                        switch (partes[0].trim()) {
                            case "nome":
                                nome = partes[1].trim();
                                break;
                            case "username":
                                username = partes[1].trim();
                                break;
                            case "password":
                                password = partes[1].trim();
                                break;
                        }
                    }
                }

                if (nome != null && username != null && password != null) {
                    System.out.println("DEBUG - Criando novo administrador: " + nome + ", " + username);
                    administradores.add(new Administrador(nome, username, password));
                } else {
                    System.out.println("DEBUG - Dados incompletos: nome=" + nome + ", username=" + username + ", password=" + password);
                }
            }
        }

        return administradores;
    }

    // Getters e Setters
    public List<Jogador> getJogadores() {
        return new ArrayList<>(jogadores);
    }

    public void setJogadores(List<Jogador> jogadores) {
        this.jogadores = jogadores != null ? new ArrayList<>(jogadores) : new ArrayList<>();
    }

    public List<Treinador> getTreinadores() {
        return new ArrayList<>(treinadores);
    }

    public void setTreinadores(List<Treinador> treinadores) {
        this.treinadores = treinadores != null ? new ArrayList<>(treinadores) : new ArrayList<>();
    }

    public List<Torneio> getTorneios() {
        return new ArrayList<>(torneios);
    }

    public void setTorneios(List<Torneio> torneios) {
        this.torneios = torneios != null ? new ArrayList<>(torneios) : new ArrayList<>();
    }

    public List<Equipa> getEquipas() {
        return new ArrayList<>(equipas);
    }

    public void setEquipas(List<Equipa> equipas) {
        this.equipas = equipas != null ? new ArrayList<>(equipas) : new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Administrador{" +
                "nome='" + getNome() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", jogadores=" + jogadores.size() +
                ", treinadores=" + treinadores.size() +
                ", torneios=" + torneios.size() +
                ", equipas=" + equipas.size() +
                '}';
    }
}

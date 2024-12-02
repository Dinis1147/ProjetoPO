import java.util.ArrayList;
import java.util.List;
import java.io.*;

public class Treinador extends Entidade {
    private List<Equipa> equipas;
    private String email;
    private Equipa equipaAtual;
    private String especialidade;
    private int anosExperiencia;

    public Treinador(String nome, String username, String password, String email, String especialidade, int anosExperiencia) {
        super(nome, username, password, email);
        this.equipas = new ArrayList<>();
        this.email = email;
        this.equipaAtual = null;
        this.especialidade = especialidade;
        this.anosExperiencia = anosExperiencia;
    }

    public Treinador(String nome, String username, String password, String email) {
        super(nome, username, password, email);
        this.equipas = new ArrayList<>();
        this.email = email;
        this.equipaAtual = null;
        this.especialidade = "";
        this.anosExperiencia = 0;
    }

    public Equipa getEquipa() {
        return equipaAtual;
    }

    public void setEquipa(Equipa equipa) {
        this.equipaAtual = equipa;
        if (equipa != null && !equipas.contains(equipa)) {
            equipas.add(equipa);
        }
    }

    public static List<Treinador> carregarTodosTreinadoresTxt(String filename) {
        List<Treinador> treinadores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.startsWith("Treinador:")) {
                    linha = reader.readLine();
                    String[] partes = linha.split(", ");
                    String nome = partes[0].split("=")[1];
                    String username = partes[1].split("=")[1];
                    String password = partes[2].split("=")[1];
                    String email = partes[3].split("=")[1];
                    Treinador treinador = new Treinador(nome, username, password, email);
                    treinadores.add(treinador);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return treinadores;
    }

    public void criarEquipa(Equipa equipa) {
        equipas.add(equipa);
        equipa.setTreinador(this);
        this.equipaAtual = equipa;
    }

    public Jogador procurarJogador(String nomeJogador) {
        for (Equipa equipa : equipas) {
            for (Jogador jogador : equipa.getJogadores()) {
                if (jogador.getNome().equalsIgnoreCase(nomeJogador)) {
                    return jogador;
                }
            }
        }
        return null;
    }

    public void adicionarJogadorEquipa(String nomeEquipa, Jogador jogador) {
        Equipa equipa = procurarEquipa(nomeEquipa);
        if (equipa != null) {
            equipa.adicionarJogador(jogador);
            System.out.println("Jogador " + jogador.getNome() + " adicionado à equipa " + nomeEquipa);
        } else {
            System.out.println("Equipa não encontrada.");
        }
    }

    public void removerJogadorEquipa(String nomeEquipa, String nomeJogador) {
        Equipa equipa = procurarEquipa(nomeEquipa);
        if (equipa != null) {
            Jogador jogadorRemover = equipa.getJogadores().stream()
                    .filter(jogador -> jogador.getNome().equalsIgnoreCase(nomeJogador))
                    .findFirst()
                    .orElse(null);
            if (jogadorRemover != null) {
                equipa.removerJogador(jogadorRemover);
                System.out.println("Jogador " + nomeJogador + " removido da equipa " + nomeEquipa);
            } else {
                System.out.println("Jogador não encontrado na equipa " + nomeEquipa);
            }
        } else {
            System.out.println("Equipa não encontrada.");
        }
    }

    public void editarDadosEquipa(String nomeEquipa, String novoNome) {
        Equipa equipa = procurarEquipa(nomeEquipa);
        if (equipa != null) {
            equipa.setNome(novoNome);
            System.out.println("Nome da equipa atualizado para: " + novoNome);
        } else {
            System.out.println("Equipa não encontrada.");
        }
    }

    public Equipa procurarEquipa(String nomeEquipa) {
        return equipas.stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nomeEquipa))
                .findFirst()
                .orElse(null);
    }

    public void inscreverEquipaEmTorneio(Torneio torneio, String nomeEquipa) {
        Equipa equipa = procurarEquipa(nomeEquipa);
        if (equipa != null) {
            torneio.adicionarEquipa(equipa);
            System.out.println("Equipa " + nomeEquipa + " inscrita no torneio " + torneio.getNome());
        } else {
            System.out.println("Equipa não encontrada.");
        }
    }

    public void acompanharPartidas(Torneio torneio) {
        if (equipaAtual != null) {
            System.out.println("Partidas da equipa " + equipaAtual.getNome() + ":");
            // Implementar lógica para mostrar partidas da equipa
        } else {
            System.out.println("Você não está treinando nenhuma equipa no momento.");
        }
    }

    public List<Equipa> getEquipas() {
        return equipas;
    }

    public void salvarDadosTxt(String caminho) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminho, true))) {
            writer.write(this.toString()); // Aqui se assume que o método toString() tem uma representação útil dos dados do treinador.
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar os dados do treinador: " + e.getMessage());
        }
    }

    public String toFileString() {
        return String.format("nome=%s,username=%s,password=%s,email=%s,especialidade=%s,anosExperiencia=%d",
            getNome(), getUsername(), getPassword(), email, especialidade, anosExperiencia);
    }

    public void salvarTreinadorTxt(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, true))) {
            writer.write(toFileString());
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Erro ao salvar treinador: " + e.getMessage());
        }
    }

    public static List<Treinador> carregarTreinadoresTxt(String filename) {
        List<Treinador> treinadores = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                String nome = null;
                String username = null;
                String password = null;
                String email = null;
                String especialidade = null;
                int anosExperiencia = 0;

                for (String dado : dados) {
                    String[] partes = dado.split("=");
                    if (partes.length == 2) {
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
                            case "email":
                                email = partes[1].trim();
                                break;
                            case "especialidade":
                                especialidade = partes[1].trim();
                                break;
                            case "anosExperiencia":
                                anosExperiencia = Integer.parseInt(partes[1].trim());
                                break;
                        }
                    }
                }

                if (nome != null && username != null && password != null && email != null && especialidade != null) {
                    treinadores.add(new Treinador(nome, username, password, email, especialidade, anosExperiencia));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar treinadores: " + e.getMessage());
        }
        return treinadores;
    }

    public String getEspecialidade() {
        return especialidade;
    }

    public void setEspecialidade(String especialidade) {
        this.especialidade = especialidade;
    }

    public int getAnosExperiencia() {
        return anosExperiencia;
    }

    public void setAnosExperiencia(int anosExperiencia) {
        this.anosExperiencia = anosExperiencia;
    }

    @Override
    public String toString() {
        return "Treinador{" +
                "nome='" + getNome() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", equipas=" + equipas +
                ", especialidade='" + especialidade + '\'' +
                ", anosExperiencia=" + anosExperiencia +
                '}';
    }

    public String getEmail() {
        return email;
    }
}

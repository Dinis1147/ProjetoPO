import java.util.ArrayList;
import java.util.List;
import java.io.*;


public class Treinador extends Entidade {
    private List<Equipa> equipas;
        private String email;
    
        public Treinador(String nome, String username, String password, String email) {
            super(nome, username, password, email);
            this.equipas = new ArrayList<>();
            this.email = email;
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
        Equipa equipa = equipas.stream()
            .filter(e -> e.getNome().equalsIgnoreCase(nomeEquipa))
            .findFirst()
            .orElse(null);
    if (equipa != null) {
        equipa.setNome(novoNome);  // Exemplo de atualização do nome da equipa
        System.out.println("Nome da equipa atualizado para: " + novoNome);
    } else {
        System.out.println("Equipa não encontrada.");
         }
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
        System.out.println("Partidas do Torneio: " + torneio.getNome());
        for (Partida partida : torneio.getPartidas()) {
            System.out.println(partida);
        }
    }

    private Equipa procurarEquipa(String nomeEquipa) {
        return equipas.stream()
                .filter(e -> e.getNome().equalsIgnoreCase(nomeEquipa))
                .findFirst()
                .orElse(null);
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

    @Override
    public String toString() {
        return "Treinador{" +
                "nome='" + getNome() + '\'' +
                ", username='" + getUsername() + '\'' +
                ", equipas=" + equipas +
                '}';
    }

	public String getEmail() {
		return email;
		
	}
}

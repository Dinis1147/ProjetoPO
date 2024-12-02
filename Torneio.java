import java.util.List;
import java.util.ArrayList;


public class Torneio {
    private static int contadorId = 1;
    private int id;
    private String nome;
    private String jogo;
    private List<Equipa> equipasParticipantes;
    private List<Partida> partidas;
    private List<String> tabelaClassificacao;

    public Torneio(String nome, String jogo) {
        this.id = contadorId++;
        this.nome = nome;
        this.jogo = jogo;
        this.equipasParticipantes = new ArrayList<>();
        this.partidas = new ArrayList<>();
        this.tabelaClassificacao = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getJogo() {
        return jogo;
    }

    public List<Equipa> getEquipasParticipantes() {
        return equipasParticipantes;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void adicionarEquipa(Equipa equipa) {
        equipasParticipantes.add(equipa);
    }

    public void agendarPartida(Equipa equipaA, Equipa equipaB, String data) {
        Partida partida = new Partida(equipaA, equipaB, data);
        partidas.add(partida);
    }

    public void registrarResultado(Partida partida, int pontosA, int pontosB) {
        partida.setPontos(pontosA, pontosB);
        atualizarClassificacao(partida);
    }

    private void atualizarClassificacao(Partida partida) {
        // Exemplo de lógica para atualizar a classificação
        Equipa vencedora = partida.getVencedor();
        if (vencedora != null) {
            tabelaClassificacao.add("Vencedor: " + vencedora.getNome());
        } else {
            tabelaClassificacao.add("Empate entre: " + partida.getEquipaA().getNome() + " e " + partida.getEquipaB().getNome());
        }
    }

    public void exibirClassificacao() {
        System.out.println("Classificação do Torneio " + nome + ":");
        for (String linha : tabelaClassificacao) {
            System.out.println(linha);
        }
    }

    public void exibirEstatisticas() {
        System.out.println("Estatísticas do Torneio " + nome + ":");
        for (Partida partida : partidas) {
            System.out.println(partida);
        }
    }
}


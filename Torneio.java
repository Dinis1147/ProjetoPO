import java.util.List;
import java.util.ArrayList;

public class Torneio {
    private static int contadorId = 1;
    private int id;
    private String nome;
    private String jogo;
    private String dataInicio;
    private String dataFim;
    private double premio;
    private List<Equipa> equipasParticipantes;
    private List<Partida> partidas;
    private TabelaClassificacao tabelaClassificacao;
    private boolean torneioEmAndamento;
    private boolean torneioFinalizado;

    public Torneio(String nome, String jogo, String dataInicio, String dataFim, double premio) {
        this.id = contadorId++;
        this.nome = nome;
        this.jogo = jogo;
        this.dataInicio = dataInicio;
        this.dataFim = dataFim;
        this.premio = premio;
        this.equipasParticipantes = new ArrayList<>();
        this.partidas = new ArrayList<>();
        this.tabelaClassificacao = new TabelaClassificacao();
        this.torneioEmAndamento = false;
        this.torneioFinalizado = false;
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

    public String getDataInicio() {
        return dataInicio;
    }

    public String getDataFim() {
        return dataFim;
    }

    public double getPremio() {
        return premio;
    }

    public List<Equipa> getEquipasParticipantes() {
        return equipasParticipantes;
    }

    public List<Partida> getPartidas() {
        return partidas;
    }

    public void adicionarEquipa(Equipa equipa) {
        if (torneioEmAndamento) {
            throw new IllegalStateException("Não é possível adicionar equipas com o torneio em andamento");
        }
        if (equipasParticipantes.contains(equipa)) {
            throw new IllegalArgumentException("Esta equipa já está inscrita no torneio");
        }
        equipasParticipantes.add(equipa);
        tabelaClassificacao.adicionarEquipa(equipa);
    }

    public void adicionarPartida(Partida partida) {
        partidas.add(partida);
    }

    public void iniciarTorneio() {
        if (equipasParticipantes.size() < 2) {
            throw new IllegalStateException("São necessárias pelo menos 2 equipas para iniciar o torneio");
        }
        torneioEmAndamento = true;
    }

    public void agendarPartida(Equipa equipaA, Equipa equipaB, String data, String horario) {
        if (!torneioEmAndamento) {
            throw new IllegalStateException("O torneio precisa estar em andamento para agendar partidas");
        }
        if (!equipasParticipantes.contains(equipaA) || !equipasParticipantes.contains(equipaB)) {
            throw new IllegalArgumentException("Ambas as equipas devem estar inscritas no torneio");
        }
        Partida partida = new Partida(equipaA, equipaB, data, horario);
        partidas.add(partida);
    }

    public void registrarResultado(Partida partida, int pontosEquipaA, int pontosEquipaB) {
        if (!partidas.contains(partida)) {
            throw new IllegalArgumentException("Esta partida não pertence a este torneio");
        }
        if (partida.isResultadoRegistrado()) {
            throw new IllegalStateException("O resultado desta partida já foi registrado");
        }

        partida.registrarResultado(pontosEquipaA, pontosEquipaB);
        
        // Atualiza a tabela de classificação
        if (pontosEquipaA > pontosEquipaB) {
            tabelaClassificacao.registrarVitoria(partida.getEquipaA());
            tabelaClassificacao.registrarDerrota(partida.getEquipaB());
        } else if (pontosEquipaB > pontosEquipaA) {
            tabelaClassificacao.registrarVitoria(partida.getEquipaB());
            tabelaClassificacao.registrarDerrota(partida.getEquipaA());
        } else {
            tabelaClassificacao.registrarEmpate(partida.getEquipaA());
            tabelaClassificacao.registrarEmpate(partida.getEquipaB());
        }
    }

    public void finalizarTorneio() {
        if (!torneioEmAndamento) {
            throw new IllegalStateException("O torneio não está em andamento");
        }
        for (Partida partida : partidas) {
            if (!partida.isResultadoRegistrado()) {
                throw new IllegalStateException("Existem partidas pendentes");
            }
        }
        torneioEmAndamento = false;
        torneioFinalizado = true;
    }

    public List<Equipa> getEquipas() {
        return new ArrayList<>(equipasParticipantes);
    }

    public void removerEquipa(Equipa equipa) {
        if (torneioEmAndamento) {
            throw new IllegalStateException("Não é possível remover equipas com o torneio em andamento");
        }
        if (!equipasParticipantes.contains(equipa)) {
            throw new IllegalArgumentException("Esta equipa não está inscrita no torneio");
        }
        equipasParticipantes.remove(equipa);
        tabelaClassificacao.removerEquipa(equipa);
    }

    public List<Equipa> getClassificacao() {
        return tabelaClassificacao.getClassificacao();
    }

    public int getPontosEquipa(Equipa equipa) {
        return tabelaClassificacao.getPontosEquipa(equipa);
    }

    public boolean isFinalizado() {
        return torneioFinalizado;
    }

    public boolean isEmAndamento() {
        return torneioEmAndamento;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Torneio: ").append(nome).append("\n");
        sb.append("Jogo: ").append(jogo).append("\n");
        sb.append("Data de início: ").append(dataInicio).append("\n");
        sb.append("Data de fim: ").append(dataFim).append("\n");
        sb.append("Prêmio: ").append(premio).append("\n");
        sb.append("Status: ");
        if (torneioFinalizado) {
            sb.append("Finalizado");
        } else if (torneioEmAndamento) {
            sb.append("Em andamento");
        } else {
            sb.append("Não iniciado");
        }
        sb.append("\nEquipas participantes: ").append(equipasParticipantes.size());
        sb.append("\nPartidas realizadas: ").append(partidas.stream().filter(Partida::isJogada).count());
        sb.append("\nPartidas pendentes: ").append(partidas.stream().filter(p -> !p.isJogada()).count());
        return sb.toString();
    }
}

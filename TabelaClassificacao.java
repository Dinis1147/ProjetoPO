import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TabelaClassificacao {
    private Map<Equipa, DadosClassificacao> classificacao;

    private static class DadosClassificacao implements Comparable<DadosClassificacao> {
        private int pontos;
        private int vitorias;
        private int empates;
        private int derrotas;
        private int golsPro;
        private int golsContra;
        private Equipa equipa;

        public DadosClassificacao(Equipa equipa) {
            this.equipa = equipa;
            this.pontos = 0;
            this.vitorias = 0;
            this.empates = 0;
            this.derrotas = 0;
            this.golsPro = 0;
            this.golsContra = 0;
        }

        public int getSaldoGols() {
            return golsPro - golsContra;
        }

        @Override
        public int compareTo(DadosClassificacao outro) {
            // Primeiro critério: pontos
            if (this.pontos != outro.pontos) {
                return Integer.compare(outro.pontos, this.pontos);
            }
            // Segundo critério: número de vitórias
            if (this.vitorias != outro.vitorias) {
                return Integer.compare(outro.vitorias, this.vitorias);
            }
            // Terceiro critério: saldo de gols
            if (this.getSaldoGols() != outro.getSaldoGols()) {
                return Integer.compare(outro.getSaldoGols(), this.getSaldoGols());
            }
            // Quarto critério: gols marcados
            return Integer.compare(outro.golsPro, this.golsPro);
        }
    }

    public TabelaClassificacao() {
        this.classificacao = new HashMap<>();
    }

    public void adicionarEquipa(Equipa equipa) {
        if (!classificacao.containsKey(equipa)) {
            classificacao.put(equipa, new DadosClassificacao(equipa));
        }
    }

    public void removerEquipa(Equipa equipa) {
        classificacao.remove(equipa);
    }

    public void registrarVitoria(Equipa equipa) {
        DadosClassificacao dados = classificacao.get(equipa);
        if (dados != null) {
            dados.pontos += 3;
            dados.vitorias++;
        }
    }

    public void registrarEmpate(Equipa equipa) {
        DadosClassificacao dados = classificacao.get(equipa);
        if (dados != null) {
            dados.pontos += 1;
            dados.empates++;
        }
    }

    public void registrarDerrota(Equipa equipa) {
        DadosClassificacao dados = classificacao.get(equipa);
        if (dados != null) {
            dados.derrotas++;
        }
    }

    public void registrarGols(Equipa equipa, int golsPro, int golsContra) {
        DadosClassificacao dados = classificacao.get(equipa);
        if (dados != null) {
            dados.golsPro += golsPro;
            dados.golsContra += golsContra;
        }
    }

    public int getPontosEquipa(Equipa equipa) {
        DadosClassificacao dados = classificacao.get(equipa);
        return dados != null ? dados.pontos : 0;
    }

    public List<Equipa> getClassificacao() {
        List<DadosClassificacao> dadosOrdenados = new ArrayList<>(classificacao.values());
        Collections.sort(dadosOrdenados);
        
        List<Equipa> equipasOrdenadas = new ArrayList<>();
        for (DadosClassificacao dados : dadosOrdenados) {
            equipasOrdenadas.add(dados.equipa);
        }
        return equipasOrdenadas;
    }

    public String getEstatisticasEquipa(Equipa equipa) {
        DadosClassificacao dados = classificacao.get(equipa);
        if (dados == null) {
            return "Equipa não encontrada na classificação";
        }

        return String.format(
            "%s:\nPontos: %d\nJogos: %d\nVitórias: %d\nEmpates: %d\nDerrotas: %d\n" +
            "Gols marcados: %d\nGols sofridos: %d\nSaldo de gols: %d",
            equipa.getNome(),
            dados.pontos,
            dados.vitorias + dados.empates + dados.derrotas,
            dados.vitorias,
            dados.empates,
            dados.derrotas,
            dados.golsPro,
            dados.golsContra,
            dados.getSaldoGols()
        );
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Classificação:\n");
        sb.append(String.format("%-20s %-5s %-5s %-5s %-5s %-5s %-5s %-5s\n",
            "Equipa", "Pts", "V", "E", "D", "GP", "GC", "SG"));
        
        List<DadosClassificacao> dadosOrdenados = new ArrayList<>(classificacao.values());
        Collections.sort(dadosOrdenados);

        for (DadosClassificacao dados : dadosOrdenados) {
            sb.append(String.format("%-20s %-5d %-5d %-5d %-5d %-5d %-5d %-5d\n",
                dados.equipa.getNome(),
                dados.pontos,
                dados.vitorias,
                dados.empates,
                dados.derrotas,
                dados.golsPro,
                dados.golsContra,
                dados.getSaldoGols()));
        }
        return sb.toString();
    }
}

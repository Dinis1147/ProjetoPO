import java.util.HashMap;
import java.util.Map;

public class TabelaClassificacao {
    private Map<String, Integer> classificacao;

    public TabelaClassificacao() {
        this.classificacao = new HashMap<>();
    }

    public void adicionarEquipa(String equipaNome) {
        classificacao.put(equipaNome, 0);
    }

    public void atualizarPontuacao(String equipaNome, int pontos) {
        classificacao.put(equipaNome, classificacao.getOrDefault(equipaNome, 0) + pontos);
    }

    public void mostrarClassificacao() {
        System.out.println("Classificação Atual:");
        classificacao.forEach((equipa, pontos) -> System.out.println(equipa + ": " + pontos + " pontos"));
    }
}





public class Partida {
    private static int contadorId = 1;
    private int idPartida;
    private Equipa equipaA;
    private Equipa equipaB;
    private String data;
    private int pontosA;
    private int pontosB;
    private boolean resultadoRegistrado;

    public Partida(Equipa equipaA, Equipa equipaB, String data) {
        this.idPartida = contadorId++;
        this.equipaA = equipaA;
        this.equipaB = equipaB;
        this.data = data;
        this.pontosA = 0;
        this.pontosB = 0;
        this.resultadoRegistrado = false;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public Equipa getEquipaA() {
        return equipaA;
    }

    public Equipa getEquipaB() {
        return equipaB;
    }

    public void setPontos(int pontosA, int pontosB) {
        this.pontosA = pontosA;
        this.pontosB = pontosB;
        this.resultadoRegistrado = true;
    }

    public Equipa getVencedor() {
        if (resultadoRegistrado) {
            if (pontosA > pontosB) {
                return equipaA;
            } else if (pontosB > pontosA) {
                return equipaB;
            }
        }
        return null; // Empate ou sem resultado
    }

    @Override
    public String toString() {
        return "Partida " + idPartida + ": " + equipaA.getNome() + " vs " + equipaB.getNome() + " em " + data + " - Resultado: " + pontosA + " - " + pontosB;
    }
}

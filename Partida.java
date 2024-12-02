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

    public void registrarResultado(int pontosA, int pontosB) {
        if (resultadoRegistrado) {
            throw new IllegalStateException("O resultado desta partida já foi registrado");
        }
        if (pontosA < 0 || pontosB < 0) {
            throw new IllegalArgumentException("Os pontos não podem ser negativos");
        }
        this.pontosA = pontosA;
        this.pontosB = pontosB;
        this.resultadoRegistrado = true;
    }

    public boolean isResultadoRegistrado() {
        return resultadoRegistrado;
    }

    public boolean isJogada() {
        return resultadoRegistrado;
    }

    public int getPontosA() {
        return pontosA;
    }

    public int getPontosB() {
        return pontosB;
    }

    public String getData() {
        return data;
    }

    public String getResultado() {
        if (!resultadoRegistrado) {
            return "Partida ainda não realizada";
        }
        return equipaA.getNome() + " " + pontosA + " x " + pontosB + " " + equipaB.getNome();
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
        StringBuilder sb = new StringBuilder();
        sb.append("Partida #").append(idPartida).append("\n");
        sb.append("Data: ").append(data).append("\n");
        sb.append(equipaA.getNome()).append(" vs ").append(equipaB.getNome()).append("\n");
        if (resultadoRegistrado) {
            sb.append("Resultado: ").append(getResultado());
        } else {
            sb.append("Status: Aguardando realização");
        }
        return sb.toString();
    }
}

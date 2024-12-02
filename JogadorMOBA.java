public class JogadorMOBA extends Jogador {
    private String personagemPrincipal;
    private String KDA;

    // Construtor
    public JogadorMOBA(String nome, String username, String password, String nickname, String personagemPrincipal, String KDA) {
        super(nome, username, password, nickname); // Chama o construtor correto de Jogador
        this.personagemPrincipal = personagemPrincipal;
        this.KDA= KDA;
    }
    public String getPersonagemPrincipal() {
        return personagemPrincipal;
    }

    public void setPersonagemPrincipal(String personagemPrincipal) {
        this.personagemPrincipal = personagemPrincipal;
    }

    public String getKda() {
        return KDA;
    }

    public void setKda(String KDA) {
        this.KDA = KDA;
    }

    @Override
    public String toString() {
        return super.toString() + ", Personagem Principal: " + personagemPrincipal + ", KDA: " + KDA;
    }
}       

  

public abstract class Entidade {
    private static int contadorID = 1; // Contador estático para gerar IDs únicos
    private int id;
    private String nome;
    private String username;
    private String password;
    private String nomeCompleto;

    public Entidade(String nome, String username, String password, String nomeCompleto) {
        this.id = contadorID++; // Gera um ID único para cada instância
        this.nome = nome;
        this.username = username;
        this.password = password;
        this.nomeCompleto = nomeCompleto;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUsername() {
        return username;
    }
 
    
     // Getter para o password
    public String getPassword() {
        return password;
    }
    
    

    // Método para autenticação
    public boolean autenticar(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }

    @Override
    public String toString() {
        return "Entidade{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", username='" + username + '\'' +
                '}';
    }
}

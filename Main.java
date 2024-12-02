import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Set;
import java.util.HashSet;
import java.io.File;

public class Main {
    private static final String ADMIN_FILE = "dados/administradores.txt";
    private static final String JOGADORES_FILE = "dados/dados_jogador.txt";
    private static final String TREINADORES_FILE = "dados/dados_treinador.txt";
    private static final String EQUIPAS_FILE = "dados/dados_equipa.txt";
    private static final String TORNEIOS_FILE = "dados/dados_torneio.txt";

    private static List<Administrador> administradores;
    private static List<Jogador> jogadores;
    private static List<Treinador> treinadores;
    private static List<Equipa> equipas;
    private static List<Torneio> torneios;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        // Inicializar as listas
        administradores = carregarAdministradores();
        jogadores = carregarJogadores();
        treinadores = carregarTreinadores();
        equipas = carregarEquipas(jogadores, treinadores);
        torneios = carregarTorneios();
        
        // Atualiza os torneios do administrador
        if (!administradores.isEmpty() && !torneios.isEmpty()) {
            Administrador admin = administradores.get(0);
            for (Torneio t : torneios) {
                admin.criarTorneio(t);
            }
        }

        while (true) {
            System.out.println("\n=== Sistema de Gestão de eSports ===");
            System.out.println("1. Administrador");
            System.out.println("2. Jogador");
            System.out.println("3. Treinador");
            System.out.println("4. Sair");

            System.out.print("\nEscolha uma opção: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Opção inválida. Por favor, escolha um número entre 1 e 4.");
                System.out.print("Escolha uma opção: ");
                scanner.next();
            }
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir o newline

            if (opcao == 4) {
                System.out.println("Encerrando o programa...");
                break;
            }

            switch (opcao) {
                case 1:
                    autenticarAdministrador(scanner);
                    break;
                case 2:
                    autenticarJogador(scanner);
                    break;
                case 3:
                    autenticarTreinador(scanner);
                    break;
                default:
                    System.out.println("Opção inválida. Por favor, escolha um número entre 1 e 4.");
            }
        }
        scanner.close();
    }

    private static List<Administrador> carregarAdministradores() {
        List<Administrador> administradores = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(ADMIN_FILE));
            String linha;
            boolean primeiraLinha = true;
            
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] dados = linha.split(",");
                String nome = null;
                String username = null;
                String password = null;

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
                        }
                    }
                }

                if (nome != null && username != null && password != null) {
                    Administrador admin = new Administrador(nome, username, password);
                    administradores.add(admin);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar administradores: " + e.getMessage());
        }
        return administradores;
    }

    private static List<Jogador> carregarJogadores() {
        List<Jogador> jogadores = Jogador.carregarTodosJogadores();
        return jogadores;
    }

    private static List<Treinador> carregarTreinadores() {
        List<Treinador> treinadores = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("dados/dados_treinador.txt"));
            String linha;
            boolean primeiraLinha = true;
            
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                
                if (linha.trim().isEmpty()) {
                    continue;
                }

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
                    Treinador treinador = new Treinador(nome, username, password, email, especialidade, anosExperiencia);
                    treinadores.add(treinador);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar treinadores: " + e.getMessage());
        }
        return treinadores;
    }

    private static List<Equipa> carregarEquipas(List<Jogador> jogadores, List<Treinador> treinadores) {
        List<Equipa> equipas = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(EQUIPAS_FILE));
            String linha;
            boolean primeiraLinha = true;
            
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                
                if (linha.trim().isEmpty()) {
                    continue;
                }
                
                String[] dados = linha.split(",");
                String nome = null;
                String treinadorNome = null;
                List<String> jogadoresNomes = new ArrayList<>();
                
                for (String dado : dados) {
                    String[] partes = dado.split("=");
                    if (partes.length == 2) {
                        switch (partes[0].trim()) {
                            case "nome":
                                nome = partes[1].trim();
                                break;
                            case "treinador":
                                treinadorNome = partes[1].trim();
                                break;
                            case "jogadores":
                                String[] nomes = partes[1].split(";");
                                for (String n : nomes) {
                                    jogadoresNomes.add(n.trim());
                                }
                                break;
                        }
                    }
                }
                
                if (nome != null) {
                    // Cria a equipe primeiro
                    Equipa equipa = new Equipa(nome);
                    
                    // Adiciona o treinador se existir
                    if (treinadorNome != null) {
                        for (Treinador t : treinadores) {
                            if (t.getNome().equals(treinadorNome)) {
                                equipa.setTreinador(t);
                                break;
                            }
                        }
                    }
                    
                    // Adiciona os jogadores
                    for (String jogadorNome : jogadoresNomes) {
                        for (Jogador j : jogadores) {
                            if (j.getNome().equals(jogadorNome)) {
                                try {
                                    equipa.adicionarJogador(j);
                                } catch (IllegalArgumentException e) {
                                    // Ignora se o jogador já estiver na equipe
                                    System.out.println("Aviso: " + e.getMessage());
                                }
                                break;
                            }
                        }
                    }
                    
                    equipas.add(equipa);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar equipes: " + e.getMessage());
        }
        return equipas;
    }

    private static List<Torneio> carregarTorneios() {
        List<Torneio> torneios = new ArrayList<>();
        try {
            File arquivo = new File(TORNEIOS_FILE);
            if (!arquivo.exists()) {
                return torneios;
            }

            BufferedReader reader = new BufferedReader(new FileReader(TORNEIOS_FILE));
            String linha;
            boolean primeiraLinha = true;
            
            while ((linha = reader.readLine()) != null) {
                if (primeiraLinha) {
                    primeiraLinha = false;
                    continue;
                }
                
                if (linha.trim().isEmpty()) {
                    continue;
                }

                String[] dados = linha.split(",");
                String nome = null;
                String jogo = null;
                String dataInicio = null;
                String dataFim = null;
                double premio = 0;
                List<String> equipasNomes = new ArrayList<>();

                for (String dado : dados) {
                    String[] partes = dado.split("=");
                    if (partes.length == 2) {
                        switch (partes[0].trim()) {
                            case "nome":
                                nome = partes[1].trim();
                                break;
                            case "jogo":
                                jogo = partes[1].trim();
                                break;
                            case "dataInicio":
                                dataInicio = partes[1].trim();
                                break;
                            case "dataFim":
                                dataFim = partes[1].trim();
                                break;
                            case "premio":
                                try {
                                    premio = Double.parseDouble(partes[1].trim());
                                } catch (NumberFormatException e) {
                                    premio = 0;
                                }
                                break;
                            case "equipas":
                                if (!partes[1].trim().isEmpty()) {
                                    String[] nomes = partes[1].split(";");
                                    for (String n : nomes) {
                                        equipasNomes.add(n.trim());
                                    }
                                }
                                break;
                        }
                    }
                }

                if (nome != null && jogo != null && dataInicio != null && dataFim != null) {
                    Torneio torneio = new Torneio(nome, jogo, dataInicio, dataFim, premio);
                    
                    // Adiciona as equipes ao torneio
                    for (String nomeEquipa : equipasNomes) {
                        for (Equipa e : equipas) {
                            if (e.getNome().equals(nomeEquipa)) {
                                torneio.adicionarEquipa(e);
                                break;
                            }
                        }
                    }
                    
                    torneios.add(torneio);
                }
            }
            reader.close();
        } catch (IOException e) {
            System.out.println("Erro ao carregar torneios: " + e.getMessage());
        }
        return torneios;
    }

    private static void salvarDados() {
        try {
            // Criar diretório de dados se não existir
            java.io.File dataDir = new java.io.File("dados");
            if (!dataDir.exists()) {
                dataDir.mkdirs();
            }

            // Salvar dados
            salvarAdministradores();
            salvarJogadores();
            salvarTreinadores();
            salvarEquipas();

        } catch (IOException e) {
            System.out.println("Erro ao salvar dados: " + e.getMessage());
        }
    }

    private static void salvarAdministradores() throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(ADMIN_FILE));
        writer.write("Administrador:\n");
        for (Administrador admin : administradores) {
            writer.write(String.format("nome=%s,username=%s,password=%s\n",
                admin.getNome(), admin.getUsername(), admin.getPassword()));
        }
        writer.close();
    }

    private static void salvarJogadores() {
        for (Jogador jogador : jogadores) {
            jogador.salvar();
        }
    }

    private static void salvarTreinadores() {
        for (Treinador treinador : treinadores) {
            treinador.salvarTreinadorTxt(TREINADORES_FILE);
        }
    }

    private static void salvarEquipas() {
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter(EQUIPAS_FILE));
            writer.write("Equipas:\n");
            for (Equipa equipa : equipas) {
                writer.write("nome=" + equipa.getNome() + ",");
                writer.write("treinador=" + equipa.getTreinador().getNome() + ",");
                writer.write("jogadores=");
                
                List<Jogador> jogadoresEquipa = equipa.getJogadores();
                for (int i = 0; i < jogadoresEquipa.size(); i++) {
                    writer.write(jogadoresEquipa.get(i).getNome());
                    if (i < jogadoresEquipa.size() - 1) {
                        writer.write(";");
                    }
                }
                writer.newLine();
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("Erro ao salvar equipas: " + e.getMessage());
        }
    }

    private static void criarAdministrador(Scanner scanner, List<Administrador> administradores) {
        System.out.print("Nome do administrador: ");
        String nome = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Administrador novoAdmin = new Administrador(nome, username, password);
        administradores.add(novoAdmin);
        novoAdmin.salvarDadosTxt(ADMIN_FILE);
        System.out.println("Administrador criado com sucesso!");
    }

    private static void criarJogador(Scanner scanner, List<Jogador> jogadores, int tipoPreDefinido) {
        System.out.println("\nCriar novo jogador:");
        int tipo = tipoPreDefinido;
        
        if (tipoPreDefinido == 1) {
            System.out.println("Escolha o tipo de jogador:");
            System.out.println("1. Jogador MOBA");
            System.out.println("2. Jogador EFootball");
            System.out.println("3. Jogador FPS");
            System.out.print("Escolha o tipo: ");
            tipo = scanner.nextInt();
            scanner.nextLine();
            
            if (tipo < 1 || tipo > 3) {
                System.out.println("Tipo de jogador inválido!");
                return;
            }
        }

        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Nickname: ");
        String nickname = scanner.nextLine();

        Jogador novoJogador;
        switch (tipo) {
            case 1: // MOBA
                System.out.print("Personagem Principal: ");
                String personagem = scanner.nextLine();
                novoJogador = new JogadorMOBA(nome, username, password, nickname, personagem, "0/0/0");
                break;
            case 2: // EFootball
                String posicao;
                do {
                    System.out.print("Posição (GUARDA-REDES/DEFESA/MEDIO/AVANCADO): ");
                    posicao = scanner.nextLine().toUpperCase();
                    if (!posicao.equals("GUARDA-REDES") && !posicao.equals("DEFESA") && 
                        !posicao.equals("MEDIO") && !posicao.equals("AVANCADO")) {
                        System.out.println("Posição inválida! Por favor, escolha entre: GUARDA-REDES, DEFESA, MEDIO ou AVANCADO");
                    }
                } while (!posicao.equals("GUARDA-REDES") && !posicao.equals("DEFESA") && 
                         !posicao.equals("MEDIO") && !posicao.equals("AVANCADO"));
                novoJogador = new JogadorEFootball(nome, username, password, nickname, posicao, 0, 0, 0);
                break;
            case 3: // FPS
                novoJogador = new JogadorFPS(nome, username, password, nickname, 0.0, 0);
                break;
            default:
                System.out.println("Tipo de jogador inválido!");
                return;
        }

        jogadores.add(novoJogador);
        System.out.println("Jogador criado com sucesso!");
        salvarDados();
    }

    private static void criarTreinador(Scanner scanner, Administrador administrador) {
        System.out.println("\nCriar novo treinador:");
        
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        
        System.out.print("Username: ");
        String username = scanner.nextLine();
        
        System.out.print("Password: ");
        String password = scanner.nextLine();
        
        System.out.print("Email: ");
        String email = scanner.nextLine();

        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();

        System.out.print("Anos de experiência: ");
        int anosExperiencia = scanner.nextInt();
        scanner.nextLine(); // Consumir nova linha

        Treinador novoTreinador = new Treinador(nome, username, password, email, especialidade, anosExperiencia);
        administrador.adicionarTreinador(novoTreinador);
        
        // Salvar o treinador no arquivo
        novoTreinador.salvarTreinadorTxt("dados/dados_treinador.txt");
        System.out.println("Treinador " + nome + " criado e salvo com sucesso!");
        salvarDados();
    }

    private static void criarEquipa(Scanner scanner, Administrador administrador, List<Jogador> jogadores, List<Treinador> treinadores) {
        System.out.println("\nCriar nova equipa:");
        
        if (jogadores.isEmpty()) {
            System.out.println("Não há jogadores disponíveis para criar uma equipa.");
            return;
        }
        
        if (treinadores.isEmpty()) {
            System.out.println("Não há treinadores disponíveis para criar uma equipa.");
            return;
        }

        System.out.print("Nome da equipa: ");
        String nomeEquipa = scanner.nextLine();

        // Selecionar treinador
        System.out.println("\nTreinadores disponíveis:");
        for (int i = 0; i < treinadores.size(); i++) {
            System.out.println((i + 1) + ". " + treinadores.get(i).getNome());
        }
        
        System.out.print("Escolha o treinador (1-" + treinadores.size() + "): ");
        int escolhaTreinador = scanner.nextInt();
        scanner.nextLine(); // Consumir newline
        
        if (escolhaTreinador < 1 || escolhaTreinador > treinadores.size()) {
            System.out.println("Escolha inválida. Criação cancelada.");
            return;
        }
        
        Treinador treinador = treinadores.get(escolhaTreinador - 1);
        Set<Jogador> jogadoresEquipa = new HashSet<>(); // Usando Set para evitar duplicatas

        // Selecionar jogadores
        while (true) {
            System.out.println("\nJogadores disponíveis:");
            for (int i = 0; i < jogadores.size(); i++) {
                Jogador j = jogadores.get(i);
                String status = jogadoresEquipa.contains(j) ? " (Selecionado)" : "";
                System.out.println((i + 1) + ". " + j.getNome() + status);
            }
            
            System.out.print("Escolha um jogador para adicionar (0 para finalizar): ");
            int escolhaJogador = scanner.nextInt();
            scanner.nextLine(); // Consumir newline
            
            if (escolhaJogador == 0) {
                if (jogadoresEquipa.isEmpty()) {
                    System.out.println("A equipa precisa ter pelo menos um jogador. Criação cancelada.");
                    return;
                }
                break;
            }
            
            if (escolhaJogador < 1 || escolhaJogador > jogadores.size()) {
                System.out.println("Escolha inválida.");
                continue;
            }
            
            Jogador jogador = jogadores.get(escolhaJogador - 1);
            if (jogadoresEquipa.contains(jogador)) {
                System.out.println("Este jogador já está na equipa.");
            } else {
                jogadoresEquipa.add(jogador);
                System.out.println("Jogador " + jogador.getNome() + " adicionado à equipa.");
            }
        }

        // Criar a equipa
        Equipa novaEquipa = new Equipa(nomeEquipa);
        novaEquipa.setTreinador(treinador);
        for (Jogador jogador : jogadoresEquipa) {
            novaEquipa.adicionarJogador(jogador);
        }
        equipas.add(novaEquipa);
        
        // Salvar a equipa no arquivo
        try {
            BufferedWriter writer = new BufferedWriter(new FileWriter("dados/dados_equipa.txt", true));
            writer.write("nome=" + novaEquipa.getNome() + ",");
            writer.write("treinador=" + novaEquipa.getTreinador().getNome() + ",");
            writer.write("jogadores=");
            
            boolean primeiro = true;
            for (Jogador j : novaEquipa.getJogadores()) {
                if (!primeiro) {
                    writer.write(";");
                }
                writer.write(j.getNome());
                primeiro = false;
            }
            writer.newLine();
            writer.close();
            
            System.out.println("Equipa criada e salva com sucesso!");
            salvarDados();
        } catch (IOException e) {
            System.out.println("Erro ao salvar a equipa: " + e.getMessage());
        }
    }

    private static void autenticarAdministrador(Scanner scanner) {
        System.out.print("Digite o username do administrador: ");
        String username = scanner.nextLine();
        System.out.print("Digite a password: ");
        String password = scanner.nextLine();

        for (Administrador admin : administradores) {
            if (admin.autenticar(username, password)) {
                System.out.println("Login bem-sucedido como administrador " + admin.getUsername());
                menuAdministrador(scanner, admin, jogadores, treinadores, equipas);
                return;
            }
        }
        System.out.println("Username ou senha incorretos. Tente novamente.");
    }
 
    private static Jogador autenticarJogador(Scanner scanner) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
    
        Jogador jogadorEncontrado = jogadores.stream()
                .filter(jogador -> jogador.autenticar(username, password))
                .findFirst()
                .orElse(null);
    
        if (jogadorEncontrado != null) {
            System.out.println("Login bem-sucedido! Bem-vindo, " + jogadorEncontrado.getNickname());
            menuJogador(scanner, jogadorEncontrado);
        } else {
            System.out.println("Usuário ou senha incorretos. Tente novamente.");
        }
    
        return jogadorEncontrado;
    }

    private static Treinador autenticarTreinador(Scanner scanner) {
        System.out.print("Digite o username do treinador: ");
        String username = scanner.nextLine();
        System.out.print("Digite a password: ");
        String password = scanner.nextLine();

        for (Treinador treinador : treinadores) {
            if (treinador.autenticar(username, password)) {
                System.out.println("Login bem-sucedido como treinador " + treinador.getUsername());
                menuTreinador(scanner, treinador);
                return treinador;
            }
        }
        System.out.println("Username ou senha incorretos. Tente novamente.");
        return null;
    }

    private static void menuAdministrador(Scanner scanner, Administrador administrador, List<Jogador> jogadores, List<Treinador> treinadores, List<Equipa> equipas) {
        while (true) {
            System.out.println("\nMenu Administrador:");
            System.out.println("1. Adicionar Jogador");
            System.out.println("2. Remover Jogador");
            System.out.println("3. Adicionar Treinador");
            System.out.println("4. Remover Treinador");
            System.out.println("5. Criar Equipa");
            System.out.println("6. Gerir Equipas");
            System.out.println("7. Criar Torneio");
            System.out.println("8. Gerir Torneios");
            System.out.println("9. Agendar Partidas");
            System.out.println("10. Registar Resultados");
            System.out.println("11. Ver Estatísticas");
            System.out.println("12. Voltar");

            System.out.print("\nEscolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    criarJogador(scanner, jogadores, 1);
                    break;
                case 2:
                    if (jogadores.isEmpty()) {
                        System.out.println("Não há jogadores para remover.");
                        break;
                    }
                    System.out.println("\nJogadores disponíveis:");
                    for (int i = 0; i < jogadores.size(); i++) {
                        System.out.println((i + 1) + ". " + jogadores.get(i).getNome());
                    }
                    System.out.print("Escolha o jogador para remover (0 para cancelar): ");
                    int escolhaJogador = scanner.nextInt();
                    scanner.nextLine();
                    if (escolhaJogador > 0 && escolhaJogador <= jogadores.size()) {
                        Jogador jogador = jogadores.remove(escolhaJogador - 1);
                        System.out.println("Jogador " + jogador.getNome() + " removido com sucesso!");
                    }
                    break;
                case 3:
                    criarTreinador(scanner, administrador);
                    break;
                case 4:
                    if (treinadores.isEmpty()) {
                        System.out.println("Não há treinadores para remover.");
                        break;
                    }
                    System.out.println("\nTreinadores disponíveis:");
                    for (int i = 0; i < treinadores.size(); i++) {
                        System.out.println((i + 1) + ". " + treinadores.get(i).getNome());
                    }
                    System.out.print("Escolha o treinador para remover (0 para cancelar): ");
                    int escolhaTreinador = scanner.nextInt();
                    scanner.nextLine();
                    if (escolhaTreinador > 0 && escolhaTreinador <= treinadores.size()) {
                        Treinador treinador = treinadores.remove(escolhaTreinador - 1);
                        System.out.println("Treinador " + treinador.getNome() + " removido com sucesso!");
                    }
                    break;
                case 5:
                    criarEquipa(scanner, administrador, jogadores, treinadores);
                    break;
                case 6:
                    gerirEquipas(scanner, administrador, equipas);
                    break;
                case 7:
                    try {
                        System.out.print("Nome do torneio: ");
                        String nomeTorneio = scanner.nextLine();
                        
                        if (nomeTorneio.trim().isEmpty()) {
                            System.out.println("O nome do torneio não pode estar vazio!");
                            break;
                        }
                        
                        System.out.print("Jogo (MOBA, FPS, etc.): ");
                        String jogo = scanner.nextLine();
                        
                        if (jogo.trim().isEmpty()) {
                            System.out.println("O jogo não pode estar vazio!");
                            break;
                        }
                        
                        System.out.print("Data de início (dd/mm/aaaa): ");
                        String dataInicio = scanner.nextLine();
                        
                        if (!dataInicio.matches("\\d{2}/\\d{2}/\\d{4}")) {
                            System.out.println("Formato de data inválido! Use dd/mm/aaaa");
                            break;
                        }
                        
                        System.out.print("Data de fim (dd/mm/aaaa): ");
                        String dataFim = scanner.nextLine();
                        
                        if (!dataFim.matches("\\d{2}/\\d{2}/\\d{4}")) {
                            System.out.println("Formato de data inválido! Use dd/mm/aaaa");
                            break;
                        }
                        
                        System.out.print("Prêmio: ");
                        double premio;
                        try {
                            premio = Double.parseDouble(scanner.nextLine());
                            if (premio < 0) {
                                System.out.println("O prêmio não pode ser negativo!");
                                break;
                            }
                        } catch (NumberFormatException e) {
                            System.out.println("Valor do prêmio inválido!");
                            break;
                        }
                        
                        Torneio novoTorneio = new Torneio(nomeTorneio, jogo, dataInicio, dataFim, premio);
                        administrador.criarTorneio(novoTorneio);
                        salvarTorneioNoArquivo(novoTorneio);
                        System.out.println("Torneio criado com sucesso!");
                    } catch (Exception e) {
                        System.out.println("Erro ao criar torneio: " + e.getMessage());
                    }
                    break;
                case 8:
                    System.out.println("\n=== Gestão de Torneios ===");
                    System.out.println("1. Criar Torneio");
                    System.out.println("2. Adicionar Equipa a Torneio");
                    System.out.println("3. Voltar");
                    
                    System.out.print("Escolha uma opção: ");
                    int opcaoTorneios = scanner.nextInt();
                    scanner.nextLine();
                    
                    switch (opcaoTorneios) {
                        case 1:
                            System.out.print("Nome do torneio: ");
                            String nomeTorneio = scanner.nextLine();
                            
                            System.out.print("Jogo (MOBA, FPS, etc.): ");
                            String jogo = scanner.nextLine();
                            
                            System.out.print("Data de início (dd/mm/aaaa): ");
                            String dataInicio = scanner.nextLine();
                            
                            System.out.print("Data de fim (dd/mm/aaaa): ");
                            String dataFim = scanner.nextLine();
                            
                            System.out.print("Prêmio: ");
                            double premio = scanner.nextDouble();
                            scanner.nextLine();
                            
                            Torneio novoTorneio = new Torneio(nomeTorneio, jogo, dataInicio, dataFim, premio);
                            administrador.criarTorneio(novoTorneio);
                            salvarTorneioNoArquivo(novoTorneio);
                            System.out.println("Torneio criado com sucesso!");
                            break;
                        case 2:
                            // Recarregar as equipes do arquivo
                            equipas = carregarEquipas(jogadores, treinadores);
                            
                            if (equipas.isEmpty()) {
                                System.out.println("Não há equipes cadastradas.");
                                break;
                            }
                            
                            System.out.println("\nEquipas disponíveis:");
                            for (int i = 0; i < equipas.size(); i++) {
                                System.out.println((i + 1) + ". " + equipas.get(i).getNome());
                            }
                            
                            System.out.print("Escolha o número da equipa: ");
                            int escolhaEquipe = scanner.nextInt();
                            scanner.nextLine();
                            
                            if (escolhaEquipe < 1 || escolhaEquipe > equipas.size()) {
                                System.out.println("Opção inválida!");
                                break;
                            }
                            
                            Equipa equipaEscolhida = equipas.get(escolhaEquipe - 1);
                            
                            // Recarregar os torneios do arquivo
                            torneios = carregarTorneios();
                            
                            if (torneios.isEmpty()) {
                                System.out.println("Não há torneios cadastrados. Por favor, crie um torneio primeiro.");
                                break;
                            }
                            
                            System.out.println("\nTorneios disponíveis:");
                            for (int i = 0; i < torneios.size(); i++) {
                                System.out.println((i + 1) + ". " + torneios.get(i).getNome());
                            }
                            
                            System.out.print("Escolha o número do torneio: ");
                            int escolhaTorneio = scanner.nextInt();
                            scanner.nextLine();
                            
                            if (escolhaTorneio < 1 || escolhaTorneio > torneios.size()) {
                                System.out.println("Opção inválida!");
                                break;
                            }
                            
                            Torneio torneioEscolhido = torneios.get(escolhaTorneio - 1);
                            torneioEscolhido.adicionarEquipa(equipaEscolhida);
                            salvarTorneioNoArquivo(torneioEscolhido);
                            System.out.println("Equipa adicionada ao torneio com sucesso!");
                            break;
                        case 3:
                            break;
                        default:
                            System.out.println("Opção inválida!");
                            break;
                    }
                    break;
                case 12:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void gerirEquipas(Scanner scanner, Administrador administrador, List<Equipa> equipas) {
        while (true) {
            System.out.println("\nGestão de Equipas:");
            if (equipas.isEmpty()) {
                System.out.println("Não há equipas cadastradas.");
            } else {
                for (int i = 0; i < equipas.size(); i++) {
                    Equipa equipa = equipas.get(i);
                    StringBuilder info = new StringBuilder();
                    info.append((i + 1)).append(". ").append(equipa.getNome());
                    
                    // Adicionar informação do treinador
                    Treinador treinador = equipa.getTreinador();
                    info.append(" (Treinador: ").append(treinador != null ? treinador.getNome() : "Não definido").append(")");
                    
                    // Adicionar informação dos jogadores
                    List<Jogador> jogadores = equipa.getJogadores();
                    info.append("\n   Jogadores (").append(jogadores.size()).append("): ");
                    if (!jogadores.isEmpty()) {
                        for (int j = 0; j < jogadores.size(); j++) {
                            info.append(jogadores.get(j).getNome());
                            if (j < jogadores.size() - 1) {
                                info.append(", ");
                            }
                        }
                    } else {
                        info.append("Nenhum jogador");
                    }
                    
                    System.out.println(info.toString());
                }
            }
            System.out.println("\n1. Ver detalhes de uma equipa");
            System.out.println("2. Remover equipa");
            System.out.println("3. Voltar");

            System.out.print("\nEscolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.print("Escolha o número da equipa: ");
                    int escolhaEquipa = scanner.nextInt();
                    scanner.nextLine();
                    if (escolhaEquipa > 0 && escolhaEquipa <= equipas.size()) {
                        Equipa equipa = equipas.get(escolhaEquipa - 1);
                        System.out.println("\nDetalhes da Equipa:");
                        System.out.println(equipa.toString());
                        
                        // Mostrar treinador
                        Treinador treinador = equipa.getTreinador();
                        if (treinador != null) {
                            System.out.println("Treinador: " + treinador.getNome());
                        } else {
                            System.out.println("Treinador: Não definido");
                        }
                        
                        // Mostrar jogadores
                        List<Jogador> jogadores = equipa.getJogadores();
                        if (!jogadores.isEmpty()) {
                            System.out.println("\nJogadores da equipa:");
                            for (int i = 0; i < jogadores.size(); i++) {
                                Jogador jogador = jogadores.get(i);
                                System.out.println((i + 1) + ". " + jogador.getNome() + " (" + jogador.getNickname() + ")");
                            }
                        } else {
                            System.out.println("\nNenhum jogador na equipa.");
                        }
                    }
                    break;
                case 2:
                    System.out.print("Escolha o número da equipa para remover: ");
                    escolhaEquipa = scanner.nextInt();
                    scanner.nextLine();
                    if (escolhaEquipa > 0 && escolhaEquipa <= equipas.size()) {
                        Equipa equipa = equipas.get(escolhaEquipa - 1);
                        administrador.removerEquipa(equipa);
                        System.out.println("Equipa " + equipa.getNome() + " removida com sucesso!");
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void menuGerirTorneio(Scanner scanner, Administrador administrador, Torneio torneio) {
        while (true) {
            System.out.println("\nGerenciar Torneio: " + torneio.getNome());
            System.out.println("1. Adicionar Equipa");
            System.out.println("2. Remover Equipa");
            System.out.println("3. Ver Equipas Participantes");
            System.out.println("4. Ver Partidas");
            System.out.println("5. Ver Classificação");
            System.out.println("6. Voltar");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    List<Equipa> equipasDisponiveis = administrador.getEquipas();
                    if (equipasDisponiveis.isEmpty()) {
                        System.out.println("Não há equipas disponíveis para adicionar.");
                        break;
                    }

                    System.out.println("\nEquipas disponíveis:");
                    for (int i = 0; i < equipasDisponiveis.size(); i++) {
                        System.out.println((i + 1) + ". " + equipasDisponiveis.get(i).getNome());
                    }

                    System.out.print("Escolha a equipa para adicionar (0 para cancelar): ");
                    int escolhaEquipa = scanner.nextInt();
                    scanner.nextLine();

                    if (escolhaEquipa > 0 && escolhaEquipa <= equipasDisponiveis.size()) {
                        Equipa equipa = equipasDisponiveis.get(escolhaEquipa - 1);
                        torneio.adicionarEquipa(equipa);
                        System.out.println("Equipa adicionada com sucesso!");
                    }
                    break;

                case 2:
                    List<Equipa> equipasParticipantes = torneio.getEquipas();
                    if (equipasParticipantes.isEmpty()) {
                        System.out.println("Não há equipas participantes no torneio.");
                        break;
                    }

                    System.out.println("\nEquipas participantes:");
                    for (int i = 0; i < equipasParticipantes.size(); i++) {
                        System.out.println((i + 1) + ". " + equipasParticipantes.get(i).getNome());
                    }

                    System.out.print("Escolha a equipa para remover (0 para cancelar): ");
                    escolhaEquipa = scanner.nextInt();
                    scanner.nextLine();

                    if (escolhaEquipa > 0 && escolhaEquipa <= equipasParticipantes.size()) {
                        Equipa equipa = equipasParticipantes.get(escolhaEquipa - 1);
                        torneio.removerEquipa(equipa);
                        System.out.println("Equipa removida com sucesso!");
                    }
                    break;

                case 3:
                    equipasParticipantes = torneio.getEquipas();
                    if (equipasParticipantes.isEmpty()) {
                        System.out.println("Não há equipas participantes no torneio.");
                    } else {
                        System.out.println("\nEquipas participantes:");
                        for (Equipa equipa : equipasParticipantes) {
                            System.out.println("- " + equipa.getNome());
                        }
                    }
                    break;

                case 4:
                    List<Partida> partidas = torneio.getPartidas();
                    if (partidas.isEmpty()) {
                        System.out.println("Não há partidas agendadas neste torneio.");
                    } else {
                        System.out.println("\nPartidas do torneio:");
                        for (Partida partida : partidas) {
                            System.out.println("- " + partida.getEquipaA().getNome() + 
                                " vs " + partida.getEquipaB().getNome() + 
                                " - " + partida.getData() +
                                (partida.isJogada() ? " (Finalizada)" : " (Pendente)"));
                        }
                    }
                    break;

                case 5:
                    List<Equipa> classificacao = torneio.getClassificacao();
                    if (classificacao.isEmpty()) {
                        System.out.println("Não há classificação disponível.");
                    } else {
                        System.out.println("\nClassificação do torneio:");
                        for (int i = 0; i < classificacao.size(); i++) {
                            Equipa equipa = classificacao.get(i);
                            System.out.println((i + 1) + "º - " + equipa.getNome() + 
                                " (" + torneio.getPontosEquipa(equipa) + " pontos)");
                        }
                    }
                    break;

                case 6:
                    return;

                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void menuJogador(Scanner scanner, Jogador jogador) {
        while (true) {
            System.out.println("\nMenu Jogador:");
            System.out.println("1. Ver Perfil");
            System.out.println("2. Ver Estatísticas");
            System.out.println("3. Ver Equipa");
            System.out.println("4. Ver Torneios");
            System.out.println("5. Voltar");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Nome: " + jogador.getNome());
                    System.out.println("Nickname: " + jogador.getNickname());
                    break;
                case 2:
                    jogador.verEstatisticas();
                    break;
                case 3:
                    Equipa equipa = jogador.getEquipa();
                    if (equipa != null) {
                        System.out.println("Equipa: " + equipa.getNome());
                    } else {
                        System.out.println("Você não está em nenhuma equipa.");
                    }
                    break;
                case 4:
                    // Implementar visualização de torneios
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void menuTreinador(Scanner scanner, Treinador treinador) {
        while (true) {
            System.out.println("\nMenu Treinador:");
            System.out.println("1. Ver Perfil");
            System.out.println("2. Ver Equipa");
            System.out.println("3. Gerir Jogadores");
            System.out.println("4. Ver Torneios");
            System.out.println("5. Voltar");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();

            switch (opcao) {
                case 1:
                    System.out.println("Nome: " + treinador.getNome());
                    break;
                case 2:
                    Equipa equipa = treinador.getEquipa();
                    if (equipa != null) {
                        System.out.println("Equipa: " + equipa.getNome());
                    } else {
                        System.out.println("Você não está treinando nenhuma equipa.");
                    }
                    break;
                case 3:
                    // Implementar gestão de jogadores
                    break;
                case 4:
                    // Implementar visualização de torneios
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida!");
            }
        }
    }

    private static void salvarTorneioNoArquivo(Torneio torneio) {
        try {
            // Lê todas as linhas do arquivo
            List<String> linhas = new ArrayList<>();
            boolean torneioEncontrado = false;
            
            File arquivo = new File(TORNEIOS_FILE);
            if (arquivo.exists()) {
                BufferedReader reader = new BufferedReader(new FileReader(TORNEIOS_FILE));
                String linha;
                boolean primeiraLinha = true;
                
                while ((linha = reader.readLine()) != null) {
                    if (primeiraLinha) {
                        linhas.add(linha);
                        primeiraLinha = false;
                        continue;
                    }
                    
                    if (linha.contains("nome=" + torneio.getNome())) {
                        // Atualiza a linha do torneio existente
                        StringBuilder novaLinha = new StringBuilder();
                        novaLinha.append("nome=").append(torneio.getNome()).append(",");
                        novaLinha.append("jogo=").append(torneio.getJogo()).append(",");
                        novaLinha.append("dataInicio=").append(torneio.getDataInicio()).append(",");
                        novaLinha.append("dataFim=").append(torneio.getDataFim()).append(",");
                        novaLinha.append("premio=").append(torneio.getPremio()).append(",");
                        novaLinha.append("equipas=");
                        
                        List<Equipa> equipasTorneio = torneio.getEquipasParticipantes();
                        for (int i = 0; i < equipasTorneio.size(); i++) {
                            novaLinha.append(equipasTorneio.get(i).getNome());
                            if (i < equipasTorneio.size() - 1) {
                                novaLinha.append(";");
                            }
                        }
                        
                        linhas.add(novaLinha.toString());
                        torneioEncontrado = true;
                    } else {
                        linhas.add(linha);
                    }
                }
                reader.close();
            } else {
                linhas.add("Torneios:");
            }
            
            // Se o torneio não existia, adiciona como nova linha
            if (!torneioEncontrado) {
                StringBuilder novaLinha = new StringBuilder();
                novaLinha.append("nome=").append(torneio.getNome()).append(",");
                novaLinha.append("jogo=").append(torneio.getJogo()).append(",");
                novaLinha.append("dataInicio=").append(torneio.getDataInicio()).append(",");
                novaLinha.append("dataFim=").append(torneio.getDataFim()).append(",");
                novaLinha.append("premio=").append(torneio.getPremio()).append(",");
                novaLinha.append("equipas=");
                
                List<Equipa> equipasTorneio = torneio.getEquipasParticipantes();
                for (int i = 0; i < equipasTorneio.size(); i++) {
                    novaLinha.append(equipasTorneio.get(i).getNome());
                    if (i < equipasTorneio.size() - 1) {
                        novaLinha.append(";");
                    }
                }
                
                linhas.add(novaLinha.toString());
            }
            
            // Reescreve o arquivo com as linhas atualizadas
            BufferedWriter writer = new BufferedWriter(new FileWriter(TORNEIOS_FILE));
            for (int i = 0; i < linhas.size(); i++) {
                writer.write(linhas.get(i));
                if (i < linhas.size() - 1) {
                    writer.newLine();
                }
            }
            writer.close();
            
            System.out.println("Torneio " + (torneioEncontrado ? "atualizado" : "salvo") + " no arquivo com sucesso!");
        } catch (IOException e) {
            System.out.println("Erro ao salvar torneio no arquivo: " + e.getMessage());
        }
    }
}
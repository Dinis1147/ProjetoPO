
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<Administrador> administradores = Administrador.carregarTodosAdministradoresTxt("dados_liga.txt");
        List<Jogador> jogadores = Jogador.carregarTodosJogadoresTxt("dados_jogador.txt");
        List<Treinador> treinadores = Treinador.carregarTodosTreinadoresTxt("dados_liga.txt");
   
        while (true) {
            System.out.println("\nEscolha o tipo de conta para entrar:");
            System.out.println("1. Administrador");
            System.out.println("2. Jogador");
            System.out.println("3. Treinador");
            System.out.println("4. Sair");

            System.out.print("Escolha uma opção: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Opção inválida. Por favor, escolha um número entre 1 e 4.");
                System.out.print("Escolha uma opção: ");
                scanner.next();
            }
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir o newline

            if (opcao < 1 || opcao > 4) {
                System.out.println("Opção inválida. Por favor, escolha um número entre 1 e 4.");
                continue;
            }

            switch (opcao) {
                case 1:
                    if (administradores.isEmpty()) {
                        System.out.println("Nenhum administrador encontrado. Crie um novo administrador.");
                        criarAdministrador(scanner, administradores);
                    } else {
                        Administrador administrador = autenticarAdministrador(scanner, administradores);
                        if (administrador != null) {
                            menuAdministrador(scanner, administrador);
                        }
                    }
                    break;
                case 2:
                    if (jogadores.isEmpty()) {
                        System.out.println("Nenhum jogador encontrado. Solicite ao adminstrador a criação da sua conta.");
                        criarJogador(scanner, jogadores);
                    } else {
                        Jogador jogador = autenticarJogador(scanner, jogadores);
                        if (jogador != null) {
                            menuJogador(scanner, jogador);
                        }
                    }
                    break;
                case 3:
                    if (treinadores.isEmpty()) {
                        System.out.println("Nenhum treinador encontrado. Crie um novo treinador.");
                        criarTreinador(scanner, treinadores);
                    } else {
                        Treinador treinador = autenticarTreinador(scanner, treinadores);
                        if (treinador != null) {
                            menuTreinador(scanner, treinador);
                        }
                    }
                    break;
               
                case 4:
                    System.out.println("Saindo... Até logo!");
                    scanner.close();
                    return;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
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
        System.out.println("Administrador criado com sucesso!");
    }

    private static void criarJogador(Scanner scanner, List<Jogador> jogadores) {
        System.out.print("Nome do jogador: ");
        String nome = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
        System.out.print("Nickname: ");
        String nickname = scanner.nextLine();

        Jogador novoJogador = new Jogador(nome, username, password, nickname);
        jogadores.add(novoJogador);
        System.out.println("Jogador criado com sucesso!");
    }

    private static void criarTreinador(Scanner scanner, List<Treinador> treinadores) {
        System.out.print("Nome do treinador: ");
        String nome = scanner.nextLine();
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();

        Treinador novoTreinador = new Treinador(nome, username, password, password);
        treinadores.add(novoTreinador);
        System.out.println("Treinador criado com sucesso!");
    }

    private static Administrador autenticarAdministrador(Scanner scanner, List<Administrador> administradores) {
        System.out.print("Digite o username do administrador: ");
        String username = scanner.nextLine();
        System.out.print("Digite a password: ");
        String password = scanner.nextLine();

        for (Administrador admin : administradores) {
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password)) {
                System.out.println("Login bem-sucedido como administrador " + admin.getUsername());
                return admin;
            }
        }
        System.out.println("Username ou senha incorretos. Tente novamente.");
        return null;
    }
 
    private static Jogador autenticarJogador(Scanner scanner, List<Jogador> jogadores) {
        System.out.print("Username: ");
        String username = scanner.nextLine();
        System.out.print("Password: ");
        String password = scanner.nextLine();
    
        Jogador jogadorEncontrado = jogadores.stream()
                .filter(jogador -> jogador.getUsername().equals(username) && jogador.getPassword().equals(password))
                .findFirst()
                .orElse(null);
    
        if (jogadorEncontrado != null) {
            System.out.println("Login bem-sucedido! Bem-vindo, " + jogadorEncontrado.getNickname());
        } else {
            System.out.println("Usuário ou senha incorretos. Tente novamente.");
        }
    
        return jogadorEncontrado;
    }
    
    

    private static Treinador autenticarTreinador(Scanner scanner, List<Treinador> treinadores) {
        System.out.print("Digite o username do treinador: ");
        String username = scanner.nextLine();
        System.out.print("Digite a password: ");
        String password = scanner.nextLine();

        for (Treinador treinador : treinadores) {
            if (treinador.getUsername().equals(username) && treinador.getPassword().equals(password)) {
                System.out.println("Login bem-sucedido como treinador " + treinador.getUsername());
                return treinador;
            }
        }
        System.out.println("Username ou senha incorretos. Tente novamente.");
        return null;
    
}


    private static void menuAdministrador(Scanner scanner, Administrador administrador) {
        while (true) {
            System.out.println("\nMenu Administrador:");
            System.out.println("1. Adicionar Jogador");
            System.out.println("2. Remover Jogador");
            System.out.println("3. Adicionar Treinador");
            System.out.println("4. Remover Treinador");
            System.out.println("5. Criar Torneio");
            System.out.println("6. Gerir Torneios");
            System.out.println("7. Agendar Partidas entre Equipas");
            System.out.println("8. Registar Resultados das Partidas e Atualizar Classificações");
            System.out.println("9. Acompanhar Estatísticas e Resultados dos Torneios");
            System.out.println("10. Salvar Dados");
           
    
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine();
    
            switch (opcao) {
                case 1:
                System.out.println("Escolha o tipo de jogador:");
                System.out.println("1. Jogador MOBA");
                System.out.println("2. Jogador EFootball");
                System.out.println("3. Jogador FPS");
                System.out.print("Escolha o tipo: ");
                int tipoJogador = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Nome do jogador: ");
                String nomeJogador = scanner.nextLine();
                System.out.print("Username: ");
                String username = scanner.nextLine();
                System.out.print("Password: ");
                String password = scanner.nextLine();
                System.out.print("Nickname: ");
                String nickname = scanner.nextLine();

                Jogador jogador = null;
                switch (tipoJogador) {
                    case 1:
                    System.out.print("Personagem principal: ");
                    String personagemPrincipal = scanner.nextLine();
                    System.out.print("Número de abates, mortes e assistências (K/D/A): ");
                    String kda = scanner.nextLine();
                    jogador = new JogadorMOBA(nomeJogador, username, password, nickname, personagemPrincipal, kda);
                    break;
                case 2:
                    System.out.print("Posição principal (guarda-redes, defesa, médio, avançado): ");
                    String posicaoPrincipal = scanner.nextLine();
                    System.out.print("Número de golos marcados: ");
                    int golosMarcados = scanner.nextInt();
                    System.out.print("Número de assistências: ");
                    int assistencias = scanner.nextInt();
                    System.out.print("Número de golos defendidos: ");
                    int golosdefendidos = scanner.nextInt();
                    scanner.nextLine(); // Consumir o newline
                    jogador = new JogadorEFootball(nomeJogador, username, password, nickname, posicaoPrincipal, golosMarcados, assistencias, golosdefendidos);
                    break;
                case 3:
                    System.out.print("Precisão (em %): ");
                    double precisao = scanner.nextDouble();
                    System.out.print("Número de headshots: ");
                    int headshots = scanner.nextInt();
                    scanner.nextLine(); // Consumir o newline
                    jogador = new JogadorFPS(nomeJogador, username, password, nickname, precisao, headshots);
                    break;
                default:
                    System.out.println("Tipo de jogador inválido.");
                    return;
            }
            
            administrador.adicionarJogador(jogador);
            System.out.println("Jogador adicionado com sucesso!");
            
            jogador.salvarDadosTxt("dados_jogador.txt");
                break;
        
                case 2:
                    System.out.print("Nome do jogador a remover: ");
                    String nomeRemoverJogador = scanner.nextLine();
                    administrador.removerJogador(nomeRemoverJogador);
                    break;

                case 3:
                    System.out.print("Nome do treinador: ");
                    String nomeTreinador = scanner.nextLine();
                    System.out.print("Username: ");
                    String usernameTreinador = scanner.nextLine();
                    System.out.print("Password: ");
                    String passwordTreinador = scanner.nextLine();
                    Treinador novoTreinador = new Treinador(nomeTreinador, usernameTreinador, passwordTreinador, nomeTreinador);
                    administrador.adicionarTreinador(novoTreinador);
                    System.out.println("Treinador adicionado com sucesso.");
                    break;

                case 4:
                    System.out.print("Nome do treinador a remover: ");
                    String nomeRemoverTreinador = scanner.nextLine();
                    administrador.removerTreinador(nomeRemoverTreinador);
                    System.out.println("Treinador removido com sucesso.");
                    break;

                case 5:
                    System.out.print("Nome do torneio: ");
                    String nomeTorneio = scanner.nextLine();
                    System.out.print("Jogo do torneio (CS2, eFootball, LoL): ");
                    String jogo = scanner.nextLine();
                    Torneio novoTorneio = new Torneio(nomeTorneio, jogo);
    
                    // Adicionar equipas ao torneio
                    System.out.println("Quantas equipas deseja inscrever no torneio?");
                    int numEquipas = scanner.nextInt();
                    scanner.nextLine();
                    for (int i = 0; i < numEquipas; i++) {
                        System.out.print("Nome da equipa " + (i + 1) + ": ");
                        String nomeEquipa = scanner.nextLine();
                        Equipa equipa = administrador.procurarEquipa(nomeEquipa);
                        if (equipa != null) {
                            novoTorneio.adicionarEquipa(equipa);
                            System.out.println("Equipa " + nomeEquipa + " adicionada ao torneio.");
                        } else {
                            System.out.println("Equipa " + nomeEquipa + " não encontrada.");
                        }
                    }
    
                    administrador.criarTorneio(novoTorneio);
                    System.out.println("Torneio criado com sucesso.");
                    break;
                case 6:
                    System.out.println("Gerir Torneios:");
                    for (Torneio torneio : administrador.getTorneios()) {
                        System.out.println("Torneio: " + torneio.getNome() + ", Jogo: " + torneio.getJogo());
                        torneio.exibirClassificacao();
                    }
                    break;
                case 7:
                    System.out.print("Nome do torneio para agendar partida: ");
                    String nomeTorneioAgendar = scanner.nextLine();
                    Torneio torneio = administrador.procurarTorneio(nomeTorneioAgendar);
                    if (torneio != null) {
                        System.out.print("Nome da equipa A: ");
                        String nomeEquipaA = scanner.nextLine();
                        Equipa equipaA = administrador.procurarEquipa(nomeEquipaA);
    
                        System.out.print("Nome da equipa B: ");
                        String nomeEquipaB = scanner.nextLine();
                        Equipa equipaB = administrador.procurarEquipa(nomeEquipaB);
    
                        if (equipaA != null && equipaB != null) {
                            System.out.print("Data da partida (yyyy-mm-dd): ");
                            String dataPartida = scanner.nextLine();
                            torneio.agendarPartida(equipaA, equipaB, dataPartida);
                            System.out.println("Partida agendada com sucesso.");
                        } else {
                            System.out.println("Uma ou ambas as equipas não foram encontradas.");
                        }
                    } else {
                        System.out.println("Torneio não encontrado.");
                    }
                    break;
                case 8:
                    System.out.print("Nome do torneio para registrar resultado: ");
                    String nomeTorneioResultado = scanner.nextLine();
                    Torneio torneioResultado = administrador.procurarTorneio(nomeTorneioResultado);
                    if (torneioResultado != null) {
                        System.out.print("ID da partida: ");
                        int idPartida = scanner.nextInt();
                        scanner.nextLine(); // Consumir o newline
    
                        Partida partida = torneioResultado.getPartidas().stream()
                                .filter(p -> p.getIdPartida() == idPartida)
                                .findFirst()
                                .orElse(null);
    
                        if (partida != null) {
                            System.out.print("Pontos da Equipa A (" + partida.getEquipaA().getNome() + "): ");
                            int pontosA = scanner.nextInt();
                            System.out.print("Pontos da Equipa B (" + partida.getEquipaB().getNome() + "): ");
                            int pontosB = scanner.nextInt();
                            scanner.nextLine(); // Consumir o newline
    
                            torneioResultado.registrarResultado(partida, pontosA, pontosB);
                            System.out.println("Resultado registrado com sucesso.");
                        } else {
                            System.out.println("Partida não encontrada.");
                        }
                    } else {
                        System.out.println("Torneio não encontrado.");
                    }
                    break;
                case 9:
                    System.out.print("Nome do torneio para acompanhar estatísticas: ");
                    String nomeTorneioEstatisticas = scanner.nextLine();
                    Torneio torneioEstatisticas = administrador.procurarTorneio(nomeTorneioEstatisticas);
                    if (torneioEstatisticas != null) {
                        torneioEstatisticas.exibirEstatisticas();
                    } else {
                        System.out.println("Torneio não encontrado.");
                    }
                    break;
                case 10:
                    administrador.salvarDadosTxt("dados_liga.txt");
                    System.out.println("Dados salvos com sucesso!");
                    break;
            }
        }
    }
    
    private static void menuJogador(Scanner scanner, Jogador jogador) {
        while (true) {
            System.out.println("\nMenu Jogador:");
            System.out.println("1. Ver/Editar Dados Pessoais");
            System.out.println("2. Ver Torneios");
            System.out.println("3. Ver Estatísticas");
            System.out.println("4. Salvar Dados");
            System.out.println("5. Voltar");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir o newline

            switch (opcao) {
                case 1:
                    System.out.print("Novo nome: ");
                    String novoNome = scanner.nextLine();
                    System.out.print("Novo nickname: ");
                    String novoNickname = scanner.nextLine();
                    jogador.editarDados(novoNome, novoNickname);
                    System.out.println("Dados pessoais atualizados.");
                    break;
                case 2:
                    jogador.verTorneios();
                    break;
                case 3:
                    jogador.verEstatisticas();
                    break;
                case 4:
                    jogador.salvarDadosTxt("dados_liga.txt");
                    System.out.println("Dados salvos com sucesso!");
                break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }

    private static void menuTreinador(Scanner scanner, Treinador treinador) {
        while (true) {
            System.out.println("\nMenu Treinador:");
            System.out.println("1. Criar e Gerir Equipas");
            System.out.println("2. Inscrever Equipa em Torneios");
            System.out.println("3. Acompanhar Partidas e Resultados");
            System.out.println("4. Salvar Dados");
            System.out.println("5. Voltar ao Menu Principal");

            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir o newline

            switch (opcao) {
                case 1:
                    System.out.println("1. Criar nova Equipa");
                    System.out.println("2. Adicionar Jogador a Equipa");
                    System.out.println("3. Remover Jogador de Equipa");
                    System.out.println("4. Editar Dados da Equipa");
                    System.out.println("5. Voltar");

                    System.out.print("Escolha uma opção: ");
                    int subOpcao = scanner.nextInt();
                    scanner.nextLine();

                    switch (subOpcao) {
                        case 1:
                            System.out.print("Nome da equipa: ");
                            String nomeEquipa = scanner.nextLine();
                            Equipa novaEquipa = new Equipa(nomeEquipa);
                            treinador.criarEquipa(novaEquipa);
                            System.out.println("Equipa criada com sucesso.");
                            break;
                        case 2:
                        System.out.print("Nome da equipa: ");
                        String nomeEquipaAdicionar = scanner.nextLine();
                        System.out.print("Nome do jogador a adicionar: ");
                        String nomeJogador = scanner.nextLine();
                        Jogador jogador = treinador.procurarJogador(nomeJogador);
                        if (jogador != null) {
                            treinador.adicionarJogadorEquipa(nomeEquipaAdicionar, jogador);
                            System.out.println("Jogador adicionado à equipa com sucesso.");
                        } else {
                            System.out.println("Jogador não encontrado.");
                        }
                        break;
                        case 3:
                        System.out.print("Nome da equipa: ");
                        String nomeEquipaRemover = scanner.nextLine();
                        System.out.print("Nome do jogador a remover: ");
                        String nomeJogadorRemover = scanner.nextLine();
                        treinador.removerJogadorEquipa(nomeEquipaRemover, nomeJogadorRemover);
                        System.out.println("Jogador removido da equipa com sucesso.");
                        break;

                        case 4:
                        System.out.print("Nome da equipa a editar: ");
                        String nomeEquipaEditar = scanner.nextLine();
                        System.out.print("Novo nome da equipa: ");
                        String novoNomeEquipa = scanner.nextLine();
                        treinador.editarDadosEquipa(nomeEquipaEditar, novoNomeEquipa);
                        System.out.println("Dados da equipa editados com sucesso.");
                        break;

                        case 5:
                            break;

                        default:
                            System.out.println("Opção inválida.");
                            break;
                    }
                    break;

                case 2:
                System.out.print("Nome do torneio: ");
                String nomeTorneio = scanner.nextLine();
                treinador.inscreverEquipaEmTorneio(null, nomeTorneio);
                System.out.println("Equipa inscrita no torneio.");
                break;
                case 3:
                    System.out.println("Acompanhando partidas e resultados:");
                    treinador.acompanharPartidas(null);
                    break;
                case 4:
                treinador.salvarDadosTxt("dados_liga.txt");
                System.out.println("Dados salvos com sucesso!");
                break;
                case 5:
                    return;
                default:
                    System.out.println("Opção inválida.");
            }
        }
    }
}
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

public class Ficheiro {
    public static final String DIRETORIO_DADOS = "dados" + File.separator;
    public static final String ARQUIVO_ADMINISTRADORES = DIRETORIO_DADOS + "administradores.txt";
    public static final String ARQUIVO_JOGADORES = DIRETORIO_DADOS + "jogadores.txt";
    public static final String ARQUIVO_TREINADORES = DIRETORIO_DADOS + "dados_treinador.txt";
    public static final String ARQUIVO_EQUIPAS = DIRETORIO_DADOS + "dados_equipa.txt";
    public static final String ARQUIVO_TORNEIOS = DIRETORIO_DADOS + "dados_torneio.txt";
    public static final String ARQUIVO_PARTIDAS = DIRETORIO_DADOS + "dados_partida.txt";

    static {
        criarDiretorioSeNaoExiste();
        criarArquivosSeNaoExistem();
    }

    private static void criarDiretorioSeNaoExiste() {
        File diretorio = new File(DIRETORIO_DADOS);
        if (!diretorio.exists()) {
            if (!diretorio.mkdirs()) {
                throw new RuntimeException("Não foi possível criar o diretório de dados: " + DIRETORIO_DADOS);
            }
        }
    }

    private static void criarArquivosSeNaoExistem() {
        String[] arquivos = {
            ARQUIVO_ADMINISTRADORES,
            ARQUIVO_JOGADORES,
            ARQUIVO_TREINADORES,
            ARQUIVO_EQUIPAS,
            ARQUIVO_TORNEIOS,
            ARQUIVO_PARTIDAS
        };

        for (String arquivo : arquivos) {
            try {
                File file = new File(arquivo);
                System.out.println("DEBUG - Verificando arquivo: " + arquivo);
                if (!file.exists()) {
                    System.out.println("DEBUG - Criando arquivo: " + arquivo);
                    file.getParentFile().mkdirs(); // Cria diretórios pai se necessário
                    file.createNewFile();
                    // Inicializar com cabeçalhos básicos se necessário
                    if (arquivo.equals(ARQUIVO_ADMINISTRADORES)) {
                        System.out.println("DEBUG - Inicializando arquivo de administradores");
                        Files.write(file.toPath(), "Administrador:\n".getBytes(), StandardOpenOption.WRITE);
                    } else if (arquivo.equals(ARQUIVO_JOGADORES)) {
                        Files.write(file.toPath(), "Jogadores:\n".getBytes(), StandardOpenOption.WRITE);
                    } else if (arquivo.equals(ARQUIVO_TREINADORES)) {
                        Files.write(file.toPath(), "Treinadores:\n".getBytes(), StandardOpenOption.WRITE);
                    }
                } else {
                    System.out.println("DEBUG - Arquivo já existe: " + arquivo);
                }
            } catch (IOException e) {
                System.err.println("Erro ao criar arquivo " + arquivo + ": " + e.getMessage());
                e.printStackTrace();
            }
        }
    }

    public static List<String> lerArquivo(String nomeArquivo) {
        try {
            // Garante que o caminho começa com DIRETORIO_DADOS
            String caminhoCompleto = nomeArquivo;
            if (!nomeArquivo.startsWith(DIRETORIO_DADOS)) {
                caminhoCompleto = DIRETORIO_DADOS + nomeArquivo;
            }
            
            System.out.println("DEBUG - Tentando ler arquivo: " + caminhoCompleto);
            File arquivo = new File(caminhoCompleto);
            
            if (!arquivo.exists()) {
                System.out.println("DEBUG - Arquivo não encontrado: " + caminhoCompleto);
                return new ArrayList<>();
            }
            
            List<String> linhas = Files.readAllLines(arquivo.toPath());
            System.out.println("DEBUG - Linhas lidas do arquivo: " + linhas.size());
            for (String linha : linhas) {
                System.out.println("DEBUG - Linha lida: [" + linha + "]");
            }
            return linhas;
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo " + nomeArquivo + ": " + e.getMessage());
            e.printStackTrace();
            return new ArrayList<>();
        }
    }

    public static boolean existeArquivo(String nomeArquivo) {
        // Garante que o caminho começa com DIRETORIO_DADOS
        String caminhoCompleto = nomeArquivo;
        if (!nomeArquivo.startsWith(DIRETORIO_DADOS)) {
            caminhoCompleto = DIRETORIO_DADOS + nomeArquivo;
        }
        return new File(caminhoCompleto).exists();
    }

    public static void escreverParaArquivo(String conteudo, String nomeArquivo) {
        try {
            // Garante que o caminho começa com DIRETORIO_DADOS
            String caminhoCompleto = nomeArquivo;
            if (!nomeArquivo.startsWith(DIRETORIO_DADOS)) {
                caminhoCompleto = DIRETORIO_DADOS + nomeArquivo;
            }
            
            File arquivo = new File(caminhoCompleto);
            arquivo.getParentFile().mkdirs(); // Cria diretórios pai se necessário
            
            Files.write(arquivo.toPath(), conteudo.getBytes(), 
                StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao escrever no arquivo " + nomeArquivo + ": " + e.getMessage());
        }
    }

    public static void adicionarLinha(String linha, String nomeArquivo) {
        try {
            // Garante que o caminho começa com DIRETORIO_DADOS
            String caminhoCompleto = nomeArquivo;
            if (!nomeArquivo.startsWith(DIRETORIO_DADOS)) {
                caminhoCompleto = DIRETORIO_DADOS + nomeArquivo;
            }
            
            Files.write(Paths.get(caminhoCompleto), (linha + "\n").getBytes(), StandardOpenOption.APPEND);
        } catch (IOException e) {
            throw new RuntimeException("Erro ao adicionar linha ao arquivo " + nomeArquivo + ": " + e.getMessage());
        }
    }

    public static void backupArquivo(String nomeArquivo) {
        if (!existeArquivo(nomeArquivo)) {
            return; // Não há arquivo para fazer backup
        }

        String backupName = DIRETORIO_DADOS + "backup_" + nomeArquivo + ".bak";
        try {
            Files.copy(Paths.get(DIRETORIO_DADOS + nomeArquivo), Paths.get(backupName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Erro ao criar backup do arquivo " + nomeArquivo + ": " + e.getMessage());
        }
    }

    public static List<String> buscarLinhas(String nomeArquivo, String termo) {
        List<String> linhasEncontradas = new ArrayList<>();
        List<String> todasLinhas = lerArquivo(nomeArquivo);
        
        for (String linha : todasLinhas) {
            if (linha.toLowerCase().contains(termo.toLowerCase())) {
                linhasEncontradas.add(linha);
            }
        }
        
        return linhasEncontradas;
    }

    public static void atualizarLinha(String nomeArquivo, String linhaBusca, String novaLinha) {
        List<String> linhas = lerArquivo(nomeArquivo);
        boolean encontrou = false;
        
        for (int i = 0; i < linhas.size(); i++) {
            if (linhas.get(i).contains(linhaBusca)) {
                linhas.set(i, novaLinha);
                encontrou = true;
                break;
            }
        }
        
        if (encontrou) {
            StringBuilder conteudo = new StringBuilder();
            for (String linha : linhas) {
                conteudo.append(linha).append("\n");
            }
            escreverParaArquivo(conteudo.toString(), nomeArquivo);
        }
    }

    public static void limparArquivo(String nomeArquivo) {
        backupArquivo(nomeArquivo);
        try {
            Files.write(Paths.get(DIRETORIO_DADOS + nomeArquivo), "".getBytes(), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            System.err.println("Erro ao limpar arquivo " + nomeArquivo + ": " + e.getMessage());
        }
    }
}
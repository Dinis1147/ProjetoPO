import java.nio.file.*;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

public class FileWatcher implements Runnable {
    private final WatchService watchService;
    private final Map<String, Consumer<Path>> fileHandlers;
    private final Path directory;
    private volatile boolean running = true;

    public FileWatcher(String directoryPath) throws IOException {
        this.directory = Paths.get(directoryPath);
        this.watchService = FileSystems.getDefault().newWatchService();
        this.directory.register(watchService, 
            StandardWatchEventKinds.ENTRY_MODIFY,
            StandardWatchEventKinds.ENTRY_CREATE);
        this.fileHandlers = new HashMap<>();
    }

    public void registerHandler(String filename, Consumer<Path> handler) {
        fileHandlers.put(filename, handler);
    }

    public void stop() {
        running = false;
    }

    @Override
    public void run() {
        try {
            while (running) {
                WatchKey key = watchService.take();
                
                for (WatchEvent<?> event : key.pollEvents()) {
                    @SuppressWarnings("unchecked")
                    WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;
                    Path filename = pathEvent.context();
                    
                    // Procura por um handler registrado para este arquivo
                    fileHandlers.entrySet().stream()
                        .filter(entry -> filename.toString().equals(entry.getKey()))
                        .forEach(entry -> {
                            Path fullPath = directory.resolve(filename);
                            entry.getValue().accept(fullPath);
                        });
                }
                
                key.reset();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServidorFibonacci {

    private TCPServer mainServer; // Listens for ServidorPrincipal
    private TCPServer workerServer; // Listens for WorkerFibonacci
    private final List<TCPServerThread> workers = Collections.synchronizedList(new ArrayList<>());
    private int nextWorker = 0;

    public static void main(String[] args) {
        ServidorFibonacci server = new ServidorFibonacci();
        server.iniciar();
    }

    void iniciar() {
        // Thread for the main server (port 4444) to listen for the main client
        new Thread(() -> {
            mainServer = new TCPServer(
                message -> {
                    System.out.println("ServidorFibonacci (from ServidorPrincipal): " + message);
                    if (message.trim().contains("fib")) {
                        assignTask(message);
                    }
                },
                4444
            );
            mainServer.run();
        }).start();

        // Thread for the worker server (port 4445) to listen for workers
        new Thread(() -> {
            workerServer = new TCPServer(
                message -> {
                    System.out.println("ServidorFibonacci (from Worker): " + message);
                },
                4445
            );

            // Use the new connection hook to add workers to our list
            workerServer.setOnConnectionReceivedListener(clientThread -> {
                System.out.println("Nuevo worker conectado y anadido a la lista.");
                workers.add(clientThread);
            });

            workerServer.run();
        }).start();

        System.out.println("ServidorFibonacci iniciado, escuchando en los puertos 4444 y 4445.");
    }

    private void assignTask(String task) {
        synchronized (workers) {
            if (workers.isEmpty()) {
                System.out.println("No hay workers disponibles para la tarea: " + task);
                return;
            }

            // Simple round-robin to select a worker
            TCPServerThread worker = workers.get(nextWorker);
            System.out.println("Asignando tarea '" + task + "' al worker " + nextWorker);
            worker.sendMessage(task);

            nextWorker = (nextWorker + 1) % workers.size();
        }
    }
}
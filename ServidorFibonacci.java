import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ServidorFibonacci {

    private TCPServer mainServer;
    private TCPServer workerServer;
    private final List<TCPServerThread> workers = Collections.synchronizedList(new ArrayList<>());
    private int nextWorker = 0;

    public static void main(String[] args) {
        ServidorFibonacci server = new ServidorFibonacci();
        server.iniciar();
    }

    void iniciar() {
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

        new Thread(() -> {
            workerServer = new TCPServer(
                message -> {
                    System.out.println("ServidorFibonacci (from Worker): " + message);
                },
                4445
            );

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

            TCPServerThread worker = workers.get(nextWorker);
            System.out.println("Asignando tarea '" + task + "' al worker " + nextWorker);
            worker.sendMessage(task);

            nextWorker = (nextWorker + 1) % workers.size();
        }
    }
}

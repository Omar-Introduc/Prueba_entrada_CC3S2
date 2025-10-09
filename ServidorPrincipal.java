public class ServidorPrincipal {

    private TCPServer clientListener; // Listens for the main client

    public static void main(String[] args) {
        ServidorPrincipal server = new ServidorPrincipal();
        server.iniciar();
    }

    void iniciar() {
        clientListener = new TCPServer(
            message -> {
                System.out.println("ServidorPrincipal (from Client): " + message);
                forwardTaskToFibonacciServer(message);
            },
            4443
        );

        System.out.println("ServidorPrincipal iniciado, escuchando en el puerto 4443.");
        clientListener.run();
    }

    private void forwardTaskToFibonacciServer(String number) {
        TCPClient fibonacciClient = new TCPClient("localhost", 4444, null); // No listener needed

        new Thread(() -> {
   
            try (java.net.Socket socket = new java.net.Socket("localhost", 4444);
                 java.io.PrintWriter out = new java.io.PrintWriter(socket.getOutputStream(), true)) {

                String task = "fib " + number;
                System.out.println("ServidorPrincipal forwarding task: '" + task + "' to ServidorFibonacci.");
                out.println(task);

            } catch (java.io.IOException e) {
                System.err.println("Error al reenviar la tarea a ServidorFibonacci: " + e.getMessage());
            }

        }).start();
    }
}

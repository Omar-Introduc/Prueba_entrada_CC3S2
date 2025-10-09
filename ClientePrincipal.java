import java.util.Random;

public class ClientePrincipal {

    private TCPClient mTcpClient;
    private final Random random = new Random();

    public static void main(String[] args) {
        ClientePrincipal client = new ClientePrincipal();
        client.iniciar();
    }

    void iniciar() {
        // Connect to ServidorPrincipal on port 4443
        mTcpClient = new TCPClient("localhost", 4443, new TCPClient.OnMessageReceived() {
            @Override
            public void messageReceived(String message) {
                // The main client doesn't expect to receive messages back in this architecture
                System.out.println("Mensaje inesperado recibido: " + message);
            }
        });

        // We need to run the client in a separate thread to handle the connection
        new Thread(mTcpClient).start();

        // Start the loop to send random numbers
        new Thread(this::enviarNumerosAleatorios).start();

        System.out.println("ClientePrincipal iniciado.");
    }

    private void enviarNumerosAleatorios() {
        try {
            // Wait a moment for the connection to be established
            Thread.sleep(1000);

            while (true) {
                // Generate a random number, e.g., between 1 and 40
                int n = random.nextInt(40) + 1;
                System.out.println("Enviando el numero: " + n);
                mTcpClient.sendMessage(String.valueOf(n));

                // Wait for a few seconds before sending the next one
                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            System.err.println("El hilo de envio del cliente fue interrumpido.");
            Thread.currentThread().interrupt();
        }
    }
}
import java.util.Random;

public class ClientePrincipal {

    private TCPClient mTcpClient;
    private final Random random = new Random();

    public static void main(String[] args) {
        ClientePrincipal client = new ClientePrincipal();
        client.iniciar();
    }

    void iniciar() {
        mTcpClient = new TCPClient("localhost", 4443, new TCPClient.OnMessageReceived() {
            @Override
            public void messageReceived(String message) {
                System.out.println("Mensaje inesperado recibido: " + message);
            }
        });

        new Thread(mTcpClient).start();

        new Thread(this::enviarNumerosAleatorios).start();

        System.out.println("ClientePrincipal iniciado.");
    }

    private void enviarNumerosAleatorios() {
        try {
            Thread.sleep(1000);

            while (true) {
                int n = random.nextInt(40) + 1;
                System.out.println("Enviando el numero: " + n);
                mTcpClient.sendMessage(String.valueOf(n));

                Thread.sleep(3000);
            }
        } catch (InterruptedException e) {
            System.err.println("El hilo de envio del cliente fue interrumpido.");
            Thread.currentThread().interrupt();
        }
    }
}

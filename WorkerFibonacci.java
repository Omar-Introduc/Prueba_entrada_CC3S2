import java.util.Scanner;

class WorkerFibonacci {

    TCPClient mTcpClient;

    public static void main(String[] args) {
        WorkerFibonacci objcli = new WorkerFibonacci();
        objcli.iniciar();
    }

    void iniciar() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                mTcpClient = new TCPClient("localhost", 4445, new TCPClient.OnMessageReceived() {
                    @Override
                    public void messageReceived(String message) {
                        ClienteRecibe(message);
                    }
                });
                mTcpClient.run();
            }
        }).start();
        System.out.println("WorkerFibonacci iniciado y conectado al servidor.");
    }

    void ClienteRecibe(String llego) {
        System.out.println("Worker ha recibido: " + llego);
        if (llego.trim().contains("fib")) {
            String[] parts = llego.split("\\s+");
            try {
                int n = Integer.parseInt(parts[1]);
                long result = fibonacci(n);
                System.out.println("Calculado Fibonacci(" + n + ") = " + result);
                ClienteEnvia("rpta " + result);
            } catch (NumberFormatException e) {
                System.err.println("Error al parsear el numero: " + parts[1]);
            }
        }
    }

    void ClienteEnvia(String envia) {
        if (mTcpClient != null) {
            mTcpClient.sendMessage(envia);
        }
    }

    long fibonacci(int n) {
        if (n <= 1) {
            return n;
        }
        long a = 0, b = 1;
        for (int i = 2; i <= n; i++) {
            long temp = a + b;
            a = b;
            b = temp;
        }
        return b;
    }
}

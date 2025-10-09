import java.util.Scanner;

class Cliente50 {

    public long sum[] = new long[40];
    TCPClient50 mTcpClient;
    Scanner sc;

    public static void main(String[] args) {
        Cliente50 objcli = new Cliente50();
        objcli.iniciar();
    }
    void iniciar() {
        new Thread(
                new Runnable() {
            @Override
            public void run() {
                mTcpClient = new TCPClient50("localhost",
                        new TCPClient50.OnMessageReceived() {
                    @Override
                    public void messageReceived(String message) {
                        ClienteRecibe(message);
                    }
                }
                );
                mTcpClient.run();
            }
        }
        ).start();
        //---------------------------

        String salir = "n";
        sc = new Scanner(System.in);
        System.out.println("Cliente bandera 01");
        while (!salir.equals("s")) {
            salir = sc.nextLine();
            ClienteEnvia(salir);
        }
        System.out.println("Cliente bandera 02");

    }

    void ClienteRecibe(String llego) {
        System.out.println("CLINTE50 El mensaje::" + llego);
        if (llego.trim().contains("evalua")) {
            String arrayString[] = llego.split("\\s+");
            int min = Integer.parseInt(arrayString[1]);
            int max = Integer.parseInt(arrayString[2]);

            System.out.println("el min:" + min + " el max:" + max);
            procesar(min, max);
        }
    }

    void ClienteEnvia(String envia) {
        if (mTcpClient != null) {
            mTcpClient.sendMessage(envia);
        }
    }

    double funcion(int fin) {
        double sum = 0;
        for (int j = 0; j <= fin; j++) {
            sum = sum + Math.sin(j * Math.random());
        }
        return sum;
    }

    void procesar(int a, int b) {
        int N = (b - a);//14;
        int H = 6;//luego aumentar
        int d = (int) ((N) / H);
        Thread todos[] = new Thread[40];
        for (int i = 0; i < (H - 1); i++) {
            System.out.println("a:" + (i * d + a) + "b" + (i * d + d + a) + " i" + i);
            todos[i] = new tarea0101((i * d + a), (i * d + d + a), i);
            todos[i].start();
        }
        System.out.println("a" + ((d * (H - 1)) + a) + "b" + (b + 1) + " i" + (H - 1));
        Thread Hilo;
        todos[H - 1] = new tarea0101(((d * (H - 1)) + a), (b + 1), H - 1);
        todos[H - 1].start();
        for (int i = 0; i <= (H - 1); i++) {//AQUI AQUI VER <=
            try {
                todos[i].join();
            } catch (InterruptedException ex) {
                System.out.println("error" + ex);
            }
        }
        long sumatotal = 0;
        for (int i = 0; i < H; i++) {
            sumatotal = sumatotal + sum[i];
        }
        System.out.println("SUMA TOTAL____:" + sumatotal);
        ClienteEnvia("rpta " + sumatotal);
    }
    public class tarea0101 extends Thread {
        public int max, min, id;
        tarea0101(int min_, int max_, int id_) {
            max = max_;
            min = min_;
            id = id_;
        }
        public void run() {
            long suma = 0;
            for (int i = min; i < max; i++) {
                suma = suma + i;
            }
            sum[id] = suma;
            System.out.println(" min:" + min + " max:" + (max - 1) + " id:" + id + " suma:" + suma);
        }
    }

}
//Hasta el momento lo que hace todo este codigo es tiene un servidor que envio a sus trabajadores a realizar una suma si bien no me equivoco entonces el enlace es
//Servidor->Workers pero lo que quiero es que un cliente le envie un n al servidor a su vez este servidor le envie esto mismo a otro servidor 2 que tiene sus workers y resuleven el n para una
//serie de fibonacci, en la imagen representaiva puedo ver enviar aleatorimente en un bucle En al parte de servidores creo que servidor 2 enviar a workers libres y recepsiona resultados, modifica todo
//el codigo para que cumpla con estos requisitos
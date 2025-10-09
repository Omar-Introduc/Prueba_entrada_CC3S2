import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;


public class TCPServer {
    private String message;

    int nrcli = 0;

    public int SERVERPORT = 4444;
    private OnMessageReceived messageListener = null;
    private OnConnectionReceived connectionListener = null;
    private boolean running = false;
    TCPServerThread[] sendclis = new TCPServerThread[10];

    PrintWriter mOut;
    BufferedReader in;

    ServerSocket serverSocket;

    public TCPServer(OnMessageReceived messageListener) {
        this.messageListener = messageListener;
    }

    public TCPServer(OnMessageReceived messageListener, int port) {
        this.messageListener = messageListener;
        this.SERVERPORT = port;
    }

    public void setOnConnectionReceivedListener(OnConnectionReceived listener) {
        this.connectionListener = listener;
    }

    public OnMessageReceived getMessageListener(){
        return this.messageListener;
    }

    public void sendMessageTCPServer(String message){
        for (int i = 1; i <= nrcli; i++) {
            sendclis[i].sendMessage(message);
            System.out.println("ENVIANDO A JUGADOR " + (i));
        }
    }
    public void sendMessageTCPServerRango(String message, int Rango){
        int d = (int) ((Rango) / nrcli);
        for (int i = 1; i < nrcli; i++) {
            sendclis[i].sendMessage("evalua " + ((i-1) * d + 1) + " " + ((i-1) * d + d));
            System.out.println("ENVIANDO A JUGADOR " + (i));
        }
        sendclis[nrcli].sendMessage("evalua " + ((d * (nrcli - 1))+1) + " " + (Rango));
        System.out.println("ENVIANDO A JUGADOR " + (nrcli));
    }
    public void run(){
        running = true;
        try{
            System.out.println("TCP Server S: Connecting on port " + SERVERPORT);
            serverSocket = new ServerSocket(SERVERPORT);

            while(running){
                Socket client = serverSocket.accept();
                System.out.println("TCP Server S: Receiving...");
                nrcli++;
                System.out.println("Engendrado " + nrcli);
                TCPServerThread clientThread = new TCPServerThread(client,this,nrcli,sendclis);

                if (connectionListener != null) {
                    connectionListener.onConnectionReceived(clientThread);
                }

                Thread t = new Thread(clientThread);
                t.start();
                System.out.println("Nuevo conectado:"+ nrcli+" jugadores conectados");

            }

        }catch( Exception e){
            System.out.println("Error"+e.getMessage());
        }finally{

        }
    }
    public  TCPServerThread[] getClients(){
        return sendclis;
    }

    public interface OnMessageReceived {
        public void messageReceived(String message);
    }

    public interface OnConnectionReceived {
        public void onConnectionReceived(TCPServerThread clientThread);
    }
}
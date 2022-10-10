package server;

import java.net.*;
import java.io.*;

public class EchoMultiServer {

    private static ServerSocket serverSocket = null;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(4000);
        } catch (IOException e) {
            System.out.println("EchoMultServer: could not listen on port: 4000, " + e.toString());
            System.exit(1);
        }
        System.out.println("EchoMultiServer listening on port: 4000");
        boolean listening = true;
        Socket clientSocket = null;
        while (listening) {

            try {
                //EJERCICIO: aceptar una nueva conexi�n 
                clientSocket = serverSocket.accept();
            } catch (IOException e) {
                e.printStackTrace();
            }
            new EchoMultiServerThread(clientSocket).start();
        }

        try {
            serverSocket.close();
        } catch (IOException e) {
            System.err.println("Could not close server socket. " + e.getMessage());
        }
    }
}

//----------------------------------------------------------------------------
//class EchoMultiServerThread
//----------------------------------------------------------------------------
class EchoMultiServerThread extends Thread {

    private EchoObject eo;
    private Socket clientSocket = null;
    private String myURL = "localhost";
    private BufferedReader is = null;
    private PrintWriter os = null;
    private String inputline = new String();

    EchoMultiServerThread(Socket socket) {
        super("EchoMultiServerThread(" + socket.getRemoteSocketAddress() + ")");
        clientSocket = socket;
        eo = new EchoObject();
        try {
            is = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            os = new PrintWriter(clientSocket.getOutputStream());
        } catch (IOException e) {
            System.err.println("Error sending/receiving " + e.getMessage());
            e.printStackTrace();
        }
        try {
            myURL = InetAddress.getLocalHost().getHostName();
        } catch (UnknownHostException e) {
            System.out.println("Unknown Host: " + e.toString());
            System.exit(1);
        }
    }

    public void run() {
        try {
            while ((inputline = is.readLine()) != null) {
                System.out.println(inputline);
                
                //EJERCICIO: Invocar el objeto 
                os.println(eo.echo(inputline));
                os.flush();
            }
            os.close();
            is.close();
            clientSocket.close();
        } catch (IOException e) {
            System.err.println("Error sending/receiving : " + e.getMessage());
            e.printStackTrace();
            
        }
    }
}

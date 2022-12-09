package by.bsuir.kugach.wt_laba_3.server.service.classes;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private Socket clientSocket;
    private ServerSocket server;
    private BufferedReader in;
    private BufferedWriter out;
    ServerLogic serverLogic;

    public Server(ServerLogic serverLogic) {
        this.serverLogic = serverLogic;
    }

    public boolean makeConnection() {
        try {
            try {
                server = new ServerSocket(8888);
                System.out.println("Waiting for client connection");
                clientSocket = server.accept();
                try {
                    in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                    out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                    String word = in.readLine();
                    if (word.equals("CONNECT")) {

                        System.out.println(word);
                        out.write("CONNECT" + System.lineSeparator());
                        out.flush();
                        return true;
                    } else
                        return false;
                } catch (Exception e) {
                    clientSocket.close();
                    in.close();
                    out.close();
                }
                return false;
            } catch (Exception e) {
                server.close();
            }
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getCommand() throws IOException, InterruptedException {
        try {
            return in.readLine();
        } catch (IOException e) {
            clientSocket.close();
            in.close();
            out.close();
            serverLogic.startConnection();
        }
        return "";
    }

    public void sendData(String data) throws IOException, InterruptedException {
        try {
            out.write(data + System.lineSeparator());
            out.flush();
        } catch (IOException e) {
            clientSocket.close();
            in.close();
            out.close();
            serverLogic.startConnection();
        }
    }

    public void sendClose() throws IOException, InterruptedException {
        try {
            out.write("STOP" + System.lineSeparator());
            out.flush();
        } catch (IOException e) {
            clientSocket.close();
            in.close();
            out.close();
            serverLogic.startConnection();
        }
    }

    public void close() throws IOException {
        in.close();
        out.close();
        server.close();
    }
}

package by.bsuir.kugach.wt_laba_3.client.service;

import by.bsuir.kugach.wt_laba_3.client.view.CommandReader;

import java.io.IOException;

public class ClientLogic {
    Client client;
    String command;
    boolean serverEnable;

    public ClientLogic() {
        client = new Client();
        command = "";
    }

    public void startClient() throws IOException {
        CommandReader commandReader = new CommandReader();
        serverEnable = client.makeConnection();
        if (serverEnable) {
            ServerReader serverReader = new ServerReader(client, this);
            serverReader.start();
        }
        while (!command.equals("EXIT") && serverEnable) {
            command = commandReader.getCommand();
            command = command.replaceAll("\\s+", "_");
            System.out.println(command);
            client.sendCommand(command.replaceAll("\\s+", "_") + System.lineSeparator());
        }
    }

    public void setNonEnable() {
        serverEnable = false;
    }
}

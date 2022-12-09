package by.bsuir.kugach.wt_laba_3.client.main;

import by.bsuir.kugach.wt_laba_3.client.service.ClientLogic;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ClientLogic clientLogic = new ClientLogic();
        clientLogic.startClient();
    }
}

package by.bsuir.kugach.wt_laba_3.server.main;

import by.bsuir.kugach.wt_laba_3.server.service.classes.ServerLogic;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerLogic serverLogic = new ServerLogic();
        serverLogic.startConnection();
    }
}

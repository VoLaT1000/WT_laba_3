package by.bsuir.kugach.wt_laba_3.server.service.classes;

import by.bsuir.kugach.wt_laba_3.server.entity.Criteria;
import by.bsuir.kugach.wt_laba_3.server.entity.IUserInfo;
import by.bsuir.kugach.wt_laba_3.server.entity.SearchCriteria;
import by.bsuir.kugach.wt_laba_3.server.entity.User;
import by.bsuir.kugach.wt_laba_3.server.view.MessageReader;
import by.bsuir.kugach.wt_laba_3.server.view.ResultPrinter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerLogic {
    private Criteria criteria;
    private Criteria studentCriteria;
    private ResultPrinter resultPrinter;
    private User clientInfo;
    private Server server;
    private boolean work;
    private List<Thread> threads;

    public ServerLogic() {
        criteria = new Criteria(SearchCriteria.User.getCriteriaName());
        studentCriteria = new Criteria(SearchCriteria.Student.getCriteriaName());
        threads = new ArrayList<Thread>();
        server = new Server(this);
        Thread consoleReader = new MessageReader(this);
        consoleReader.start();
        resultPrinter = new ResultPrinter();
    }

    public void startConnection() throws InterruptedException, IOException {
        clientInfo = new User();
        clientInfo.setName("");
        clientInfo.setAllowance("");
        boolean isConnect = false;
        while (!isConnect) {
            isConnect = server.makeConnection();
        }
        sendData("Please, Login" + System.lineSeparator());
        work = true;
        while (work) {
            String command = server.getCommand();
            Thread newCommand = new CommandHandler(command, this);
            newCommand.start();
            if (!command.equals("EXIT")) {
                threads.add(newCommand);
            }
        }
        server.close();
        System.out.println("STOPPED");
    }

    public void stopConnection() throws InterruptedException, IOException {
        for (Thread thread : threads) {
            thread.join();
        }
        work = false;
        server.sendClose();
    }

    public void sendData(String data) throws IOException, InterruptedException {
        server.sendData(data);
    }

    public Criteria getClientCriteria() {
        return criteria;
    }

    public User getClientInfo() {
        return clientInfo;
    }

    public Criteria getStudentCriteria() {
        return studentCriteria;
    }

    public ResultPrinter getResultPrinter() {
        return resultPrinter;
    }
}

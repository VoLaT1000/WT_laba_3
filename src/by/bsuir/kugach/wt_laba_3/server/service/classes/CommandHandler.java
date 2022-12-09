package by.bsuir.kugach.wt_laba_3.server.service.classes;

import by.bsuir.kugach.wt_laba_3.server.entity.IUserInfo;
import by.bsuir.kugach.wt_laba_3.server.entity.SearchCriteria;
import by.bsuir.kugach.wt_laba_3.server.service.interfaces.IServerService;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public class CommandHandler extends  Thread{
    private String fullCommand;
    private ServerLogic serverLogic;

    public CommandHandler(String command, ServerLogic serverLogic) {
        this.fullCommand = command;
        this.serverLogic = serverLogic;
    }

    public void run() {
        String command;
        System.out.println(fullCommand);
        int index = 0;
        if (fullCommand.contains("[")) {
            index = fullCommand.indexOf("[");
            command = fullCommand.substring(0, index);
        } else {
            command = fullCommand;
        }
        switch (command) {
            case "EXIT" -> {
                try {
                    serverLogic.stopConnection();
                    serverLogic.startConnection();
                } catch (InterruptedException | IOException e) {
                    e.printStackTrace();
                }
            }
            case "REG" -> {
                int newIndex = fullCommand.indexOf("]", index);
                String name = fullCommand.substring(index + 1, newIndex);
                index = fullCommand.indexOf("[", newIndex);
                newIndex = fullCommand.indexOf("]", index);
                String password = fullCommand.substring(index + 1, newIndex);
                index = fullCommand.indexOf("[", newIndex);
                newIndex = fullCommand.indexOf("]", index);
                String allowance = fullCommand.substring(index + 1, newIndex);
                ServiceFactory factory = ServiceFactory.getInstance();
                IServerService service = factory.getApplianceService();
                boolean isAdded = false;
                try {
                    isAdded = service.addUser(name, password, allowance);
                } catch (IOException | SAXException | ParserConfigurationException | TransformerException e) {
                    e.printStackTrace();
                }
                if (isAdded) {
                    try {
                        serverLogic.sendData("REG Name: " + name + ", Password: " + password + ", allowance: " + allowance + System.lineSeparator());
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                    serverLogic.getClientInfo().setAllowance(allowance);
                    serverLogic.getClientInfo().setName(name);
                } else {
                    try {
                        serverLogic.sendData("TRY AGAIN" + System.lineSeparator());
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }

            }
            case "LOGIN" -> {
                int newIndex = fullCommand.indexOf("]", index);
                String name = fullCommand.substring(index + 1, newIndex);
                index = fullCommand.indexOf("[", newIndex);
                newIndex = fullCommand.indexOf("]", index);
                String password = fullCommand.substring(index + 1, newIndex);
                serverLogic.getClientCriteria().add(SearchCriteria.User.name.getEnumName(), name);
                serverLogic.getClientCriteria().add(SearchCriteria.User.password.getEnumName(), password);
                ServiceFactory factory = ServiceFactory.getInstance();
                IServerService service = factory.getApplianceService();
                IUserInfo clientInfo = service.getUser(serverLogic.getClientCriteria());
                if (clientInfo != null) {
                    serverLogic.getClientInfo().setName(clientInfo.getParameters().get(0));
                    serverLogic.getClientInfo().setAllowance(clientInfo.getParameters().get(1));
                    try {
                        serverLogic.sendData("LOGIN Name: " + serverLogic.getClientInfo().getName() + ", allowance: " + serverLogic.getClientInfo().getAllowance() + System.lineSeparator());
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    try {
                        serverLogic.sendData("TRY AGAIN" + System.lineSeparator());
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            case "GETALL" -> {
                if (!serverLogic.getClientInfo().getAllowance().equals("")) {
                    ServiceFactory factory = ServiceFactory.getInstance();
                    IServerService service = factory.getApplianceService();
                    List<IUserInfo> studentInfoList = service.getAll();
                    String data = "";
                    if (!studentInfoList.isEmpty()) {
                        for (IUserInfo studentInfo : studentInfoList) {
                            data = data + studentInfo.toString() + System.lineSeparator();
                        }
                        try {
                            serverLogic.sendData(data);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            serverLogic.sendData("TRY AGAIN" + System.lineSeparator());
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        serverLogic.sendData("Not enough rights" + System.lineSeparator());
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            case "GET" -> {
                if (!serverLogic.getClientInfo().getAllowance().equals("")) {
                    int newIndex = fullCommand.indexOf("]", index);
                    String name = fullCommand.substring(index + 1, newIndex);
                    ServiceFactory factory = ServiceFactory.getInstance();
                    IServerService service = factory.getApplianceService();
                    serverLogic.getStudentCriteria().getCriteria().clear();
                    serverLogic.getStudentCriteria().add(SearchCriteria.Student.name.getEnumName(), name);
                    IUserInfo studentInfo;
                    studentInfo = service.getStudent(serverLogic.getStudentCriteria());
                    String data = "";
                    if (studentInfo != null) {
                        data = "Name: " + studentInfo.toString() + System.lineSeparator();
                        try {
                            serverLogic.sendData(data);
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            serverLogic.sendData("TRY AGAIN" + System.lineSeparator());
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        serverLogic.sendData("Not enough rights" + System.lineSeparator());
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

            case "EDIT" -> {
                if (serverLogic.getClientInfo().getAllowance().equals("EDIT") || serverLogic.getClientInfo().getAllowance().equals("ADD")) {
                    int newIndex = fullCommand.indexOf("]", index);
                    String name = fullCommand.substring(index + 1, newIndex);
                    index = fullCommand.indexOf("[", newIndex);
                    newIndex = fullCommand.indexOf("]", index);
                    String newName = fullCommand.substring(index + 1, newIndex);
                    index = fullCommand.indexOf("[", newIndex);
                    newIndex = fullCommand.indexOf("]", index);
                    String averageScore = fullCommand.substring(index + 1, newIndex);
                    ServiceFactory factory = ServiceFactory.getInstance();
                    IServerService service = factory.getApplianceService();
                    boolean isEdit = service.regStudent(name, newName, averageScore);
                    if (isEdit) {
                        try {
                            serverLogic.sendData("EDIT Name: " + name + ", newName: " + newName + ", averageScore: " + averageScore + System.lineSeparator());
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            serverLogic.sendData("TRY AGAIN" + System.lineSeparator());
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        serverLogic.sendData("Not enough rights" + System.lineSeparator());
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            case "ADD" -> {
                if (serverLogic.getClientInfo().getAllowance().equals("ADD")) {
                    int newIndex = fullCommand.indexOf("]", index);
                    String name = fullCommand.substring(index + 1, newIndex);
                    index = fullCommand.indexOf("[", newIndex);
                    newIndex = fullCommand.indexOf("]", index);
                    String averageScore = fullCommand.substring(index + 1, newIndex);
                    ServiceFactory factory = ServiceFactory.getInstance();
                    IServerService service = factory.getApplianceService();
                    boolean isAdded = false;
                    try {
                        isAdded = service.addStudent(name, averageScore);
                    } catch (ParserConfigurationException | TransformerException | SAXException | IOException e) {
                        e.printStackTrace();
                    }
                    if (isAdded) {
                        try {
                            serverLogic.sendData("ADD Name: " + name + ", AverageScore: " + averageScore + System.lineSeparator());
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    } else {
                        try {
                            serverLogic.sendData("TRY AGAIN" + System.lineSeparator());
                        } catch (IOException | InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    try {
                        serverLogic.sendData("Not enough rights" + System.lineSeparator());
                    } catch (IOException | InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
            case "LOGOUT" -> {
                serverLogic.getClientInfo().setName("");
                serverLogic.getClientInfo().setAllowance("");
                try {
                    serverLogic.sendData("Please, Login" + System.lineSeparator());
                } catch (IOException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        ;
    }
}

package by.bsuir.kugach.wt_laba_3.server.service.classes;

import by.bsuir.kugach.wt_laba_3.server.service.interfaces.IServerService;

public class ServiceFactory {
    private static ServiceFactory instance = new ServiceFactory();
    private IServerService applianceService = new ServerService();
    private ServiceFactory(){
    }
    public IServerService getApplianceService(){
        return applianceService;
    }
    public static ServiceFactory getInstance(){
        return instance;
    }
}

package by.bsuir.kugach.wt_laba_3.server.dao.classes;

import by.bsuir.kugach.wt_laba_3.server.dao.interfaces.IApplienceDAO;

public class DAOFactory {
    private static DAOFactory instance = new DAOFactory();
    private IApplienceDAO applienceDAO = new ApplianceDAO();
    private DAOFactory(){
    }
    public IApplienceDAO getApplienceDAO(){
        return applienceDAO;
    }
    public static DAOFactory getInstance(){
        return instance;
    }
}

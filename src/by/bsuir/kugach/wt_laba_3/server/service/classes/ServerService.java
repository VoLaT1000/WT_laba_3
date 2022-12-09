package by.bsuir.kugach.wt_laba_3.server.service.classes;

import by.bsuir.kugach.wt_laba_3.server.dao.classes.DAOFactory;
import by.bsuir.kugach.wt_laba_3.server.dao.interfaces.IApplienceDAO;
import by.bsuir.kugach.wt_laba_3.server.entity.Criteria;
import by.bsuir.kugach.wt_laba_3.server.entity.IUserInfo;
import by.bsuir.kugach.wt_laba_3.server.entity.SearchCriteria;
import by.bsuir.kugach.wt_laba_3.server.service.interfaces.IServerService;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ServerService implements IServerService {
    private IApplienceDAO applianceDAO;
    public ServerService(){
         DAOFactory factory = DAOFactory.getInstance();
         applianceDAO = factory.getApplienceDAO();
    }
    public List<IUserInfo> getAll() {
        return applianceDAO.getAll("src/by/bsuir/kugach/wt_laba_3/resources/students_db.xml", "Student");
    }

    public boolean addUser(String name, String password, String allowance) throws IOException, SAXException, ParserConfigurationException, TransformerException {
        List<String[]> parameters = new ArrayList<>();
        parameters.add(new String[]{SearchCriteria.User.name.getEnumName(), name});
        parameters.add(new String[]{SearchCriteria.User.password.getEnumName(), password});
        parameters.add(new String[]{"allowance", allowance});
        return applianceDAO.add(parameters, "src/main/resources/users_db.xml", "Client");
    }

    public IUserInfo getUser(Criteria criteria) {
        List<IUserInfo> clientInfoList = applianceDAO.get(criteria, "src/main/resources/users_db.xml", "Client");
        if (!clientInfoList.isEmpty()) {
            return clientInfoList.get(0);
        }
        return null;
    }

    public IUserInfo getStudent(Criteria criteria) {
        List<IUserInfo> clientInfoList = applianceDAO.get(criteria, "src/main/resources/students_db.xml", "Student");
        if (!clientInfoList.isEmpty()) {
            return clientInfoList.get(0);
        }
        return null;
    }

    public boolean regStudent(String name, String newName, String averageScore) {
        List<String[]> parameters = new ArrayList<>();
        parameters.add(new String[]{SearchCriteria.Student.name.getEnumName(), newName});
        parameters.add(new String[]{SearchCriteria.Student.averageScore.getEnumName(), averageScore});
        return applianceDAO.edit(name, parameters, "src/main/resources/students_db.xml");
    }

    public boolean addStudent(String name, String averageScore) throws ParserConfigurationException, TransformerException, SAXException, IOException {
        List<String[]> parameters = new ArrayList<>();
        parameters.add(new String[]{SearchCriteria.Student.name.getEnumName(), name});
        parameters.add(new String[]{SearchCriteria.Student.averageScore.getEnumName(), averageScore});
        return applianceDAO.add(parameters, "src/main/resources/students_db.xml", "Student");
    }
}

package by.bsuir.kugach.wt_laba_3.server.service.interfaces;

import by.bsuir.kugach.wt_laba_3.server.entity.Criteria;
import by.bsuir.kugach.wt_laba_3.server.entity.IUserInfo;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public interface IServerService {
    List<IUserInfo> getAll();
    IUserInfo getUser(Criteria criteria);
    IUserInfo getStudent(Criteria criteria);
    boolean regStudent(String name, String newName, String averageScore);
    boolean addStudent(String name, String averageScore) throws ParserConfigurationException, TransformerException, SAXException, IOException;
    boolean addUser(String name, String password, String allowance) throws IOException, SAXException, ParserConfigurationException, TransformerException;
}

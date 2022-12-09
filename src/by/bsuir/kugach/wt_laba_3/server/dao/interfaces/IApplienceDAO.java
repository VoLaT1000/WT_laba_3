package by.bsuir.kugach.wt_laba_3.server.dao.interfaces;

import by.bsuir.kugach.wt_laba_3.server.entity.Criteria;
import by.bsuir.kugach.wt_laba_3.server.entity.IUserInfo;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.util.List;

public interface IApplienceDAO {
    List<IUserInfo> getAll(String path, String type);
    List<IUserInfo> get(Criteria criteria, String path, String type);
    boolean add(List<String[]> parameters, String path, String type) throws ParserConfigurationException, IOException, SAXException, TransformerException;
    boolean edit(String name, List<String[]> parameters, String path);
}

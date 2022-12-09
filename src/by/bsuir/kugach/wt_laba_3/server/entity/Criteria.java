package by.bsuir.kugach.wt_laba_3.server.entity;

import java.util.HashMap;
import java.util.Map;

public class Criteria {
    private String groupSearchName;
    private Map<String, Object> criteria = new HashMap<String, Object>();
    public Criteria(String groupSearchName){
        this.groupSearchName = groupSearchName;
    }
    public String getGroupSearchName(){
        return groupSearchName;
    }
    public void add(String searchCriteria, Object value){
        criteria.put(searchCriteria, value);
    }
    public Map<String, Object> getCriteria(){
        return criteria;
    }
}

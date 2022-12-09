package by.bsuir.kugach.wt_laba_3.server.entity;

import java.util.List;

public class Factory {
    public IUserInfo getInfo(List<String> parameters, String type){
        if(type.equals("User")){
            User user = new User();
            user.setName(parameters.get(0));
            user.setAllowance(parameters.get(1));
            return user;
        }
        else {
            Student student = new Student();
            student.setName(parameters.get(0));
            student.setAverageScore(parameters.get(1));
            return student;
        }
    }
}

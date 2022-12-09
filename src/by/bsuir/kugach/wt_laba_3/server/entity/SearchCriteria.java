package by.bsuir.kugach.wt_laba_3.server.entity;

public class SearchCriteria {
    public static enum User{
        name("name"),
        password("password");
        private String enumName;
        User(String enumName){
            this.enumName = enumName;
        }
        public String getEnumName(){
            return enumName;
        }
        public static String getCriteriaName(){
            return "User";
        }
    }
    public static enum Student{
        name("name"),
        averageScore("averageScore");
        private String enumName;
        Student(String enumName){
            this.enumName = enumName;
        }
        public String getEnumName(){
            return enumName;
        }
        public static String getCriteriaName(){
            return "Student";
        }
    }
    private SearchCriteria(){
    }
}

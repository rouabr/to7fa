package models;
import java.util.Date;

public class User {
    private int user_id;
    private String user_name;
    private String user_phone;
    private String user_email;
    private String user_password;
    private Date date_birth;
    private String user_sexe;
    private String user_role;
    private Date date_creation;
    private Date date_last_login;
    private String code_check;

    // Constructeur
    public User(int user_id, String user_name, String user_phone, String user_email, String user_password, Date date_birth,
                String user_sexe, String user_role, Date date_creation, Date date_last_login, String code_check) {
        this.user_id = user_id;
        this.user_name = user_name;
        this.user_phone = user_phone;
        this.user_email = user_email;
        this.user_password = user_password;
        this.date_birth = date_birth;
        this.user_sexe = user_sexe;
        this.user_role = user_role;
        this.date_creation = date_creation;
        this.date_last_login = date_last_login;
        this.code_check = code_check;
    }

    // Getters et setters
    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_phone() {
        return user_phone;
    }

    public void setUser_phone(String user_phone) {
        this.user_phone = user_phone;
    }

    public String getUser_email() {
        return user_email;
    }

    public void setUser_email(String user_email) {
        this.user_email = user_email;
    }

    public String getUser_password() {
        return user_password;
    }

    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }

    public Date getDate_birth() {
        return date_birth;
    }

    public void setDate_birth(Date date_birth) {
        this.date_birth = date_birth;
    }

    public String getUser_sexe() {
        return user_sexe;
    }

    public void setUser_sexe(String user_sexe) {
        this.user_sexe = user_sexe;
    }

    public String getUser_role() {
        return user_role;
    }

    public void setUser_role(String user_role) {
        this.user_role = user_role;
    }

    public Date getDate_creation() {
        return date_creation;
    }

    public void setDate_creation(Date date_creation) {
        this.date_creation = date_creation;
    }

    public Date getDate_last_login() {
        return date_last_login;
    }

    public void setDate_last_login(Date date_last_login) {
        this.date_last_login = date_last_login;
    }

    public String getCode_check() {
        return code_check;
    }

    public void setCode_check(String code_check) {
        this.code_check = code_check;
    }
}

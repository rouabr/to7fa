package services;

import java.sql.SQLException;
import java.util.List;

public interface CRUD <T>{
    void insertOne(T t) throws SQLException;
   void deleteOne(int i) throws SQLException ;
    public void updateOne(T t) throws SQLException ;
    List<T> selectAll() throws SQLException ;
    List<T> selectAlltri() throws SQLException ;
    public List<T> selectByname(String ch) throws SQLException;

}

package services;

import java.sql.SQLException;
import java.util.List;

public interface CRUD <T> {
    void  insertOne(T t) throws SQLException;
   void  updateOne(T t) throws SQLException;
   void  deleteOne(int id) throws SQLException;
    List<T> selectAll() throws SQLException;

}

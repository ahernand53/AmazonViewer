package com.anncode.amazonviewer.db;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static com.anncode.amazonviewer.db.DataBase.*;

public interface IDBConnection {

    default Connection connectToDB(){
        Connection connection = null;
        try{
            Class.forName("com.mysql.jdbc.Driver");
            connection = DriverManager.getConnection(
                URL+DB_NAME,
                    DB_USER,
                    DB_PASS
            );
            if (connection != null){
                System.out.println("Se establecio la conexion :)");
            }
        } catch (Exception e){
            System.out.println("Error al conectar la base de datos");
        } finally {
            return connection;
        }
    }

}

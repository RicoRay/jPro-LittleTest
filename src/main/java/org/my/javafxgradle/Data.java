/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.my.javafxgradle;

import com.mysql.cj.jdbc.MysqlDataSource;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Titi
 */
public class Data {

    /**
     * Insert en base user et message
     * @param user
     * @param message 
     */
    public static void insertMessage(String user, String message) {
        try {
            //String url = "jdbc:mysql://db4free.net:3307/labase2030";
            
            String sql = "INSERT INTO tb_Message (user_id, message, submission_date) VALUES (?, ?, NOW())";
            MysqlDataSource dataSource = getMysqlDataSource();
            
            try (Connection cn = dataSource.getConnection()) {
                PreparedStatement pst = cn.prepareStatement(sql);
                
                pst.setString(1, user);
                pst.setString(2, message);
                
                pst.executeUpdate();
                pst.closeOnCompletion();
                cn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * 
     * @param User
     * @return 
     */
    public static ObservableList<String> getMessage(String User){
        ObservableList<String> maOL = null;
        
        String sql = "SELECT concat(user_id,' : ', message, ' | ', submission_date) \"data\" \n" +
                     "FROM tb_Message WHERE submission_date > subdate(now(), 1) \n" +
                     "ORDER BY submission_date DESC";
        
        MysqlDataSource dataSource = getMysqlDataSource();
        
        ArrayList monAl = new ArrayList();
        
        try {
            try (Connection cn = dataSource.getConnection()) {
                try (Statement stmt = cn.createStatement()) {
                    ResultSet rs = stmt.executeQuery(sql);
                    
                    while(rs.next()){
                        monAl.add(rs.getString("data"));
                    }
                    stmt.closeOnCompletion();
                }
                cn.close();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        if (!monAl.isEmpty()){
           maOL = FXCollections.observableArrayList(monAl);                        
        }
        
        return maOL;
    }

    /**
     * La dataSource
     *
     * @return
     */
    private static MysqlDataSource getMysqlDataSource() {
        String databasePropPath = "/properties/database.properties";                
        
        MysqlDataSource dataSource = new MysqlDataSource();

//        String login = "seb62375";
//        String pwd = "neutre3nC02";
//
//        dataSource.setDatabaseName("labase2030");
//        dataSource.setServerName("db4free.net");

        Tools tools = new Tools();

        String login = tools.getPropertie(databasePropPath, "database.login");
        String pwd = tools.getPropertie(databasePropPath, "database.password");
        dataSource.setUser(login);
        dataSource.setPassword(pwd);
        dataSource.setDatabaseName(tools.getPropertie(databasePropPath, "database.databasename"));
        dataSource.setServerName(tools.getPropertie(databasePropPath, "database.servername"));
        dataSource.setPortNumber(3306);
        
        try {
            dataSource.setUseSSL(false);
        } catch (SQLException ex) {
            Logger.getLogger(Data.class.getName()).log(Level.SEVERE, null, ex);
        }

        return dataSource;
    }
}

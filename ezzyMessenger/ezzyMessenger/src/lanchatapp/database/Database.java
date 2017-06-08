/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanchatapp.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Phu
 */
public class Database {

    private Connection conn;
    private String url = "jdbc:mysql://sql12.freemysqlhosting.net/sql12177339?characterEncoding=UTF-8";
    private String usr = "sql12177339";
    private String pw = "iDjDWv2pYl";
    
    public void createConnection() {
        try {
            conn = DriverManager.getConnection(url, usr, pw);
            System.out.println("Kết nối thành công");
        } catch (SQLException ex) {
            System.out.println("Kết nối thất bại");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertUser(String userName, int port, String password, String name, String email) {
        String query = "INSERT INTO user values ('" + userName + "','" + port + "','" + password + "','" + name + "','" + email + "')";
        try {
            Statement statement = conn.createStatement();
            statement.execute(query);
            statement.close();
        } catch (SQLException ex) {
            System.out.println("Không thể tạo tài khoản ");
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        } finally{
            try {
                conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // kiểm tra đăng nhập
    public boolean checkUser(String usrname, String pssword) {
        String query = "SELECT *FROM user WHERE username = '" + usrname + "' AND password = '" + pssword + "'";
        try {
            Statement statement = conn.createStatement();
            PreparedStatement preStatement = conn.prepareStatement(query);
            ResultSet result = preStatement.executeQuery();
            if (result.next()) {
                return true;
            }
            statement.close();
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    // Kiểm tra cập nhật
    public boolean checkVer(String ver) {
        String query = "SELECT *FROM ver WHERE ver = '" + ver + "'";
        try {
            Statement statement = conn.createStatement();
            PreparedStatement preStatement = conn.prepareStatement(query);
            ResultSet result = preStatement.executeQuery();
            if (result.next()) {
                return true;
            }
            statement.close();
            return false;
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    
    // kiểm tra đăng kí
    public boolean checkSignUp(String usrname, int port) {
        String query = "SELECT *FROM user where username = '" + usrname + "' AND port = '" + port + "'";
        try {
            Statement stament = conn.createStatement();
            PreparedStatement preStatement = conn.prepareStatement(query);
            ResultSet result = preStatement.executeQuery();
            if (result.next()) {
                return true;
            }

        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    //hàm trả về tên người dùng đăng nhập
    public String getNameLogInDB(String usrname, String pssword) {
        String name = null;
        String query = "SELECT  name FROM user WHERE username = '" + usrname + "' AND password = '" + pssword + "'";
        try {
            Statement statement = conn.createStatement();
            PreparedStatement preStatement = conn.prepareCall(query);
            ResultSet result = preStatement.executeQuery();
            while (result.next()) {
                name = result.getString("name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }

    // hàm trả về port khi đăng nhập
    public int getPortLogInDB(String usrname, String pssword) {
        int port = 0;
        String query = "SELECT port FROM user WHERE username = '" + usrname + "' AND password = '" + pssword + "'";

        try {
            Statement preStatement = conn.createStatement();
            PreparedStatement preStatement2 = conn.prepareCall(query);
            ResultSet result = preStatement2.executeQuery();
            while (result.next()) {
                port = result.getInt("port");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return port;
    }

    // hàm trả về danh sách người dùng trong database
    public String[] nameList() {
        String[] names = new String[50];
        String query = "SELECT *FROM user";

        try {
            Statement statement = conn.createStatement();
            PreparedStatement preStatement = conn.prepareStatement(query);
            ResultSet result = preStatement.executeQuery();
            int i = 0;
            while (result.next()) {
                names[i] = result.getString("name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Database.class.getName()).log(Level.SEVERE, null, ex);
        }

        return names;
    }
}

package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import static org.example.ConexDb.DBconnect.ConexiuneDB;

public class Main {

    public static void main(String[] args) throws SQLException, IOException {
        Scanner sc = new Scanner(System.in);
        BufferedReader br=new BufferedReader(new InputStreamReader(System.in));
        int ch;
        do
        {
            System.out.println("*****************************************************\n");
            System.out.println("APP MENU :");
            System.out.println("1 - Print database");
            System.out.println("2 - insert db");
            System.out.println("3 - delete from db");
            System.out.println("4 - update db");
            System.out.println("5 - exit app db");
            System.out.println("*****************************************************\n");
            System.out.print("Enter choice : ");
            ch=Integer.parseInt(br.readLine());
            if(ch==1)
                readDb();
            else if(ch==2)
                 insertDb(sc);
            else if(ch==3)
                deleteDb(sc);
            else if(ch==4)
            updateDb(sc);
            else
                System.out.println("Exit app");
        }while(ch!=5);

    }

    public static void readDb() throws SQLException {
        System.out.println("List id ,user and password:");
        Connection conn = ConexiuneDB();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM users order BY id");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            System.out.printf("id:%d username:%s password:%s%n", rs.getInt("id"), rs.getString("username"), rs.getString("password"));
        }


    }

    public static void insertDb(Scanner user) throws SQLException {
        Connection conn = ConexiuneDB();

        PreparedStatement pSt = conn.prepareStatement("insert into users(username, password) values(?, ?)");
        System.out.println("insert user:");
        pSt.setString(1, user.next());
        System.out.println("insert password:");
        pSt.setString(2, user.next());
        int val = pSt.executeUpdate(); // 1


    }

    public static void deleteDb(Scanner sc) throws SQLException {
        Connection conn = ConexiuneDB();


        PreparedStatement deleteStmt = conn.prepareStatement("DELETE FROM users WHERE id = ?");
        System.out.println("ENTER ID TO DELETE:");
        deleteStmt.setInt(1, sc.nextInt());

        String deletedRows = String.valueOf(deleteStmt.executeUpdate());
        System.out.printf("deleted %s username(s)%n", deletedRows);

    } public static void updateDb(Scanner sc) throws SQLException {
        Connection conn = ConexiuneDB();
        PreparedStatement updateStmt =
                conn.prepareStatement("UPDATE users SET username = ?, password = ? WHERE id = ?");
        System.out.println("enter id  to UPDATE");
        updateStmt.setInt(3,sc.nextInt());
        System.out.println("enter the new  username to UPDATE");
        updateStmt.setString(1,sc.next());
        System.out.println("enter new password to UPDATE");
        updateStmt.setString(2,sc.next());


        int updatedRows = updateStmt.executeUpdate();
        System.out.printf("updated %s username(s)", updatedRows);



    }

}
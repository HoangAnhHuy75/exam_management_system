/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package admin.desktop;

import config.ConnectedDatabase;

/**
 *
 * @author kiman
 */
public class AdminDesktop {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Kết quả kết nối cơ sở dữ liệu"+ConnectedDatabase.getConnectedDB());
    }
    
}

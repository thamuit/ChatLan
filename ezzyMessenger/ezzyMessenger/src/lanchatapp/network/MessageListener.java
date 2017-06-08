/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanchatapp.network;

import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Phu
 */



public class MessageListener extends Thread{
    private ServerSocket server;
    private int port; // port của server
    private WriteableGUI gui; 
    
    
    // truyền thông tin vào object
    public MessageListener(WriteableGUI gui,int port) {
        this.port = port;
        this.gui = gui;
        try {
            server = new ServerSocket(port);
        } catch (IOException ex) {
            Logger.getLogger(MessageListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
   
    @Override
    public void run(){
        Socket clientSocket;
        try {
            while((clientSocket = server.accept()) != null){
                Toolkit.getDefaultToolkit().beep();
                InputStream is = clientSocket.getInputStream();
                Charset.forName("UTF-8").newEncoder();
                BufferedReader br = new BufferedReader(new InputStreamReader(is,"utf8")); 
                String line = br.readLine();
                if(line != null){
                    gui.write(line);
                }
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Không thể kết nối!!!");
        }
    }
}

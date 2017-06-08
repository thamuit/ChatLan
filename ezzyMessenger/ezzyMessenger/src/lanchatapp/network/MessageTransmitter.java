/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanchatapp.network;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 *
 * @author Phu
 */
public class MessageTransmitter extends Thread{
    
    String message, hostname;
    int port;
    
    public MessageTransmitter() {
    }

    public MessageTransmitter(String message, String hostname, int port) {
        this.message = message;
        this.hostname = hostname;  // ip
        this.port = port;
    }
    // socket kết nối với server socket, server socket nhận socket.. 
    @Override
    public void run() {
        try {
            Charset.forName("UTF-8").newEncoder();
            Socket s = new Socket(hostname,port);
            s.getOutputStream().write(message.getBytes("utf8")); 
            s.close();
        } catch (IOException ex) {
            Logger.getLogger(MessageTransmitter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}

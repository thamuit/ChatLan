/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanchatapp.network;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phu
 */
public class FileTransmitter {

    private Socket socket;

    public FileTransmitter(String host, int port, String file) {
        try {
            socket = new Socket(host, port);
            sendFile(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void sendFile(String file) {
        DataOutputStream dos = null;
        try {
            dos = new DataOutputStream(socket.getOutputStream());
            FileInputStream fis = new FileInputStream(file);
            byte[] buffer = new byte[10];
            while (fis.read(buffer) > 0) {
                dos.write(buffer);
            }
            fis.close();
            dos.close();
        } catch (IOException ex) {
            Logger.getLogger(FileTransmitter.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dos.close();
            } catch (IOException ex) {
                Logger.getLogger(FileTransmitter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}

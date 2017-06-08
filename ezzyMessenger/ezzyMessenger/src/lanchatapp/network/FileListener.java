/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanchatapp.network;

import java.io.DataInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Phu
 */
public class FileListener extends Thread{
    private ServerSocket server;
    private String fileName;
    
    public FileListener(int port,String fileName) {
        this.fileName = fileName;
		try {
			server = new ServerSocket(port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    public void run() {
		while (true) {
			try {
				Socket clientSock = server.accept();
				saveFile(clientSock);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
    private void saveFile(Socket clientSock) {
	DataInputStream dis = null;
        try {
            dis = new DataInputStream(clientSock.getInputStream());
            FileOutputStream fos = new FileOutputStream(fileName);
            byte[] buffer = new byte[4096];
            int read = 0;
            int totalRead = 0;
            while((read = dis.read(buffer)) > 0){
                fos.write(buffer, 0, read);
            }
            fos.close();
            dis.close();
        } catch (IOException ex) {
            Logger.getLogger(FileListener.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                dis.close();
            } catch (IOException ex) {
                Logger.getLogger(FileListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
	}
}

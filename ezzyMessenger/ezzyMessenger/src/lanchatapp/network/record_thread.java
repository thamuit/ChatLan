/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanchatapp.network;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.TargetDataLine;

/**
 *
 * @author Hong Tham
 */
public class record_thread extends Thread{
    public TargetDataLine audio_in = null;
    public DatagramSocket dout;
    byte[] byte_buff = new byte[512];
    public InetAddress server_IP;
    public int server_port;
    @Override
    public void run(){
        int i=0;
        while(CallTransmitter.calling){
            try{
                audio_in.read(byte_buff, 0,byte_buff.length);
                DatagramPacket data = new DatagramPacket(byte_buff,byte_buff.length,server_IP,server_port);
                System.out.println("send #"+i++);
                dout.send(data);
            } catch (IOException ex) {
                Logger.getLogger(record_thread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        audio_in.close();
        audio_in.drain();
        System.out.println("Thread stop");
    }
}

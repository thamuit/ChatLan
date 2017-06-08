/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanchatapp.network;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.TargetDataLine;
import javax.swing.JOptionPane;
import lanchatapp.gui.call_voice;

/**
 *
 * @author Hong Tham
 */
public class CallTransmitter {
    public static boolean calling = false;
    private int server_port;
    private String add_server;
    public CallTransmitter (int server_port, String add_server){
        this.server_port = server_port;
        this.add_server = add_server;
    }
    public CallTransmitter (){
        this.server_port = server_port;
        this.add_server = add_server;
    }
    public static AudioFormat getAudioFormat(){
        float sampleRate = 8000.0F;
        int sampleSizeInbit = 16;
        int chanel =2;
        boolean signed = true;
        boolean bigEndian = false;
        return new AudioFormat(sampleRate, sampleSizeInbit,chanel,signed,bigEndian);
    }
    TargetDataLine audio_in;
    public void init_audio() {
        try{
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(TargetDataLine.class, format);
            if(!AudioSystem.isLineSupported(info)){
                JOptionPane.showMessageDialog(null,"not support");
                System.exit(0);
            }
            audio_in = (TargetDataLine)AudioSystem.getLine(info);
            audio_in.open(format);
            audio_in.start();
            record_thread r = new record_thread();
            InetAddress inet = InetAddress.getByName(add_server);
            r.audio_in = audio_in;
            r.dout = new DatagramSocket();
            r.server_IP = inet;
            r.server_port = server_port;
            CallTransmitter.calling= true;
            r.start();
        } catch (LineUnavailableException ex) {
            Logger.getLogger(call_voice.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SocketException ex) {
            Logger.getLogger(call_voice.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnknownHostException ex) {
            Logger.getLogger(call_voice.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

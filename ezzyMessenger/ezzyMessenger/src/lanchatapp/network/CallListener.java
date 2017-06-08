/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanchatapp.network;

import java.net.DatagramSocket;
import java.net.SocketException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JOptionPane;

/**
 *
 * @author Hong Tham
 */
public class CallListener {
    public static boolean calling = false;
    private final int server_port;
    private final String add_server;
    public CallListener (int port, String add_server){
        this.server_port = port;
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
    SourceDataLine audio_out;
    public void init_audio(){
        try {
            AudioFormat format = getAudioFormat();
            DataLine.Info info = new DataLine.Info(SourceDataLine.class, format);
            if(!AudioSystem.isLineSupported(info)){
                JOptionPane.showMessageDialog(null,"not support");
                System.exit(0);
            }
            audio_out = (SourceDataLine)AudioSystem.getLine(info);
            audio_out.open(format);
            audio_out.start();
            player_thread p = new player_thread();
            p.din = new DatagramSocket(server_port);
            p.audio_out = audio_out;
            CallListener.calling= true;
            p.start();
        } catch (LineUnavailableException | SocketException ex) {
            Logger.getLogger(CallListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

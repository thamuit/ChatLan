/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lanchatapp;

/**
 *
 * @author Phu
 */
public class TimeConvert {
    public static String secToH(long sec){
        String time = "";
        long hour, min;
        hour = sec / 3600;
        sec %= 3600;
        min = sec / 60;
        sec %= 60;
        return hour + " giờ " + min + " phút " + sec + " giây";
    }
    public static void main(String[] args) {
        System.out.println(secToH(5002));
    }
}

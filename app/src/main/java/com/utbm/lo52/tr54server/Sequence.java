package com.utbm.lo52.tr54server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.HashMap;

/**
 * Created by lekaizer on 17/12/14.
 */
public class Sequence extends Thread {
    private DatagramSocket sendingSocket;
    private DatagramSocket receivingSocket;

    private DatagramPacket packetSent;
    private DatagramPacket packetReceived;

    final private int BUFFER_SIZE = 1024;
    private byte[] buffer;

    private InetAddress ipAddress;
    private int dPort;
    private int sPort;

    private boolean VOIE_1_IS_OCCUPIED = true;
    private boolean VOIE_2_IS_OCCUPIED = true;

    private HashMap<String,RDU> tableVoie1;
    private HashMap<String,RDU> tableVoie2;

    public Sequence(InetAddress ipAddress, int sPort, int dPort) throws SocketException{
        this.ipAddress = ipAddress;
        this.sPort = sPort;
        this.dPort = dPort;
        buffer = new byte[BUFFER_SIZE];
        sendingSocket = new DatagramSocket();
        receivingSocket = new DatagramSocket(this.dPort);
        receivingSocket.setSoTimeout(10);
        tableVoie1 = new HashMap<>();
        tableVoie2 = new HashMap<>();
    }

    public void run(){

        while(true){
            packetReceived = new DatagramPacket(buffer,buffer.length);
            try {
                receivingSocket.receive(packetReceived);
                if(packetReceived.getData()!=null){
                       String[] str = new String(packetReceived.getData(),"UTF-8").split("-");
                       if(str.length > 2){
                            if(str[1].equals("1")){
                                tableVoie1.put(str[0],new RDU(str));
                            } else if(str[1].equals("2")) {
                                tableVoie2.put(str[0],new RDU(str));
                            }
                        } else {
                           if(tableVoie1.containsKey(str[0]))
                               tableVoie1.remove(str[0]);
                           else if(tableVoie2.containsKey(str[0]))
                               tableVoie2.remove(str[0]);
                       }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            /** sending allowed robot list
             *
             * */
            // Voie 1
            String list = "";
            for(String robot : tableVoie1.keySet())
                if (!VOIE_1_IS_OCCUPIED) {
                    list += tableVoie1.get(robot).getBot_name() + "-";
                } else {
                    if (tableVoie1.get(robot).getDistance() <= 15)
                        list += tableVoie1.get(robot).getBot_name() + "-";
                }
            buffer = list.getBytes();
            packetSent = new DatagramPacket(buffer,buffer.length,ipAddress,sPort);
            try {
                sendingSocket.send(packetReceived);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String list2 = "";
            for(String robot : tableVoie2.keySet())
                if (!VOIE_2_IS_OCCUPIED) {
                    list2 += tableVoie2.get(robot).getBot_name() + "-";
                } else {
                    if (tableVoie2.get(robot).getDistance() <= 15)
                        list2 += tableVoie2.get(robot).getBot_name() + "-";
                }
            buffer = list2.getBytes();
            packetSent = new DatagramPacket(buffer,buffer.length,ipAddress,sPort);
            try {
                sendingSocket.send(packetReceived);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

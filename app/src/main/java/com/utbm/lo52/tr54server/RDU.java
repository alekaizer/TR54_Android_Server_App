package com.utbm.lo52.tr54server;

/**
 * Created by lekaizer on 17/12/14.
 */
public class RDU {
    private String bot_name;
    private int voie;
    private float distance;
    private float vitesse = 0;

    public RDU(String[] str){
        if(str[0]!=null)
            this.bot_name = str[0];
        if(str[1]!=null)
            this.voie = Integer.parseInt(str[1]);
        if(str[2]!=null)
            Float.parseFloat(str[2]);
        if(str[3]!=null)
            Float.parseFloat(str[3]);
    }
    public RDU(String name, int voie, float distance){
        this.bot_name = name;
        this.voie = voie;
        this.distance = distance;
    }

    public RDU(String name, int voie, float distance, float vitesse){
        this.bot_name = name;
        this.voie = voie;
        this.distance = distance;
        this.vitesse = vitesse;
    }

    public String getBot_name(){
        return bot_name;
    }

    public int getVoie(){
        return voie;
    }

    public float getDistance(){
        return distance;
    }

    public float getVitesse(){
        return vitesse;
    }
}

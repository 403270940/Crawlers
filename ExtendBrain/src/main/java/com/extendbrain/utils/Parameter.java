package com.extendbrain.utils;

/**
 * Created by yli on 2015/7/17.
 */
public class Parameter {

    //��Ҫģ����Ϣ�У�IMEI�ţ��ֻ�ţ�SIM�����ںţ����iso��ע�Ĺ���룬
    // SIM���ṩ�̵��ƶ�����룬������Ӫ�̵���ƣ�
    // �ֻ��ͺţ�SDK�汾�ţ�����ϵͳ�汾���ֻ�MAC��ַ
    String IMEI;
    String MAC;
    String IMSI;
    String MANU;
    String MODEL;
    String PHONE;
    String SDK;

    public Parameter(String IMEI, String MAC, String IMSI, String MANU, String MODEL, String PHONE, String SDK) {
        this.IMEI = IMEI;
        this.MAC = MAC;
        this.IMSI = IMSI;
        this.MANU = MANU;
        this.MODEL = MODEL;
        this.PHONE = PHONE;
        this.SDK = SDK;
    }

    public String getSDK() {
        return SDK;
    }

    public void setSDK(String SDK) {
        this.SDK = SDK;
    }

    public String getIMEI() {
        return IMEI;
    }

    public void setIMEI(String IMEI) {
        this.IMEI = IMEI;
    }

    public String getMAC() {
        return MAC;
    }

    public void setMAC(String MAC) {
        this.MAC = MAC;
    }

    public String getIMSI() {
        return IMSI;
    }

    public void setIMSI(String IMSI) {
        this.IMSI = IMSI;
    }

    public String getMANU() {
        return MANU;
    }

    public void setMANU(String MANU) {
        this.MANU = MANU;
    }

    public String getMODEL() {
        return MODEL;
    }

    public void setMODEL(String MODEL) {
        this.MODEL = MODEL;
    }

    public String getPHONE() {
        return PHONE;
    }

    public void setPHONE(String PHONE) {
        this.PHONE = PHONE;
    }
}

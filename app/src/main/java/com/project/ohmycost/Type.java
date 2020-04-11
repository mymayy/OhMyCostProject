package com.project.ohmycost;

public class Type {
    private String type;
    private int pay;
    public Type(String type,int pay){
        this.pay=pay;
        this.type=type;
    }
    public String getType(){
        return type;
    }
    public double getPay(){
        return pay;
    }
    public void setPay(int pay){
        this.pay=pay;
    }
    public String toString(){
        return type+"\t\t\t\t\t\t"+pay;
    }
}

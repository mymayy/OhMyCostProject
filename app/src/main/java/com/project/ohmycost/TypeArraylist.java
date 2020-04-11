package com.project.ohmycost;

import java.util.ArrayList;

public class TypeArraylist {
    private ArrayList<Type> tList = new ArrayList<>();
    public void add(Type t){
        tList.add(t);
    }
    public void delete(Type t){
        for(int i=0;i<tList.size();i++){
            if(tList.get(i).getType().equalsIgnoreCase(t.getType())){
                tList.remove(t);
                break;
            }
        }
    }
    public int getTotal(){
        int total=0;
        for(int i=0;i<tList.size();i++){
            total+=tList.get(i).getPay();
        }
        return total;
    }
    public String toString(){
        String result="";
        for(int i=0;i<tList.size();i++){
            result+=tList.get(i).toString()+"\n";
        }
        return result;
    }
}

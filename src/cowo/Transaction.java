/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package cowo;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author dnb
 */
public class Transaction {

    private HashMap<String, String> mapTransFull = new HashMap();
    static public ArrayList<Transaction> listTransactions = new ArrayList();
    int year;
    int month;
    int day;
    int hour;
    int minute;
    String [] arrayTime;
    ArrayList <Integer> arrayListTime = new ArrayList();


    //Beginning Constructor
    Transaction(String[] values) {
        for (int i = 0; i < Main.headers.length; i++) {
            
            mapTransFull.put(Main.headers[i], values[i]);

            //Bank bank = new Node();


            }
//        System.out.println("year is: "+mapTransFull.get("year"));
//        System.out.println("year is: "+mapTransFull.get("abstract"));

            //adds the 2 banks of this current transaction
            // to the set all the banks are present in the dataset - simply referred by their BIC
            Main.mapYearsToAbstracts.put(Integer.parseInt(mapTransFull.get("year")),mapTransFull.get("abstract"));
            

    }//End Constructor


    
    public HashMap<String, String> getMapTransFull() {

        return mapTransFull;
    }


    public String getLnValue() {

        return mapTransFull.get("ln_value");
    }

    public String getRate() {

        return mapTransFull.get("rate");
    }





} // End class Transaction


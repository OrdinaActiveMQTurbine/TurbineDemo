/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package net.timico.messaging;

import java.io.Serializable;
import java.util.Date;

public class TurbineData implements Serializable {
    
    private static final long serialVersionUID = 3000L;
    
    private long id;
    
    private String stringData;
    
    private Date datum;   
    
    TurbineData( String stringData) {       
        this.stringData = stringData;
        this.datum = new Date();
    }

    public long getId() {
        return id;
    }
   
    public void setId(long id) {
        this.id = id;
    }

    public String getStringData() {
        return stringData;
    }
    
    public void setStringData(String stringData) {
        this.stringData = stringData;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = new Date();
    }
}

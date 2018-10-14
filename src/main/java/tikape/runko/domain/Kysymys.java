/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.util.ArrayList;
import java.util.List;
import tikape.runko.domain.Vastaus;

/**
 *
 * @author oleg
 */
public class Kysymys {
    private Integer id;
    private String kurssi;
    private String aihe;
    private String kysymysteksti;
    private List<Vastaus> vastaukset;
    
    public Kysymys(Integer id, String kurssi, String aihe, String teksti) {
        this.id = id;
        this.kurssi = kurssi;
        this.aihe = aihe;
        this.kysymysteksti = teksti;
        this.vastaukset = new ArrayList();
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public String getTeksti() {
        return this.kysymysteksti;
    }
    
    public String getKurssi() {
        return this.kurssi;
    }
    
    public String getAihe() {
        return this.aihe;
    }
    
    public List<Vastaus> getVastaukset() {
        return this.vastaukset;
    }
    
    public void addVastaukset(List<Vastaus> vastaukset) {
        this.vastaukset.addAll(vastaukset);
    }
}

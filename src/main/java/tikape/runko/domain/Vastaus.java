/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author oleg
 */
public class Vastaus {
    private Integer id;
    private String vastausteksti;
    private Boolean oikein;
    private Integer kysymys_id;
    
    public Vastaus(Integer id, String teksti, Boolean oikein, Integer kysymys_id) {
        this.id = id;
        this.vastausteksti = teksti;
        this.oikein = oikein;
        this.kysymys_id = kysymys_id;
    }
    
    public Integer getId() {
        return this.id;
    }
    
    public String getTeksti() {
        return this.vastausteksti;
    }
    
    public Boolean onOikein() {
        return this.oikein;
    }
    
    public Integer getKysymys() {
        return this.kysymys_id;
    }
}

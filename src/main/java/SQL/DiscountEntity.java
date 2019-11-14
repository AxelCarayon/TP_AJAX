/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SQL;

/**
 *
 * @author Axel
 */
public class DiscountEntity {
    
    private String code;
    private double rate;

    public DiscountEntity(String c, double r){
        this.code = c;
        this.rate= r;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }
    
    @Override
    public String toString(){
        return "Code : " + this.code + " " + "Taux : " + this.rate;
    }
    
}

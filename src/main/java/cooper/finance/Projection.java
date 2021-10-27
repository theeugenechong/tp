package cooper.finance;

import java.util.ArrayList;

//@@author ChrisLangton

public class Projection {
    public int rate;
    public int years;
    public double principal;
    public ArrayList<Double> growthValues;

    public Projection() {
        //this.principal = principal;
        //this.rate = rate;
        //this.years = years;
        this.growthValues = new ArrayList<>();
    }

    public ArrayList<Double> getProjection() {
        return growthValues;
    }
}

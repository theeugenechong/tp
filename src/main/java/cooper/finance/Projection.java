package cooper.finance;

import java.util.ArrayList;

//@@author ChrisLangton

public class Projection {

    public ArrayList<Double> growthValues;

    public Projection() {
        this.growthValues = new ArrayList<>();
    }

    public ArrayList<Double> getProjection() {
        return growthValues;
    }
}

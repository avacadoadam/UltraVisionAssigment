package Titles;

import java.util.Calendar;
import java.util.Date;

public class Title {

    private int titleID;
    private String titleName;
    private Calendar yearOfRelease;
    private ProductType productType;
    private boolean rented;


    public Title(int titleID, String titleName, Calendar yearOfRelease, ProductType productType, boolean rented) {
        this.titleID = titleID;
        this.titleName = titleName;
        this.yearOfRelease = yearOfRelease;
        this.productType = productType;
        this.rented = rented;
    }

    public int getTitleID() {
        return titleID;
    }

    public String getTitleName() {
        return titleName;
    }

    public Calendar getYearOfRelease() {
        return yearOfRelease;
    }

    public ProductType getProductType() {
        return productType;
    }

    public boolean isRented() {
        return rented;
    }

}
package Titles;

import java.time.LocalDate;

public class Title {

    private int titleID;
    private String titleName;
    private LocalDate yearOfRelease;
    private ProductType productType;
    private boolean rented;


    public Title(int titleID, String titleName, LocalDate yearOfRelease, ProductType productType, boolean rented) {
        this.titleID = titleID;
        this.titleName = titleName;
        this.yearOfRelease = yearOfRelease;
        this.productType = productType;
        this.rented = rented;
    }

    public Title(String titleName, LocalDate yearOfRelease, ProductType productType) {
        this.titleName = titleName;
        this.yearOfRelease = yearOfRelease;
        this.productType = productType;
        this.rented = false;
        titleID = 0;
    }

    public int getTitleID() {
        return titleID;
    }

    public String getTitleName() {
        return titleName;
    }

    public LocalDate getYearOfRelease() {
        return yearOfRelease;
    }

    public ProductType getProductType() {
        return productType;
    }

    public boolean isRented() {
        return rented;
    }

}

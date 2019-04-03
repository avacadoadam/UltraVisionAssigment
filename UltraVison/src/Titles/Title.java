package Titles;

import java.util.Date;

public abstract class Title {

    private int titleID;
    private String titleName;
    private Date yearOfRelease;
    private ProductType accessLevel;

    public Title(int titleID, String titleName, Date yearOfRelease, ProductType accessLevel) {
        this.titleID = titleID;
        this.titleName = titleName;
        this.yearOfRelease = yearOfRelease;
        this.accessLevel = accessLevel;
    }

    public int getTitleID() {
        return titleID;
    }

    public String getTitleName() {
        return titleName;
    }

    public Date getYearOfRelease() {
        return yearOfRelease;
    }

    public ProductType getProductType() {
        return accessLevel;
    }
}

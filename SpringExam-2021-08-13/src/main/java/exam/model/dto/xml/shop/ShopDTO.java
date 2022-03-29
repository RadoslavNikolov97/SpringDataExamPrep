package exam.model.dto.xml.shop;

import exam.model.dto.xml.town.TownNameDTOForXML;
import org.hibernate.validator.constraints.UniqueElements;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

@XmlAccessorType(XmlAccessType.FIELD)
public class ShopDTO {
    @XmlElement(name = "address")
    @NotNull
    @Size(min = 4)
    private String address;
    @XmlElement(name = "employee-count")
    @Min(1)
    @Max(50)
    private int employeeCount;
    @XmlElement(name = "income")
    @Min(20000)
    private double income;
    @XmlElement(name = "name")
    @NotNull
    @Size(min = 4)
    private String name;
    @XmlElement(name = "shop-area")
    @Min(150)
    private int shopArea;
    @XmlElement(name = "town")
    @NotNull
    private TownNameDTOForXML townNameDTO;

    public ShopDTO() {}

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getEmployeeCount() {
        return employeeCount;
    }

    public void setEmployeeCount(int employeeCount) {
        this.employeeCount = employeeCount;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getShopArea() {
        return shopArea;
    }

    public void setShopArea(int shopArea) {
        this.shopArea = shopArea;
    }

    public TownNameDTOForXML getTownNameDTO() {
        return townNameDTO;
    }

    public void setTownNameDTO(TownNameDTOForXML townNameDTO) {
        this.townNameDTO = townNameDTO;
    }
}

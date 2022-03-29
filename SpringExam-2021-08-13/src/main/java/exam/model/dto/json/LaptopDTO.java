package exam.model.dto.json;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import javax.validation.constraints.*;


public class LaptopDTO {

    @SerializedName("macAddress")
    @Expose
    @Size(min = 8)
    @NotNull
    private String macAddress;
    @SerializedName("cpuSpeed")
    @Expose
    @Positive
    @NotNull
    private Double cpuSpeed;
    @SerializedName("ram")
    @Expose
    @Min(8)
    @Max(128)
    @NotNull
    private Integer ram;
    @SerializedName("storage")
    @Expose
    @Min(128)
    @Max(1024)
    @NotNull
    private Integer storage;
    @SerializedName("description")
    @Expose
    @Size(min = 10)
    @NotNull
    private String description;
    @SerializedName("price")
    @Expose
    @Positive
    @NotNull
    private Double price;
    @SerializedName("warrantyType")
    @Expose
    @NotNull
    private String warrantyType;
    @SerializedName("shop")
    @Expose
    @NotNull
    private ShopNameDTO shop;

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public Double getCpuSpeed() {
        return cpuSpeed;
    }

    public void setCpuSpeed(Double cpuSpeed) {
        this.cpuSpeed = cpuSpeed;
    }

    public Integer getRam() {
        return ram;
    }

    public void setRam(Integer ram) {
        this.ram = ram;
    }

    public Integer getStorage() {
        return storage;
    }

    public void setStorage(Integer storage) {
        this.storage = storage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getWarrantyType() {
        return warrantyType;
    }

    public void setWarrantyType(String warrantyType) {
        this.warrantyType = warrantyType;
    }

    public ShopNameDTO getShop() {
        return shop;
    }

    public void setShop(ShopNameDTO shop) {
        this.shop = shop;
    }

}


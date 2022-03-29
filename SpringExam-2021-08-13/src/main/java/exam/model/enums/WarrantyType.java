package exam.model.enums;

public enum WarrantyType {
    BASIC ("BASIC"),
    PREMIUM ("PREMIUM"),
    LIFETIME("LIFETIME");


    private final String warranty;


    WarrantyType(String warency) {
        this.warranty = warency;
    }

    public String getWarranty() {
        return warranty;
    }
}

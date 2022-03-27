package softuni.exam.models.enums;

public enum Rating {
    BAD("BAD"),
    GOOD("GOOD"),
    UNKNOWN("UNKNOWN");

    private final String rating;

    Rating(String rating) {
        this.rating = rating;
    }

    public String getRating() {
        return rating;
    }
}

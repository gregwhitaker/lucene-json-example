package example.lucene.json;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class Product {

    private String id;
    private boolean active;
    private String category;
    @JsonProperty("category_code") private int categoryCode;
    @JsonProperty("short_name") private String shortName;
    @JsonProperty("long_name") private String longName;
    private String description;
    private String gender;
    private Prices prices;
    private List<Sku> skus;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getCategoryCode() {
        return categoryCode;
    }

    public void setCategoryCode(int categoryCode) {
        this.categoryCode = categoryCode;
    }

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

    public String getLongName() {
        return longName;
    }

    public void setLongName(String longName) {
        this.longName = longName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Prices getPrices() {
        return prices;
    }

    public void setPrices(Prices prices) {
        this.prices = prices;
    }

    public List<Sku> getSkus() {
        return skus;
    }

    public void setSkus(List<Sku> skus) {
        this.skus = skus;
    }

    /**
     *
     */
    public static class Prices {
        private double msrp;
        private double list;
        private double sale;

        public double getMsrp() {
            return msrp;
        }

        public void setMsrp(double msrp) {
            this.msrp = msrp;
        }

        public double getList() {
            return list;
        }

        public void setList(double list) {
            this.list = list;
        }

        public double getSale() {
            return sale;
        }

        public void setSale(double sale) {
            this.sale = sale;
        }
    }

    /**
     *
     */
    public static class Sku {
        private String sku;
        private String size;

        public String getSku() {
            return sku;
        }

        public void setSku(String sku) {
            this.sku = sku;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }
    }
}

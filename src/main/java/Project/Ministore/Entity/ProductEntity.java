package Project.Ministore.Entity;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "product")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "product_name")
    private String product_name;
    @Column(name = "description")
    private String description;
    @Column(name = "product_price")
    private Double product_price;
    @Column(name = "stock_quantity")
    private int stock_quantity;
    @Column(name = "image")
    @Lob
    private byte[] image;
    @Column(name = "origin")
    private String origin;
    @Column(name = "discount")
    private int discount;
    @Column(name = "discount_price")
    private Double discount_price;
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<ReviewEntity> reviewEntities;
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<PromotionEntity> promotionEntities;
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<ProductDetailEntity> productDetailEntities;
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<OrdersDetailEntity> ordersDetailEntities;
    @OneToMany(mappedBy = "productEntity", cascade = CascadeType.ALL)
    private List<ImageEntity> imageEntities;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "category_id",nullable = true)
    private CategoryEntity categoryEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getProduct_price() {
        return product_price;
    }

    public void setProduct_price(Double product_price) {
        this.product_price = product_price;
    }

    public int getStock_quantity() {
        return stock_quantity;
    }

    public void setStock_quantity(int stock_quantity) {
        this.stock_quantity = stock_quantity;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public List<ReviewEntity> getReviewEntities() {
        return reviewEntities;
    }

    public void setReviewEntities(List<ReviewEntity> reviewEntities) {
        this.reviewEntities = reviewEntities;
    }

    public List<PromotionEntity> getPromotionEntities() {
        return promotionEntities;
    }

    public void setPromotionEntities(List<PromotionEntity> promotionEntities) {
        this.promotionEntities = promotionEntities;
    }

    public List<ProductDetailEntity> getProductDetailEntities() {
        return productDetailEntities;
    }

    public void setProductDetailEntities(List<ProductDetailEntity> productDetailEntities) {
        this.productDetailEntities = productDetailEntities;
    }

    public List<OrdersDetailEntity> getOrdersDetailEntities() {
        return ordersDetailEntities;
    }

    public void setOrdersDetailEntities(List<OrdersDetailEntity> ordersDetailEntities) {
        this.ordersDetailEntities = ordersDetailEntities;
    }

    public List<ImageEntity> getImageEntities() {
        return imageEntities;
    }

    public void setImageEntities(List<ImageEntity> imageEntities) {
        this.imageEntities = imageEntities;
    }

    public CategoryEntity getCategoryEntity() {
        return categoryEntity;
    }

    public void setCategoryEntity(CategoryEntity categoryEntity) {
        this.categoryEntity = categoryEntity;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public Double getDiscount_price() {
        return discount_price;
    }

    public void setDiscount_price(Double discount_price) {
        this.discount_price = discount_price;
    }
}

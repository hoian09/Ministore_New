package Project.Ministore.Entity;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product_detail")
public class ProductDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "size")
    private String size;
    @Column(name = "color")
    private String color;
    @Column(name = "avaliability")
    private String avaliability;
    @Column(name = "price")
    private Double price;
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name ="product_detail_size",
            joinColumns = @JoinColumn(name = "product_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "size_id")
    )
    private Set<SizeEntity> sizeEntities = new HashSet<>();
    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
            name ="product_detail_color",
            joinColumns = @JoinColumn(name = "product_detail_id"),
            inverseJoinColumns = @JoinColumn(name = "color_id")
    )
    private Set<ColorEntity> colorEntities = new HashSet<>();
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id")
    private ProductEntity productEntity;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getAvaliability() {
        return avaliability;
    }

    public void setAvaliability(String avaliability) {
        this.avaliability = avaliability;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Set<SizeEntity> getSizeEntities() {
        return sizeEntities;
    }

    public void setSizeEntities(Set<SizeEntity> sizeEntities) {
        this.sizeEntities = sizeEntities;
    }

    public Set<ColorEntity> getColorEntities() {
        return colorEntities;
    }

    public void setColorEntities(Set<ColorEntity> colorEntities) {
        this.colorEntities = colorEntities;
    }

    public ProductEntity getProductEntity() {
        return productEntity;
    }

    public void setProductEntity(ProductEntity productEntity) {
        this.productEntity = productEntity;
    }
}

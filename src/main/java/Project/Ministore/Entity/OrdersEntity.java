package Project.Ministore.Entity;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "orders")
public class OrdersEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date date;
    @Column(name = "status")
    private String status;
    @Column(name = "total_amount")
    private Double total_amount;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shipping_address_id")
    private ShippingAddressEntity shippingAddressEntity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "promotion_id")
    private PromotionEntity promotionEntity;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "shopping_card_id")
    private ShoppingCartEntity shoppingCartEntity;
    @OneToMany(mappedBy = "ordersEntity",cascade = CascadeType.ALL)
    private List<OrdersDetailEntity> ordersDetailEntities;
    @OneToMany(mappedBy = "ordersEntity", cascade = CascadeType.ALL)
    private List<PaymentEntity> paymentEntities;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ShippingAddressEntity getShippingAddressEntity() {
        return shippingAddressEntity;
    }

    public void setShippingAddressEntity(ShippingAddressEntity shippingAddressEntity) {
        this.shippingAddressEntity = shippingAddressEntity;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Double getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(Double total_amount) {
        this.total_amount = total_amount;
    }

    public PromotionEntity getPromotionEntity() {
        return promotionEntity;
    }

    public void setPromotionEntity(PromotionEntity promotionEntity) {
        this.promotionEntity = promotionEntity;
    }

    public ShoppingCartEntity getShoppingCartEntity() {
        return shoppingCartEntity;
    }

    public void setShoppingCartEntity(ShoppingCartEntity shoppingCartEntity) {
        this.shoppingCartEntity = shoppingCartEntity;
    }

    public List<OrdersDetailEntity> getOrdersDetailEntities() {
        return ordersDetailEntities;
    }

    public void setOrdersDetailEntities(List<OrdersDetailEntity> ordersDetailEntities) {
        this.ordersDetailEntities = ordersDetailEntities;
    }

    public List<PaymentEntity> getPaymentEntities() {
        return paymentEntities;
    }

    public void setPaymentEntities(List<PaymentEntity> paymentEntities) {
        this.paymentEntities = paymentEntities;
    }
}

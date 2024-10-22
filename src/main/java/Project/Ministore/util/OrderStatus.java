package Project.Ministore.util;

public enum OrderStatus {
    IN_PROGRESS(1, "Đang tiến hành"), ORDER_RECEIVED(2, "Đã Nhận Được Đơn Hàng"),
    PRODUCT_PACKED(3, "Sản Phẩm Được Đóng Gói"), OUT_FOR_DELIVERY(4, "Ra Ngoài Để Giao Hàng"),
    DELIVERED(5, "Đã Giao Hàng"),CANCEL(6,"Đã Hủy"),SUCCESS(7,"Thành Công");

    private int id;

    private String name;

    private OrderStatus(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

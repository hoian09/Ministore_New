package Project.Ministore.Dto;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

public class OrdersAddressEntityDto {
    @NotBlank(message = "Tên không thể rỗng hoặc trống")
    private String first_name;
    @NotBlank(message = "Họ không thể rỗng hoặc trống")
    private String last_name;
    @Email(message = "Email phải hợp lệ")
    @NotBlank(message = "Email không thể rỗng hoặc trống")
    private String email;
    @NotBlank(message = "Số điện thoại không thể rỗng hoặc trống")
    private int phone;
    @NotBlank(message = "Địa chỉ không thể rỗng hoặc trống")
    private String address;
    @NotBlank(message = "Thành phố không thể rỗng hoặc trống")
    private String city;
    @NotBlank(message = "Tỉnh không thể rỗng hoặc trống")
    private String province;
    @Column(name = "payment_type")
    private String payment_type;

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public int getPhone() {
        return phone;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getPayment_type() {
        return payment_type;
    }

    public void setPayment_type(String payment_type) {
        this.payment_type = payment_type;
    }
}

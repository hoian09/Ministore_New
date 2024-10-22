package Project.Ministore.Entity;
import javax.persistence.*;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
@Entity
@Table(name = "account")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name", nullable = false)
    private String name;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "address")
    private String address;
    @Column(name = "city")
    private String city;
    @Column(name = "province")
    private String province;
    @Column(name = "pincode")
    private String pincode;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "phone")
    private Long phone;
    @Column(name = "profile_image",nullable = false)
    @Lob
    private byte[] profileImage;
    @Column(name = "role")
    private String role;
    @Column(name = "is_enable")
    private Boolean enable;
    @Column(name = "account_nonlocked")
    private Boolean account_nonlocked;
    @Column(name = "failed_attempt")
    private int failed_attempt;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "lock_time")
    private Date lock_time;
    @Column(name = "reset_token")
    private String resetToken;
    @OneToMany(mappedBy = "accountEntity", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CartEntity> cartEntities;
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public Long getPhone() {
        return phone;
    }

    public void setPhone(Long phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public byte[] getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(byte[] profileImage) {
        this.profileImage = profileImage;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<CartEntity> getCartEntities() {
        return cartEntities;
    }

    public void setCartEntities(List<CartEntity> cartEntities) {
        this.cartEntities = cartEntities;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Boolean getEnable() {
        return enable;
    }

    public void setEnable(Boolean enable) {
        this.enable = enable;
    }

    public Boolean getAccount_nonlocked() {
        return account_nonlocked;
    }

    public void setAccount_nonlocked(Boolean account_nonlocked) {
        this.account_nonlocked = account_nonlocked;
    }

    public int getFailed_attempt() {
        return failed_attempt;
    }

    public void setFailed_attempt(int failed_attempt) {
        this.failed_attempt = failed_attempt;
    }

    public Date getLock_time() {
        return lock_time;
    }

    public void setLock_time(Date lock_time) {
        this.lock_time = lock_time;
    }

    public String getResetToken() {
        return resetToken;
    }

    public void setResetToken(String resetToken) {
        this.resetToken = resetToken;
    }
}

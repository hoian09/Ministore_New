package Project.Ministore.controller;
import Project.Ministore.Dto.OrdersAddressEntityDto;
import Project.Ministore.Entity.*;
import Project.Ministore.repository.AccountRepository;
import Project.Ministore.repository.ProductRepository;
import Project.Ministore.service.*;
import Project.Ministore.util.OrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Objects;

import static org.thymeleaf.util.NumberUtils.formatCurrency;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private AccountService accountService;
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private CartService cartService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductService productService;
    @Autowired
    private OrdersService ordersService;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private VNPayService vnPayService;
    @GetMapping("/")
    public String home() {

        return "user/home";
    }

    @ModelAttribute
    public void getUserDetails(Principal principal, Model model) {
        if (principal != null) {
            String email = principal.getName();
            AccountEntity user = accountService.getUserByEmail(email);
            model.addAttribute("user", user);
            int countCart = cartService.getCountCart(user.getId());
            model.addAttribute("countCart", countCart);
        }
        List<CategoryEntity> allActiveCategory = categoryService.findByActiveTrue();
        model.addAttribute("categories", allActiveCategory);
    }

    @GetMapping("/addCart")
    public String addToCart(@RequestParam Integer pid, @RequestParam Integer uid, HttpSession session) {
        CartEntity saveCart = cartService.saveCart(pid, uid);
        if (ObjectUtils.isEmpty(saveCart)) {
            session.setAttribute("erorrMsg", "Thêm sản phẩm vào giỏ hàng không thành công");
        } else {
            session.setAttribute("succMsg", "Sản phẩm đã được thêm vào giỏ hàng");
        }
        return "redirect:/products/" + pid;
    }

    @GetMapping("/cart")
    public String loadCartPage(Principal p, Model model, HttpSession session) {
        AccountEntity user = getLoggedInUserDetails(p);
        List<CartEntity> carts = cartService.getCartByUser(user.getId());
        model.addAttribute("carts", carts);
        Long totalOrderPrice = carts.get(carts.size() - 1).getTotal_orderPrice();
        DecimalFormat df = new DecimalFormat("#,###");
        String formattedTotalOrderPrice = df.format(totalOrderPrice) + " ₫";
        model.addAttribute("totalOrderPrice", formattedTotalOrderPrice);
        return "/user/cart";
    }

    public AccountEntity getLoggedInUserDetails(Principal principal) {
        String email = principal.getName();
        AccountEntity account = accountService.getUserByEmail(email);
        return account;
    }

    @GetMapping(value = "/getProductFile/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getProductFile(@PathVariable("id") int id) {
        ProductEntity product = productRepository.findById(id).orElse(null);
        if (product != null && product.getImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(product.getImage());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping(value = "/getAccountFile/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getAccountFile(@PathVariable("id") int id) {
        AccountEntity account = accountRepository.findById(id).orElse(null);
        if (account != null && account.getProfileImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(account.getProfileImage());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/cartQuantityUpdate")
    public String updateCartQuantity(@RequestParam String sy, @RequestParam Integer cid) {
        cartService.updateQuantity(sy, cid);
        return "redirect:/user/cart";
    }

    @GetMapping("/orders")
    public String orderPage(Principal principal, Model model) {
        AccountEntity user = getLoggedInUserDetails(principal);
        List<CartEntity> carts = cartService.getCartByUser(user.getId());
        model.addAttribute("carts", carts);
        if (carts.size() > 0) {
            Long orderPrice = carts.get(carts.size() - 1).getTotal_orderPrice();
            Long totalOrderPrice = carts.get(carts.size() - 1).getTotal_orderPrice() + 25000;
            DecimalFormat df = new DecimalFormat("#,###");
            String formattedOrderPrice = df.format(orderPrice) + " ₫";
            String formattedTotalOrderPrice = df.format(totalOrderPrice) + " ₫";
            model.addAttribute("orderPrice", formattedOrderPrice);
            model.addAttribute("totalOrderPrice", formattedTotalOrderPrice);
        }
        return "/user/order";
    }

//    @PostMapping("/save-order")
//    public String saveOrders(@RequestParam String payment_type, @ModelAttribute OrdersAddressEntityDto ordersAddressEntityDto,
//                             Principal principal) {
//        AccountEntity user = getLoggedInUserDetails(principal);
//        ordersService.saveOrder(user.getId(), ordersAddressEntityDto);
//        if ("COD".equals(payment_type)) {
//            // Xử lý đơn hàng COD
//            return "redirect:/user/success"; // Redirect đến trang thành công cho COD
//        } else if ("ONLINE".equals(payment_type)) {
//            // Xử lý đơn hàng Online
//            return "redirect:/user/ordersuccess";
//        }// Redirect đến trang thành công cho Online
//        return "redirect:/error";
//    }
    @PostMapping("/save-order")
    public String saveOrders(@RequestParam String payment_type, @ModelAttribute OrdersAddressEntityDto ordersAddressEntityDto,
                             Principal principal, HttpServletRequest request) {
        AccountEntity user = getLoggedInUserDetails(principal);
        ordersService.saveOrder(user.getId(), ordersAddressEntityDto);

        // Nếu chọn phương thức COD
        if ("COD".equals(payment_type)) {
            return "redirect:/user/success";
        }
        // Nếu chọn phương thức thanh toán Online
        else if ("ONLINE".equals(payment_type)) {
            Long orderTotal = (long) cartService.getTotalCart(user.getId());// Lấy tổng giá đơn hàng
            String orderInfo = "Order by " + user.getEmail(); // Thông tin đơn hàng
            String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
            String vnpayUrl = vnPayService.createOrder(orderTotal, orderInfo, baseUrl);
            return "redirect:" + vnpayUrl;
        }

        return "redirect:/error";
    }


    @GetMapping("/success")
    public String loadSuccess() {
        return "user/success";
    }

    @GetMapping("/user-orders")
    public String myOrders(Model model, Principal principal) {
        AccountEntity loginUser = getLoggedInUserDetails(principal);
        List<OrdersEntity> orders = ordersService.getOrdersByUser(loginUser.getId());
        model.addAttribute("orders", orders);
        return "user/my-orders";
    }

    @GetMapping("/update-status")
    public String updateOrderStatus(@RequestParam int id, @RequestParam int st, HttpSession session) {
        OrderStatus[] values = OrderStatus.values();
        String status = null;

        for (OrderStatus orderSt : values) {
            if (Objects.equals(orderSt.getId(), st)) {
                status = orderSt.getName();
            }
        }
        Boolean updateOrder = ordersService.updateOrderStatus(id, status);
        if (updateOrder) {
            session.setAttribute("succMsg", "Đã Cập Nhật Trạng Thái");
        } else {
            session.setAttribute("errorMsg", "Trạng Thái Chưa Được Cập Nhật");
        }
        return "redirect:/user/user-orders";
    }

    @GetMapping("/profile")
    public String profile() {
        return "/user/profile";
    }
    @PostMapping("/update-profile")
    public String updateProfile(@RequestParam("name") String name,
                                @RequestParam("email") String email,
                                @RequestParam("address") String address,
                                @RequestParam("city") String city,
                                @RequestParam("province") String province,
                                @RequestParam("pincode") String pincode,
                                @RequestParam("phone") Long phone,
                                @RequestParam("profile_image") MultipartFile profileImage, Model model, HttpSession session) {
        try {
//             Kiểm tra logic và xử lý dữ liệu
            // Tạo đối tượng AccountEntity và thiết lập các thuộc tính
            AccountEntity user = new AccountEntity();
            user.setName(name);
            user.setEmail(email);
            user.setAddress(address);
            user.setCity(city);
            user.setProvince(province);
            user.setPincode(pincode);
            user.setPhone(phone);
            // Kiểm tra nếu có tệp hình ảnh được tải lên
            if (!profileImage.isEmpty()) {
                user.setProfileImage(profileImage.getBytes());
            }
            // Lưu dữ liệu người dùng vào cơ sở dữ liệu
            accountRepository.save(user);
            model.addAttribute("msg", "Người dùng đã đăng ký thành công!");
            return "redirect:/user/profile";
        } catch (Exception e) {
            model.addAttribute("msg", "Error: " + e.getMessage());
            return "user/profile";
        }
    }
    @PostMapping("/submitOrder")
    public String submidOrder(@RequestParam("price") Long formattedOrderPrice,
                              @RequestParam("orderInfo") String orderInfo,
                              HttpServletRequest request){
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String vnpayUrl = vnPayService.createOrder(formattedOrderPrice, orderInfo, baseUrl);
        return "redirect:" + vnpayUrl;
    }

    @PostMapping("/checkout")
    public String checkout(@RequestParam("orderInfo") String orderInfo,
                           @RequestParam("totalPrice") long totalPrice,
                           HttpServletRequest request) {

        // Gọi phương thức createOrder từ VNPayService để tạo URL thanh toán
        String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
        String paymentUrl = vnPayService.createOrder(totalPrice, orderInfo, baseUrl);

        // Chuyển hướng người dùng tới VNPay để thanh toán
        return "redirect:" + paymentUrl;
    }
}


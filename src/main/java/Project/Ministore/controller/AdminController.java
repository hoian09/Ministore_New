package Project.Ministore.controller;
import Project.Ministore.Entity.AccountEntity;
import Project.Ministore.Entity.CategoryEntity;
import Project.Ministore.Entity.OrdersEntity;
import Project.Ministore.Entity.ProductEntity;
import Project.Ministore.repository.AccountRepository;
import Project.Ministore.repository.CategoryRepository;
import Project.Ministore.repository.ProductRepository;
import Project.Ministore.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.Principal;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {
@Autowired
private CategoryService categoryService;
@Autowired
private CategoryRepository categoryRepository;
@Autowired
private ProductRepository productRepository;
@Autowired
private ProductService productService;
@Autowired
private AccountService accountService;
@Autowired
private AccountRepository accountRepository;
@Autowired
private CartService cartService;
@Autowired
private OrdersService ordersService;
    @GetMapping("/")
    public String index(){
        return "admin/index";
    }

    @ModelAttribute
    public void getUserDetails(Principal principal, Model model){
        if (principal !=null ){
            String email = principal.getName();
            AccountEntity user = accountService.getUserByEmail(email);
            model.addAttribute("user", user);
            int countCart = cartService.getCountCart(user.getId());
            model.addAttribute("countCart", countCart);
        }
        List<CategoryEntity> allActiveCategory = categoryService.findByActiveTrue();
        model.addAttribute("category", allActiveCategory);
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct(Model model){
        List<CategoryEntity> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        return "admin/add-product";
    }

@PostMapping(value = "/insertImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE, "text/plain;charset=UTF-8"})
public ModelAndView insertImage(@RequestParam("name") String name,
                         @RequestParam("is_active") Boolean active,
                         @RequestPart("image") MultipartFile image) {
    try {
        CategoryEntity category = new CategoryEntity();
        category.setName(name);
        category.setActive(active);
        category.setImage(image.getBytes());
        categoryRepository.save(category);
        return new ModelAndView("redirect:/admin/category");
    } catch (Exception e) {
        return new ModelAndView("admin/category", "msg", "Error: " + e.getMessage());
    }
}
    @GetMapping(value = "/category")
    public ModelAndView listCategory(ModelAndView model, @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo) throws IOException {
        List<CategoryEntity> listCate = (List<CategoryEntity>) categoryRepository.findAll();
        model.addObject("listCate", listCate);
        model.setViewName("admin/category");
        model.addObject("category", categoryService.getAllCategory());
        Page<CategoryEntity> list1 = categoryService.getAllCategory(pageNo);
        model.addObject("totalPage", list1.getTotalPages());// Trang hiện tại
        model.addObject("currentPage", pageNo);// Tổng số trang
        model.addObject("listCate", list1.getContent());// Lấy danh sách sản phẩm hiện tại
        model.addObject("totalCategory", list1.getTotalElements());
        return model;
    }

    @GetMapping(value = "/getCategoryFile/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getCategoryFile(@PathVariable("id") int id) {
        CategoryEntity category = categoryRepository.findById(id).orElse(null);
        if (category != null && category.getImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(category.getImage());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/deleteCategory/{id}")
    public String deleteCategory(@PathVariable(name = "id") int id){
        categoryRepository.deleteById(id);
        return "redirect:/admin/category";
    }

    @GetMapping("/loadEditCategory/{id}")
    public ModelAndView loadEditCategory(@PathVariable(name = "id") int id, Model model){
    ModelAndView mav = new ModelAndView("/admin/edit-category");
    CategoryEntity category = categoryRepository.findById(id).get();
    mav.addObject("category", category);
        return mav;
    }

    @PostMapping(value = "/saveCategory", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveCategory(@RequestParam("id") Integer id,
                               @RequestParam("name") String name,
                               @RequestParam("image") MultipartFile image,
                               @RequestParam("is_active") Boolean active,
                               Model model) {
        try {
            CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new Exception("Category not found"));
            category.setName(name);
            category.setActive(active);

            // Kiểm tra xem có tệp hình ảnh mới không
            if (!image.isEmpty()) {
                category.setImage(image.getBytes());
            }
            categoryRepository.save(category); // Lưu category đã chỉnh sửa
            return "redirect:/admin/category"; // Chuyển hướng về danh sách
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage()); // Gửi thông báo lỗi
            return "admin/category"; // Quay lại trang chỉnh sửa nếu có lỗi
        }
    }

    @PostMapping(value = "/insertImagee", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE, "text/plain;charset=UTF-8"})
    public ModelAndView insertImage(
            @RequestParam("product_name") String product_name,
            @RequestParam("description") String description,
            @RequestParam("price") Long price,
            @RequestParam("stock_quantity") Integer stock_quantity,
            @RequestParam("image") MultipartFile image,
            @RequestParam("origin") String origin,
            @RequestParam("is_active") Boolean active,
            @RequestParam(value = "category_id", required = true) Integer categoryId){
        try {
            ProductEntity product = new ProductEntity();
            product.setProduct_name(product_name);
            product.setDescription(description);
            product.setPrice(price);
            product.setStock_quantity(stock_quantity);
            product.setImage(image.getBytes());
            product.setOrigin(origin);
            product.setActive(active);
            CategoryEntity category = categoryRepository.findById(categoryId)
                    .orElseThrow(() -> new RuntimeException("Category not found"));
            product.setCategoryEntity(category);
            productRepository.save(product);
            return new ModelAndView("redirect:/admin/");
        } catch (Exception e) {
            return new ModelAndView("admin/add-product", "msg", "Error: " + e.getMessage());
        }
    }

    @GetMapping(value = "/fetchh")
    public ModelAndView listProduct(ModelAndView model) throws IOException {
        List<ProductEntity> listPro = (List<ProductEntity>) productRepository.findAll();
        model.addObject("listPro", listPro);
        model.setViewName("admin/add-product");
        return model;
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

    @GetMapping("/deleteProduct/{id}")
    public String deleteProduct(@PathVariable(name = "id") int id){
        productRepository.deleteById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/loadEditProduct/{id}")
    public ModelAndView loadEditProduct(@PathVariable(name = "id") int id, Model model){
        ModelAndView mav = new ModelAndView("/admin/edit-product");
        ProductEntity product = productRepository.findById(id).get();
        mav.addObject("product", product);
        return mav;
    }

    @PostMapping(value = "/saveProduct", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String saveProduct (@RequestParam("id") Integer id,
                            @RequestParam("product_name") String product_name,
                             @RequestParam("description") String description,
                             @RequestParam("price") Long price,
                             @RequestParam("stock_quantity") Integer stock_quantity,
                             @RequestParam("image") MultipartFile image,
                             @RequestParam("origin") String origin,
                            @RequestParam("discount") Integer discount,
                               Model model) {
    try {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new Exception("Product not found"));
        product.setProduct_name(product_name);
        product.setDescription(description);
        product.setPrice(price);
        product.setStock_quantity(stock_quantity);
        product.setImage(image.getBytes());
        product.setOrigin(origin);
        product.setDiscount_price(product.getPrice());
        // Kiểm tra xem có tệp hình ảnh mới không
        if (!image.isEmpty()) {
            product.setImage(image.getBytes());
        }

        //S = 100-5/100 = 95
        if (discount != null && discount >= 0) {
            // Lấy giá của sản phẩm
            Long originalPrice = product.getPrice(); // Giả sử giá là Long

            // Tính toán chiết khấu
            Long discountAmount = Math.round(originalPrice * (discount / 100.0)); // Tính chiết khấu và làm tròn
            Long discountedPrice = originalPrice - discountAmount; // Tính giá sau chiết khấu

            product.setPrice(discountedPrice);
        }
        product.setDiscount(discount);
        productRepository.save(product);
        // Lưu category đã chỉnh sửa
        return "redirect:/admin/products"; // Chuyển hướng về danh sách
    } catch (Exception e) {
        model.addAttribute("msg", e.getMessage()); // Gửi thông báo lỗi
        return "admin/edit-product"; // Quay lại trang chỉnh sửa nếu có lỗi
    }
}

@GetMapping("/products")
    public String showProduct(Model model,@Param("keyword") String keyword,
                              @RequestParam(name = "pageNo", defaultValue = "1") Integer pageNo){
        model.addAttribute("products", productService.getAllProduct());
    Page<ProductEntity> list;
    // Nếu có từ khóa tìm kiếm, thực hiện tìm kiếm với phân trang
    if (keyword != null && !keyword.isEmpty()){
        list = productService.searchProduct(keyword, pageNo);
        model.addAttribute("keyword", keyword);// Để hiển thị lại từ khóa trên giao diện
    }else {
        // Nếu không có từ khóa, chỉ thực hiện phân trang
        list = productService.getAllProduct(pageNo);
    }
    // Thêm các thông tin cần thiết vào model
    model.addAttribute("totalPage", list.getTotalPages());// Trang hiện tại
    model.addAttribute("currentPage", pageNo);// Tổng số trang
    model.addAttribute("products", list.getContent());// Lấy danh sách sản phẩm hiện tại
    model.addAttribute("totalProducts", list.getTotalElements());// Tổng số sản phẩm
    return "admin/products";
}
@GetMapping("/users")
    public String getAllUsers(Model model){
    List<AccountEntity> users = accountService.getUsers("ROLE_USER");
    model.addAttribute("users", users);
    return "admin/users";

}
    @GetMapping(value = "/getAccountFile/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public ResponseEntity<byte[]> getAccountFile(@PathVariable("id") int id) {
        AccountEntity user = accountRepository.findById(id).orElse(null);
        if (user != null && user.getProfileImage() != null) {
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_JPEG)
                    .body(user.getProfileImage());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @GetMapping("/updateSts")
    public String getAllUsers(@RequestParam Boolean enable, @RequestParam Integer id, HttpSession session){
    Boolean f = accountService.updateAccountStatus(id, enable);
    if (f){
        session.setAttribute("succMsg","Trạng Thái Tài khoản được cập nhật");
    }else {
        session.setAttribute("errorMsg", "Tài Khoản Chưa Cập Nhật");
    }
            return "redirect:/admin/users";
    }
    @GetMapping("/orders")
    public String getAllOrders(Model model){
        List<OrdersEntity> allOrders = ordersService.getAllOrders();
        model.addAttribute("orders",allOrders);
        return "admin/orders";
    }
}

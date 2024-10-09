package Project.Ministore.controller;
import Project.Ministore.Entity.CategoryEntity;
import Project.Ministore.Entity.ProductEntity;
import Project.Ministore.repository.CategoryRepository;
import Project.Ministore.repository.ProductRepository;
import Project.Ministore.service.CategoryService;
import Project.Ministore.service.ProductService;
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
    @GetMapping("/")
    public String index(){
        return "admin/index";
    }

    @GetMapping("/loadAddProduct")
    public String loadAddProduct(Model model){
        List<CategoryEntity> categories = categoryService.getAllCategory();
        model.addAttribute("categories", categories);
        return "admin/add-product";
    }

    @GetMapping("/category")
    public String category(){
        return "admin/category";
    }

@PostMapping(value = "/insertImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE, "text/plain;charset=UTF-8"})
public ModelAndView insertImage(@RequestParam("name") String name,
                         @RequestPart("image") MultipartFile image) {
    try {
        CategoryEntity category = new CategoryEntity();
        category.setName(name);
        category.setImage(image.getBytes());
        categoryRepository.save(category);
        return new ModelAndView("redirect:/admin/fetch");
    } catch (Exception e) {
        return new ModelAndView("admin/category", "msg", "Error: " + e.getMessage());
    }
}

    @GetMapping(value = "/fetch")
    public ModelAndView listCategory(ModelAndView model) throws IOException {
        List<CategoryEntity> listCate = (List<CategoryEntity>) categoryRepository.findAll();
        model.addObject("listCate", listCate);
        model.setViewName("admin/category");
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
        return "redirect:/admin/fetch";
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
                               Model model) {
        try {
            CategoryEntity category = categoryRepository.findById(id).orElseThrow(() -> new Exception("Category not found"));
            category.setName(name);

            // Kiểm tra xem có tệp hình ảnh mới không
            if (!image.isEmpty()) {
                category.setImage(image.getBytes());
            }
            categoryRepository.save(category); // Lưu category đã chỉnh sửa
            return "redirect:/admin/fetch"; // Chuyển hướng về danh sách
        } catch (Exception e) {
            model.addAttribute("msg", e.getMessage()); // Gửi thông báo lỗi
            return "admin/edit-category"; // Quay lại trang chỉnh sửa nếu có lỗi
        }
    }

//    @GetMapping("/products")
//    public String loadViewProduct(Model model){
//        model.addAttribute("products", productService.getAllProduct());
//        return "admin/products";
//    }

    @PostMapping(value = "/insertImagee", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE, "text/plain;charset=UTF-8"})
    public ModelAndView insertImage(
            @RequestParam("product_name") String product_name,
            @RequestParam("description") String description,
            @RequestParam("product_price") Double product_price,
            @RequestParam("stock_quantity") Integer stock_quantity,
            @RequestParam("image") MultipartFile image,
            @RequestParam("origin") String origin,
            @RequestParam(value = "category_id", required = true) Integer categoryId){
        try {
            ProductEntity product = new ProductEntity();
            product.setProduct_name(product_name);
            product.setDescription(description);
            product.setProduct_price(product_price);
            product.setStock_quantity(stock_quantity);
            product.setImage(image.getBytes());
            product.setOrigin(origin);
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
                             @RequestParam("product_price") Double product_price,
                             @RequestParam("stock_quantity") Integer stock_quantity,
                             @RequestParam("image") MultipartFile image,
                             @RequestParam("origin") String origin,
                            @RequestParam("discount") Integer discount,
                               Model model) {
    try {
        ProductEntity product = productRepository.findById(id).orElseThrow(() -> new Exception("Product not found"));
        product.setProduct_name(product_name);
        product.setDescription(description);
        product.setProduct_price(product_price);
        product.setStock_quantity(stock_quantity);
        product.setImage(image.getBytes());
        product.setOrigin(origin);
        product.setDiscount_price(product.getProduct_price());
//        product.setDiscount(discount);
//        product.setDiscount_price(product.getProduct_price());
        // Kiểm tra xem có tệp hình ảnh mới không
        if (!image.isEmpty()) {
            product.setImage(image.getBytes());
        }
        //S = 100-5/100 = 95
        if(discount != null && discount >= 0){
            Double discountAmount = product.getProduct_price() * (discount / 100.0);
            Double discoutPrice = product.getProduct_price() - discountAmount;
            product.setProduct_price(discoutPrice);
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

}

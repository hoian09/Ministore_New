package Project.Ministore.controller;

import Project.Ministore.Entity.CategoryEntity;
import Project.Ministore.Entity.ProductEntity;
import Project.Ministore.repository.CategoryRepository;
import Project.Ministore.repository.ProductRepository;
import Project.Ministore.service.CategoryService;
import Project.Ministore.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
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
        List<CategoryEntity> listStu = (List<CategoryEntity>) categoryRepository.findAll();
        model.addObject("listStu", listStu);
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
    @GetMapping("/products")
    public String loadViewProduct(Model model){
        model.addAttribute("products", productService.getAllProduct());
        return "admin/products";
    }
    @PostMapping(value = "/insertImagee", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = {MediaType.APPLICATION_JSON_VALUE, "text/plain;charset=UTF-8"})
    public ModelAndView insertImage(
            @RequestParam("product_name") String product_name,
            @RequestParam("description") String description,
            @RequestParam("product_price") Double product_price,
            @RequestParam("stock_quantity") Integer stock_quantity,
            @RequestParam("image") MultipartFile image,
            @RequestParam("origin") String origin){
        try {
            ProductEntity product = new ProductEntity();
            product.setProduct_name(product_name);
            product.setDescription(description);
            product.setProduct_price(product_price);
            product.setStock_quantity(stock_quantity);
            product.setImage(image.getBytes());
            product.setOrigin(origin);

            productRepository.save(product);
            return new ModelAndView("redirect:/admin/fetchh");
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
}

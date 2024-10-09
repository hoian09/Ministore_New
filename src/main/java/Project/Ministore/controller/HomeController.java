package Project.Ministore.controller;
import Project.Ministore.Entity.CategoryEntity;
import Project.Ministore.Entity.ProductEntity;
import Project.Ministore.repository.CategoryRepository;
import Project.Ministore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;

@Controller
public class HomeController {
@Autowired
   private CategoryRepository categoryRepository;
@Autowired
private ProductRepository productRepository;

    @GetMapping("/")
    public  String index(){
        return  "index";
    }

    @GetMapping("/login")
    public  String login(){
        return  "login";
    }

    @GetMapping("/register")
    public  String register(){
        return  "register";
    }

    @GetMapping("/products")
    public  String product(Model model){
        List<CategoryEntity> category = categoryRepository.findAll();
        List<ProductEntity> product = productRepository.findAll();
        model.addAttribute("category", category);
        model.addAttribute("product", product);
        return  "product";
    }
    @GetMapping("/view_product")
    public  String viewproduct(){
        return  "view_product";
    }

}

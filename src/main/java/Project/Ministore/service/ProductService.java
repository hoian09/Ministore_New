package Project.Ministore.service;

import Project.Ministore.Entity.ProductEntity;
import Project.Ministore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class ProductService {
@Autowired
    ProductRepository productRepository;

    public ProductEntity saveProduct(ProductEntity product){
    return productRepository.save(product);
}
    public List<ProductEntity> getAllProduct(){
    return productRepository.findAll();
}
    // Tìm kiếm sản phẩm theo từ khóa với phân trang
    public Page<ProductEntity> searchProduct(String keyword, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 8);
        return this.productRepository.searchProduct(keyword, pageable);
    }
    // Lấy tất cả sản phẩm phân trang
    public Page<ProductEntity> getAllProduct(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,8);// Số sản phẩm mỗi trang
        return this.productRepository.findAll(pageable);
    }
    public List<ProductEntity> findByPriceLessThan(Long price) {
        return productRepository.findByPriceLessThan(price);
    }

    public List<ProductEntity> findByPriceBetween(Long min, Long max) {
        return productRepository.findByPriceBetween(min, max);
    }

    public List<ProductEntity> findByPriceGreaterThan(Long price) {
        return productRepository.findByPriceGreaterThan(price);
    }
    public Page<ProductEntity> getProductsByCategory(Integer categoryId, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo - 1, 10); // Fetch 10 products per page
        return productRepository.findByCategoryId(categoryId, pageable);
    }
}

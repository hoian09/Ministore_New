package Project.Ministore.service;

import Project.Ministore.Entity.ProductEntity;
import Project.Ministore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

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
        Pageable pageable = PageRequest.of(pageNo - 1, 2);
        return this.productRepository.searchProduct(keyword, pageable);
    }
    // Lấy tất cả sản phẩm phân trang
    public Page<ProductEntity> getAllProduct(Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo-1,2);// Số sản phẩm mỗi trang
        return this.productRepository.findAll(pageable);
    }
}

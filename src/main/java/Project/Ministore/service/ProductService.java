package Project.Ministore.service;

import Project.Ministore.Entity.ProductEntity;
import Project.Ministore.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
}

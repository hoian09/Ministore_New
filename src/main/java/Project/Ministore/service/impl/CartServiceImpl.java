package Project.Ministore.service.impl;

import Project.Ministore.Entity.AccountEntity;
import Project.Ministore.Entity.CartEntity;
import Project.Ministore.Entity.ProductEntity;
import Project.Ministore.repository.AccountRepository;
import Project.Ministore.repository.CartRepository;
import Project.Ministore.repository.ProductRepository;
import Project.Ministore.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    @Autowired
     private CartRepository cartRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private ProductRepository productRepository;
    @Override
    public CartEntity saveCart(int productId, int accountId) {
        AccountEntity user = accountRepository.findById(accountId).get();
        ProductEntity product = productRepository.findById(productId).get();
        CartEntity cartStatus = cartRepository.findByProductEntity_IdAndAccountEntity_Id(productId,accountId);
        CartEntity cart = null;
        if (ObjectUtils.isEmpty(cartStatus)){
            cart = new CartEntity();
            cart.setProductEntity(product);
            cart.setAccountEntity(user);
            cart.setQuantity(cart.getQuantity()+1);
            cart.setTotal_price(1 * product.getDiscount_price());
        }else {
            cart = cartStatus;
            cart.setQuantity(cart.getQuantity() + 1);
            cart.setTotal_price(cart.getQuantity() * cart.getProductEntity().getDiscount_price());
        }
        CartEntity saveCart = cartRepository.save(cart);
        return saveCart;
    }

    @Override
    public List<CartEntity> getCartByUser(int accountId) {
        List<CartEntity> carts = cartRepository.findByAccountEntity_Id(accountId);
        Long total_orderPrice = 0L;
        List<CartEntity> updateCarts = new ArrayList<>();
        for (CartEntity c : carts){
           Long total_price = (c.getProductEntity().getPrice() * c.getQuantity());
            c.setTotal_price(total_price);
            total_orderPrice = total_orderPrice + total_price;
            c.setTotal_orderPrice(total_orderPrice);
            updateCarts.add(c);
        }
        return carts;
    }

    @Override
    public int getCountCart(int accountId) {
        int countByUserId = cartRepository.countByAccountEntity_Id(accountId);

        return countByUserId;
    }

    @Override
    public void updateQuantity(String sy, int cid) {
        CartEntity cart = cartRepository.findById(cid).get();
        int updateQuantity;
        if (sy.equalsIgnoreCase("de")){
            updateQuantity = cart.getQuantity() -1;
            if (updateQuantity <= 0){
                cartRepository.delete(cart);
            }else {
                cart.setQuantity(updateQuantity);
                cartRepository.save(cart);
            }
        } else {
            updateQuantity = cart.getQuantity() +1;
            cart.setQuantity(updateQuantity);
            cartRepository.save(cart);
        }
    }

    @Override
    public Long getTotalCart(int accountId) {
        List<CartEntity> cartItems = cartRepository.findByAccountEntity_Id(accountId);
        Long totalPrice = 0L;
        List<CartEntity> carts = getCartByUser(accountId);
        if (carts.size() > 0) {
            Long orderPrice = carts.get(carts.size() - 1).getTotal_orderPrice();
            totalPrice = carts.get(carts.size() - 1).getTotal_orderPrice() + 25000;
        }

        return totalPrice;
    }

    @Override
    public void clearCart(int accountId) {
        List<CartEntity> carts = cartRepository.findByAccountEntity_Id(accountId);
        for (CartEntity cart : carts) {
            cartRepository.delete(cart);
        }
    }
}

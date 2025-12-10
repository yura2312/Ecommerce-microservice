package com.clownstore.cart.controller;

import com.clownstore.cart.model.Cart;
import com.clownstore.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private CartService service;

    public CartController(CartService service) {
        this.service = service;
    }

//    @GetMapping("/hi")
//    public String hi(@AuthenticationPrincipal Jwt user) {
//        String subject = user.getSubject();
//        return "hi " + user.getClaimAsString("preferred_username") + "\nid: " + subject;
//    }

    @PostMapping("/")
    public ResponseEntity<Cart> addItemToCart(@AuthenticationPrincipal Jwt jwt,
                                              @RequestParam String productId,
                                              @RequestParam int quantity) {
        String userId = jwt.getSubject();
        Cart cart = service.addToCart(userId, productId, quantity);
        return ResponseEntity.ok(cart);
    }

    @GetMapping("/all")
    public ResponseEntity<List<Cart>> getAll(){
        List<Cart> allCarts = service.findAll();
        return ResponseEntity.ok(allCarts);
    }

    @GetMapping("/")
    public ResponseEntity<Cart> getById(@RequestParam String userId){
        Cart cart = service.findById(userId);
        return ResponseEntity.ok(cart);
    }
}

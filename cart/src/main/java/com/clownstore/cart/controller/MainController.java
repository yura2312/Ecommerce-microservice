package com.clownstore.cart.controller;

import com.clownstore.cart.model.Cart;
import com.clownstore.cart.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cart")
public class MainController {

    private CartService service;

    public MainController(CartService service) {
        this.service = service;
    }

    @GetMapping("/hi")
    public String hi(@AuthenticationPrincipal Jwt user) {
        String subject = user.getSubject();
        return "hi " + user.getClaimAsString("preferred_username") + "\nid: " + subject;
    }

    @PostMapping("/")
    public ResponseEntity<Cart> addItemToCart(@AuthenticationPrincipal Jwt jwt,
                                              @RequestParam String productId,
                                              @RequestParam int quantity) {
        String userId = jwt.getSubject();
        Cart cart = service.addToCart(userId, productId, quantity);
        return ResponseEntity.ok(cart);
    }
}

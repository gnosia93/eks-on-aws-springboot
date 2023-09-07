package com.example.shop.controller;

import com.example.shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@Slf4j
@RequiredArgsConstructor
@RequestMapping(value="/product")
@RestController
@Profile({"!test"})
public class ProductController {

    private final ProductService productService;

    @ResponseBody
    @RequestMapping(value="/sellCount/productId={productId}&count={count}", method=RequestMethod.PUT)
    public ResponseEntity<?> updateSellCount(@PathVariable int productId,
                                  @PathVariable int count) {
        productService.increaseSellCount(productId, count);
        int productSellCount = productService.getProductSellCount(productId);

        HashMap<String, Object> productResponse = new HashMap<String, Object>();
        productResponse.put("sellCount", productSellCount);

        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }
}

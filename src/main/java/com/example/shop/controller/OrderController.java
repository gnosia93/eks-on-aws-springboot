package com.example.shop.controller;

import com.example.shop.configuration.MsaServiceConfiguration;
import com.fasterxml.jackson.databind.util.JSONPObject;
import com.google.gson.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;


@RequestMapping(value="/order")
@RestController
public class OrderController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private MsaServiceConfiguration msaServiceConfiguration;
    private final RestTemplate restTemplate = new RestTemplate();
    @ResponseBody
    @RequestMapping(value="/memberId={memberId}&productId={productId}", method= RequestMethod.GET)
    public ResponseEntity<?> order(@PathVariable Integer memberId,
                                   @PathVariable Integer productId) {

        String prodServiceUrl = msaServiceConfiguration.getProd() + "/" + productId;
        String pointServiceUrl = msaServiceConfiguration.getPoint() + "/" + memberId;

        logger.info(prodServiceUrl);
        // prod 서비스 호출
        ResponseEntity<String> prodServiceResponse = restTemplate.getForEntity(
                prodServiceUrl, String.class);

        // point 서비스 호출
        ResponseEntity<String> pointServiceResponse = restTemplate.getForEntity(
                pointServiceUrl, String.class);

        logger.info(prodServiceResponse.getBody());
        logger.info(pointServiceResponse.getBody());

        HashMap<String, Object> orderResponse = new HashMap<String, Object>();
        orderResponse.put("prod", prodServiceResponse.getBody());
        orderResponse.put("point", pointServiceResponse.getBody());

        return ResponseEntity.status(HttpStatus.OK).body(orderResponse);
    }

}

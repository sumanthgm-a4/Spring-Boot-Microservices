package com.example.api_gateway;

import java.util.List;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GatewayServiceController {

    private final DiscoveryClient discoveryClient;

    public GatewayServiceController(DiscoveryClient discoveryClient) {
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/registered-services")
    public List<String> getServices() {
        return discoveryClient.getServices();  // Returns all registered service names
    }
}

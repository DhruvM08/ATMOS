package com.atmos.notification_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "auth-service", url = "${AUTH_SERVICE_URL:http://auth-service:8081}/api/v1/user")
public interface UserClient {

    @GetMapping("/getusers/city")
    List<Map<String, Object>> getUsersByCity(@RequestParam("city") String city);

    @GetMapping("/getusers")
    List<Map<String, Object>> getAllUsers();
}

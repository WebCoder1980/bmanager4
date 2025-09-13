package org.myproject.bmanager4.controller;

import org.myproject.bmanager4.dto.AppResponse;
import org.myproject.bmanager4.dto.CommonNodeDTO;
import org.myproject.bmanager4.service.GatewayEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/content")
public class CommonController {
    @Autowired
    private GatewayEntityService gatewayEntityService;

    @GetMapping("/Any")
    public ResponseEntity<AppResponse<Map<String, CommonNodeDTO>>> get() {
        return ResponseEntity.ok()
                .body(new AppResponse<>(gatewayEntityService.getAll(null)));
    }

    @GetMapping("/{entity}")
    public ResponseEntity<AppResponse<Map<String, CommonNodeDTO>>> get(@PathVariable String entity) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(gatewayEntityService.getAll(entity)));
    }
}

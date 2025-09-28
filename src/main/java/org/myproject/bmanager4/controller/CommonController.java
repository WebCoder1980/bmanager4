package org.myproject.bmanager4.controller;

import org.myproject.bmanager4.dto.AppResponse;
import org.myproject.bmanager4.dto.CommonNodeDTO;
import org.myproject.bmanager4.dto.CommonNodesStartAndEndDTO;
import org.myproject.bmanager4.service.GatewayEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/content")
public class CommonController {
    @Autowired
    private GatewayEntityService gatewayEntityService;

    @GetMapping("/Any")
    public ResponseEntity<AppResponse<CommonNodesStartAndEndDTO>> get() {
        return ResponseEntity.ok()
                .body(new AppResponse<>(gatewayEntityService.getAll(null)));
    }

    @GetMapping("/{entity}")
    public ResponseEntity<AppResponse<CommonNodesStartAndEndDTO>> get(@PathVariable String entity) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(gatewayEntityService.getAll(entity)));
    }

    @PostMapping("/{entity}")
    public ResponseEntity<AppResponse<CommonNodeDTO>> create(
            @PathVariable String entity,
            @RequestBody Map<String, Object> properties
    ) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(gatewayEntityService.create(entity, properties)));
    }
}

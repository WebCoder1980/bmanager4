package org.myproject.bmanager4.controller;

import org.myproject.bmanager4.dto.AppResponse;
import org.myproject.bmanager4.dto.CommonNodeDTO;
import org.myproject.bmanager4.dto.CommonNodesStartAndEndDTO;
import org.myproject.bmanager4.service.entityservice.Neo4jQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/neo4j/query")
public class Neo4jQueryController {
    @Autowired
    private Neo4jQueryService neo4jQueryService;

    @PostMapping("/get-all")
    public ResponseEntity<AppResponse<CommonNodesStartAndEndDTO>> getAll(@RequestBody String queryStr) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(neo4jQueryService.getAll(queryStr)));
    }

    @PostMapping("/execute")
    public ResponseEntity<AppResponse<String>> execute(@RequestBody String queryStr) {
        neo4jQueryService.execute(queryStr);
        return ResponseEntity.ok()
                .body(new AppResponse<>("OK"));
    }

    @PostMapping("/execute-n")
    public ResponseEntity<AppResponse<CommonNodeDTO>> executeN(@RequestBody String queryStr) {
        return ResponseEntity.ok()
                .body(new AppResponse<>(neo4jQueryService.executeNQuery(queryStr)));
    }
}

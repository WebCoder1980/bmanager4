package org.myproject.bmanager4.service;

import org.myproject.bmanager4.dto.CommonNodeDTO;
import org.myproject.bmanager4.service.entityservice.CommonEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GatewayEntityService {
    @Autowired
    private CommonEntityService commonEntityService;

    public Map<String, CommonNodeDTO> getAll(String entity) {
        return commonEntityService.getAll(entity);
    }
}

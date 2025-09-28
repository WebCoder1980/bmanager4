package org.myproject.bmanager4.service;

import org.myproject.bmanager4.dto.common.CommonNodeDTO;
import org.myproject.bmanager4.dto.common.CommonNodesStartAndEndDTO;
import org.myproject.bmanager4.service.entityservice.CommonEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GatewayEntityService {
    @Autowired
    private CommonEntityService commonEntityService;

    public CommonNodesStartAndEndDTO getAll(String entity) {
        return commonEntityService.getAll(entity);
    }

    public CommonNodeDTO create(String entity, Map<String, Object> properties) {
        return commonEntityService.create(entity, properties);
    }
}

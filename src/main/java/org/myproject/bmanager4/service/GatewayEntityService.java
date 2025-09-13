package org.myproject.bmanager4.service;

import org.myproject.bmanager4.dto.CommonNodesStartAndEndDTO;
import org.myproject.bmanager4.service.entityservice.CommonEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GatewayEntityService {
    @Autowired
    private CommonEntityService commonEntityService;

    public CommonNodesStartAndEndDTO getAll(String entity) {
        return commonEntityService.getAll(entity);
    }
}

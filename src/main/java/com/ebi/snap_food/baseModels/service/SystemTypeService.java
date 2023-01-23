package com.ebi.snap_food.baseModels.service;


import com.ebi.snap_food.baseModels.dto.SystemTypeDto;
import com.ebi.snap_food.baseModels.model.SystemType;
import com.ebi.snap_food.baseModels.repository.SystemTypeRepository;
import com.ebi.snap_food.baseModels.repository.SystemTypeSpecification;
import com.ebi.snap_food.baseModels.searchInfo.ResponseList;
import com.ebi.snap_food.baseModels.searchInfo.SystemTypeSearchInfo;
import com.ebi.snap_food.infra.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Transactional
@Service
public class SystemTypeService  extends BaseService<SystemType, SystemTypeDto, Long> {

 @Autowired
 private SystemTypeRepository repository;

 @Autowired
 private SystemTypeSpecification specification;

  private Integer defaultPageSize = 3;


    @Override
    public SystemTypeRepository getRepository() {
        return repository;
    }

    @Override
    public Class<SystemTypeDto> getDtoClazz() {
        return SystemTypeDto.class;
    }

    @Override
    public Class<SystemType> getEntityClazz() {
        return SystemType.class;
    }





  //----------------------------[جست و جوی داینامیک در جدول انواع سیستم ها]----------------------------
    public ResponseList getSystemTypes(SystemTypeSearchInfo searchInfo) {
        List<SystemType> list = null;
        Page<SystemType> pages = null;
        if (searchInfo.getPageNumber() == null) {
            pages = new PageImpl<>(repository.findAll(specification.getSystemType(searchInfo)));
        } else {
            if (searchInfo.getCount() == null)
                searchInfo.setCount(defaultPageSize);
            Pageable paging = PageRequest.of(searchInfo.getPageNumber() - 1, searchInfo.getCount());
            pages = repository.findAll(specification.getSystemType(searchInfo), paging);
        }
        if (pages != null && pages.getContent() != null) {
            list = pages.getContent();
            if (list != null && list.size() > 0) {
                ResponseList responseList = new ResponseList();
                responseList.setTotalPage(pages.getTotalPages());
                responseList.setTotalCount(pages.getTotalElements());
                responseList.setPageNo(pages.getNumber() + 1);
                responseList.setResult(new ArrayList<>());
                for (SystemType systemType : list) {
                    responseList.getResult().add(systemType.convert(SystemTypeDto.class));
                }
                return responseList;
            }
        }
        return null;
    }

}

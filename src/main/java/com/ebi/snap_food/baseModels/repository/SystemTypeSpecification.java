package com.ebi.snap_food.baseModels.repository;


import com.ebi.snap_food.baseModels.model.SystemType;
import com.ebi.snap_food.baseModels.searchInfo.SystemTypeSearchInfo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;


@Component
public class SystemTypeSpecification {
    public Specification<SystemType> getSystemType(SystemTypeSearchInfo searchInfo) {
        return (root, query, CriteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
          if(searchInfo.getName() != null ) {
              predicates.add(CriteriaBuilder.like(root.get("name"), "%"+searchInfo.getName()+"%"));
          }
            query.orderBy(CriteriaBuilder.desc(root.get("name")));
            return CriteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
      }
    }
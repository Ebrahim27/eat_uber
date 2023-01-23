package com.ebi.snap_food.baseModels.repository;

import com.ebi.snap_food.baseModels.model.CarPrice;
import com.ebi.snap_food.baseModels.searchInfo.CarPriceSearchInfo;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

    @Component
    public class CarPriceSpecification {
        public Specification<CarPrice> getCarPrice(CarPriceSearchInfo searchInfo) {
            return (root, query, CriteriaBuilder) -> {
                List<Predicate> predicates = new ArrayList<>();
                if(searchInfo.getType() != null ) {
                    predicates.add(CriteriaBuilder.like(root.get("type"), "%"+searchInfo.getType()+"%"));
                }
                if(searchInfo.getModel() != null ) {
                    predicates.add(CriteriaBuilder.equal(root.get("model"),searchInfo.getModel()));
                }
                if(searchInfo.getHash() != null ) {
                    predicates.add(CriteriaBuilder.equal(root.get("hash"), searchInfo.getHash()));
                }
                if(searchInfo.getYear() != null ) {
                    predicates.add(CriteriaBuilder.equal(root.get("year"),searchInfo.getYear()));
                }
                if(searchInfo.getPrice() != null ) {
                    predicates.add(CriteriaBuilder.equal(root.get("price"),searchInfo.getPrice()));
                }
                if(searchInfo.getChange_percent() != null ) {
                    predicates.add(CriteriaBuilder.equal(root.get("change_"),searchInfo.getPrice()));
                }
                query.orderBy(CriteriaBuilder.desc(root.get("id")));
                return CriteriaBuilder.and(predicates.toArray(new Predicate[0]));

            };
        }
    }

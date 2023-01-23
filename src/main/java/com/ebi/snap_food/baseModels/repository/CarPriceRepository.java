package com.ebi.snap_food.baseModels.repository;

import com.ebi.snap_food.baseModels.dto.CarPriceDto;
import com.ebi.snap_food.baseModels.model.CarPrice;
import com.ebi.snap_food.infra.repository.BaseCrudRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CarPriceRepository extends BaseCrudRepository<CarPrice, Long, CarPriceDto> {

   Optional<CarPrice> findByHash(String hash);


   List<CarPrice> findByMapName(String mapName);

   @Query("select  c from CarPrice c where c.map.id =:map")
   List<CarPrice> getCarPriceByMap(@Param("map") Long map );


}

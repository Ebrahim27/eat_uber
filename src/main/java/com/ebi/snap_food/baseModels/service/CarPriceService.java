package com.ebi.snap_food.baseModels.service;


import com.ebi.snap_food.baseModels.dto.CarPriceDto;
import com.ebi.snap_food.baseModels.dto.SystemTypeDto;
import com.ebi.snap_food.baseModels.model.CarPrice;
import com.ebi.snap_food.baseModels.repository.CarPriceRepository;
import com.ebi.snap_food.baseModels.repository.CarPriceSpecification;
import com.ebi.snap_food.baseModels.searchInfo.CarPriceSearchInfo;
import com.ebi.snap_food.baseModels.searchInfo.ResponseList;
import com.ebi.snap_food.infra.service.BaseService;
import com.ebi.snap_food.utils.ErrorMessage;
import ir.iixgateway.iixswitch.arena.SourceArenaService;
import ir.iixgateway.iixswitch.arena.model.CarListResponseItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Transactional
@Service
public class CarPriceService extends BaseService<CarPrice, CarPriceDto, Long> {

    @Autowired
    private CarPriceRepository repository;
    @Autowired
    private SourceArenaService sourceArenaService;
    @Autowired
    private CarPriceSpecification specification;
    @Autowired
    private SystemTypeService systemTypeService;

    private Integer defaultPageSize = 3;

    @Override
    public CarPriceRepository getRepository() {
        return repository;
    }

    @Override
    public Class<CarPriceDto> getDtoClazz() {
        return CarPriceDto.class;
    }

    @Override
    public Class<CarPrice> getEntityClazz() {
        return CarPrice.class;
    }


    public List<CarPrice> findCarPriceBySystemType(Long systemType ){
        return repository.getCarPriceByMap(systemType);

    }


    // -----------------------------[ویرایش رکورد ]------------------------------------------
    public ResponseEntity<Object> updateCarPrice(@RequestBody CarPriceDto dto) {
        CarPriceDto carPriceDto = findById(dto.getId());
        if (carPriceDto == null) {
            return ResponseEntity.badRequest().headers
                    (new HttpHeaders()).
                    body(new ErrorMessage(200, "رکوردی با مشخصات وارد شده یافت نشد ! "));
        }
        if (dto.getModel() != null)
            carPriceDto.setModel(dto.getModel());
        if (dto.getType() != null)
            carPriceDto.setType(dto.getType());
        if (dto.getType_en() != null)
            carPriceDto.setType_en(dto.getType_en());
        if (dto.getYear() != null)
            carPriceDto.setYear(dto.getYear());
        if (dto.getPrice() != null)
            carPriceDto.setPrice(dto.getPrice());
        if (dto.getChange_percent() != null)
            carPriceDto.setChange_percent(dto.getChange_percent());
        if (dto.getMarket_price() != null)
            carPriceDto.setMarket_price(dto.getMarket_price());
        if (dto.getLast_update() != null)
            carPriceDto.setLast_update(dto.getLast_update());
        if (dto.getKeyField() != null)
            carPriceDto.setKeyField(dto.getKeyField());
        if (dto.getHash() != null)
            carPriceDto.setHash(dto.getHash());
        update(carPriceDto);
        return ResponseEntity.ok().headers
                (new HttpHeaders()).
                body(new ErrorMessage(200, dto.getId() + "ویرایش با موفقیت انجام شد    شماره پیگیری: "));
    }


    public void updateCarPrice() {

        List<CarListResponseItem> carListResponseItems = sourceArenaService.carList();
        for (CarListResponseItem item : carListResponseItems) {
            CarPrice carPrice = repository.findByHash(item.getHash()).orElse(new CarPrice());
            carPrice.setYear(item.getYear());
            carPrice.setChange_percent(item.getChangePercent());
            carPrice.setDescription(item.getDescription());
            carPrice.setHash(item.getHash());
            carPrice.setKeyField(item.getKey());
            carPrice.setMarket_price(item.isMarketPrice());
            carPrice.setLast_update(item.getLastUpdate());
            carPrice.setModel(item.getModel());
            carPrice.setType(item.getType());
            carPrice.setType_en(item.getTypeEn());
            carPrice.setPrice(Long.parseLong(item.getPrice()));
            repository.saveAndFlush(carPrice);
        }
    }

    public double getAveragePrice(String mapName) {

        return repository.findByMapName(mapName).stream().mapToLong(CarPrice::getPrice).average().orElse(.0);
    }

    public List<CarPriceDto> getCarPrice(String mapName) {

        return repository.findByMapName(mapName).stream().map(carPrice -> carPrice.convert(CarPriceDto.class)).collect(Collectors.toList());
    }




//-------------------------------------------[ویرایش map رکوردها از جدول خودروها ]----------------------------------
    public ResponseEntity<Object> mapCarTypes( List<CarPriceDto> carPriceList, Long systemTypeId) {
        if (systemTypeId == null) {
            return ResponseEntity.badRequest().headers
                            (new HttpHeaders()).
                    body(new ErrorMessage(400, "لطفا شناسه نوع خودرو را وارد نمایید !"));
        }
        SystemTypeDto systemTypeDto = systemTypeService.findById(systemTypeId);
        if (systemTypeDto == null) {
            return ResponseEntity.badRequest().headers
                            (new HttpHeaders()).
                    body(new ErrorMessage(400, "نوع خودرو وارد شده موجود نمی باشد !"));
        }
        for (CarPriceDto carPriceDtoList : carPriceList) {
            CarPriceDto carPriceDto = findById(carPriceDtoList.getId());
            if (carPriceDto ==  null) {
            return ResponseEntity.badRequest().headers
                                (new HttpHeaders()).
                        body(new ErrorMessage(400, carPriceDtoList.getId() + " خودرو وارد شده یافت نشد !"));
            }
                carPriceDto.setMap(systemTypeDto);
               update(carPriceDto);
        }
                return ResponseEntity.ok().headers
                                (new HttpHeaders()).
                        body(new ErrorMessage(200, "ثبت موفق "));

//              else {
//                return ResponseEntity.badRequest().headers
//                                (new HttpHeaders()).
//                        body(new ErrorMessage(400, carPriceDtoList.getId() + " خودرو وارد شده یافت نشد !"));
//            }

    }


 //----------------------------[جست و جوی داینامیک در جدول قیمت خودروها]-------------------------------
    public ResponseList getCarPrices(CarPriceSearchInfo searchInfo) {
        List<CarPrice> list = null;
        Page<CarPrice> pages = null;
        if (searchInfo.getPageNumber() == null) {
            pages = new PageImpl<>(repository.findAll(specification.getCarPrice(searchInfo)));
        } else {
            if (searchInfo.getCount() == null)
                searchInfo.setCount(defaultPageSize);
            Pageable paging = PageRequest.of(searchInfo.getPageNumber() - 1, searchInfo.getCount());
            pages = repository.findAll(specification.getCarPrice(searchInfo), paging);
        }
        if (pages != null && pages.getContent() != null) {
            list = pages.getContent();
            if (list != null && list.size() > 0) {
                ResponseList responseList = new ResponseList();
                responseList.setTotalPage(pages.getTotalPages());
                responseList.setTotalCount(pages.getTotalElements());
                responseList.setPageNo(pages.getNumber() + 1);
                responseList.setResult(new ArrayList<>());
                for (CarPrice carPrice : list) {
                    responseList.getResult().add(carPrice.convert(CarPriceDto.class));
                }
                return responseList;
            }
        }
        return null;
    }
}

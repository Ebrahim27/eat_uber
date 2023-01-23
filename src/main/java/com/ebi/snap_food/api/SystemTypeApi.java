package com.ebi.snap_food.api;


import com.ebi.snap_food.baseModels.dto.SystemTypeDto;
import com.ebi.snap_food.baseModels.repository.SystemTypeRepository;
import com.ebi.snap_food.baseModels.searchInfo.ResponseList;
import com.ebi.snap_food.baseModels.searchInfo.SystemTypeSearchInfo;
import com.ebi.snap_food.baseModels.service.SystemTypeService;
import com.ebi.snap_food.utils.ErrorMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("SystemType")
@CrossOrigin
public class SystemTypeApi {

 @Autowired
  private SystemTypeService service;

 @Autowired
 private SystemTypeRepository repository;






    @PostMapping("save")//ثبت رکوردی در جدول
    public ResponseEntity<Object> save(@RequestBody SystemTypeDto systemTypeDto) {
        if (systemTypeDto.getName() == null || systemTypeDto.getMap() == null ) {
            return ResponseEntity.badRequest().headers
                            (new HttpHeaders()).
                    body(new ErrorMessage(400, "نام و نقشه را کامل وارد نمایید! "));
        }
        try {
         service.save(systemTypeDto);
        return ResponseEntity.ok().headers
                        (new HttpHeaders()).
                body(new ErrorMessage(200, "ثبت موفق "));
        } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.internalServerError().headers
                        (new HttpHeaders()).
                body(new ErrorMessage(500, "خطا در عملیات لطفا مجددا سعی کنید ! "));
       }
    }




    @PutMapping("update")//ویرایش رکوردی از جدول
    public ResponseEntity<Object> update(@RequestBody SystemTypeDto dto) {
        if (dto.getId() == null) {
            return ResponseEntity.badRequest().headers
                            (new HttpHeaders()).
                    body(new ErrorMessage(400, "شناسه را برای ویرایش وارد کنید "));
        }
        SystemTypeDto systemTypeDto = service.findById(dto.getId());
        if (systemTypeDto == null) {
            return ResponseEntity.badRequest().headers
                            (new HttpHeaders()).
                    body(new ErrorMessage(400, " رکورد یافت نشد! "));
        }
        if(dto.getName() != null)
            systemTypeDto.setName(dto.getName());
        if(dto.getMap() != null)
            systemTypeDto.setMap(dto.getMap());
        try {
             service.update(dto);
            return ResponseEntity.ok().headers
                            (new HttpHeaders()).
                    body(new ErrorMessage(200,systemTypeDto.getId()+"ویرایش با موفقیت انجام شد    شماره پیگیری : "));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().headers
                            (new HttpHeaders()).
                    body(new ErrorMessage(500, "خطا در عملیات لطفا مجددا سعی کنید ! "));
        }
    }



    @GetMapping("search-by-id/{id}")// جست و جوی یک رکورد براساس ارسال شناسه رکورد
    @ResponseBody
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            SystemTypeDto systemTypeDto  = service.findById(id);
            if ( systemTypeDto == null) {
                return ResponseEntity.badRequest().headers
                                (new HttpHeaders()).
                        body(new ErrorMessage(400, " رکوردی  یافت نشد !  "));
            }
            return ResponseEntity.ok().headers
                            (new HttpHeaders()).
                    body(systemTypeDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().headers
                            (new HttpHeaders()).
                    body(new ErrorMessage(500, "خطا در عمليات لطفا مجددا سعي کنيد "));
        }
    }


    @GetMapping("find-all")//مشاهده لیست کامل از همه رکوردها
    @ResponseBody
    public ResponseEntity<Object> findAll() {
        try {
            List<SystemTypeDto> systemTypeDtoList = service.findAll();
            if (systemTypeDtoList.size()==0) {
                return ResponseEntity.badRequest().headers
                                (new HttpHeaders()).
                        body(new ErrorMessage(200, " لیست خالی است!  "));
            }
            return ResponseEntity.ok().headers
                            (new HttpHeaders()).
                    body(systemTypeDtoList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().headers
                            (new HttpHeaders()).
                    body(new ErrorMessage(500, "خطا در عمليات لطفا مجددا سعي کنيد "));
        }
    }



    @DeleteMapping("delete/{id}")//حذف رکوردی از جدول با آیدی
    @ResponseBody
    public ResponseEntity<Object> delete(@PathVariable Long id) throws EmptyResultDataAccessException {
        if (id == null) {
            return ResponseEntity.badRequest().headers
                            (new HttpHeaders()).
                    body(new ErrorMessage(400, "شناسه را برای حذف انتخاب کنید"));
        }
        try {
            service.delete(id);
            return ResponseEntity.ok().headers
                            (new HttpHeaders()).
                    body(new ErrorMessage(200, "حذف موفق"));
        } catch (EmptyResultDataAccessException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().headers
                            (new HttpHeaders()).
                    body(new ErrorMessage(500, "شناسه وارد شده یافت نشد ! "));
        }
    }



    @PostMapping("getSystemType")
    @ResponseBody
    public ResponseEntity<Object> searchSystemType(@RequestBody SystemTypeSearchInfo searchInfo) {
        try {
            ResponseList systemTypes = service.getSystemTypes(searchInfo);
            return ResponseEntity.ok().headers(new HttpHeaders()).body(systemTypes != null ? systemTypes : new ErrorMessage(200, "دیتایی یافت نشد"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.ok().body(new ErrorMessage(400, "خطا در عمليات لطفا دوباره سعي کنيد"));

        }
    }
}

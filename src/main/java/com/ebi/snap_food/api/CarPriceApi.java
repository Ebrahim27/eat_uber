package com.ebi.snap_food.api;

import com.ebi.snap_food.baseModels.dto.CarPriceDto;
import com.ebi.snap_food.baseModels.model.CarPrice;
import com.ebi.snap_food.baseModels.searchInfo.CarPriceSearchInfo;
import com.ebi.snap_food.baseModels.searchInfo.ResponseList;
import com.ebi.snap_food.baseModels.service.CarPriceService;
import com.ebi.snap_food.utils.ErrorMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;



@RestController
@RequestMapping("CarPrice")
@CrossOrigin
@Slf4j
public class CarPriceApi {

    @Autowired
    private CarPriceService service;




    // ------------------------------------------------------[ثبت]--------------------------------------------------
    @PostMapping("save")//ثبت رکوردی از جدول
    public ResponseEntity<Object> save(@RequestBody CarPriceDto carPriceDto) {// شرط های کنترلی باید به تایید خانم مهندس حاجیلو برسد
        try {
            if (carPriceDto.getModel() == null || carPriceDto.getType() == null) {
                return ResponseEntity.badRequest().headers
                        (new HttpHeaders()).
                        body(new ErrorMessage(400, "مدل  و نوع اتومبیل را وارد نمایید! "));
            }
            if (carPriceDto.getPrice() == null) {
                return ResponseEntity.badRequest().headers
                        (new HttpHeaders()).
                        body(new ErrorMessage(400, "قیمت اتومبیل را وارد نمایید! "));
            }
            if (carPriceDto.getYear() == null) {
                return ResponseEntity.badRequest().headers
                        (new HttpHeaders()).
                        body(new ErrorMessage(400, "سال تولید اتومبیل را وارد نمایید! "));
            }
            service.save(carPriceDto);
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

// ------------------------------------------------------[ویرایش map رکوردها از جدول خودروها ]------------------------------------
@PutMapping("mapCarType/{systemTypeId}")
public ResponseEntity<Object> mapCarType( @RequestBody List<CarPriceDto> carPriceDto,
                                         @PathVariable Long systemTypeId) {
        try {
              return service.mapCarTypes(carPriceDto, systemTypeId);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().headers
                            (new HttpHeaders()).
                    body(new ErrorMessage(500, "خطا در عمليات لطفا مجددا سعي کنيد ! "));
        }
  }


// -----------------------------------------[دریافت لیست مشخصات خودرو ها با ارسال آیدی نوع خودرو]----------------------------------
    @GetMapping("findCarPrice/{systemTypeId}")
    public ResponseEntity<Object> findCarPrice(@PathVariable Long systemTypeId){
        if(systemTypeId == null )
            return ResponseEntity.badRequest().headers
                            (new HttpHeaders()).
                    body(new ErrorMessage(400, " لطفا شناسه مشتری را وارد نمایید!"));
        try{
            List<CarPrice> carPrices  = service.findCarPriceBySystemType(systemTypeId);
            if(carPrices.size() == 0 ) {
                return ResponseEntity.badRequest().headers
                                (new HttpHeaders()).
                        body(new ErrorMessage(400, "خودرویی برای نوع وارد شده ثبت نشده است !"));
            }
            return ResponseEntity.ok().headers
                            (new HttpHeaders()).
                    body(carPrices);
        }catch (Exception e){
            e.printStackTrace();
            return ResponseEntity.badRequest().headers
                            (new HttpHeaders()).
                    body(new ErrorMessage(400, " خطا در عملیات لطفا مجددا سعی نمایید !"));
        }
    }


    // ------------------------------------------------------[ویرایش]--------------------------------------------------
    @PutMapping("update")//ویرایش رکوردی از جدول کنترل در کلاس سرویس
    public ResponseEntity<Object> update(@RequestBody CarPriceDto dto) {
        if (dto.getId() == null) {
            return ResponseEntity.badRequest().headers
                    (new HttpHeaders()).
                    body(new ErrorMessage(400, "شناسه را برای ویرایش وارد کنید "));
        }
        try {
            return service.updateCarPrice(dto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().headers
                    (new HttpHeaders()).
                    body(new ErrorMessage(500, "خطا در عملیات لطفا مجددا سعی کنید ! "));
        }
    }


// ------------------------------------------------------[جست و جو با شناسه رکورد]------------------------------------

    @GetMapping("search-by-id/{id}")// جست و جوی یک رکورد براساس ارسال شناسه رکورد
    @ResponseBody
    public ResponseEntity<Object> findById(@PathVariable Long id) {
        try {
            CarPriceDto carPriceDto = service.findById(id);
            if (carPriceDto == null) {
                return ResponseEntity.badRequest().headers
                        (new HttpHeaders()).
                        body(new ErrorMessage(400, " رکوردی  یافت نشد !  "));
            }
            return ResponseEntity.ok().headers
                    (new HttpHeaders()).
                    body(carPriceDto);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().headers
                    (new HttpHeaders()).
                    body(new ErrorMessage(500, "خطا در عمليات لطفا مجددا سعي کنيد "));
        }
    }

    // ------------------------------------------------------[دریافت لیست کامل]--------------------------------------------------
    @GetMapping("find-all")//مشاهده لیست کامل از همه رکوردها
    @ResponseBody
    public ResponseEntity<Object> findAll() {
        try {
            List<CarPriceDto> carPriceDtoList = service.findAll();
            if (carPriceDtoList.size() == 0) {
                return ResponseEntity.badRequest().headers
                        (new HttpHeaders()).
                        body(new ErrorMessage(200, " لیست خالی است!  "));
            }
            return ResponseEntity.ok().headers
                    (new HttpHeaders()).
                    body(carPriceDtoList);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().headers
                    (new HttpHeaders()).
                    body(new ErrorMessage(500, "خطا در عمليات لطفا مجددا سعي کنيد "));
        }
    }


    // ------------------------------------------------------[حذف رکورد]--------------------------------------------------
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


    @PostMapping("update-car-price")
    @ResponseBody
    public ResponseEntity<Object> updateCarPrice() {
        try {
            service.updateCarPrice();

            return ResponseEntity.ok().headers(new HttpHeaders()).body(new ErrorMessage(200, "عملیات با موفقیت انجام شد"));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok().body(new ErrorMessage(400, "خطا در عمليات لطفا دوباره سعي کنيد"));

        }
    }

    @GetMapping("get-average-price/{mapName}")
    @ResponseBody
    public ResponseEntity<Object> getAveragePrice(@PathVariable String mapName) {
        try {


            double result =service.getAveragePrice(mapName);
            DecimalFormat df = new DecimalFormat("0", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
            df.setMaximumFractionDigits(340);
//String  stringResult= (df.format(result));

            return ResponseEntity.ok().headers(new HttpHeaders()).body(df.format(result));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok().body(new ErrorMessage(400, "خطا در عمليات لطفا دوباره سعي کنيد"));

        }
    }

    @GetMapping("get-car-price/{mapName}")
    @ResponseBody
    public ResponseEntity<Object> getCarPrice(@PathVariable String mapName) {
        try {

            return ResponseEntity.ok().headers(new HttpHeaders()).body(service.getCarPrice(mapName));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return ResponseEntity.ok().body(new ErrorMessage(400, "خطا در عمليات لطفا دوباره سعي کنيد"));

        }
    }


  // ------------------------------------------------------[جست و جوی داینامیک در جدول خودروها]--------------------------------------------------
    @PostMapping("getCarPrice")
    @ResponseBody
    public ResponseEntity<Object> searchCarPrice(@RequestBody CarPriceSearchInfo searchInfo) {
        try {
            ResponseList carPrices = service.getCarPrices(searchInfo);
            return ResponseEntity.ok().headers(new HttpHeaders()).body(carPrices != null ? carPrices : new ErrorMessage(200, "دیتایی یافت نشد"));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(new ErrorMessage(500, "خطا در عمليات لطفا دوباره سعي کنيد"));

        }
    }
}

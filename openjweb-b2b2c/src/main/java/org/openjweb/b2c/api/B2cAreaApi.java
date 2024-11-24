package org.openjweb.b2c.api;


import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.openjweb.b2c.entity.B2cArea;
import org.openjweb.b2c.module.params.B2cAreaParam;
import org.openjweb.b2c.service.B2cAreaService;
import org.openjweb.common.response.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/b2c/b2carea/")
@Slf4j
public class B2cAreaApi {
    //http://localhost:8001/api/b2c/b2carea/edit?rowId=22391594cb9146219943357c9c072c32

    @Autowired
    B2cAreaService b2cAreaService;

    @RequestMapping("/edit")
    public ResponseResult edit(B2cAreaParam param){

        B2cArea ent = this.b2cAreaService.getObject(param.getRowId());
        return ResponseResult.okResult(ent);

    }

    /**
     * 根据页面上选择的ID批量删除
     * @param selectedIds
     * @return
     */
    @RequestMapping("/del")
    public ResponseResult del( String selectedIds){
        //http://localhost:8001/api/b2c/b2carea/del?selectedIds=12,133
        try{
            this.b2cAreaService.del(selectedIds);
            return ResponseResult.okResult(0,"删除成功!");
        }
        catch(Exception ex){
            ex.printStackTrace();
            return ResponseResult.errorResult(-1,"删除失败!");
        }
    }

    @RequestMapping("/save")
    public ResponseResult save( B2cAreaParam param){
        try {
            log.info("新增记录调用开始..........");
            //http://localhost:8001/api/b2c/b2carea/save?areaCode=8888888&areaName=9999&recordVersion=0
            //没配乐观锁插件的时候，record_version必须手工赋值
            //http://localhost:8001/api/b2c/b2carea/save?areaCode=8888888&areaName=121212
            //param.setCreateDt(StringUtil.getCurrentDateTime());
            //param.setUpdateDt(StringUtil.getCurrentDateTime());
            //param.setCreateUid("system");
            //param.setUpdateUid("system");
            this.b2cAreaService.save(param);
            log.info("新增记录调用结束..........");
            return ResponseResult.okResult(0,"新增成功!");
        }
        catch(Exception  ex){
            ex.printStackTrace();
            return ResponseResult.errorResult(-1,"新增失败!");
        }

    }
    //http://localhost:8001/api/b2c/b2carea/save2?rowId=22391594cb9146219943357c9c072c32&token=admin111
    @RequestMapping("/save2")
    public ResponseResult save2( B2cAreaParam param){
        try {
            String rowId = param.getRowId();
            log.info("根据rowId查询实体开始..........");
            B2cArea ent = this.b2cAreaService.getObject(rowId);
            log.info("根据rowId查询实体结束，开始保存..........");
            if(ent!=null) {
                log.info("修改名字.....");
                ent.setAreaName("南部大区22223333");
                ent.setUpdateUid(null);//得重置下才自动填充
                ent.setUpdateDt(null);////得重置下才自动填充 2024-02-05 11:28:40


            }
            else{
                log.info("没找到实体类.....");

            }
            this.b2cAreaService.updateById(ent);
            log.info("保存完毕..........");
        }
        catch(Exception  ex){
            ex.printStackTrace();
            return ResponseResult.errorResult(-1,"新增失败!");
        }
        return ResponseResult.okResult(0,"success");

    }

    @RequestMapping("/save3")
    public ResponseResult save3( B2cAreaParam param){
        //http://localhost:8001/api/b2c/b2carea/save3?rowId=a458530726f84808b043b396ac8402e5&areaCode=new1&areaName=newname2&recordVersion=5
        try {

            this.b2cAreaService.updateById(param);
        }
        catch(Exception  ex){
            ex.printStackTrace();
            return ResponseResult.errorResult(-1,"修改失败!");
        }
        return ResponseResult.okResult(0,"success");

    }

    @RequestMapping("/area")
    public ResponseResult testArea( B2cAreaParam param){

        IPage<B2cArea> page = this.b2cAreaService.list(param);
        return ResponseResult.okResult(page.getRecords());
    }

}

package com.msb.hjycommunity.web.controller.system;

import com.msb.hjycommunity.common.core.controller.BaseController;
import com.msb.hjycommunity.common.core.domain.BaseResponse;
import com.msb.hjycommunity.common.core.page.PageResult;
import com.msb.hjycommunity.common.utils.SecurityUtils;
import com.msb.hjycommunity.system.domain.SysDictData;
import com.msb.hjycommunity.system.service.SysDictDataService;
import com.msb.hjycommunity.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 数据字典信息
 * @author spikeCong
 * @date 2023/5/22
 **/
@RestController
@RequestMapping("/system/dict/data")
public class SysDictDataController extends BaseController {

    @Autowired
    private SysDictDataService dictDataService;

    @Autowired
    private SysDictTypeService dictTypeService;

    /**
     * 查询字典数据列表
     */
    @RequestMapping("/list")
    public PageResult list(SysDictData sysDictData){
        startPage();
        List<SysDictData> list = dictDataService.selectDictDataList(sysDictData);
        return getData(list);
    }

    /**
     * 根据Id查询字典详细信息
     */
    @GetMapping(value = "/{dictCode}")
    public BaseResponse getInfo(@PathVariable Long dictCode){

        return BaseResponse.success(dictDataService.selectDictDataById(dictCode));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/type/{dictType}")
    public BaseResponse getInfo(@PathVariable String dictType){

        return BaseResponse.success(dictTypeService.selectDictDataByType(dictType));
    }

    /**
     * 新增字典数据
     */
    @PostMapping
    public BaseResponse add(@RequestBody SysDictData sysDictData){
        sysDictData.setCreateBy(SecurityUtils.getUserName());
        return toAjax(dictDataService.insertDictData(sysDictData));
    }

    /**
     * 修改字典数据
     */
    @PutMapping
    public BaseResponse edit(@RequestBody SysDictData sysDictData){
        sysDictData.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(dictDataService.updateDictData(sysDictData));
    }

    /**
     *删除字典数据
     */
    @DeleteMapping("/{dictCodes}")
    public BaseResponse remove(@PathVariable Long[] dictCodes){

        return toAjax(dictDataService.deleteDictDataByIds(dictCodes));
    }


}

package com.msb.hjycommunity.web.controller.system;

import com.msb.hjycommunity.common.constant.UserConstants;
import com.msb.hjycommunity.common.core.controller.BaseController;
import com.msb.hjycommunity.common.core.domain.BaseResponse;
import com.msb.hjycommunity.common.core.page.PageResult;
import com.msb.hjycommunity.common.utils.SecurityUtils;
import com.msb.hjycommunity.system.domain.SysDictType;
import com.msb.hjycommunity.system.service.SysDictTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author spikeCong
 * @date 2023/5/22
 **/
@RestController
@RequestMapping("/system/dict/type")
public class SysDictTypeController extends BaseController {

    @Autowired
    private SysDictTypeService sysDictTypeService;

    /**
     * 多条件分页查询字典类型数据
     */
    @GetMapping("/list")
    public PageResult list(SysDictType dictType){
        startPage();
        List<SysDictType> list = sysDictTypeService.selectDictTypeList(dictType);
        return getData(list);
    }

    /**
     * 根据Id查询字典类型详细信息
     */
    @GetMapping(value = "/{dictId}")
    public BaseResponse getInfo(@PathVariable Long dictId){
        return BaseResponse.success(sysDictTypeService.selectDictTypeById(dictId));
    }

/*
     * 新增字典类型
     */
    @PostMapping
    public BaseResponse add(@RequestBody SysDictType sysDictType){

        if(UserConstants.NOT_UNIQUE.equals(sysDictTypeService.checkDictTypeUnique(sysDictType))){
            return BaseResponse.fail("新增字典" + sysDictType.getDictName() + "失败,字典类型已经存在");
        }
        sysDictType.setCreateBy(SecurityUtils.getUserName());
        return toAjax(sysDictTypeService.insertDictType(sysDictType));
    }

    /*
     * 修改字典类型
     */
    @PutMapping
    public BaseResponse edit(@RequestBody SysDictType sysDictType){
        if(UserConstants.NOT_UNIQUE.equals(sysDictTypeService.checkDictTypeUnique(sysDictType))){
            return BaseResponse.fail("修改字典" + sysDictType.getDictName() + "失败,字典类型已经存在");
        }
        sysDictType.setUpdateBy(SecurityUtils.getUserName());
        return toAjax(sysDictTypeService.updateDictType(sysDictType));
    }

    /*
     * 删除字典类型
     */
    @DeleteMapping("/{dictIds}")
    public BaseResponse remove(@PathVariable Long[] dictIds){
        return toAjax(sysDictTypeService.deleteDictTypeByIds(dictIds));
    }

    /**
     * 清空缓存
     */
    @DeleteMapping("/clearCache")
    public BaseResponse clearCache(){
        sysDictTypeService.clearCache();
        return BaseResponse.success("清除缓存成功");
    }
}

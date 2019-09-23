package com.ak.platform.controller.tool;

import com.ak.common.core.controller.BaseController;
import com.ak.common.core.domain.AjaxResult;
import com.ak.common.core.domain.AjaxResult.Type;
import com.ak.framework.util.RedisUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

/**
 * @author Vean
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/rest/redis")
@Api(value = "Redis云数据缓存接口", tags = "Redis云数据缓存接口")
public class RedisController extends BaseController {

    @Autowired
    private RedisUtils redisUtils;

    @RequestMapping(value = "/test", method = {RequestMethod.GET})
    @ApiOperation(value = "Redis Test信息(Content-Type为text/html)", notes = "Redis Test(Content-Type为text/html)")
    public String test() {
        redisUtils.set("Redis Test", "Redis Test");
        String string = redisUtils.get("Redis Test").toString();
        return string;
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/exists", method = {RequestMethod.GET})
    @ApiOperation(value = "Redis exists接口(Content-Type为text/html)", notes = "Redis exists接口(Content-Type为text/html)")
    public AjaxResult exists(@RequestParam(required = false) String key) {
        if (redisUtils.hasKey(key)) {
            return success("存在！");
        } else {
            return error("不存在！");
        }
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    @RequestMapping(value = "/get", method = {RequestMethod.GET})
    @ApiOperation(value = "Redis get接口(Content-Type为text/html)", notes = "Redis get接口(Content-Type为text/html)")
    public AjaxResult get(@RequestParam(required = false) String key) {
        if (redisUtils.hasKey(key)) {
            return new AjaxResult(Type.SUCCESS, "成功", redisUtils.get(key));
        } else {
            return error("不存在！");
        }
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @RequestMapping(value = "/set", method = {RequestMethod.POST})
    @ApiOperation(value = "Redis set接口(Content-Type为text/html)", notes = "Redis set接口(Content-Type为text/html)")
    public AjaxResult set(@RequestParam(required = false) String key, @RequestParam(required = false) String value) {
        if (redisUtils.set(key, value)) {
            return success("添加/更新成功！");
        } else {
            return error("添加/更新失败！");
        }
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    @RequestMapping(value = "/setExpireTime", method = {RequestMethod.POST})
    @ApiOperation(value = "Redis set expireTime接口(Content-Type为text/html)", notes = "Redis set expireTime接口接口(Content-Type为text/html)")
    public AjaxResult set(@RequestParam(required = false) String key, @RequestParam(required = false) String value,
                          @RequestParam(required = false) Long expireTime) {
        if (redisUtils.set(key, value, expireTime)) {
            return success("添加/更新成功！");
        } else {
            return error("添加/更新失败！");
        }
    }

    /**
     * 获取keys
     *
     * @param pattern
     */
    @RequestMapping(value = "/getKyes", method = {RequestMethod.GET})
    @ApiOperation(value = "Redis get kyes接口(Content-Type为text/html)", notes = "Redis get kyes接口接口(Content-Type为text/html)")
    public AjaxResult getKyes(@RequestParam(required = false) String pattern) {
        Object keys = redisUtils.get(pattern);
        return AjaxResult.success(keys);
    }


}
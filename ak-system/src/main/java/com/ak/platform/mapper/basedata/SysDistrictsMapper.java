package com.ak.platform.mapper.basedata;

import com.ak.platform.domain.basedata.SysDistricts;

import java.util.List;

public interface SysDistrictsMapper {
    /**
     * 查询地区信息
     *
     * @param id 地区ID
     * @return 地区信息
     */
    public SysDistricts selectDistrictsById(Integer id);

    /**
     * 查询地区列表
     *
     * @param districts 地区信息
     * @return 地区集合
     */
    public List<SysDistricts> selectDistrictsList(SysDistricts districts);

    /**
     * 新增地区
     *
     * @param districts 地区信息
     * @return 结果
     */
    public int insertDistricts(SysDistricts districts);

    /**
     * 修改地区
     *
     * @param districts 地区信息
     * @return 结果
     */
    public int updateDistricts(SysDistricts districts);

    /**
     * 删除地区
     *
     * @param id 地区ID
     * @return 结果
     */
    public int deleteDistrictsById(Integer id);

    /**
     * 批量删除地区
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteDistrictsByIds(String[] ids);

}
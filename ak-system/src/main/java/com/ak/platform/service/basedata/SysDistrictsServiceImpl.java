package com.ak.platform.service.basedata;

import com.ak.common.core.text.Convert;
import com.ak.platform.domain.basedata.SysDistricts;
import com.ak.platform.mapper.basedata.SysDistrictsMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Vean
 */
@Service
public class SysDistrictsServiceImpl implements ISysDistrictsService {
    @Autowired
    private SysDistrictsMapper districtsMapper;

    /**
     * 查询地区信息
     *
     * @param id 地区ID
     * @return 地区信息
     */
    @Override
    public SysDistricts selectDistrictsById(Integer id) {
        return districtsMapper.selectDistrictsById(id);
    }

    /**
     * 查询地区列表
     *
     * @param districts 地区信息
     * @return 地区集合
     */
    @Override
    public List<SysDistricts> selectDistrictsList(SysDistricts districts) {
        return districtsMapper.selectDistrictsList(districts);
    }

    /**
     * 新增地区
     *
     * @param districts 地区信息
     * @return 结果
     */
    @Override
    public int insertDistricts(SysDistricts districts) {
        return districtsMapper.insertDistricts(districts);
    }

    /**
     * 修改地区
     *
     * @param districts 地区信息
     * @return 结果
     */
    @Override
    public int updateDistricts(SysDistricts districts) {
        return districtsMapper.updateDistricts(districts);
    }

    /**
     * 删除地区对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteDistrictsByIds(String ids) {
        return districtsMapper.deleteDistrictsByIds(Convert.toStrArray(ids));
    }

}
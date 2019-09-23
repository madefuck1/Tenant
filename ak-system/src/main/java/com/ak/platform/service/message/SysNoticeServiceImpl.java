package com.ak.platform.service.message;

import com.ak.common.core.text.Convert;
import com.ak.utils.CageDataSource;
import com.ak.framework.datasource.DataSourceHolder;
import com.ak.platform.domain.message.SysNotice;
import com.ak.platform.mapper.message.SysNoticeMapper;
import com.ak.platform.mapper.system.SysDatasourceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告 服务层实现
 *
 * @author Vean
 * @date 2018-06-25
 */
@Service
public class SysNoticeServiceImpl implements ISysNoticeService {
    @Autowired
    private SysNoticeMapper noticeMapper;

    @Autowired
    private SysDatasourceMapper dataSourceMapper;

    /**
     * 查询公告信息
     *
     * @param noticeId 公告ID
     * @return 公告信息
     */
    @Override
    public SysNotice selectNoticeById(Long noticeId) {
        return noticeMapper.selectNoticeById(noticeId);
    }

    /**
     * 查询公告列表
     *
     * @param notice 公告信息
     * @return 公告集合
     */
    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice) {
        try {
            CageDataSource.action(dataSourceMapper);
            return noticeMapper.selectNoticeList(notice);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceHolder.clearDataSource();
        }
        return null;
    }

    /**
     * 新增公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int insertNotice(SysNotice notice) {
        try {
            CageDataSource.action(dataSourceMapper);
            return noticeMapper.insertNotice(notice);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            DataSourceHolder.clearDataSource();
        }
        return 0;
    }

    /**
     * 修改公告
     *
     * @param notice 公告信息
     * @return 结果
     */
    @Override
    public int updateNotice(SysNotice notice) {
        return noticeMapper.updateNotice(notice);
    }

    /**
     * 删除公告对象
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    @Override
    public int deleteNoticeByIds(String ids) {
        return noticeMapper.deleteNoticeByIds(Convert.toStrArray(ids));
    }
}

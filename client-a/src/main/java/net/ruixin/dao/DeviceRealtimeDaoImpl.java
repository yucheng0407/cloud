package net.ruixin.dao;

import net.ruixin.domain.entity.DeviceRealtime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by yucheng on 2019/7/4.
 */
@Component
public class DeviceRealtimeDaoImpl {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void saveDeviceRealtime(List<DeviceRealtime> deviceRealtimes) {
        jdbcTemplate.batchUpdate("INSERT  INTO T_DEVICE_HISTORY(SBBM,X,Y,GXSJ,ID,SFYX_ST,XGSJ,RKSJ,SD,FX,GC,JD) VALUES(?,?,?,?,SEQ_T_DEVICE_HISTORY.NEXTVAL,'1',SYSDATE,?,?,?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                DeviceRealtime deviceRealtime = deviceRealtimes.get(i);
                ps.setString(1, deviceRealtime.getVehicleNo());
                ps.setDouble(2, deviceRealtime.getLon());
                ps.setDouble(3, deviceRealtime.getLat());
                ps.setTimestamp(4, new Timestamp(deviceRealtime.getGxsj().getTime()));
                ps.setTimestamp(5, new Timestamp(deviceRealtime.getGxsj().getTime()));
                ps.setInt(6, deviceRealtime.getSpeed());
                ps.setInt(7, deviceRealtime.getDirection());
                ps.setInt(8, deviceRealtime.getAltitude());
                ps.setInt(9, deviceRealtime.getJd());
//                ps.setInt(5, i);
            }

            @Override
            public int getBatchSize() {
                return deviceRealtimes.size();
            }
        });
    }

    /**
     * 获取服务信息
     *
     * @return
     */
    public void deleteJob() {
        insertJob();
        jdbcTemplate.update("DELETE FROM T_DEVICE_HISTORY WHERE GXSJ<=TRUNC(SYSDATE-1)");
    }

    private void insertJob() {
        jdbcTemplate.update("INSERT INTO  T_DEVICE_HISTORY_OLD  SELECT * FROM T_DEVICE_HISTORY WHERE GXSJ<=TRUNC(SYSDATE-1)");
    }

}

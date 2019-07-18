package net.ruixin.dao;

import net.ruixin.domain.entity.DeviceRealtime;
import net.ruixin.domain.entity.DeviceStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by yucheng on 2019/7/4.
 */
@Component
public class DeviceStatusDaoImpl {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insertDeviceStatus(List<DeviceStatus> deviceStatues) {
        jdbcTemplate.batchUpdate("INSERT /*+append*/ INTO T_DEVICE_STATUS(ZT,ID,SBBM,X,Y,GXSJ) VALUES('1',SEQ_T_DEVICE_STATUS.NEXTVAL,?,?,?,?)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                DeviceStatus deviceStatus = deviceStatues.get(i);
                ps.setString(1, deviceStatus.getVehicleNo());
                ps.setDouble(2, deviceStatus.getLon());
                ps.setDouble(3, deviceStatus.getLat());
                ps.setDate(4, new Date(deviceStatus.getGxsj().getTime()));
            }

            @Override
            public int getBatchSize() {
                return deviceStatues.size();
            }
        });
    }

    public void updateDeviceStatus(List<DeviceStatus> deviceStatues) {
        jdbcTemplate.batchUpdate("UPDATE T_DEVICE_STATUS SET ZT='1',X=?,Y=?,GXSJ=? WHERE SBBM=?", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                DeviceStatus deviceStatus = deviceStatues.get(i);
                ps.setDouble(1, deviceStatus.getLon());
                ps.setDouble(2, deviceStatus.getLat());
                ps.setDate(3, new Date(deviceStatus.getGxsj().getTime()));
                ps.setString(4, deviceStatus.getVehicleNo());
            }

            @Override
            public int getBatchSize() {
                return deviceStatues.size();
            }
        });
    }

    public void saveDeviceStatus(List<DeviceRealtime> deviceRealtimes) {
        Map<String, DeviceRealtime> deviceMap = new HashMap<String, DeviceRealtime>();
        for (DeviceRealtime deviceRealtime : deviceRealtimes) {
            DeviceRealtime deviceRealtime1 = deviceMap.get(deviceRealtime.getVehicleNo());
            if (deviceRealtime1 == null || deviceRealtime.getGxsj().getTime() >= deviceRealtime1.getGxsj().getTime()) {
                deviceMap.put(deviceRealtime.getVehicleNo(), deviceRealtime);
            }
        }
        Collection<DeviceRealtime> valueCollection = deviceMap.values();
        final int size = valueCollection.size();
        List<DeviceRealtime> valueList = new ArrayList<DeviceRealtime>(valueCollection);
        jdbcTemplate.batchUpdate("MERGE INTO T_DEVICE_STATUS T1\n" +
                "USING (SELECT * FROM(SELECT ? SBBM,? NEWGXSJ,US.GXSJ OLDGXSJ,1 SFYX_ST FROM DUAL L LEFT JOIN T_DEVICE_STATUS US ON SBBM=?) WHERE OLDGXSJ IS NULL OR NEWGXSJ>=OLDGXSJ ) T2\n" +
                "ON (T1.SBBM = T2.SBBM AND T1.SFYX_ST = T2.SFYX_ST)\n" +
                "WHEN MATCHED THEN\n" +
                "  UPDATE\n" +
                "     SET T1.X = ?, T1.Y = ?, T1.GXSJ = ?, T1.ZT = '1', T1.XGSJ = SYSDATE\n" +
                "WHEN NOT MATCHED THEN\n" +
                "  INSERT\n" +
                "    (SFYX_ST,ZT, X, Y, SBBM, GXSJ,SD,FX,GC,JD, XGSJ,ID)\n" +
                "  VALUES\n" +
                "    ('1','1', ?, ?, ?, ?,?,?,?,?, SYSDATE, SEQ_T_DEVICE_STATUS.NEXTVAL)", new BatchPreparedStatementSetter() {
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
                DeviceRealtime deviceRealtime = valueList.get(i);
                ps.setString(1, deviceRealtime.getVehicleNo());
                ps.setTimestamp(2, new Timestamp(deviceRealtime.getGxsj().getTime()));
                ps.setString(3, deviceRealtime.getVehicleNo());
                //更新
                ps.setDouble(4, deviceRealtime.getLon());
                ps.setDouble(5, deviceRealtime.getLat());
                ps.setTimestamp(6, new Timestamp(deviceRealtime.getGxsj().getTime()));
                //添加
                ps.setDouble(7, deviceRealtime.getLon());
                ps.setDouble(8, deviceRealtime.getLat());
                ps.setString(9, deviceRealtime.getVehicleNo());
                ps.setTimestamp(10, new Timestamp(deviceRealtime.getGxsj().getTime()));
                ps.setInt(11, deviceRealtime.getSpeed());
                ps.setInt(12, deviceRealtime.getDirection());
                ps.setInt(13, deviceRealtime.getAltitude());
                ps.setInt(14, deviceRealtime.getJd());
//                ps.setInt(5, i);
            }

            @Override
            public int getBatchSize() {
                return valueList.size();
            }
        });
    }
}

package net.ruixin.dao;

import net.ruixin.domain.entity.DeviceRealtime;
import org.hibernate.annotations.SQLUpdate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.util.List;
import java.util.Map;

/**
 * @author gai
 * 设备实时信息表dao
 */
public interface IDeviceRealtimeDao extends JpaRepository<DeviceRealtime,Long> {
    /**
     * 获取服务信息
     * @param vehicleNo
     * @return
     */
    @Query(value = "SELECT R.IP, R.PORT\n" +
            "  FROM T_DEVICE_SERVICE R\n" +
            " WHERE instr(R.DEVICEAUTH,\n (SELECT T.id FROM T_DEVICE_INFOMATION T  \n WHERE T.DEVICE_CODE=:vehicleNo))>0\n" +
            "   AND R.SFYX_ST = '1'",nativeQuery = true)
    List<Map<String, Object>> getServer(@Param("vehicleNo") String vehicleNo);

    /**
     * 获取设备信息
     * @param vehicleNo
     * @return
     */
    @Query(value = "SELECT T.DEVICE_ORGAN\n" +
            "  FROM T_DEVICE_INFOMATION T\n" +
            " WHERE T.DEVICE_CODE = :vehicleNo",nativeQuery = true)
    Map getInformation(@Param("vehicleNo") String vehicleNo);

     /**
     * 获取服务信息
     * @return
     */
     @Query(value = "SELECT R.IP, R.PORT,R.DEVICEAUTH\n" +
             " FROM T_DEVICE_SERVICE R\n" +
             "WHERE  R.SFYX_ST = '1'",nativeQuery = true)
    List<Map<String,Object>> getServers();
    /**
     * 获取服务信息
     * @return
     */
    @Query(value = "SELECT DEVICE_CODE,ID,T.DEVICE_ORGAN\n" +
            "  FROM T_DEVICE_INFOMATION T",nativeQuery = true)
    List<Map<String,Object>> getInformations();

}

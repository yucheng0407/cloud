package net.ruixin.dao;

import net.ruixin.domain.entity.DeviceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * @author gai
 * 设备状态表dao
 */
public interface IDeviceStatusDao extends JpaRepository<DeviceStatus,Long> {
    /**
     * 获取所有的设备状态
     * @return
     */
    @Query(" from DeviceStatus where sfyxSt='1' ")
    List<DeviceStatus> getAllDeviceStatuses();

    /**
     * 获取设备状态根据设备号
     * @param vehicleNo
     * @return
     */
    @Query(" from DeviceStatus where vehicleNo=:vehicleNo and sfyxSt='1' ")
    DeviceStatus getDeviceStatusByVehicleNo(@Param("vehicleNo") String vehicleNo);
}

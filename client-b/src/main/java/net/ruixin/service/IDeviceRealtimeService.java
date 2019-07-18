package net.ruixin.service;

import net.ruixin.domain.entity.DeviceRealtime;
import net.ruixin.domain.entity.DeviceStatus;

import java.util.List;
import java.util.Map;

/**
 * @author gai
 *         设备实时信息表service
 */
public interface IDeviceRealtimeService {
    /**
     * 保存实时信息
     *
     * @param deviceRealtime
     */
    void saveDeviceRealtime(DeviceRealtime deviceRealtime) throws CloneNotSupportedException;

    /**
     * 保存实时信息
     *
     * @param deviceRealtimes
     */
    void saveDeviceRealtimes(List<DeviceRealtime> deviceRealtimes) throws CloneNotSupportedException;

    /**
     * 获取服务信息
     *
     * @param vehicleNo
     * @return
     */
    List<Map<String, Object>> getServer(String vehicleNo);

    /**
     * 获取服务信息
     *
     * @return
     */
    List<Map<String, Object>> getServers();

    /**
     * 获取设备信息
     *
     * @param vehicleNo
     * @return
     */
    Map getInformation(String vehicleNo);

    List<Map<String, Object>> getInformations();

    Map<String, DeviceStatus> getUpdateDeviceStatus();

    List<DeviceStatus> getInsertDeviceStatus();

    void openSaveTread(List<DeviceRealtime> deviceRealtimes, Map<String, DeviceStatus> updateDeviceStatus, List<DeviceStatus> insertDeviceStatus);

    void openSaveTread(List<DeviceRealtime> deviceRealtimes);

    void deleteJob();

}

package net.ruixin.service.impl;

import net.ruixin.activemq.DeviceRealtimeListener;
import net.ruixin.dao.DeviceRealtimeDaoImpl;
import net.ruixin.dao.DeviceStatusDaoImpl;
import net.ruixin.dao.IDeviceRealtimeDao;
import net.ruixin.dao.IDeviceStatusDao;
import net.ruixin.domain.entity.DeviceRealtime;
import net.ruixin.domain.entity.DeviceStatus;
import net.ruixin.service.IDeviceRealtimeService;
import net.ruixin.udp.UdpThreadPool;
import net.ruixin.util.constant.Sfyx_st;
import net.ruixin.util.tools.EHCacheTool;
import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;

/**
 * @author gai
 */
@Service
@Transactional(readOnly = true, rollbackFor = Exception.class)
@CacheConfig(cacheNames = EHCacheTool.DEFAULT)
public class DeviceRealtimeServiceImpl implements IDeviceRealtimeService, InitializingBean {
    @Autowired
    private IDeviceRealtimeDao deviceRealtimeDao;
    @Autowired
    private IDeviceStatusDao deviceStatusDao;
    @Autowired
    private EHCacheTool ehCacheTool;
    @PersistenceContext
    private EntityManager entityManger;
    @Autowired
    private DeviceRealtimeDaoImpl deviceRealtimeDaoImpl;
    @Autowired
    private DeviceStatusDaoImpl deviceStatusDaoImpl;
    @Autowired
    private UdpThreadPool udpThreadPool;
    public Map<String, DeviceStatus> updateDeviceStatus = new LinkedHashMap<>();
    public List<DeviceStatus> insertDeviceStatus = new ArrayList<>();
    private Cache deviceStatusCache;

    @Override
    public void afterPropertiesSet() {
        deviceStatusCache = ehCacheTool.getCache();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDeviceRealtime(DeviceRealtime deviceRealtime) throws CloneNotSupportedException {

        DeviceStatus deviceStatus;
        Element element = deviceStatusCache.get(deviceRealtime.getVehicleNo());
        if (element == null) {
            deviceStatus = deviceStatusDao.getDeviceStatusByVehicleNo(deviceRealtime.getVehicleNo());
            if (deviceStatus == null) {
                deviceStatus = new DeviceStatus();
                deviceStatus.setSfyxSt(Sfyx_st.VALID);
                deviceStatus.setZt(DeviceStatus.ZX);
                deviceStatus.setVehicleNo(deviceRealtime.getVehicleNo());
            }
        } else {
            deviceStatus = (DeviceStatus) element.getObjectValue();
        }
        //实体保存速度慢
//        deviceRealtimeDao.save(deviceRealtime);
        DeviceStatus deviceStatus1 = deviceStatus.clone();
        deviceStatus = this.DeviceRealtime2DeviceStatus(deviceStatus, deviceRealtime);
        if (deviceStatus1.getGxsj() == null || deviceStatus.getGxsj().getTime() >= deviceStatus1.getGxsj().getTime()) {
            if (deviceStatus1.getGxsj() == null) {
                insertDeviceStatus.add(deviceStatus);//添加
            } else if (deviceStatus.getGxsj().getTime() >= deviceStatus1.getGxsj().getTime()) {
                updateDeviceStatus.put(deviceRealtime.getVehicleNo(), deviceStatus);//更新
            }
            //   deviceStatusDao.save(deviceStatus);
            deviceStatusCache.put(new Element(deviceRealtime.getVehicleNo(), deviceStatus));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveDeviceRealtimes(List<DeviceRealtime> deviceRealtimes) throws CloneNotSupportedException {
        deviceRealtimeDaoImpl.saveDeviceRealtime(deviceRealtimes);
        deviceStatusDaoImpl.saveDeviceStatus(deviceRealtimes);
//        if (insertDeviceStatus.size() > 0) deviceStatusDaoImpl.insertDeviceStatus(insertDeviceStatus);
//        if (updateDeviceStatus.size() > 0) {
//            Collection<DeviceStatus> valueCollection = updateDeviceStatus.values();
//            final int size = valueCollection.size();
//            List<DeviceStatus> valueList = new ArrayList<DeviceStatus>(valueCollection);
//            deviceStatusDaoImpl.updateDeviceStatus(valueList);
//        }
//        insertDeviceStatus.clear();
//        updateDeviceStatus.clear();

    }

    @Transactional(rollbackFor = Exception.class)
    public void saveDeviceRealtimes(List<DeviceRealtime> deviceRealtimes, Map<String, DeviceStatus> updateDeviceStatus, List<DeviceStatus> insertDeviceStatus) throws CloneNotSupportedException {
//        for (int i = 0; i < deviceRealtimes.size(); i++) {
//            saveDeviceRealtime(deviceRealtimes.get(i));
//        }
        deviceRealtimeDaoImpl.saveDeviceRealtime(deviceRealtimes);
//        if (insertDeviceStatus.size() > 0) deviceStatusDaoImpl.insertDeviceStatus(insertDeviceStatus);
//        if (updateDeviceStatus.size() > 0) {
//            Collection<DeviceStatus> valueCollection = updateDeviceStatus.values();
//            final int size = valueCollection.size();
//            List<DeviceStatus> valueList = new ArrayList<DeviceStatus>(valueCollection);
//            deviceStatusDaoImpl.updateDeviceStatus(valueList);
//        }
//        insertDeviceStatus.clear();
//        updateDeviceStatus.clear();

    }
    @Override
    @Cacheable(key = "#root.method + '_' + #p0")
    public List<Map<String, Object>> getServer(String vehicleNo) {
        return deviceRealtimeDao.getServer(vehicleNo);
    }

    @Override
    @Cacheable(key = "#root.method + '_' + #p0")
    public List<Map<String, Object>> getServers() {
        return deviceRealtimeDao.getServers();
    }

    @Override
    @Cacheable(key = "#root.method + '_' + #p0")
    public Map getInformation(String vehicleNo) {
        return deviceRealtimeDao.getInformation(vehicleNo);
    }

    @Override
    @Cacheable(key = "#root.method + '_' + #p0")
    public List<Map<String, Object>> getInformations() {
        return deviceRealtimeDao.getInformations();
    }

    private DeviceStatus DeviceRealtime2DeviceStatus(DeviceStatus deviceStatus, DeviceRealtime deviceRealtime) {
        deviceStatus.setLat(deviceRealtime.getLat());
        deviceStatus.setLon(deviceRealtime.getLon());
        deviceStatus.setGxsj(deviceRealtime.getGxsj());
        deviceStatus.setAltitude(deviceRealtime.getAltitude());
        deviceStatus.setSpeed(deviceRealtime.getSpeed());
        deviceStatus.setDirection(deviceRealtime.getDirection());
        deviceStatus.setJd(deviceRealtime.getJd());
        deviceStatus.setZt(DeviceStatus.ZX);
        return deviceStatus;
    }

    public void openSaveTread(List<DeviceRealtime> deviceRealtimes, Map<String, DeviceStatus> updateDeviceStatus, List<DeviceStatus> insertDeviceStatus) {
       // udpThreadPool.getThreadPool().execute(new Task(deviceRealtimes,updateDeviceStatus,insertDeviceStatus));
    }
    public void openSaveTread(List<DeviceRealtime> deviceRealtimes) {
        udpThreadPool.getThreadPool().execute(new Task(deviceRealtimes));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteJob() {
        deviceRealtimeDaoImpl.deleteJob();
    }

    class Task implements Runnable {
        private List<DeviceRealtime> deviceRealtimesTask;
        private Map<String, DeviceStatus> updateDeviceStatusTask;
        private List<DeviceStatus> insertDeviceStatusTask;

        public Task(List<DeviceRealtime> deviceRealtimes, Map<String, DeviceStatus> updateDeviceStatus, List<DeviceStatus> insertDeviceStatus) {
            this.deviceRealtimesTask = deviceRealtimes;
            this.updateDeviceStatusTask = updateDeviceStatus;
            this.insertDeviceStatusTask = insertDeviceStatus;
        }
        public Task(List<DeviceRealtime> deviceRealtimes) {
            this.deviceRealtimesTask = deviceRealtimes;
        }
        @Override
        public void run() {
            try {
                System.out.println("进入"+Thread.currentThread().getName());
                saveDeviceRealtimes(deviceRealtimesTask);
              //  saveDeviceRealtimes(deviceRealtimesTask,updateDeviceStatusTask,insertDeviceStatusTask);
                //  DeviceRealtimeDaoImpl.this.deviceRealtimeDaoImpl.saveDeviceRealtime(deviceRealtimes);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public Map<String, DeviceStatus> getUpdateDeviceStatus() {
        return updateDeviceStatus;
    }

    public List<DeviceStatus> getInsertDeviceStatus() {
        return insertDeviceStatus;
    }

}

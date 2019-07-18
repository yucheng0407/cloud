package net.ruixin.domain.entity;

import net.ruixin.util.constant.Sfyx_st;
import net.ruixin.util.json.CustomJsonDateDeserializer;
import net.ruixin.util.tools.EHCacheTool;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Objects;

/**
 * @author gai
 * 设备状态表
 */
@Table(name = "T_DEVICE_STATUS")
@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class DeviceStatus implements Serializable,Cloneable {

    private static final long serialVersionUID = 1L;

    public static final String ZX="1";
    public static final String BZX="0";

    /**
     * 主键ID
     */
    @Id
    @SequenceGenerator(name = "SEQ_T_DEVICE_STATUS", sequenceName = "SEQ_T_DEVICE_STATUS", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_DEVICE_STATUS")
    private Long id;

    /**
     * 设备编码
     */
    @Column(name = "SBBM")
    private String vehicleNo;
    /**
     * 状态
     */
    @Column(name = "ZT")
    private String zt;
    /**
     * 更新时间
     */
    @Column(name = "GXSJ")
    private Date gxsj;
    @Column(name = "X")
    private Double lon;
    @Column(name = "Y")
    private Double lat;
    /**
     * 速度
     */
    @Column(name = "SD")
    private Integer speed;
    /**
     * 方向
     */
    @Column(name = "FX")
    private Integer direction;
    /**
     * 海拔
     */
    @Column(name = "GC")
    private Integer altitude;
    /**
     * 备注
     */
    @Column(name = "BZ")
    private String bz;
    /**
     * 精度
     */
    @Column(name = "JD")
    private Integer jd;

    /**
     * 创建人ID
     */
    @Column(name = "CJR_ID")
    @CreatedBy
    private Long cjrId;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "CJSJ")
    @CreatedDate
    private Date cjsj;

    /**
     * 修改人ID
     */
    @Column(name = "XGR_ID")
    private Long xgrId;

    /**
     * 修改时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "XGSJ")
    @LastModifiedDate
    private Date xgsj;


    /**
     * 是否有效 0:无效 1:有效
     */
    @Enumerated
    @Column(name = "SFYX_ST")
    private Sfyx_st sfyxSt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getVehicleNo() {
        return vehicleNo;
    }

    public void setVehicleNo(String vehicleNo) {
        this.vehicleNo = vehicleNo;
    }

    public String getZt() {
        return zt;
    }

    public void setZt(String zt) {
        this.zt = zt;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Integer getSpeed() {
        return speed;
    }

    public void setSpeed(Integer speed) {
        this.speed = speed;
    }

    public Integer getDirection() {
        return direction;
    }

    public void setDirection(Integer direction) {
        this.direction = direction;
    }

    public Integer getAltitude() {
        return altitude;
    }

    public void setAltitude(Integer altitude) {
        this.altitude = altitude;
    }

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Integer getJd() {
        return jd;
    }

    public void setJd(Integer jd) {
        this.jd = jd;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public Date getXgsj() {
        return xgsj;
    }

    public void setXgsj(Date xgsj) {
        this.xgsj = xgsj;
    }

    public Sfyx_st getSfyxSt() {
        return sfyxSt;
    }

    public void setSfyxSt(Sfyx_st sfyxSt) {
        this.sfyxSt = sfyxSt;
    }

    public Long getCjrId() {
        return cjrId;
    }

    public void setCjrId(Long cjrId) {
        this.cjrId = cjrId;
    }

    public Long getXgrId() {
        return xgrId;
    }

    public void setXgrId(Long xgrId) {
        this.xgrId = xgrId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeviceStatus that = (DeviceStatus) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(vehicleNo, that.vehicleNo) &&
                Objects.equals(zt, that.zt) &&
                Objects.equals(gxsj, that.gxsj) &&
                Objects.equals(lon, that.lon) &&
                Objects.equals(lat, that.lat) &&
                Objects.equals(speed, that.speed) &&
                Objects.equals(direction, that.direction) &&
                Objects.equals(altitude, that.altitude) &&
                Objects.equals(bz, that.bz) &&
                Objects.equals(jd, that.jd) &&
                Objects.equals(cjrId, that.cjrId) &&
                Objects.equals(cjsj, that.cjsj) &&
                Objects.equals(xgrId, that.xgrId) &&
                Objects.equals(xgsj, that.xgsj) &&
                sfyxSt == that.sfyxSt;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, vehicleNo, zt, gxsj, lon, lat, speed, direction, altitude, bz, jd, cjrId, cjsj, xgrId, xgsj, sfyxSt);
    }
    @Override
    public DeviceStatus clone() throws CloneNotSupportedException {
        return (DeviceStatus) super.clone();
    }
}

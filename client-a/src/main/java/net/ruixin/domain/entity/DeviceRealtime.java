package net.ruixin.domain.entity;

import net.ruixin.util.constant.Sfyx_st;
import net.ruixin.util.json.CustomJsonDateDeserializer;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author gai
 * 设备实时信息表
 */
@Table(name = "T_DEVICE_HISTORY")
@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class DeviceRealtime implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final String ACTIVEMQ_TAG="device_realtime";

    /**
     * 主键ID
     */
    @Id
    @SequenceGenerator(name = "SEQ_T_DEVICE_HISTORY", sequenceName = "SEQ_T_DEVICE_HISTORY", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_DEVICE_HISTORY")
    private Long id;
    /**
     * 设备编码
     */
    @Column(name = "SBBM")
    private String vehicleNo;
    /**
     * 入库时间
     */
    @Column(name = "RKSJ")
    private Date rksj;
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
    private Long cjrId;

    /**
     * 创建时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @CreatedDate
    @Column(name = "CJSJ")
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
    @LastModifiedDate
    @Column(name = "XGSJ")
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

    public Date getRksj() {
        return rksj;
    }

    public void setRksj(Date rksj) {
        this.rksj = rksj;
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
}

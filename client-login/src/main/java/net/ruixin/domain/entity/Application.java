package net.ruixin.domain.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 应用实体
 * Created by yucheng on 2019/7/24.
 */
@Table(name = "T_SYS_APPLICATION")
@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Application implements Serializable,Cloneable  {
    @Id
    @Column(name="DM")
    private String dm;
    @Column(name="APPLICATION")
    private String application;
    @Column(name="NAME")
    private String name;
    @Column(name="IP")
    private String ip;
    @Column(name="PORT")
    private String port;
    @Column(name="CJSJ")
    private Date cjsj;
    @OneToMany(targetEntity = Service.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumns(@JoinColumn(name = "SSDM", referencedColumnName = "DM"))
    List<Service> services;

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }
}

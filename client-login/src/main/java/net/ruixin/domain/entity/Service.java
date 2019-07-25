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
 * Created by yucheng on 2019/7/24.
 */
@Table(name = "T_SYS_SERVICE")
@Entity
@DynamicInsert
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Service implements Serializable,Cloneable {
    @Id
    @Column(name = "DM")
    private String dm;
    @Column(name = "NAME")
    private String name;
    @Column(name = "SSDM")
    private String ssdm;
    @Column(name = "CJSJ")
    private Date cjsj;
    @OneToMany(targetEntity = SubService.class, cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumns(@JoinColumn(name = "SSDM", referencedColumnName = "DM"))
    List<SubService> subServices;

    public String getDm() {
        return dm;
    }

    public void setDm(String dm) {
        this.dm = dm;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSsdm() {
        return ssdm;
    }

    public void setSsdm(String ssdm) {
        this.ssdm = ssdm;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public List<SubService> getSubServices() {
        return subServices;
    }

    public void setSubServices(List<SubService> subServices) {
        this.subServices = subServices;
    }
}

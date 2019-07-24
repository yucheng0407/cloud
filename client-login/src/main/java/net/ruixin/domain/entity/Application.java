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
@Data
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
    @OneToMany(targetEntity = Service.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumns(@JoinColumn(name = "DM", referencedColumnName = "SSDM"))
    List<Service> services;
}

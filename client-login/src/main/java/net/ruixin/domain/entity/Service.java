package net.ruixin.domain.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

/**
 * Created by yucheng on 2019/7/24.
 */
@Table(name = "T_SYS_SERVICE")
@Entity
@DynamicInsert
@DynamicUpdate
@Data
@EntityListeners(AuditingEntityListener.class)
public class Service {
    @Id
    @Column(name = "DM")
    private String dm;
    @Column(name = "NAME")
    private String name;
    @Column(name = "SSDM")
    private String ssdm;
    @Column(name = "CJSJ")
    private Date cjsj;
    @OneToMany(targetEntity = Service.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumns(@JoinColumn(name = "DM", referencedColumnName = "SSDM"))
    List<SubService> subServices;
}

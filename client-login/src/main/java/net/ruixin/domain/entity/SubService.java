package net.ruixin.domain.entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by yucheng on 2019/7/24.
 */
@Table(name = "T_SYS_SUBSERVICE")
@Entity
@DynamicInsert
@DynamicUpdate
@Data
@EntityListeners(AuditingEntityListener.class)
public class SubService {
    @Id
    @Column(name = "DM")
    private String dm;
    @Column(name = "NAME")
    private String name;
    @Column(name = "TYPE")
    private String type;
    @Column(name = "URL")
    private String url;
    @Column(name = "CJSJ")
    private Date cjsj;
    @Column(name = "SSDM")
    private String ssdm;
}

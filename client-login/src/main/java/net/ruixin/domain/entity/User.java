package net.ruixin.domain.entity;

import lombok.Data;
import net.ruixin.util.constant.Sfyx_st;
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
@Table(name = "SYS_USER")
@Entity
@DynamicInsert
@DynamicUpdate
@Data
@EntityListeners(AuditingEntityListener.class)
public class User implements Serializable,Cloneable {

    private static final long serialVersionUID = 1L;

    public static final String ZX="1";
    public static final String BZX="0";

    /**
     * 主键ID
     */
    @Id
//    @SequenceGenerator(name = "SEQ_T_DEVICE_STATUS", sequenceName = "SEQ_T_DEVICE_STATUS", allocationSize = 1)
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_T_DEVICE_STATUS")
    private Integer id;

    /**
     * 设备编码
     */
    @Column(name = "USERNAME")
    private String username;
    /**
     * 设备编码
     */
    @Column(name = "LOGINNAME")
    private String loginName;
    /**
     * 状态
     */
    @Column(name = "PASSWORD")
    private String passWord;
    /**
     * 状态
     */
    @Column(name = "ZT")
    private String zt;
    /**
     * 更新时间
     */
    @Column(name = "CJSJ")
    private Date cjsj;

}

package com.diaoruifeng.domian;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "ta_order")
@Getter
@Setter
public class Order implements Serializable {
    private static final long serialVersionUID = 7763496390090672385L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String orderid;
    private String name;
    @DateTimeFormat(pattern = "yyyy-MM-dd HH-mm-ss")
    private Date createdate;

    @OneToMany(mappedBy = "ta_order",cascade=CascadeType.ALL,fetch=FetchType.LAZY)
    private List<Shop> shopList;
}

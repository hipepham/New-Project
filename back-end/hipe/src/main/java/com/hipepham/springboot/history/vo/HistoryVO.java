package com.hipepham.springboot.history.vo;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class HistoryVO {
    private String action;
    private String oldValue;
    private String newValue;
    private Date createTime;
    private String property;
    private String modifiedBy;

    public HistoryVO(String action, String oldValue, String newValue, Date createTime, String property, String modifiedBy) {
        this.action = action;
        this.oldValue = oldValue;
        this.newValue = newValue;
        this.createTime = createTime;
        this.property = property;
        this.modifiedBy = modifiedBy;
    }
}

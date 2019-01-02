package com.wisdom.project.base.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author HanXueFeng
 * @ProjectName project： Nhoa
 * @class package：com.wisdom.nhoa.base
 * @class describe：
 * @time 2018/3/26 13:06
 * @change
 */

public class EmptyListModel implements Serializable {
private List result;

    public List getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }
}

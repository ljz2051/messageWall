package com.springapp.mvc.commons.interceptor.mybatis.page;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by py on 2017/2/24.
 */
public class Page {
    private PageParameter page;
    private Map<String, Object> map = new HashMap<String, Object>();
    private Object ob;

    public Page() {
        super();
    }

    public Page(PageParameter page, Map<String, Object> map, Object ob) {
        this.page = page;
        this.map = map;
        this.ob = ob;
    }

    public PageParameter getPage() {
        return page;
    }

    public void setPage(PageParameter page) {
        this.page = page;
    }

    public Object getOb() {
        return ob;
    }

    public void setOb(Object ob) {
        this.ob = ob;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}

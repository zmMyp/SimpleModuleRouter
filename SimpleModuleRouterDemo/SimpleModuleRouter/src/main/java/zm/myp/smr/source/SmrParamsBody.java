package zm.myp.smr.source;

import java.util.HashMap;

/**
 * Created by qianjian on 2019/4/25.
 */

public class SmrParamsBody extends HashMap<String,Object> {

    public SmrParamsBody set(String key, Object val){
        put(key,val);
        return this;
    }
}

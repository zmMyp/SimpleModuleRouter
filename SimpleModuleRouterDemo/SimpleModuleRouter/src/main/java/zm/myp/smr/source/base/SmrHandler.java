package zm.myp.smr.source.base;

/**
 * Created by qianjian on 2019/4/25.
 */

public interface SmrHandler {
    void handle(SmrRequestContext context) throws Exception;
}

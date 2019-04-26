package zm.myp.smr.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by qianjian on 2019/4/26.
 */

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface SmrLoadModule {
    public String[] modules();
}

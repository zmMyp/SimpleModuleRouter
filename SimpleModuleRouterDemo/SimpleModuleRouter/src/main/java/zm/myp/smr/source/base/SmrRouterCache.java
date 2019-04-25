package zm.myp.smr.source.base;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by qianjian on 2019/4/25.
 * 储存路由信息
 */

public class SmrRouterCache<T> {

    private List<SmrRouterWraper<T>> routerList = new ArrayList<>();

    //保存回调
    private static HashMap<String,SmrResponseCallBack> callBackList=new HashMap<>();


    public static  void saveCallBack(String key,SmrResponseCallBack smrResponse){
        if(!callBackList.containsKey(key)){
            callBackList.put(key,smrResponse);
        }
    }

    public  static  SmrResponseCallBack getCallBack(String key){

        if(!callBackList.containsKey(key)){
            return  null;
        }
        return callBackList.get(key);
    }

    public static  void removeCallBack(String key){
        if(callBackList.containsKey(key)){
            callBackList.remove(key);
        }
    }

    public void add(String path, T handler) {

        routerList.add(new SmrRouterWraper(path,handler));
    }

    public T matched(SmrRequestContext smrRequestContext) {

        for (SmrRouterWraper<T> l : routerList) {
            if (l.matches(smrRequestContext.getUrl())) {
                return l.handler;
            }
        }

        return null;
    }

    //路由信息包装类
    class  SmrRouterWraper<T>{

        private String path;
        public T handler;

        public SmrRouterWraper(String path, T handler){
            this.path=path;
            this.handler=handler;
        }

        public boolean matches(String path) {
            return this.path.equals(path);
        }
    }

}


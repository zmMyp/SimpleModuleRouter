##简介
SimpleModuleRouter，路由式框架，为多模块开发提供了便利，方便主app与module之间，以及各module之间像http请求一样（包含请求url,参数，回调）调用各自的功能，并且可以像rxjava一样控制调用功能和回调功能执行的线程。话不多说，直接上代码
```
 //请求地址
            String url = "kb51://module1/fun1";

            // 拼接参数
            SmrParamsBody smrParamsBody = new SmrParamsBody()
                    .set("p1", "我是主壳传来的数据")
                    .set("p2", "ssss");

            // 发起请求module1的功能，带有线程切换
            SmrRequestClient.build().
                    requestOnThread(SchedulerType.MAIN_THREAD).
                    responseOnThread(SchedulerType.MAIN_THREAD).
                    call(this, url, smrParamsBody, new SmrResponseCallBack() {
                        @Override
                        public void response(Object data)  {
                            Log.v("smr1", "responsethread" + Thread.currentThread().getName());
                            tv1.setText("当前处于主壳页面,module1返回的数据是:" + ((SmrParamsBody) data).get("m1"));
                        }
                    });

```
为什么要搞这框架呢？
随着公司app参入的模块越来越多，一个app分成了商场模块，认证模块等等，越来越不适合放到一个模块里，并且，业务逻辑上的这些模块，也需要互相调用，正好笔者以前也看过些butterKnife,Rxjava这些主流框架的源码，就迫不及待想小试牛刀。

假如不适用模块化框架，把业务上划分的模块独立成例如module1和modul2,开发的时候，可能modul和module之间，module和主app module1之间少不了互相调用

![使用前.png](https://upload-images.jianshu.io/upload_images/7706076-506fcc8feb25bef6.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![使用后.png](https://upload-images.jianshu.io/upload_images/7706076-93941d6dc7c14d94.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

工程目录如下划分的:
![目录一.png](https://upload-images.jianshu.io/upload_images/7706076-055f39d6c1387103.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)



##使用步骤：
1. 配置apt
(1) .工程整体的build.gradle中配置如下
```
buildscript {
    repositories {
        jcenter()
    }
    dependencies {
        classpath 'com.android.tools.build:gradle:2.3.0'
        classpath 'com.neenbedankt.gradle.plugins:android-apt:1.8'
        // NOTE: Do not place your application dependencies here; they belong
        // in the individual module build.gradle files
    }
}
```
(2) .主壳的build.gradle中配置如下
```
apply plugin: 'com.android.application'
apply plugin: 'com.neenbedankt.android-apt'
.......
dependencies {
   ........
    compile project(':SimpleModuleRouter')
    compile project(':SimpleModuleRouter-annotations')
    apt project(':SimpleModuleRouter-compiler')
    compile project(':Module1')
    compile project(':Module2')
}

```
2.模块也就是示例中的modul1和modul2中的build.gradle中导入SimpleModuleRoute
```
dependencies {
   ........
    apt project(':SimpleModuleRouter-compiler')
   }

```

3.注册模块功能
示例如下代码：在Module1中，实现一个SmrModule的实现类，在register注册这个模块要提供的功能，和启动url

```
public class SmrModuleApp implements SmrModule {


    @Override
    public void register(SmrApplication app) {

        app.register("kb51://module1/fun1", new SmrHandler() {
            @Override
            public void handle(SmrRequestContext smrRequestContext) throws Exception {

                //Log.v("smr1",smrRequestContext.getParams().get("p1").toString());

                //返回数据给调用方
                //smrRequestContext.responseCall(new SmrParamsBody().set("md1","模块1发回的数据"));

                Navigation.navigateToMain(smrRequestContext);

            }
        });

    }

}

```
注册好之后，这样无论主壳app还是module2都可以调用这个功能，调用如下
4.调用
```
 //请求地址
            String url = "kb51://module1/fun1";

            // 拼接参数
            SmrParamsBody smrParamsBody = new SmrParamsBody()
                    .set("p1", "我是主壳传来的数据")
                    .set("p2", "ssss");

            // 发起请求module1的功能，带有线程切换
            SmrRequestClient.build().
                    requestOnThread(SchedulerType.MAIN_THREAD).
                    responseOnThread(SchedulerType.MAIN_THREAD).
                    call(this, url, smrParamsBody, new SmrResponseCallBack() {
                        @Override
                        public void response(Object data)  {
                            Log.v("smr1", "responsethread" + Thread.currentThread().getName());
                            tv1.setText("当前处于主壳页面,module1返回的数据是:" + ((SmrParamsBody) data).get("m1"));
                        }
                    });
```

如果不用reqesutOnThread和responseOnThread，那么默认在主线程中

package com.usst.anotation;

import java.lang.reflect.Method;
/**
 * 方法上面的注解是值是可以是使用者自己写入，写在方法上
 *  则表明方法上有什么特别的意思(写了注解有什么特别含义，也可以说是拿到注解属性值的作用)
 *   下面拿到name和id的值就可以实现其他的功能，比如API注解是生成文档，等等等
 * */
public class DemoMain {
    public static void main(String[] args) {
      DemoMain demoMain = new DemoMain();
      demoMain.outputAnnoDetail(AnnotationDemo.class);
    }

    private void outputAnnoDetail(Class clazz){
        Method[] methods = clazz.getDeclaredMethods();
        for(Method method  : methods) {
            TestAnnonation testAnnonation  = method.getAnnotation(TestAnnonation.class);
            if (testAnnonation != null) {
                System.out.println("name------>" + testAnnonation.name() + "------>Id------>" + testAnnonation.id());
            }
        }
    }
}

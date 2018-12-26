package com.bwie.day1225;

import android.app.Activity;
import android.content.Context;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author : 张腾
 * @date : 2018/12/25.
 * desc :
 */
public class ButterKnife {

    /**
     * 注解绑定
     * @param context
     */
    public static void bind(final Context context) {
        ///< 0. 获取Class类
        Class classObj = context.getClass();
        // 1.获取Field数组
        //Field[] fields = classObj.getFields();        // 只能获取public声明的,ButterKnife用的应该是这种
        Field[] fields = classObj.getDeclaredFields();  // public和private的都可以获取
        for (Field field : fields){
            // 2.判断是否添加了BindView注解，如果是我们则可以获取控件id
            if (field.isAnnotationPresent(InjectOnClickAnnotation.class)){
                // 3.获取注解实例
                InjectOnClickAnnotation bindView = field.getAnnotation(InjectOnClickAnnotation.class);
                // 4.获取上面的控件id
                //int viewId = bindView.viewId();
                int viewId = bindView.value();
                // 判断下上下文类型吧!(真正的Butterknife在不同类型界面使用方法有区别)
                if (context instanceof Activity){
                    // 5.我们暂时用以前常用的findviewbyid来获取控件吧
                    //Object viewObj = ((Activity)context).findViewById(viewId);
                    try {
                        // 5.two 用反射获取方法，进而执行获取控件
                        Method method = classObj.getMethod("findViewById", int.class);
                        Object viewObj = method.invoke(context, viewId);
                        // 6.将获取的控件设置到file上面
                        // 注意，如果控件类型声明的是private的，这里需要添加；
                        //       如果是public的，可以不用添加；
                        field.setAccessible(true);
                        field.set(context, viewObj);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    }
                }
            }

            // 2.如果是事件注解类型
            if (field.isAnnotationPresent(InjectOnClickAnnotation.class)){
                InjectOnClickAnnotation onClick = field.getAnnotation(InjectOnClickAnnotation.class);
                // 3.获取控件id和方法名称
                final int viewId = onClick.value();
                String funcName = onClick.onClick();

                try {
                    // 4. 给控件变量赋值
                    final Method method = classObj.getMethod("findViewById", int.class);
                    final Object viewObj = method.invoke(context, viewId);
                    field.setAccessible(true);
                    field.set(context, viewObj);

                    // 5.执行方法
                    final Method methodClick = classObj.getMethod(funcName, View.class);
                    // 5.0. 给控件设置点击监听事件(别的方法后面我们再说...先来常规笨的常用的这种)
                    ((View)viewObj).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            try {
                                // 5.1 然后执行方法
                                methodClick.invoke(context, viewObj);
                            } catch (IllegalAccessException e) {
                                e.printStackTrace();
                            } catch (InvocationTargetException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}

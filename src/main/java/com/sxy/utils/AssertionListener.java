package com.sxy.utils;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.util.ArrayList;
import java.util.List;

public class AssertionListener extends TestListenerAdapter {
    @Override
    public void onTestStart(ITestResult result){//仅在启动任何测试方法时才调用onTestStart()。
        Assertion.flag = true;
        Assertion.errors.clear();//启动之初，将errrors清空以及flag置为ture
    }
    @Override
    public void onTestFailure(ITestResult tr){
        this.handleAssertion(tr);
    }
    @Override
    public void onTestSkipped(ITestResult tr){
        this.handleAssertion(tr);
    }
    @Override
    public void onTestSuccess(ITestResult tr){
        this.handleAssertion(tr);
    }

    private int index =0;

    /**
     *监听器做参数，掌握assertion的flag和error
     * Asserion.flag为false时，说明抛出或者捕获了异常，那么监听器置为false，并获取异常信息，最后恢复初始值。
     * @param tr
     */
    private void handleAssertion(ITestResult tr) {
        if(!Assertion.flag){//如果发生异常抛出了，Assertion工具flag就会false
            Throwable throwable = tr.getThrowable();//监听器捕获异常
            if(throwable == null){
                throwable = new Throwable();//构造一个新的可抛出的 null作为其详细信息。
            }
            //返回堆栈跟踪元素数组
            StackTraceElement[] traces = throwable.getStackTrace();//提供对 printStackTrace()打印的堆栈跟踪信息的编程访问。
            //初始化一个0空间的异常跟踪信息数组
            StackTraceElement[] alltrace = new StackTraceElement[0];
            //遍历这个errors这个list集合的所有error元素
            for (Error e:Assertion.errors) {
                //取得error的所有跟踪信息数组
                 StackTraceElement[] errorTraces = e.getStackTrace();
                 //调用方法获取监听到的符合条件的跟踪信息数组
                 StackTraceElement[] et = this.getKeyStackTrace(tr,errorTraces);
                 //将信息以及方法信息等储存到跟踪信息数组
                 StackTraceElement[] message = new StackTraceElement[]{new StackTraceElement("message:"
                 +e.getMessage()+"in method:",tr.getMethod().getMethodName(),tr.getTestClass().getRealClass().getSimpleName(),index)};
                 
                 index = 0;
                 alltrace = this.merge(alltrace,message);//将错误信息内容与空数组合并成新的数组
                 alltrace = this.merge(alltrace,et);//将符合条件的跟踪信息数组与错误信息数组合并
            }

            if(traces != null){//当抛出的异常堆栈跟踪元素组成的数组不为空，也就是有异常抛出现象
                traces = this.getKeyStackTrace(tr,traces);//调用方法获取throwable的有效跟踪信息
                alltrace = this.merge(alltrace,traces);//将throwalbe的有效跟踪信息与上面的error的错误信息与跟踪信息合并
            }
            //设置将被返回的堆栈微量元素 getStackTrace()和由印刷 printStackTrace()和相关方法。
            throwable.setStackTrace(alltrace);
            tr.setThrowable(throwable);//throwable设置堆栈跟踪，监听器设置throwable
            Assertion.flag = true;//初始化flag
            Assertion.errors.clear();//清空
            tr.setStatus(ITestResult.FAILURE);//监听器置为失败
        }
    }

    /**
     * 将两个堆栈跟踪元素数组进行合并
     * @param traces1
     * @param traces2
     * @return
     */
    private StackTraceElement[] merge(StackTraceElement[] traces1, StackTraceElement[] traces2) {
        StackTraceElement[] ste = new StackTraceElement[traces1.length+traces2.length];//新建一个两个数组长度和的大数组
        for (int i = 0; i < traces1.length; i++) {
            ste[i]=traces1[i];//遍历第一个数组，把元素依次放入新数组
        }
        for (int i = 0; i < traces2.length; i++) {
            ste[traces1.length+i]=traces2[i];//遍历第二个数组，将元素依次放入新数组，注意角标延续数组1
        }
        return ste;//返回最终大数组
    }

    /**
     *获取监器捕获的与测试类一致的跟踪信息内容，并返回数组
     * @param tr
     * @param stackTraceElements
     * @return
     */
    private StackTraceElement[] getKeyStackTrace(ITestResult tr, StackTraceElement[] stackTraceElements) {
        List<StackTraceElement> ets = new ArrayList<StackTraceElement>();
        //遍历这个呢个堆栈跟踪元素数组，得到一个与监听的测试类一致的跟踪元素列表
        for (StackTraceElement stackTraceElement:stackTraceElements) {
            //stackTraceElement.getClassName()返回包含由该堆栈跟踪元素表示的执行点的类的完全限定名称。
            //equals如果指定的对象是另一个表示与该实例相同的执行点的 StackTraceElement实例，则返回true。
            if(stackTraceElement.getClassName().equals(tr.getTestClass().getName())){//如果跟踪信息的类名和测试类名一致
                ets.add(stackTraceElement);
                index = stackTraceElement.getLineNumber();//返回包含由该堆栈跟踪元素表示的执行点的源行的行号。
            }
        }
        //创建列表长度的一个数组
        StackTraceElement[] et = new StackTraceElement[ets.size()];
        for (int i = 0; i < et.length; i++) {
            et[i] = ets.get(i);//遍历数组，依次将列表中的元素放入数组
        }
        return et;//将堆栈跟踪元素信息数组返回
    }
}

package com.example.imageviewer.test;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;
import android.widget.Toast;

public class HorizonSrollViewEx extends ViewGroup {
    //储存孩子的数量、宽度、
    private int mChildSize;
    private int mChildWidth;
    private int mChildIndex;
    private int screenWidth;

    public int getScreenWidth() {
        return screenWidth;
    }

    //记录上次滑动的坐标
    private int mLastX=0;
    private int mLastY=0;
    //记录上次滑动的坐标（onInterceptTouchEvent)
    private int mLastXIntercept=0;
    private int mLastYIntercept=0;

    //弹性滑动对象和速度追踪器
    Scroller mScroller;
    VelocityTracker mTracker;

    @Override
    protected void onDetachedFromWindow() {

        mTracker.recycle();
        super.onDetachedFromWindow();
    }

    public HorizonSrollViewEx(Context context) {
        super(context);
        init();
    }

    public HorizonSrollViewEx(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public HorizonSrollViewEx(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    //创建
    public void init(){
        if(mScroller==null){
            mScroller=new Scroller(getContext());
            mTracker=VelocityTracker.obtain();
        }
    }


    //假定所有子元素的宽/高都是一样的
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int measureWidth=0;
        int measureHeight=0;
        //获得子元素的数量
        final int childCount=getChildCount();
        //获得子元素的MeasureSpec
        measureChildren(widthMeasureSpec,heightMeasureSpec);
        //解包父容器的MeasureSpec
        int widthspecMode=MeasureSpec.getMode(widthMeasureSpec);
        int widthspecSize=MeasureSpec.getSize(widthMeasureSpec);
        int heightspecMode=MeasureSpec.getMode(heightMeasureSpec);
        int heightspecSize=MeasureSpec.getSize(heightMeasureSpec);

        //给父容器宽高
        if(childCount==0){//没有子元素的话，宽高为0
            setMeasuredDimension(0,0);
        } else if(widthspecMode==MeasureSpec.AT_MOST&&heightspecMode==MeasureSpec.AT_MOST){
            final View childView=getChildAt(0);
            measureHeight=childView.getHeight();//高度为子元素的高
            for (int i = 0; i <getChildCount()-1 ; i++) {
                final View view=getChildAt(i);
                if(view.getHeight()>measureHeight){
                    measureHeight=view.getHeight();
                }
            }

            measureWidth=childView.getMeasuredWidth()*childCount;//宽度为所有子元素宽度的和
            //假设子元素宽高不同
            //宽度应遍历子元素，相加其宽度
            //高度应取所有子元素中最大的
            setMeasuredDimension(measureWidth,measureHeight);
        }else if(widthspecMode==MeasureSpec.AT_MOST){
            final View childView=getChildAt(0);
            measureHeight=heightspecSize;//高度为子元素的高
            measureWidth=widthspecSize*childCount;//宽度为所有子元素宽度的和
            screenWidth=widthspecSize;
            setMeasuredDimension(measureWidth,measureHeight);
        }else if(heightspecMode==MeasureSpec.AT_MOST){
            final View childView=getChildAt(0);
            measureHeight=childView.getHeight();//高度为子元素的高
            measureWidth=widthspecSize;//宽度为所有子元素宽度的和
            setMeasuredDimension(measureWidth,measureHeight);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
      int childLeft=0;
      final int childCount=getChildCount();
      mChildSize=childCount;
        for (int i = 0; i <childCount ; i++) {
            final View childView=getChildAt(i);
            //当子元素可见，对其进行layout
            if(childView.getVisibility()!=View.GONE){
                final int childWidth=screenWidth;
                mChildWidth=childWidth;
                childView.layout(childLeft,0,childWidth+childLeft,childView.getMeasuredHeight());
                childLeft+=childWidth;
            }
        }


    }

    /**
     * 判断这个事件是否要拦截
     */

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        //返回值默认是false
        boolean intercepted=false;
        //获取手指相对于控件的坐标
        int x=(int)ev.getX();
        int y=(int)ev.getY();
        switch(ev.getAction()){
            case MotionEvent.ACTION_DOWN:{
                intercepted=false;
                //滑到一半突然点一下
                if(!mScroller.isFinished()){
                    //中止上一次滑动的动作
                    mScroller.abortAnimation();
                    intercepted=true;//确定拦截
                }
                break;
            }
            case MotionEvent.ACTION_MOVE:{
                //纵向移动的绝对值小于横向移动的绝对值
                //认为是横向滑动，拦截
                int deltaX=x-mLastXIntercept;
                int deltaY=y-mLastYIntercept;
                if(Math.abs(deltaX)>Math.abs(deltaY))
                {
                    intercepted=true;
                }
                else{
                    intercepted=false;
                }
                break;
            }
            case MotionEvent.ACTION_UP:{
                intercepted=false;
                break;
            }
            default:
                break;
        }
        Toast.makeText(getContext(), "intercepted:"+intercepted, Toast.LENGTH_SHORT).show();
        mLastX=x;
        mLastY=y;
        mLastXIntercept=x;
        mLastYIntercept=y;
        return intercepted;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mTracker.addMovement(event);
        int x=(int)event.getX();
        int y=(int)event.getY();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:{
                //滑到一半就中止滑动
                if(!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                break;
            }
            case MotionEvent.ACTION_MOVE:{
                int deltaX=x-mLastX;
                int deltaY=y-mLastY;
                scrollBy(-deltaX,0);
                break;
            }
            case MotionEvent.ACTION_UP:{
                //当滑动速度超过一个值时，翻页
                int scrollX=getScrollX();
                mTracker.computeCurrentVelocity(1000);
                float xVelocity=mTracker.getXVelocity();
                if(Math.abs(xVelocity)>=50){
                    mChildIndex=xVelocity>0?mChildIndex-1:mChildIndex+1;
                }else{
                    //如果超过了一半也算翻页
                    mChildIndex=(scrollX+mChildWidth/2)/mChildWidth;
                }
                //保证坐标大于0且不大于最大值
                mChildIndex=Math.max(0,Math.min(mChildIndex,mChildSize-1));
                int dx=mChildIndex*mChildWidth-scrollX;
                smoothScrollBy(dx,0);
                mTracker.clear();
                break;
            }
            default:break;

        }
        mLastX=x;
        mLastY=y;
        return true;
    }


    private void smoothScrollBy(int dx,int dy){
        mScroller.startScroll(getScrollX(),getScrollY(),dx,dy);
        invalidate();
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            postInvalidate();
        }
    }
}

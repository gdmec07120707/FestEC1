package com.example.latte_ui.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.latte_core.delegates.LatteDelegate;
import com.example.latte_ui.R;
import com.joanzapata.iconify.widget.IconTextView;

import java.util.ArrayList;

/**
 * Created by FANGDINGJIE
 * 2018/2/7.
 * 图片选择布局
 */

public class AutoPhotoLayout extends LinearLayoutCompat {

    private int mCurrentNum = 0;
    private final int mMaxNum;
    private final int mMaxLineNum;
    private IconTextView mIconAdd = null;
    private LayoutParams mParams = null;
    //要删除的图片ID
    private int mDeleteId = 0;
    private AppCompatImageView mTargetImageVew = null;
    private final int mImageMargin;
    private LatteDelegate mDelegate = null;
    private ArrayList<View> mLineViews = null;
    private AlertDialog mTargetDialog = null;
    private static final String ICON_TEXT = "{fa-plus}";
    private final float mIconSize;

    private final ArrayList<ArrayList<View>> ALL_VIEWS = new ArrayList<>();
    private final ArrayList<Integer> LINE_HEIGHTS = new ArrayList<>();

    //防止多次的测量和布局过程
    private boolean mIsOnceInitOnMeasure = false;
    private boolean mHasInitOnLayout = false;

    private static final RequestOptions OPTIONS = new RequestOptions()
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.NONE);

    public AutoPhotoLayout(Context context) {
        this(context, null);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AutoPhotoLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.camera_flow_layout);
        mMaxNum = typedArray.getInt(R.styleable.camera_flow_layout_max_count,1);
        mMaxLineNum = typedArray.getInt(R.styleable.camera_flow_layout_line_count,1);
        mImageMargin = typedArray.getInt(R.styleable.camera_flow_layout_item_margin,0);
        mIconSize = typedArray.getDimension(R.styleable.camera_flow_layout_icon_size,20);
        //资源回收
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int sizeWidth = MeasureSpec.getSize(widthMeasureSpec);
        final int modeWidth = MeasureSpec.getMode(widthMeasureSpec);
        final int sizeHeight = MeasureSpec.getSize(heightMeasureSpec);
        final int modeHeight = MeasureSpec.getMode(heightMeasureSpec);
        //wrap_content
        int width = 0;
        int height = 0;
        //记录每一行的宽度和高度
        int lineWidth =0 ;
        int lineHeight = 0;
        //得到内部元素有多少个子View
        int  cCount = getChildCount();
        for(int i=0; i <cCount;i++){
            final View child = getChildAt(i);
            measureChild(child,widthMeasureSpec,heightMeasureSpec);
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            //子View的宽度
            final int childWidth = child.getMeasuredWidth()+lp.leftMargin+lp.rightMargin;
            //子view的高度
            final int childHeight = child.getMeasuredHeight()+lp.topMargin+lp.bottomMargin;
            //换行
            if(lineWidth+childWidth>sizeWidth-getPaddingLeft()-getPaddingRight()){
                width = Math.max(width,lineWidth);
                lineWidth = childWidth;
                height+= lineHeight;
                lineHeight = childHeight;
            }else{
                //为换行
                lineWidth += childWidth;
                lineHeight = Math.max(lineHeight,childHeight);
            }
            if(i == cCount-1){
                width = Math.max(lineWidth,width);
                height += lineHeight;
            }
        }
        setMeasuredDimension(
                modeWidth == MeasureSpec.EXACTLY?sizeWidth:width+getPaddingLeft()+getPaddingRight(),
                modeHeight == MeasureSpec.EXACTLY?sizeHeight:height+getPaddingTop()+getPaddingBottom()
        );

        //设置一行所有图片的宽高
        final int imageSideLen = sizeWidth/mMaxLineNum;
        //只初始化一次
        if(!mIsOnceInitOnMeasure){
            mParams = new LayoutParams(imageSideLen,imageSideLen);
            mIsOnceInitOnMeasure = true;
        }


    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        ALL_VIEWS.clear();
        LINE_HEIGHTS.clear();
        // 当前ViewGroup的宽度
        final int width = getWidth();
        int lineWidth = 0;
        int lineHeight = 0;
        if (!mHasInitOnLayout) {
            mLineViews = new ArrayList<>();
            mHasInitOnLayout = true;
        }
        final int cCount = getChildCount();
        for (int i = 0; i < cCount; i++) {
            final View child = getChildAt(i);
            final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
            final int childWith = child.getMeasuredWidth();
            final int childHeight = child.getMeasuredHeight();
            //如果需要换行
            if (childWith + lineWidth + lp.leftMargin + lp.rightMargin >
                    width - getPaddingLeft() - getPaddingRight()) {
                //记录lineHeight
                LINE_HEIGHTS.add(lineHeight);
                //记录当前一行的Views
                ALL_VIEWS.add(mLineViews);
                //重置宽和高
                lineWidth = 0;
                lineHeight = childHeight + lp.topMargin + lp.bottomMargin;
                //重置View集合
                mLineViews.clear();
            }
            lineWidth += childWith + lp.leftMargin + lp.rightMargin;
            lineHeight = Math.max(lineHeight, lineHeight + lp.topMargin + lp.bottomMargin);
            mLineViews.add(child);
        }
        //处理最后一行
        LINE_HEIGHTS.add(lineHeight);
        ALL_VIEWS.add(mLineViews);
        //设置子View位置
        int left = getPaddingLeft();
        int top = getPaddingTop();
        //行数
        final int lineNum = ALL_VIEWS.size();
        for (int i = 0; i < lineNum; i++) {
            //当前行所有的View
            mLineViews = ALL_VIEWS.get(i);
            lineHeight = LINE_HEIGHTS.get(i);
            final int size = mLineViews.size();
            for (int j = 0; j < size; j++) {
                final View child = mLineViews.get(j);
                //判断child的状态
                if (child.getVisibility() == GONE) {
                    continue;
                }
                final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();
                //设置子View的边距
                final int lc = left + lp.leftMargin;
                final int tc = top + lp.topMargin;
                final int rc = lc + child.getMeasuredWidth() - mImageMargin;
                final int bc = tc + child.getMeasuredHeight();
                //为子View进行布局
                child.layout(lc, tc, rc, bc);
                left += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
            }
            left = getPaddingLeft();
            top += lineHeight;
        }
        mIconAdd.setLayoutParams(mParams);
        mHasInitOnLayout = false;
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initAddIcon();
        mTargetDialog = new AlertDialog.Builder(getContext()).create();
    }

    private void initAddIcon() {
        mIconAdd = new IconTextView(getContext());
        mIconAdd.setText(ICON_TEXT);
        mIconAdd.setGravity(Gravity.CENTER);
        mIconAdd.setTextSize(mIconSize);
        mIconAdd.setBackgroundResource(R.drawable.border_text);
        mIconAdd.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                mDelegate.startCameraWithCheck();
            }
        });
        this.addView(mIconAdd);
    }

    public final void setDelegate(LatteDelegate delegate) {
        this.mDelegate = delegate;
    }

    public final void onCropTarget(Uri uri) {
        createNewImageView();
        Glide.with(mDelegate)
                .load(uri)
                .apply(OPTIONS)
                .into(mTargetImageVew);
    }

    private void createNewImageView() {
        mTargetImageVew = new AppCompatImageView(getContext());
        mTargetImageVew.setId(mCurrentNum);
        mTargetImageVew.setLayoutParams(mParams);
        mTargetImageVew.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                //获取要删除的图片ID
                mDeleteId = v.getId();
                mTargetDialog.show();
                final Window window = mTargetDialog.getWindow();
                if (window != null) {
                    window.setContentView(R.layout.dialog_image_click_panel);
                    window.setGravity(Gravity.BOTTOM);
                    window.setWindowAnimations(R.style.anim_panel_up_from_bottom);
                    window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    final WindowManager.LayoutParams params = window.getAttributes();
                    params.width = WindowManager.LayoutParams.MATCH_PARENT;
                    params.flags = WindowManager.LayoutParams.FLAG_DIM_BEHIND;
                    params.dimAmount = 0.5f;
                    window.setAttributes(params);
                    window.findViewById(R.id.dialog_image_clicked_btn_delete)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //得到要删除的图片
                                    final AppCompatImageView deleteImageViwe =
                                            (AppCompatImageView) findViewById(mDeleteId);
                                    //设置图片逐渐消失的动画
                                    final AlphaAnimation animation = new AlphaAnimation(1, 0);
                                    animation.setDuration(500);
                                    animation.setRepeatCount(0);
                                    animation.setFillAfter(true);
                                    animation.setStartOffset(0);
                                    deleteImageViwe.setAnimation(animation);
                                    animation.start();
                                    AutoPhotoLayout.this.removeView(deleteImageViwe);
                                    mCurrentNum -= 1;
                                    //当数目达到上限时隐藏添加按钮，不足时显示
                                    if (mCurrentNum < mMaxNum) {
                                        mIconAdd.setVisibility(VISIBLE);
                                    }
                                    mTargetDialog.cancel();
                                }
                            });
                    window.findViewById(R.id.dialog_image_clicked_btn_undetermined)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mTargetDialog.cancel();
                                }
                            });
                    window.findViewById(R.id.dialog_image_clicked_btn_cancel)
                            .setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    mTargetDialog.cancel();
                                }
                            });
                }
            }
        });
        //添加子View的时候传入位置
        this.addView(mTargetImageVew, mCurrentNum);
        mCurrentNum++;
        //当天家数目大于mMaxNum时，自动隐藏添加按钮
        if (mCurrentNum >= mMaxNum) {
            mIconAdd.setVisibility(View.GONE);
        }
    }
}

package zhou.floatlayout;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

import android.content.Context;
import android.content.res.TypedArray;
import android.database.DataSetObserver;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.SoundEffectConstants;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.Adapter;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.FrameLayout;
import android.widget.ListAdapter;

import com.yiutil.tools.Logger;

/**
 * 一个水平展开的布局,类似瀑布流
 * */
public class FlowFrameLayout extends FrameLayout {

    private OnItemClickListener mListener;
    private Adapter mAdapter;
    private int mSelectedPosition = -1;
    private int mFirstPosition = 0;
    private long mSelectedRowId;
    DataSetObserver observer;
    final private int mDefaultDividerSize = 13;
    private int mHorSpace;
    private int mVerSpace;

    private int[] mStyleable = { android.R.attr.horizontalSpacing, android.R.attr.verticalSpacing };

    public FlowFrameLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FlowFrameLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        TypedArray a = context.obtainStyledAttributes(attrs, mStyleable);
        mHorSpace = a.getDimensionPixelSize(0, mDefaultDividerSize);
        mVerSpace = a.getDimensionPixelSize(1, mDefaultDividerSize);
        a.recycle();
    }

    Queue<View> oneRow = new LinkedList<View>();
    LinkedList<Integer> dividerRow = new LinkedList<Integer>();
    boolean onMeasured = false;
    private IntBitSet newLine = new IntBitSet(0x000000000000000);

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int layoutHeight = 0;

        if (getChildCount() > 0 && !onMeasured) {
            reset();
            int someChildWidth = 0;
            int maxChildheight = 0;
            final int childCount = getChildCount();
            measureChildren(widthMeasureSpec, heightMeasureSpec);
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);

                int childWidth = child.getMeasuredWidth() + mHorSpace;
                Logger.dd("child width:%d,index:%d ", childWidth, i);
                someChildWidth += childWidth;
                int divider = mHorSpace;

                maxChildheight = Math.max(child.getMeasuredHeight(), maxChildheight);

                if (someChildWidth < widthSize) {
                    oneRow.offer(child);
                    Logger.dd("push child");
                } else {
                    newLine.set(i);
                    i--;
                    someChildWidth -= childWidth;
                    // 记录间隔宽度,避免多次onMeasure
                    if (oneRow.size() > 0) {
                        divider = (widthSize - someChildWidth) / (oneRow.size());
                    } else {
                        divider = (widthSize - someChildWidth);
                    }
                    Logger.dd("child width:%d, someChildWidth:%d", childWidth, someChildWidth);
                    int j = i - oneRow.size();

                    while (!oneRow.isEmpty()) {// 清空
                        Logger.dd("pop child j:%d", j);
                        Logger.dd("offer item");
                        dividerRow.offer(divider + oneRow.poll().getMeasuredWidth());
                        // dividerRow.offer(divider + oneRow.pop().getMeasuredWidth());
                    }
                    layoutHeight += maxChildheight;
                    maxChildheight = 0;
                    someChildWidth = 0;
                }
            }
            while (!oneRow.isEmpty()) {// 清空
                Logger.dd("pop child");
                Logger.dd("offer item");
                dividerRow.offer(oneRow.poll().getMeasuredWidth());
            }
            Logger.dd(Arrays.toString(dividerRow.toArray()));
            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                ViewGroup.LayoutParams lp = child.getLayoutParams();
                int width = child.getMeasuredWidth();

                if (dividerRow.peek() != null) {
                    width = dividerRow.poll();
                    lp = new LayoutParams(width, LayoutParams.WRAP_CONTENT);
                    child.setLayoutParams(lp);
                }
            }
            measureChildren(widthMeasureSpec, heightMeasureSpec);
        }
        if (heightMode == MeasureSpec.UNSPECIFIED) {
            heightSize = layoutHeight;
        }
        setMeasuredDimension(widthSize, heightSize);
        // super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    /** 复位状态.重新进行一次测算是需要 */
    private void reset() {
        dividerRow.clear();
        oneRow.clear();
        newLine.clean();
        onMeasured = true;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        layoutChild(left, getPaddingTop(), right, bottom);
    }

    private void layoutChild(final int l, final int t, final int r, final int b) {
        // int layoutRight = getRight();
        int cL = l, cT = t, cB = 0;
        final int count = getChildCount();
        for (int i = 0; i < count; i++) {
            View view = getChildAt(i);
            // view.getLayoutParams().width = widthMargin;
            Logger.dd("cl:%d,cT:%d,cB:%d,view width:%d", cL, cT, cB, view.getMeasuredWidth());
            if (!newLine.get(i)) {
                view.layout(cL, cT, cL + view.getMeasuredWidth(), cT + view.getMeasuredHeight());
                cL += view.getMeasuredWidth() + mHorSpace;
                cB = Math.max(cB, cT + view.getMeasuredHeight());
            } else {
                cL = l;
                cT = cB + mVerSpace;
                view.layout(cL, cT, cL + view.getMeasuredWidth(), cT + view.getMeasuredHeight());
                cL += view.getMeasuredWidth() + mHorSpace;
            }
        }
    }

    public boolean performItemClick(View view, int position, long id) {
        if (mListener != null) {
            playSoundEffect(SoundEffectConstants.CLICK);
            if (view != null) {
                view.sendAccessibilityEvent(AccessibilityEvent.TYPE_VIEW_CLICKED);
            }
            mListener.onItemClick(null, view, position, id);
            return true;
        }
        return false;
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void setAdapter(ListAdapter adapter) {
        if (observer == null) {
            observer = new FlowObserver();
        }
        if (mAdapter != null) {
            mAdapter.unregisterDataSetObserver(observer);
        }
        if (mAdapter == null || mAdapter != adapter) {
            for (int pos = 0; pos < adapter.getCount(); pos++) {
                View childView = adapter.getView(pos, null, null);
                setupChild(childView, pos);
            }
        }
        requestLayout();
        this.mAdapter = adapter;
        mAdapter.registerDataSetObserver(observer);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_DPAD_CENTER:
            case KeyEvent.KEYCODE_ENTER:
                if (!isEnabled()) {
                    return true;
                }
                if (isClickable() && isPressed() && mSelectedPosition >= 0 && mAdapter != null
                    && mSelectedPosition < mAdapter.getCount()) {

                    final View view = getChildAt(mSelectedPosition - mFirstPosition);
                    if (view != null) {
                        performItemClick(view, mSelectedPosition, mSelectedRowId);
                        view.setPressed(false);
                    }
                    setPressed(false);
                    return true;
                }
                break;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        boolean handled = true;
        final int x = (int) ev.getX();
        final int y = (int) ev.getY();
        switch (ev.getAction() & MotionEvent.ACTION_MASK) {
            case MotionEvent.ACTION_DOWN:
                int motionPosition = pointToPosition(x, y);
                mSelectedPosition = motionPosition;
                if (mSelectedPosition < 0 || mSelectedPosition > getChildCount()) {
                    break;
                }
                View view = getChildAt(mSelectedPosition - mFirstPosition);

                view.setPressed(true);
                if (mAdapter != null) {
                    mSelectedRowId = mAdapter.getItemId(motionPosition);
                }
                break;
            case MotionEvent.ACTION_UP:
                clearPress();
                motionPosition = pointToPosition(x, y);
                if (mSelectedPosition < 0 || mSelectedPosition > getChildCount() || mSelectedPosition != motionPosition) {
                    break;
                }
                mSelectedPosition = motionPosition;
                view = getChildAt(mSelectedPosition - mFirstPosition);
                performItemClick(view, mSelectedPosition, mSelectedRowId);
                handled = true;
                break;
            case MotionEvent.ACTION_CANCEL:
                clearPress();
                Logger.d();
                handled = true;
                break;
        }
        return handled;
    }

    private void clearPress() {
        for (int i = 0; i < getChildCount(); i++) {
            getChildAt(i).setPressed(false);
        }
    }

    public int pointToPosition(int x, int y) {
        Rect frame = null;
        if (frame == null) {
            Rect mTouchFrame = new Rect();
            frame = mTouchFrame;
        }

        final int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                child.getHitRect(frame);
                if (frame.contains(x, y)) {
                    return mFirstPosition + i;
                }
            }
        }
        return -1;
    }

    public void setupChild(final View view, int index) {
        LayoutParams lp = (LayoutParams) view.getLayoutParams();
        if (lp == null) {
            lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        }
        addViewInLayout(view, index, lp, true);
    }

    public void setupChild(final View view) {
        setupChild(view, getChildCount());
    }

    void reLayoutParam(View view) {
        LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);
    }

    /**
     * 负责响应 数据集变化
     *
     * @author zhou
     *
     */
    public class FlowObserver extends DataSetObserver {

        @Override
        public void onChanged() {
            for (int pos = 0; pos < mAdapter.getCount(); pos++) {
                View childView = mAdapter.getView(pos, getChildAt(pos), FlowFrameLayout.this);
                if (getChildAt(pos) != childView) {
                    setupChild(childView, pos);
                } else {
                    reLayoutParam(childView);
                }
            }
            onMeasured = false;
        }
    }

}

package zhou.floatlayout;

import java.util.Stack;

import android.content.Context;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;

/**
 * <h>水平个数未知的流布局</h> <br/>
 * 不做类似listview的缓存
 *
 * @author zhou
 *
 */
@Deprecated
public class HorizontalFlowLayout extends AdapterView<ListAdapter> {

    // 子项目之间的间距
    private int mHMargin;
    /** 行距 */
    private int mVMargin;

    private RecycleBin mRecycler = new RecycleBin();

    protected ListAdapter adapter;

    public HorizontalFlowLayout(Context context) {
        this(context, null);
    }

    public HorizontalFlowLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        final int defaultMar = 10;
        mHMargin = defaultMar;
        mVMargin = defaultMar;
    }

    /** 获取某个位置的view */
    View obtionView(int position) {
        View convertView = mRecycler.get(position);
        View view = null;
        if (convertView != null) {
            view = adapter.getView(position, convertView, this);
        } else {
            view = adapter.getView(position, null, this);
        }
        return view;
    }

    Stack<View> oneRow = new Stack<View>();

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (adapter != null) {
            int someChildWidth = 0;
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
                int childWidth = child.getMeasuredWidth();
                someChildWidth += childWidth;
                if (someChildWidth < widthSize) {
                    oneRow.push(child);
                }else{
                    someChildWidth -= childWidth;
                    int divider = (widthSize-someChildWidth)/oneRow.size();
                    for (View view : oneRow) {
                        LinearLayout.LayoutParams p = ((LinearLayout.LayoutParams) view.getLayoutParams());
                        p.rightMargin = divider;
                    }
                    // 清空
                    while (!oneRow.empty()) {
                        oneRow.pop();
                    }
                }
            }
        }
        setMeasuredDimension(widthSize, heightSize);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        layoutChild(l, t, r, b);
    }

    private void layoutChild(int l, int t, int r, int b) {
        if (adapter != null && getChildCount() < adapter.getCount()) {
            for (int pos = 0; pos < adapter.getCount(); pos++) {
                View childView = adapter.getView(pos, null, null);
                setupChild(childView);
            }
            for (int i = 0; i < getChildCount(); i++) {
                getChildAt(i);
            }
        }
    }

    void setupChild(View view) {
        LayoutParams lp = view.getLayoutParams();
        if (lp == null) {
            lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        addViewInLayout(view, 0, lp, true);
    }


    @Override
    public View getSelectedView() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void setSelection(int position) {
        // TODO Auto-generated method stub

    }

    @Override
    public void setAdapter(ListAdapter adapter) {
        removeAllViewsInLayout();
        this.adapter = adapter;
    }

    @Override
    public ListAdapter getAdapter() {
        return adapter;
    }

    /** 缓存列表中的一些控件 */
    class RecycleBin {
        private final SparseArray<View> mScrapHeap = new SparseArray<View>();

        public void put(int position, View v) {
            mScrapHeap.put(position, v);
        }

        View get(int position) {
            // System.out.print("Looking for " + position);
            View result = mScrapHeap.get(position);
            if (result != null) {
                // System.out.println(" HIT");
                mScrapHeap.delete(position);
            } else {
                // System.out.println(" MISS");
            }
            return result;
        }

        void clear() {
            final SparseArray<View> scrapHeap = mScrapHeap;
            final int count = scrapHeap.size();
            for (int i = 0; i < count; i++) {
                final View view = scrapHeap.valueAt(i);
                if (view != null) {
                    removeDetachedView(view, true);
                }
            }
            scrapHeap.clear();
        }
    }

}

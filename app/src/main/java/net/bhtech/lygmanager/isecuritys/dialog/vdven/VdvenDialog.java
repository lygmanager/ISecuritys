package net.bhtech.lygmanager.isecuritys.dialog.vdven;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.joanzapata.iconify.widget.IconTextView;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.dialog.ConformListener;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;
import net.bhtech.lygmanager.ui.tag.RightAndLeftEditText;
import net.bhtech.lygmanager.ui.tree.Node;
import net.bhtech.lygmanager.ui.tree.NodeTreeAdapter;

import java.util.LinkedList;

/**
 * Created by zhangxinbiao on 2018/6/11.
 */

public class VdvenDialog extends Dialog {

    private static Context mContext=null;

    private static UtusrEntity mUser= null;




    public VdvenDialog(@NonNull Context context) {
        super(context);
        mContext=context;
        mUser= AccountManager.getSignInfo();
    }

    public VdvenDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
        mUser= AccountManager.getSignInfo();
    }

    protected VdvenDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;
        mUser= AccountManager.getSignInfo();
    }

    public static class Builder {

        private SwipeRefreshLayout mRefreshLayout = null;
        private RecyclerView mRecyclerView = null;

        private RefreshHandler mRefreshHandler = null;

        private NodeTreeAdapter mAdapter;
        private LinkedList<Node> mLinkedList = new LinkedList<>();
        private Context mContext=null;

        private View layout;
        private VdvenDialog dialog;
        public Builder(Context context) {
            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            mContext=context;
            dialog = new VdvenDialog(context, R.style.customDialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.dialog_vdven, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mRecyclerView=layout.findViewById(R.id.rv_vdven);
            mRefreshLayout=layout.findViewById(R.id.srl_vdven);
            TextView text_title=layout.findViewById(R.id.text_title);
            final RightAndLeftEditText text=layout.findViewById(R.id.VEN_NAM);
            IconTextView search=layout.findViewById(R.id.button_search);
            text_title.setText("供应商清单");
            search.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initData(text.getEditTextInfo());
                }
            });


        }

        private void initRefreshLayout() {
            mRefreshLayout.setColorSchemeResources(
                    android.R.color.holo_blue_bright,
                    android.R.color.holo_orange_light,
                    android.R.color.holo_red_light
            );
            mRefreshLayout.setProgressViewOffset(true, 120, 300);
        }

        private void initRecyclerView(final ConformListener listener) {
            final GridLayoutManager manager = new GridLayoutManager(mContext,1);
            mRecyclerView.setLayoutManager(manager);
            mRecyclerView.addItemDecoration
                    (BaseDecoration.create(ContextCompat.getColor(mContext, R.color.app_background), 5));
            mRecyclerView.addOnItemTouchListener(VdvenClickListener.create(listener,dialog));
        }


        /**
         * 创建单按钮对话框
         * @return
         */
        public VdvenDialog createSingleButtonDialog(final ConformListener listener) {
            create(listener);
            return dialog;
        }

        /**
         * 单按钮对话框和双按钮对话框的公共部分在这里设置
         */
        private void create(final ConformListener listener) {

            dialog.setContentView(layout);
            dialog.setCancelable(true);     //用户可以点击手机Back键取消对话框显示
            dialog.setCanceledOnTouchOutside(false);        //用户不能通过点击对话框之外的地方取消对话框显示
            mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new VdvenDataConverter());

            initRefreshLayout();
            initRecyclerView(listener);
            initData("");
        }

        private void initData(String name){
            mRefreshHandler.getVdvenList(mContext,name);
        }


    }

    @Override
    public void show() {
        super.show();
        /**
         * 设置宽度全屏，要设置在show的后面
         */
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.gravity = Gravity.BOTTOM;
        layoutParams.width = ViewGroup.LayoutParams.MATCH_PARENT;
        layoutParams.height = ViewGroup.LayoutParams.WRAP_CONTENT;

        getWindow().getDecorView().setPadding(0, 0, 0, 0);

        getWindow().setAttributes(layoutParams);
    }
    @Override
    public void hide() {
        super.hide();
    }
}

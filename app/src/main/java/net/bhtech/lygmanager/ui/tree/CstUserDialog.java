package net.bhtech.lygmanager.ui.tree;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.LiemsResult;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.utils.log.LatteLogger;
import net.bhtech.lygmanager.utils.storage.LattePreference;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxinbiao on 2018/6/11.
 */

public class CstUserDialog extends Dialog {

    private static Context mContext=null;


    private static UtusrEntity mUser= null;

    public CstUserDialog(@NonNull Context context) {
        super(context);
        mContext=context;
        mUser= AccountManager.getSignInfo();
    }

    public CstUserDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
        mUser= AccountManager.getSignInfo();
    }

    protected CstUserDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;
        mUser= AccountManager.getSignInfo();
    }


    public interface ConformListener {
        void onConformClicked(String id,String name);
    }

    public static class Builder {
        private String message;
        private View contentView;
        private String positiveButtonText;
        private String negativeButtonText;
        private String singleButtonText;
        private View.OnClickListener positiveButtonClickListener;
        private View.OnClickListener negativeButtonClickListener;
        private View.OnClickListener singleButtonClickListener;

        private ListView mListView;
        private ListView mRecyclerView;

        private NodeTreeAdapter mAdapter;
        private LinkedList<Node> mLinkedList = new LinkedList<>();

        private NodeTreeAdapter mUserAdapter;
        private LinkedList<Node> mUserLinkedList = new LinkedList<>();

        private View layout;
        private CstUserDialog dialog;
        public Builder(Context context) {
            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            dialog = new CstUserDialog(context, R.style.customDialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.delegate_cstuser, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mListView=layout.findViewById(R.id.rv_cst);
            mRecyclerView=layout.findViewById(R.id.rv_user);
        }

        public Builder setMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder setContentView(View v) {
            this.contentView = v;
            return this;
        }

        public Builder setPositiveButton(String positiveButtonText, View.OnClickListener listener) {
            this.positiveButtonText = positiveButtonText;
            this.positiveButtonClickListener = listener;
            return this;
        }

        public Builder setNegativeButton(String negativeButtonText, View.OnClickListener listener) {
            this.negativeButtonText = negativeButtonText;
            this.negativeButtonClickListener = listener;
            return this;
        }

        public Builder setSingleButton(String singleButtonText, View.OnClickListener listener) {
            this.singleButtonText = singleButtonText;
            this.singleButtonClickListener = listener;
            return this;
        }

        /**
         * 创建单按钮对话框
         * @return
         */
        public CstUserDialog createSingleButtonDialog(final ConformListener listener) {
            showSingleButton();
            layout.findViewById(R.id.singleButton).setOnClickListener(singleButtonClickListener);
            //如果传入的按钮文字为空，则使用默认的“返回”
            if (singleButtonText != null) {
                ((Button) layout.findViewById(R.id.singleButton)).setText(singleButtonText);
            } else {
                ((Button) layout.findViewById(R.id.singleButton)).setText("返回");
            }
            create(listener);
            return dialog;
        }

        /**
         * 创建双按钮对话框
         * @return
         */
        public CstUserDialog createTwoButtonDialog(final ConformListener listener) {
            showTwoButton();
            layout.findViewById(R.id.positiveButton).setOnClickListener(positiveButtonClickListener);
            layout.findViewById(R.id.negativeButton).setOnClickListener(negativeButtonClickListener);
            //如果传入的按钮文字为空，则使用默认的“是”和“否”
            if (positiveButtonText != null) {
                ((Button) layout.findViewById(R.id.positiveButton)).setText(positiveButtonText);
            } else {
                ((Button) layout.findViewById(R.id.positiveButton)).setText("是");
            }
            if (negativeButtonText != null) {
                ((Button) layout.findViewById(R.id.negativeButton)).setText(negativeButtonText);
            } else {
                ((Button) layout.findViewById(R.id.negativeButton)).setText("否");
            }
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
            mAdapter = new NodeTreeAdapter(mContext, mListView, mLinkedList, new NodeTreeAdapter.ConformListener() {
                @Override
                public void onConformClicked(String cstno,String cstName) {
                    mUserAdapter = new NodeTreeAdapter(mContext, mRecyclerView, mUserLinkedList, new NodeTreeAdapter.ConformListener() {
                        @Override
                        public void onConformClicked(String usrId,String UsrName) {
                            listener.onConformClicked(usrId,UsrName);
                            dialog.hide();
                        }
                    });
                    mUserAdapter.notifyDataSetChanged();
                    mRecyclerView.setAdapter(mUserAdapter);
                    mRecyclerView.invalidate();
                    initUserData(cstno);
                }
            });
            mListView.setAdapter(mAdapter);
            initData();
        }

        private void initUserData(final String cstNo){
            final List<Node> data = new ArrayList<>();
            Observable<String> obj =
                    RxRestClient.builder()
                            .url("getSmCstUserList")
                            .params("cstno", cstNo)
                            .loader(mContext)
                            .build()
                            .post();
            obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(mContext) {
                @Override
                public void onNext(String result) {
                    if(result!=null&&!"".equals(result)) {
                        mUserLinkedList.clear();
                        JSONObject lr2 = (JSONObject) JSONObject.parse(result);
                        LiemsResult lr = JSONObject.toJavaObject(lr2, LiemsResult.class);
                        if(Integer.parseInt(lr.getCount())>0) {
                            JSONArray jsonArray = JSONArray.parseArray(lr.getRows().toString());
                            for (int j = 0; j < jsonArray.size(); j++) {
                                String USR_ID = jsonArray.getJSONObject(j).getString("USR_ID");
                                String USR_NAM = jsonArray.getJSONObject(j).getString("USR_NAM");
                                data.add(new StringNode(USR_ID, "0", USR_NAM));

                            }
                            mUserLinkedList.addAll(NodeHelper.sortNodes(data));
                            mUserAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });

        }

        private void initData(){
            final List<Node> data = new ArrayList<>();
            Observable<String> obj =
                    RxRestClient.builder()
                            .url("getSmCstList")
                            .params("orgno", mUser.getOrgNo())
                            .loader(mContext)
                            .build()
                            .post();
            obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(mContext) {
                @Override
                public void onNext(String result) {
                    if(result!=null&&!"".equals(result)) {
                        JSONObject lr2 = (JSONObject) JSONObject.parse(result);
                        LiemsResult lr = JSONObject.toJavaObject(lr2, LiemsResult.class);
                        if(Integer.parseInt(lr.getCount())>0) {
                            JSONArray jsonArray = JSONArray.parseArray(lr.getRows().toString());
                            for (int j = 0; j < jsonArray.size(); j++) {
                                String CST_NO = jsonArray.getJSONObject(j).getString("CST_NO");
                                String CSTUP_NO = jsonArray.getJSONObject(j).getString("CSTUP_NO");
                                String CST_NAM = jsonArray.getJSONObject(j).getString("CST_NAM");
                                data.add(new StringNode(CST_NO, CSTUP_NO, CST_NAM));

                            }
                            mLinkedList.addAll(NodeHelper.sortNodes(data));
                            mAdapter.notifyDataSetChanged();
                        }
                    }
                }
            });
        }

        /**
         * 显示双按钮布局，隐藏单按钮
         */
        private void showTwoButton() {
            layout.findViewById(R.id.singleButtonLayout).setVisibility(View.GONE);
            layout.findViewById(R.id.twoButtonLayout).setVisibility(View.VISIBLE);
        }

        /**
         * 显示单按钮布局，隐藏双按钮
         */
        private void showSingleButton() {
            layout.findViewById(R.id.singleButtonLayout).setVisibility(View.VISIBLE);
            layout.findViewById(R.id.twoButtonLayout).setVisibility(View.GONE);
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

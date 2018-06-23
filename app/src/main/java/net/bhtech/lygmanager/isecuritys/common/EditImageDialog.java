package net.bhtech.lygmanager.isecuritys.common;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
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
import android.widget.Button;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.joanzapata.iconify.widget.IconTextView;

import net.bhtech.lygmanager.app.AccountManager;
import net.bhtech.lygmanager.database.UtusrEntity;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.dialog.ConformListener;
import net.bhtech.lygmanager.isecuritys.dialog.vdven.VdvenClickListener;
import net.bhtech.lygmanager.isecuritys.dialog.vdven.VdvenDataConverter;
import net.bhtech.lygmanager.ui.recycler.BaseDecoration;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;
import net.bhtech.lygmanager.ui.tag.EditImageView;
import net.bhtech.lygmanager.ui.tag.RightAndLeftEditText;
import net.bhtech.lygmanager.ui.tree.Node;
import net.bhtech.lygmanager.ui.tree.NodeTreeAdapter;
import net.bhtech.lygmanager.utils.file.ImageUtil;

import java.util.LinkedList;

/**
 * Created by zhangxinbiao on 2018/6/11.
 */

public class EditImageDialog extends Dialog {

    private static Context mContext=null;

    private static UtusrEntity mUser= null;




    public EditImageDialog(@NonNull Context context) {
        super(context);
        mContext=context;
        mUser= AccountManager.getSignInfo();
    }

    public EditImageDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;
        mUser= AccountManager.getSignInfo();
    }

    protected EditImageDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;
        mUser= AccountManager.getSignInfo();
    }

    public static class Builder {

        private Button btn_save = null;
        private EditImageView editImage = null;

        private NodeTreeAdapter mAdapter;
        private LinkedList<Node> mLinkedList = new LinkedList<>();
        private Context mContext=null;

        private View layout;
        private EditImageDialog dialog;
        public Builder(Context context) {
            //这里传入自定义的style，直接影响此Dialog的显示效果。style具体实现见style.xml
            mContext=context;
            dialog = new EditImageDialog(context, R.style.customDialog);
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = inflater.inflate(R.layout.delegate_editimage, null);
            dialog.addContentView(layout, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            editImage=layout.findViewById(R.id.editImage);
            btn_save=layout.findViewById(R.id.btn_save);
            TextView text_title=layout.findViewById(R.id.text_title);
            final RightAndLeftEditText text=layout.findViewById(R.id.VEN_NAM);
            text_title.setText("图片编辑");


        }

        /**
         * 创建单按钮对话框
         * @return
         */
        public EditImageDialog createSingleButtonDialog(final ConformListener listener,final String path) {
            dialog.setContentView(layout);
            dialog.setCancelable(true);     //用户可以点击手机Back键取消对话框显示
            dialog.setCanceledOnTouchOutside(false);        //用户不能通过点击对话框之外的地方取消对话框显示
            Glide.with(mContext).load(path).into(editImage);
            btn_save.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    editImage.setDrawingCacheEnabled(true);
                    Bitmap frontBitmap=editImage.getDrawingCache();
                    Bitmap backBitmap = ImageUtil.getSmallBitmap(path, 480, 800);
                    Bitmap bitmap=ImageUtil.mergeBitmap(backBitmap, frontBitmap);
                    ImageUtil.saveBitmapFile(bitmap,path);
                    editImage.setDrawingCacheEnabled(false);
                    listener.onConformClicked(null,null,null);
                    dialog.hide();
                }
            });
            return dialog;
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

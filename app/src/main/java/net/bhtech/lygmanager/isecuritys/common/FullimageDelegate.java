package net.bhtech.lygmanager.isecuritys.common;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.net.LiemsMethods;
import net.bhtech.lygmanager.ui.tag.ZoomImageView;

import butterknife.BindView;

public class FullimageDelegate extends BottomItemDelegate {

    @BindView(R.id.iView)
    ZoomImageView iView=null;

    @BindView(R.id.text_title)
    TextView text_title=null;

    private String tableName="";
    private String picture="";
    private String key="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            picture=args.getString("pictures");
            tableName=args.getString("tableName");
            key=args.getString("key");
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_fullimage;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        text_title.setText("放大图片");
        LiemsMethods.init(getContext()).glideImage(this,iView,tableName,
                picture+".JPEG",key);
    }

    public static FullimageDelegate create(String pictures,String tableName,String key) {
        final Bundle args = new Bundle();
        args.putString("pictures", pictures);
        args.putString("tableName", tableName);
        args.putString("key", key);
        final FullimageDelegate delegate = new FullimageDelegate();
        delegate.setArguments(args);
        return delegate;
    }
}

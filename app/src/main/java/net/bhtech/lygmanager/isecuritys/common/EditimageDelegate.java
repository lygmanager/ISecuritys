package net.bhtech.lygmanager.isecuritys.common;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.View;

import com.bumptech.glide.Glide;

import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.net.LiemsMethods;
import net.bhtech.lygmanager.net.download.PermisionUtils;
import net.bhtech.lygmanager.ui.tag.EditImageView;
import net.bhtech.lygmanager.ui.tag.ZoomImageView;
import net.bhtech.lygmanager.utils.file.ImageUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class EditimageDelegate extends BottomItemDelegate {

    @BindView(R.id.editImage)
    EditImageView editImage=null;
    private String picture="";

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        final Bundle args = getArguments();
        if (args != null) {
            picture=args.getString("pictures");
        }
    }

    @Override
    public Object setLayout() {
        return R.layout.delegate_editimage;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
        Glide.with(_mActivity).load(picture).into(editImage);
    }

    @OnClick(R.id.btn_save)
    public void openFullView(View view)
    {
        PermisionUtils.verifyStoragePermissions(this.getActivity());
        editImage.setDrawingCacheEnabled(true);
        Bitmap frontBitmap=editImage.getDrawingCache();
        Bitmap backBitmap = ImageUtil.getSmallBitmap(picture, 480, 800);
        Bitmap bitmap=ImageUtil.mergeBitmap(backBitmap, frontBitmap);
        ImageUtil.saveBitmapFile(bitmap,picture);
        editImage.setDrawingCacheEnabled(false);
        this.getFragmentManager().popBackStack();
    }

    public static EditimageDelegate create(String pictures) {
        final Bundle args = new Bundle();
        args.putString("pictures", pictures);
        final EditimageDelegate delegate = new EditimageDelegate();
        delegate.setArguments(args);
        return delegate;
    }
}

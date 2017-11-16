package net.bhtech.lygmanager.ui.camera;

import android.net.Uri;

import net.bhtech.lygmanager.delegates.PermissionCheckerDelegate;
import net.bhtech.lygmanager.util.file.FileUtil;

/**
 * Created by 傅令杰
 * 照相机调用类
 */

public class LatteCamera {

    public static Uri createCropFile() {
        return Uri.parse
                (FileUtil.createFile("crop_image",
                        FileUtil.getFileNameByTime("IMG", "jpg")).getPath());
    }

    public static void start(PermissionCheckerDelegate delegate) {
        new CameraHandler(delegate).beginCameraDialog();
    }
}

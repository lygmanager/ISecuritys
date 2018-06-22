package net.bhtech.lygmanager.icons;

import com.joanzapata.iconify.Icon;

/**
 * Created by zhangxinbiao on 2017/11/10.
 */

public enum EcIcons implements Icon {
    icon_scan('\ue602'),
    icon_ali_pay('\ue606'),
    icon_dianliangfenxi('\ue65d');

    private char character;

    EcIcons(char character) {
        this.character = character;
    }

    @Override
    public String key() {
        return name().replace('_', '-');
    }

    @Override
    public char character() {
        return character;
    }
}

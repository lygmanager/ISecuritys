package net.bhtech.lygmanager.icons;

import com.joanzapata.iconify.Icon;

/**
 * Created by zhangxinbiao on 2017/11/10.
 */

public enum EcIcons implements Icon {
    icon_scan('\ue602'),
    icon_ali_pay('\ue606'),
    icon_fa_user('\ue600'),
    icon_dianliangfenxi('\ue610'),
    icon_erjicaidan('\ue611'),
    icon_caozuoguizhangjiyuan('\ue612'),
    icon_dingwei('\ue613'),
    icon_caozuorizhi('\ue614'),
    icon_baozhuangfenxi('\ue615'),
    icon_fenggupingdianliangbaobiao('\ue616'),
    icon_baobiaoxitong('\ue617'),
    icon_bianyaqiyunhangpingjia('\ue618'),
    icon_caidanguanli('\ue619'),
    icon_cedianliebiao('\ue61a'),
    icon_dianfeifenxi('\ue61b'),
    icon_dingweixitong('\ue61c'),
    icon_chejianyongdianbaobiao('\ue61d'),
    icon_caijishebeixinxichaxun('\ue61e'),
    icon_fuhefenxi('\ue61f'),
    icon_gaojingyilan('\ue620'),
    icon_gongduancanshutu('\ue621'),
    icon_jiaohusuijichaxun('\ue622'),
    icon_nenghaopaiming('\ue623'),
    icon_jiancedianduibifenxi('\ue624'),
    icon_riyongdianliangchaxun('\ue625'),
    icon_lingxiandianliufenxi('\ue626'),
    icon_quanxianguanli('\ue627'),
    icon_jienenggaizaoduibi('\ue628'),
    icon_jiaoseguanli('\ue629'),
    icon_quanchangnengliutu('\ue62a'),
    icon_quanchangniaokantu('\ue62b'),
    icon_sanjicaidan('\ue62c'),
    icon_quanchangpeidiantu('\ue62d'),
    icon_sanxiangbupinghengpingjia('\ue62e'),
    icon_shebeiliebiao('\ue62f'),
    icon_shebeidanganchaxun('\ue630'),
    icon_shouye('\ue631'),
    icon_shebeiliyongshuaipingjia('\ue632'),
    icon_shebeiyongdianbaobiao('\ue633'),
    icon_xiebofenxi('\ue634'),
    icon_shengchangongxuchaxun('\ue635'),
    icon_wugongguanlijiyouhua('\ue636'),
    icon_xiansunfenxi('\ue637'),
    icon_xinxichaxun('\ue638'),
    icon_xinxifuwu('\ue639'),
    icon_xufanggouchengfenxi('\ue63a'),
    icon_xuqiuxiangying('\ue63b'),
    icon_yongdianfenxi('\ue63c'),
    icon_xitongbangzhu('\ue63d'),
    icon_yonghuguanli('\ue63e'),
    icon_xitongguanli('\ue63f'),
    icon_youhuayunhang('\ue640'),
    icon_yuexianyichangchaxun('\ue641'),
    icon_yijicaidan('\ue642'),
    icon_zaixianjianguan('\ue643'),
    icon_yueyongdianliangchaxun('\ue644'),
    icon_yongdianqujianbaobiao('\ue645'),
    icon_zhiluyongdianbaobiao('\ue646'),
    icon_yuexianyichangfenxi('\ue647'),
    icon_zuoyezhidaoshu('\ue648'),
    icon_yijicaidan_copy('\ue649'),
    icon_fa_building('\ue890'),
    icon_fa_camera_retro('\ue89d'),
    icon_fa_cogs('\ue8d0'),
    icon_fa_compass('\ue8d4'),
    icon_fa_edit('\ue8eb'),
    icon_fa_file_archive_o('\ue900'),
    icon_fa_list_ul('\ue960'),
    icon_fa_shopping_cart('\ue9ca'),
    icon_fa_sort('\ue9d4'),
    icon_dashuju('\ue601'),
    icon_jiejuefangan('\ue602'),
    icon_dingweifuwu('\ue603'),
    icon_vr('\ue604'),
    icon_kekaofuwu('\ue658'),
    icon_hangyezixun('\ue659'),
    icon_shujuwajue('\ue65a'),
    icon_renlianshibie('\ue65b'),
    icon_quanxianguanli1('\ue65c'),
    icon_rengongzhineng('\ue65d'),
    icon_peizhiguanli('\ue65e'),
    icon_xinxifenxi('\ue65f'),
    icon_xinxiguolv('\ue660'),
    icon_shujujianmo('\ue661'),
    icon_xietongwendang('\ue662'),
    icon_yijianfenxi('\ue663'),
    icon_xinxitiqu('\ue664'),
    icon_yonghuhuaxiang('\ue665'),
    icon_zhinengsousuo('\ue666'),
    icon_zhinengyun('\ue667'),
    icon_jinqian_('\ue605'),
    icon_mubu('\ue606'),
    icon_lujingtu('\ue607'),
    icon_jiangbei('\ue608'),
    icon_bazi('\ue609'),
    icon_dengpao('\ue60a'),
    icon_gouwuche('\ue60b'),
    icon_youxiang('\ue60c'),
    icon_xinwen('\ue60d'),
    icon_dunpai('\ue60e'),
    icon_xinxi('\ue60f'),
    icon_dianhuabu('\ue64a'),
    icon_qizi('\ue64b'),
    icon_diannao('\ue64c'),
    icon_jiangpai('\ue64d'),
    icon_biaoqian('\ue64e'),
    icon_rili('\ue64f'),
    icon_ppt('\ue650'),
    icon_xinfeng('\ue651'),
    icon_wenjianjia('\ue652'),
    icon_chuizi('\ue653'),
    icon_chicun_('\ue654'),
    icon_chicun_1('\ue655'),
    icon_chicun_2('\ue656'),
    icon_chicun_3('\ue657'),;

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

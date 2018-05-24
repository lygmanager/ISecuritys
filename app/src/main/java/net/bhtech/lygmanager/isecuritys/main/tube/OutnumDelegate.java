package net.bhtech.lygmanager.isecuritys.main.tube;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.mikephil.charting.charts.LineChart;
import com.joanzapata.iconify.widget.IconTextView;

import net.bhtech.lygmanager.database.AuthUserEntity;
import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.isecuritys.sign.SignHandler;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.JsonResult;
import net.bhtech.lygmanager.net.rx.RxRestClient;
import net.bhtech.lygmanager.ui.refresh.RefreshHandler;
import net.bhtech.lygmanager.utils.chart.LineChartManager;
import net.bhtech.lygmanager.utils.log.LatteLogger;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by zhangxinbiao on 2017/11/9.
 */

public class OutnumDelegate extends BottomItemDelegate {

    @BindView(R.id.lc_outdiameter)
    LineChart OutDiameter = null;
    @BindView(R.id.line_chart2)
    LineChart line_chart2 = null;

    @Override
    public Object setLayout() {
        return R.layout.delegate_outnum;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {
//        mRefreshHandler = RefreshHandler.create(mRefreshLayout, mRecyclerView, new OverrunDataConverter());

//        mSearchView.setOnFocusChangeListener(this);
        LineChartManager lineChartManager1 = new LineChartManager(OutDiameter);
        LineChartManager lineChartManager2 = new LineChartManager(line_chart2);


        //设置x轴的数据
        ArrayList<Float> xValues = new ArrayList<>();
        for (int i = 0; i <= 10; i++) {
            xValues.add((float) i);
        }

        //设置y轴的数据()
        List<List<Float>> yValues = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            List<Float> yValue = new ArrayList<>();
            for (int j = 0; j <= 10; j++) {
                yValue.add((float) (Math.random() * 80));
            }
            yValues.add(yValue);
        }

        //颜色集合
        List<Integer> colours = new ArrayList<>();
        colours.add(Color.GREEN);
        colours.add(Color.BLUE);
        colours.add(Color.RED);
        colours.add(Color.CYAN);

        //线的名字集合
        List<String> names = new ArrayList<>();
        names.add("折线一");
        names.add("折线二");
        names.add("折线三");
        names.add("折线四");

        //创建多条折线的图表
        lineChartManager1.showLineChart(xValues, yValues.get(0), names.get(1), colours.get(3));
        lineChartManager1.setDescription("温度");
        lineChartManager1.setYAxis(100, 0, 11);
        lineChartManager1.setHightLimitLine(70,"高温报警",Color.RED);

        lineChartManager2.showLineChart(xValues, yValues, names, colours);
        lineChartManager2.setYAxis(100, 0, 11);
        lineChartManager2.setDescription("温度");
        queryOutDiameter();
    }

    public void queryOutDiameter() {
        Observable<String> obj=
                RxRestClient.builder()
                        .url("http://123.56.229.253:8080/Tube42/TubReality.do?method=queryOutDiameter")
                        .params("resId", "1")
                        .params("resNam", "#1机组")
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(_mActivity) {
            @Override
            public void onNext(String o) {LatteLogger.d(o);
                JsonResult jsonResult= JSON.parseObject(o, JsonResult.class);
                if(jsonResult!=null&&jsonResult.isSuccess()) {
                    LatteLogger.d(jsonResult.getObj());
                }
            }
        });
    }
}

package net.bhtech.lygmanager.ui.tag;


import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;

import com.alibaba.fastjson.JSONArray;

import net.bhtech.lygmanager.isecuritys.R;
import net.bhtech.lygmanager.net.cxfweservice.LatteObserver;
import net.bhtech.lygmanager.net.rx.RxRestClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by rigatol on 26/04/2017.
 */

public class CompoundButtonGroup extends ScrollView {

    // *********************************************************
    // LISTENERS
    // *********************************************************

    private static String result="";
    public interface OnButtonSelectedListener {
        void onButtonSelected(int position, String value, boolean isChecked);
    }



    // *********************************************************
    // ENUMS
    // *********************************************************


    public enum CompoundType {
        CHECK_BOX, RADIO
    }

    public enum LabelOrder {
        BEFORE, AFTER
    }



    // *********************************************************
    // INSTANCE VARIABLES
    // *********************************************************

    private CompoundButtonGroup.CompoundType compoundType  = CompoundButtonGroup.CompoundType.CHECK_BOX;
    private CompoundButtonGroup.LabelOrder labelOrder  = CompoundButtonGroup.LabelOrder.BEFORE;
    private ArrayList<FullWidthCompoundButton> buttons = new ArrayList<>();
    private int numCols = 1;
    private CompoundButtonGroup.FullWidthCompoundButtonListener fullWidthCompoundButtonListener = new CompoundButtonGroup.FullWidthCompoundButtonListener();

    private LinkedHashMap<String, String> entries;
    private CompoundButtonGroup.OnButtonSelectedListener onButtonSelectedListener;
    private Context context;
    private LinearLayout containerLayout;





    // *********************************************************
    // CONSTRUCTOR
    // *********************************************************

    public CompoundButtonGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        containerLayout = new LinearLayout(context);
        containerLayout.setOrientation(LinearLayout.VERTICAL);
        containerLayout.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));

        TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.CompoundButtonGroup, 0, 0);
        try {
            int compoundTypeInt = a.getInteger(R.styleable.CompoundButtonGroup_compoundType, 0);
            this.compoundType = getCompoundType(compoundTypeInt);

            int labelOrderInt = a.getInteger(R.styleable.CompoundButtonGroup_labelOrder, 0);
            this.labelOrder = getLabelOrder(labelOrderInt);

            numCols = a.getInteger(R.styleable.CompoundButtonGroup_numCols, 1);

            setEntries(a.getTextArray(R.styleable.CompoundButtonGroup_entries));
            if (entries != null) {
                reDraw();
            }
        }
        finally {
            a.recycle();
        }

        addView(containerLayout);
    }






    // *********************************************************
    // PUBLIC GETTERS AND SETTERS
    // *********************************************************

    public List<Integer> getCheckedPositions() {
        ArrayList<Integer> checked = new ArrayList<>();
        for (int i=0; i<buttons.size(); i++) {
            FullWidthCompoundButton button = buttons.get(i);
            if (button.isChecked()) {
                checked.add(i);
            }
        }
        return checked;
    }

    public List<String> getCheckedValues() {
        ArrayList<String> checked = new ArrayList<>();
        for (int i=0; i<buttons.size(); i++) {
            FullWidthCompoundButton button = buttons.get(i);
            if (button.isChecked()) {
                checked.add(button.getValue());
            }
        }
        return checked;
    }

    public String getCheckedValueStr() {
        String checked = "";
        for (int i=0; i<buttons.size(); i++) {
            FullWidthCompoundButton button = buttons.get(i);
            if (button.isChecked()) {
                checked=checked+"|"+button.getValue();
            }
        }
        checked=checked.equals("")?"":checked.substring(1);
        return checked;
    }

    public List<String> getCheckedText() {
        ArrayList<String> checked = new ArrayList<>();
        for (int i=0; i<buttons.size(); i++) {
            FullWidthCompoundButton button = buttons.get(i);
            if (button.isChecked()) {
                checked.add(button.getText());
            }
        }
        return checked;
    }

    public String getCheckedTextStr() {
        String checked = "";
        for (int i=0; i<buttons.size(); i++) {
            FullWidthCompoundButton button = buttons.get(i);
            if (button.isChecked()) {
                checked=checked+"|"+button.getText();
            }
        }
        checked=checked.equals("")?"":checked.substring(1);
        return checked;
    }

    public CompoundButtonGroup.CompoundType getCompoundType() {
        return compoundType;
    }

    public CompoundButtonGroup.LabelOrder getLabelOrder() {
        return labelOrder;
    }

    public int getNumCols() {
        return numCols;
    }


    public void reDraw() {
        containerLayout.removeAllViews();
        buttons.clear();

        if (numCols == 1) {
            addEntriesInOneColumn(entries, containerLayout);
        }
        else if (numCols > 1) {
            addEntriesInGrid(entries, containerLayout, numCols);
        }
    }

    public void setCheckedPosition(final int position) {
        setCheckedPositions(new ArrayList<Integer>(){{add(position);}});
    }

    public void setCheckedPositions(List<Integer> checkedPositions) {
        for (int i=0; i<buttons.size(); i++) {
            buttons.get(i).setChecked(checkedPositions.contains(i));
        }
    }

    public void setCompoundType(CompoundButtonGroup.CompoundType compoundType) {
        this.compoundType = compoundType;
    }

    public void setEntries (List<String> entries) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (String entry : entries) {
            map.put(entry, entry);
        }
        this.entries = map;
    }

    public void setEntries(CharSequence[] entries) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        for (CharSequence entry : entries) {
            map.put(entry.toString(), entry.toString());
        }
        this.entries = map;
    }

    public void setEntries(HashMap<String, String> entries) {
        LinkedHashMap<String, String> map = new LinkedHashMap<>();
        map.putAll(entries);
        this.entries = map;
    }

    public void setEntries(LinkedHashMap<String, String> entries) {
        this.entries = entries;
    }

    public void setLabelOrder(CompoundButtonGroup.LabelOrder labelOrder) {
        this.labelOrder = labelOrder;
    }

    public void setNumCols(int numCols) {
        if (numCols > 0) {
            this.numCols = numCols;
        }
        else {
            throw new RuntimeException("Cannot set a number of cols that isn't greater than zero");
        }
    }

    public void setOnButtonSelectedListener(CompoundButtonGroup.OnButtonSelectedListener onButtonSelectedListener) {
        this.onButtonSelectedListener = onButtonSelectedListener;
    }






    // *********************************************************
    // PRIVATE METHODS
    // *********************************************************

    private void addEntriesInOneColumn(HashMap<String, String> entries, LinearLayout containerLayout) {
        for (Map.Entry<String, String> entry : entries.entrySet()) {
            FullWidthCompoundButton button = buildEntry(entry.getKey(), entry.getValue());
            containerLayout.addView(button);
            buttons.add(button);
        }
    }

    private void addEntriesInGrid(HashMap<String, String> entries, LinearLayout containerLayout, int numCols) {
        LinearLayout colContainer = null;

        List<String> keyList = new ArrayList<>(entries.keySet());

        for (int i=0; i< entries.size(); i++) {

            if (i % numCols == 0) {
                colContainer = new LinearLayout(context);
                colContainer.setLayoutParams(new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                colContainer.setOrientation(LinearLayout.HORIZONTAL);
                containerLayout.addView(colContainer);
            }

            String key = keyList.get(i);
            FullWidthCompoundButton button = buildEntry(key, entries.get(key));
            colContainer.addView(button);

            buttons.add(button);
        }

        // Ugly fix to force all cells to be equally distributed on parent's width
        for (int i=0; i<keyList.size() % numCols; i++) {
            FullWidthCompoundButton hiddenBtn = buildEntry("hidden", "hidden");
            hiddenBtn.setVisibility(INVISIBLE);
            hiddenBtn.setClickable(false);
            colContainer.addView(hiddenBtn);
        }
    }

    private FullWidthCompoundButton buildEntry(String value, String label) {
        FullWidthCompoundButton fullWidthButton = new FullWidthCompoundButton(context);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        params.weight = (float) numCols;
        fullWidthButton.setLayoutParams(params);
        fullWidthButton.setText(label);
        fullWidthButton.setValue(value);
        fullWidthButton.setCompoundType(compoundType);
        fullWidthButton.setLabelOrder(labelOrder);
        fullWidthButton.setListener(fullWidthCompoundButtonListener);
        return fullWidthButton;
    }

    private CompoundButtonGroup.CompoundType getCompoundType (int compoundTypeInt) {
        switch (compoundTypeInt) {
            case 0: return CompoundButtonGroup.CompoundType.CHECK_BOX;
            case 1: return CompoundButtonGroup.CompoundType.RADIO;
            default: throw new RuntimeException("Unrecognized view type");
        }
    }

    private CompoundButtonGroup.LabelOrder getLabelOrder (int labelOrder) {
        switch (labelOrder) {
            case 0: return CompoundButtonGroup.LabelOrder.BEFORE;
            case 1: return CompoundButtonGroup.LabelOrder.AFTER;
            default: throw new RuntimeException("Unrecognized label order");
        }
    }


    // *********************************************************
    // INNER CLASSES
    // *********************************************************

    // This inner class is needed to avoid that the onButtonClicked method (implemented from the
    // interface) is PUBLIC. Therefore it cannot be overrode.

    private class FullWidthCompoundButtonListener implements FullWidthCompoundButton.Listener {

        @Override
        public void onButtonClicked(View v) {
            if (compoundType == CompoundButtonGroup.CompoundType.RADIO) {
                for (FullWidthCompoundButton button : buttons) {
                    button.setChecked(false);
                }
            }

            if (onButtonSelectedListener != null) {
                FullWidthCompoundButton compoundButton = (FullWidthCompoundButton) v;
                boolean isChecked = !compoundButton.isChecked();
                int position = buttons.indexOf(v);
                String value = compoundButton.getValue();
                onButtonSelectedListener.onButtonSelected(position, value, isChecked);
            }
        }
    }


    public interface ConformListener {
        void onConformClicked(String values,String texts);
    }

    public static void CompoundButtonAlertDialog(Context mContext,String method,WeakHashMap<String, Object> params,final ConformListener listener)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("请选择：");
        //点击对话框以外的区域是否让对话框消失
        builder.setCancelable(true);
        View dialogView = View.inflate(mContext, R.layout.tag_checkbox, null);
        builder.setView(dialogView);
        final CompoundButtonGroup compoundButtonGroup = (CompoundButtonGroup) dialogView.findViewById(R.id.checkgroup);
        final LinkedHashMap<String, String> map = new LinkedHashMap<>();
        Observable<String> obj =
                RxRestClient.builder()
                        .url(method)
                        .params(params)
                        .loader(mContext)
                        .build()
                        .post();
        obj.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new LatteObserver<String>(mContext) {
            @Override
            public void onNext(String result) {
                if(result!=null&&!"".equals(result)) {
                    JSONArray jsonArray=JSONArray.parseArray(result);
                    if(jsonArray.size()>0) {
                        for (int j = 0; j < jsonArray.size(); j++) {
                            String value = jsonArray.getJSONObject(j).getString("value");
                            String label = jsonArray.getJSONObject(j).getString("label");
                            map.put(value, label);
                        }
                    }
                    compoundButtonGroup.setEntries(map);
                    compoundButtonGroup.reDraw();
                }
            }
        });
        //设置反面按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onConformClicked(compoundButtonGroup.getCheckedValueStr(),compoundButtonGroup.getCheckedTextStr());
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        //显示对话框
        dialog.show();
    }
}
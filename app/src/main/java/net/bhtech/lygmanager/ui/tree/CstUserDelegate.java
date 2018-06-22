package net.bhtech.lygmanager.ui.tree;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ListView;

import net.bhtech.lygmanager.delegates.bottom.BottomItemDelegate;
import net.bhtech.lygmanager.isecuritys.R;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by zhangxinbiao on 2018/6/11.
 */

public class CstUserDelegate extends BottomItemDelegate {

    @BindView(R.id.rv_cst)
    ListView mListView;

    private NodeTreeAdapter mAdapter;
    private LinkedList<Node> mLinkedList = new LinkedList<>();

    @Override
    public Object setLayout() {
        return R.layout.dialog_cstuser;
    }

    @Override
    public void onBindView(@Nullable Bundle savedInstanceState, @NonNull View rootView) {

        mAdapter = new NodeTreeAdapter(this.getContext(),mListView,mLinkedList);
        mListView.setAdapter(mAdapter);
        initData();

    }

    private void initData(){
        List<Node> data = new ArrayList<>();
        addOne(data);
        mLinkedList.addAll(NodeHelper.sortNodes(data));
        mAdapter.notifyDataSetChanged();
    }

    private void addOne(List<Node> data){
        data.add(new IntegerNode(1, 0, "总公司"));//可以直接注释掉此项，即可构造一个森林
        data.add(new IntegerNode(2, 1, "一级部一级部门一级部门一级部门门级部门一级部门级部门一级部门一级部门门级部一级"));
        data.add(new IntegerNode(3, 1, "一级部门"));
        data.add(new IntegerNode(4, 1, "一级部门"));

        data.add(new IntegerNode(222, 5, "二级部门--测试1"));
        data.add(new IntegerNode(223, 5, "二级部门--测试2"));

        data.add(new IntegerNode(5, 1, "一级部门"));

        data.add(new IntegerNode(224, 5, "二级部门--测试3"));
        data.add(new IntegerNode(225, 5, "二级部门--测试4"));

        data.add(new IntegerNode(6, 1, "一级部门"));
        data.add(new IntegerNode(7, 1, "一级部门"));
        data.add(new IntegerNode(8, 1, "一级部门"));
        data.add(new IntegerNode(9, 1, "一级部门"));
        data.add(new IntegerNode(10, 1, "一级部门"));

        for (int i = 2;i <= 10;i++){
            for (int j = 0;j < 10;j++){
                data.add(new IntegerNode(1+(i - 1)*10+j,i, "二级部门"+j));
            }
        }

        for (int i = 0;i < 5;i++){
            data.add(new IntegerNode(101+i,11, "三级部门"+i));
        }

        for (int i = 0;i < 5;i++){
            data.add(new IntegerNode(106+i,22, "三级部门"+i));
        }
        for (int i = 0;i < 5;i++){
            data.add(new IntegerNode(111+i,33, "三级部门"+i));
        }
        for (int i = 0;i < 5;i++){
            data.add(new IntegerNode(115+i,44, "三级部门"+i));
        }

        for (int i = 0;i < 5;i++){
            data.add(new IntegerNode(401+i,101, "四级部门"+i));
        }
    }
}

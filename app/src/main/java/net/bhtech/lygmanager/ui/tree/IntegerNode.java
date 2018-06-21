package net.bhtech.lygmanager.ui.tree;

/**
 * Created by zhangxinbiao on 2018/6/11.
 */

public class IntegerNode extends Node<Integer>{

    private int id;//部门ID
    private int parentId;//父亲节点ID
    private String name;//部门名称

    public IntegerNode() {
    }

    public IntegerNode(int id, int parentId, String name) {
        this.id = id;
        this.parentId = parentId;
        this.name = name;
    }

    /**
     * 此处返回节点ID
     * @return
     */
    @Override
    public Integer get_id() {
        return id;
    }

    /**
     * 此处返回父亲节点ID
     * @return
     */
    @Override
    public Integer get_parentId() {
        return parentId;
    }

    @Override
    public String get_label() {
        return name;
    }

    @Override
    public boolean parent(Node dest) {
        if (id == ((Integer)dest.get_parentId()).intValue()){
            return true;
        }
        return false;
    }

    @Override
    public boolean child(Node dest) {
        if (parentId == ((Integer)dest.get_id()).intValue()){
            return true;
        }
        return false;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

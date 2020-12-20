package api;

import gameClient.util.Point3D;

public class NodeData implements node_data {

    private int key, tag;
    private double wt;
    private String info;
    private geo_location geo;
    public static int node_id;

    public NodeData() {
        this.key = node_id++;
        this.tag = 0;
        this.wt = 0;
        this.info = "";
        this.geo = new Point3D(0,0,0);
    }

    public NodeData(int key) {
        this.key = key;
        this.geo = new Point3D(0,0,0);
    }

    public NodeData(node_data other) { // copy constructor
        this.key = other.getKey();
        this.tag = other.getTag();
        this.wt = other.getWeight();
        this.info = other.getInfo();
        this.geo = other.getLocation();
    }

    /**
     * Returns the key (id) associated with this node.
     *
     * @return
     */
    @Override
    public int getKey() {
        return this.key;
    }

    /**
     * Returns the location of this node, if
     * none return null.
     *
     * @return
     */
    @Override
    public geo_location getLocation() {
        return this.geo;
    }

    /**
     * Allows changing this node's location.
     *
     * @param p - new new location  (position) of this node.
     */
    @Override
    public void setLocation(geo_location p) {
        this.geo = p;
    }

    /**
     * Returns the weight associated with this node.
     *
     * @return
     */
    @Override
    public double getWeight() {
        return this.wt;
    }

    /**
     * Allows changing this node's weight.
     *
     * @param w - the new weight
     */
    @Override
    public void setWeight(double w) {
        this.wt = w;
    }

    /**
     * Returns the remark (meta data) associated with this node.
     *
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this node.
     *
     * @param s
     */
    @Override
    public void setInfo(String s) {
        this.info = s;
    }

    /**
     * Temporal data (aka color: e,g, white, gray, black)
     * which can be used be algorithms
     *
     * @return
     */
    @Override
    public int getTag() {
        return this.tag;
    }

    /**
     * Allows setting the "tag" value for temporal marking an node - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }
}
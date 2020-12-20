package api;

public class EdgeData implements edge_data {

    private int src, dest, tag;
    private double w;
    String info;

    public EdgeData() {
        this.src = 0;
        this.dest = 0;
        this.tag = 0;
        this.w = 0;
        this.info = " ";
    }

    public EdgeData (int src, int dest, double w) {
        this.src = src;
        this.dest = dest;
        this.w = w;
    }

    public EdgeData(edge_data other) { // copy constructor
        this.src = other.getSrc();
        this.dest = other.getDest();
        this.tag = other.getTag();
        this.w = other.getWeight();
        this.info = other.getInfo();
    }

    /**
     * The id of the source node of this edge.
     *
     * @return
     */
    @Override
    public int getSrc() {
        return this.src;
    }

    /**
     * The id of the destination node of this edge
     *
     * @return
     */
    @Override
    public int getDest() {
        return this.dest;
    }

    /**
     * @return the weight of this edge (positive value).
     */
    @Override
    public double getWeight() {
        return this.w;
    }

    /**
     * Returns the remark (meta data) associated with this edge.
     *
     * @return
     */
    @Override
    public String getInfo() {
        return this.info;
    }

    /**
     * Allows changing the remark (meta data) associated with this edge.
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
     * This method allows setting the "tag" value for temporal marking an edge - common
     * practice for marking by algorithms.
     *
     * @param t - the new value of the tag
     */
    @Override
    public void setTag(int t) {
        this.tag = t;
    }

    private class EdgeLocation implements edge_location {

        private edge_data edge;

        private EdgeLocation(edge_data edge) {
            this.edge = edge;
        }

        /**
         * Returns the edge on which the location is.
         *
         * @return
         */
        @Override
        public edge_data getEdge() {
            return this.edge;
        }

        /**
         * Returns the relative ration [0,1] of the location between src and dest.
         *
         * @return
         */
        @Override
        public double getRatio() {
            return 0;
        }
    }//end class EdgeLocation
}

package gameClient;

import api.directed_weighted_graph;
import api.edge_data;
import api.geo_location;
import api.node_data;
import gameClient.util.Point3D;
import org.json.JSONObject;

public class CL_Agent {
    public static final double EPS = 0.0001;
    private static int _count = 0;
    private static int _seed = 3331;
    private int id;
    //	private long _key;
    private geo_location pos;
    private double speed;
    private edge_data currEdge;
    private node_data currNode;
    private directed_weighted_graph g;
    private CL_Pokemon currFruit;
    private long sgDt;

    private double _value;


    public CL_Agent(directed_weighted_graph g, int start_node) {
        this.g = g;
        setMoney(0);
        this.currNode = this.g.getNode(start_node);
        this.pos = currNode.getLocation();
        this.id = -1;
        setSpeed(0);
    }
    public void update(String json) {
        JSONObject line;
        try {
            // "GameServer":{"graph":"A0","pokemons":3,"agents":1}}
            line = new JSONObject(json);
            JSONObject ttt = line.getJSONObject("Agent");
            int id = ttt.getInt("id");
            if(id==this.getID() || this.getID() == -1) {
                if(this.getID() == -1) {
                    this.id = id;}
                double speed = ttt.getDouble("speed");
                String p = ttt.getString("pos");
                Point3D pp = new Point3D(p);
                int src = ttt.getInt("src");
                int dest = ttt.getInt("dest");
                double value = ttt.getDouble("value");
                this.pos = pp;
                this.setCurrNode(src);
                this.setSpeed(speed);
                this.setNextNode(dest);
                this.setMoney(value);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
    //@Override
    public int getSrcNode() {return this.currNode.getKey();}
    public String toJSON() {
        int d = this.getNextNode();
        String ans = "{\"Agent\":{"
                + "\"id\":"+this.id +","
                + "\"value\":"+this._value+","
                + "\"src\":"+this.currNode.getKey()+","
                + "\"dest\":"+d+","
                + "\"speed\":"+this.getSpeed()+","
                + "\"pos\":\""+ pos.toString()+"\""
                + "}"
                + "}";
        return ans;
    }
    private void setMoney(double v) {_value = v;}

    public boolean setNextNode(int dest) {
        boolean ans = false;
        int src = this.currNode.getKey();
        this.currEdge = g.getEdge(src, dest);
        if(currEdge !=null) {
            ans=true;
        }
        else {
            currEdge = null;}
        return ans;
    }
    public void setCurrNode(int src) {
        this.currNode = g.getNode(src);
    }
    public boolean isMoving() {
        return this.currEdge !=null;
    }
    public String toString() {
        return toJSON();
    }
    public String toString1() {
        String ans=""+this.getID()+","+ pos +", "+isMoving()+","+this.getValue();
        return ans;
    }
    public int getID() {
        // TODO Auto-generated method stub
        return this.id;
    }

    public geo_location getLocation() {
        // TODO Auto-generated method stub
        return pos;
    }


    public double getValue() {
        // TODO Auto-generated method stub
        return this._value;
    }



    public int getNextNode() {
        int ans;
        if(this.currEdge ==null) {
            ans = -1;}
        else {
            ans = this.currEdge.getDest();
        }
        return ans;
    }

    public double getSpeed() {
        return this.speed;
    }

    public void setSpeed(double v) {
        this.speed = v;
    }
    public CL_Pokemon getCurrFruit() {
        return currFruit;
    }
    public void setCurrFruit(CL_Pokemon curr_fruit) {
        this.currFruit = curr_fruit;
    }
    public void set_SDT(long ddtt) {
        long ddt = ddtt;
        if(this.currEdge !=null) {
            double w = getCurrEdge().getWeight();
            geo_location dest = g.getNode(getCurrEdge().getDest()).getLocation();
            geo_location src = g.getNode(getCurrEdge().getSrc()).getLocation();
            double de = src.distance(dest);
            double dist = pos.distance(dest);
            if(this.getCurrFruit().getEdge()==this.getCurrEdge()) {
                dist = currFruit.getLocation().distance(this.pos);
            }
            double norm = dist/de;
            double dt = w*norm / this.getSpeed();
            ddt = (long)(1000.0*dt);
        }
        this.setSgDt(ddt);
    }

    public edge_data getCurrEdge() {
        return this.currEdge;
    }
    public long getSgDt() {
        return sgDt;
    }
    public void setSgDt(long sgDt) {
        this.sgDt = sgDt;
    }
}
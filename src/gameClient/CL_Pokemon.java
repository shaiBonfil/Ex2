package gameClient;

import api.EdgeData;
import api.edge_data;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gameClient.util.Point3D;

import java.io.FileReader;


public class CL_Pokemon {
    private edge_data edge;
    private double value;
    private int type;
    private Point3D pos;
    private double minDist;
    private int minRo;

    public CL_Pokemon(Point3D p, int t, double v,double s, edge_data e) {
        this.type = t;
        this.value = v;
        this.edge = e;
        this.pos = p;
        this.minDist = 0;
        this.minRo = 0;
    }

//    public static CL_Pokemon initFromJson(String json) {
//        CL_Pokemon ans = null;
//        try {
//            JsonObject a = new JsonParser().parse(new FileReader(json)).getAsJsonObject();
//            Point3D p = new Point3D((Double.parseDouble(a.get("pos").getAsString().split(",")[0])), (Double.parseDouble(a.get("pos").getAsString().split(",")[1])), (Double.parseDouble(a.get("pos").getAsString().split(",")[2])));
//            ans = new CL_Pokemon(p, a.get("type").getAsInt(), a.get("value").getAsDouble(), new EdgeData(a.get("src").getAsInt(), a.get("dest").getAsInt(),0, a.get("w").getAsDouble()));
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return ans;
//    }

    public String toString() {
        return "F:{v=" + value + ", t=" + type + "}";
    }

    public edge_data getEdge() {
        return this.edge;
    }

    public void setEdge(edge_data edge) {
        this.edge = edge;
    }

    public Point3D getLocation() {
        return this.pos;
    }

    public int getType() {
        return this.type;
    }

    public double getValue() {
        return this.value;
    }

    public double getMinDist() {
        return minDist;
    }

    public void setMinDist(double minDist) {
        this.minDist = minDist;
    }

    public int getMinRo() {
        return this.minRo;
    }

    public void setMinRo(int minRo) {
        this.minRo = minRo;
    }

}

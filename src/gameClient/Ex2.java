package gameClient;

import Server.Game_Server_Ex2;
import api.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import gameClient.util.Point3D;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Ex2 implements Runnable {
    MyFrame frame;
    private static Arena arena;
    public static int id, scenario;
    HashMap<Integer, CL_Pokemon> pok = new HashMap<Integer, CL_Pokemon>();
    HashMap<Integer, Double> pokDist = new HashMap<Integer, Double>();
    JTextField idt = new JTextField();
    JTextField levelt = new JTextField();


    public static void main(String[] args) {
        if (args.length == 0) {
            Ex2 e = new Ex2();
            e.Login();
        } else {
            id = Integer.parseInt(args[0]);
            scenario = Integer.parseInt(args[1]);
        }
    }


    public void run() {
        int scenario_num = this.scenario;
        game_service game = Game_Server_Ex2.getServer(scenario_num); // you have [0,23] games
        game.login(id);
        String g = game.getGraph();
        String pks = game.getPokemons();
        directed_weighted_graph gg = game.getJava_Graph_Not_to_be_used();

        dw_graph_algorithms ga = new DWGraph_Algo();
        ga.init(gg);
        init(game);

        game.startGame();
        frame.setTitle("Ex2 - OOP: (NONE trivial Solution) " + game.toString() + "move" + "grade");
        int ind = 0;
        long dt = 100;


        while (game.isRunning()) {
            moveAgents(game, ga);

            try {
                if (ind % 1 == 0) {
                    frame.update(arena);
                    frame.repaint();
                }
                Thread.sleep(dt);
                ind++;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        String res = game.toString();

        System.out.println(res);
        System.exit(0);
    }


    private directed_weighted_graph jsonToGraph(String json) {
        directed_weighted_graph g = new DWGraph_DS();
        try {
            JsonObject graph = new JsonParser().parse((json)).getAsJsonObject();
            JsonArray nodes = graph.getAsJsonArray("Nodes");
            JsonArray edges = graph.getAsJsonArray("Edges");
            for (JsonElement n : nodes) {
                node_data v = new NodeData(((JsonObject) n).get("id").getAsInt());
                v.setLocation(new Point3D((Double.parseDouble(((JsonObject) n).get("pos").getAsString().split(",")[0])), (Double.parseDouble(((JsonObject) n).get("pos").getAsString().split(",")[1])), (Double.parseDouble(((JsonObject) n).get("pos").getAsString().split(",")[2]))));
                g.addNode(v);
            }
            for (JsonElement edge : edges) {
                g.connect(((JsonObject) edge).get("src").getAsInt(), ((JsonObject) edge).get("dest").getAsInt(), ((JsonObject) edge).get("w").getAsDouble());
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return g;
    }


    private void moveAgents(game_service game, dw_graph_algorithms ga) {
        String ags = game.move();
        List<CL_Agent> agents = Arena.getAgents(ags, ga.getGraph());
        List<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());

        arena.setGraph(ga.getGraph());
        arena.setAgents(agents);
        arena.setPokemons(pokemons);

        for (CL_Pokemon p : pokemons) {
            Arena.updateEdge(p, ga.getGraph());
        }

        for (CL_Agent a : agents) {
            if (a.getNextNode() == -1) {
                if (this.pok.get(a.getID()) == null) {
                    CL_Pokemon pk = closestPokemon(game, ga, a);
                }

                node_data n = next(ga, a, this.pok.get(a.getID()));
                game.chooseNextEdge(a.getID(), n.getKey());

            }
        }
    }


    private node_data next(dw_graph_algorithms ga, CL_Agent agent, CL_Pokemon pok) {
        int max, min;
        LinkedList<node_data> list;
        if (pok.getType() == -1) {
            max = Integer.max(pok.getEdge().getSrc(), pok.getEdge().getDest());
            list = (LinkedList) ga.shortestPath(agent.getSrcNode(), max);
            if (list.size() == 1) {
                this.pok.put(agent.getID(), null);
                return ga.getGraph().getNode(Integer.min(pok.getEdge().getSrc(), pok.getEdge().getDest()));
            } else {
                return list.get(1);
            }

        } else {
            min = Integer.min(pok.getEdge().getSrc(), pok.getEdge().getDest());
            list = (LinkedList) ga.shortestPath(agent.getSrcNode(), min);
            if (list.size() == 1) {
                this.pok.put(agent.getID(), null);
                return ga.getGraph().getNode(Integer.max(pok.getEdge().getSrc(), pok.getEdge().getDest()));
            } else {
                return list.get(1);
            }
        }

    }


    public CL_Pokemon closestPokemon(game_service game, dw_graph_algorithms ga, CL_Agent a) {
        ArrayList<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());
        this.pokDist.put(a.getID(), Double.MIN_VALUE);
        for (CL_Pokemon p : pokemons) {
            Arena.updateEdge(p, ga.getGraph());
            if (p.getType() == -1) {
                int max = Integer.max(p.getEdge().getSrc(), p.getEdge().getDest());
                double d = p.getValue() / ga.shortestPathDist(a.getSrcNode(), max) + p.getEdge().getWeight();
                if (d > pokDist.get(a.getID())) {
                    this.pokDist.put(a.getID(), d);
                    this.pok.put(a.getID(), p);
                }


            } else {
                int min = Integer.min(p.getEdge().getSrc(), p.getEdge().getDest());
                double d = p.getValue() / ga.shortestPathDist(a.getSrcNode(), min) + p.getEdge().getWeight();
                if (d > pokDist.get(a.getID())) {
                    this.pokDist.put(a.getID(), d);
                    this.pok.put(a.getID(), p);
                }

            }

        }
        return this.pok.get(a.getID());

    }

    public void Login() {
        JFrame frame2 = new JFrame();
        JLabel id = new JLabel("Please enter your ID:");
        JLabel level = new JLabel("Please enter your desired level: ");

        JButton submit = new JButton("SUBMIT");
        submit.addActionListener(this::ActionPerformed);

        JPanel pnl = new JPanel(new GridLayout(0, 1));
        pnl.add(id);
        pnl.add(idt);
        pnl.add(level);
        pnl.add(levelt);
        pnl.add(submit);
        pnl.setSize(1000, 700);


        frame2.setLayout(new GridLayout(0, 1));
        frame2.setSize(1000, 700);
        frame2.add(pnl, BorderLayout.CENTER);
        frame2.pack();
        frame2.setVisible(true);
        frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }

    public void ActionPerformed(ActionEvent action) {
        System.out.println("askfhl");
        this.scenario = Integer.parseInt(levelt.getText());
        this.id = Integer.parseInt(idt.getText());
        new Ex2();
        Thread t = new Thread(new Ex2());
        t.start();


    }

    private void init(game_service game) {
        String g = game.getGraph();
        dw_graph_algorithms ga = new DWGraph_Algo();

        directed_weighted_graph gg = jsonToGraph(g);
        arena = new Arena();
        arena.setGraph(gg);

        frame = new MyFrame("test Ex2");
        frame.setSize(1000, 700);
        frame.update(arena);


        frame.show();
        String info = game.toString();
        JSONObject line;
        try {
            line = new JSONObject(info);
            JSONObject ttt = line.getJSONObject("GameServer");
            int rs = ttt.getInt("agents");

            System.out.println(info);
            System.out.println(game.getPokemons());
            ArrayList<CL_Pokemon> pokemons = Arena.json2Pokemons(game.getPokemons());
            for (int i = 0; i < pokemons.size(); i++) {
                Arena.updateEdge(pokemons.get(i), gg);
            }
            for (int i = 0; i < rs; i++) {
                int ind = i % pokemons.size();
                CL_Pokemon c = pokemons.get(ind);
                int nn = c.getEdge().getDest();
                if (c.getType() < 0) {
                    nn = c.getEdge().getSrc();
                }

                game.addAgent(nn);

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


}

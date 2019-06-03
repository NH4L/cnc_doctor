package doctor.aysst.www.neo4j;

import doctor.aysst.www.utils.Http;
import net.sf.json.JSONObject;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.*;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.Comparator;


@Repository("CNCProblemDao")
public class Findanswer {

    GraphDatabaseService graphDb;
    File databaseDirectory = new File(Http.ROOT_PATH + "/graph+.db");
//    public static void main(String args[]) {
//        Findanswer A = new Findanswer();
//        try {
//            A.find("0iMC/TC型号出现了非法使用小数点的问题。");
////            A.findid("009","FANUC","0MC","程序错误(P/S报警)");
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        A.shutDown();
//    }
    public JSONObject find(String sentence)throws IOException {
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(databaseDirectory);
        registerShutdownHook(graphDb);
        String sentencekey = null;
        Map<String, Double> savesolution = null;
        JSONObject object = null;
        try (Transaction tx = graphDb.beginTx()) {
            try {
                sentencekey = new genecpyer().analyQuery(sentence);
            } catch (Exception e) {
                e.printStackTrace();
            }
            String[] keyarray = sentencekey.split(" ");
            System.out.println("test为:" + sentencekey);
            for (int i = 0; i < keyarray.length; i++) {
                keyarray[i] = "\"" + keyarray[i] + "\"";
                //System.out.println(keyarray[i]);
            }

            String query1 = "match(a:Machinetype),(b:Keyid) where a.type=" + keyarray[1] + "and a.brand=" + keyarray[0] + "and b.special=" + keyarray[2] + " return b.description";
            String query2 = "match(a:Machinetype),(b:Keyid) where a.type=" + keyarray[1] + "and b.special=" + keyarray[2] + " return b.description";
            String query3 = "match(a:Machinetype),(b:Keyid) where a.brand=" + keyarray[0] + "and b.special=" + keyarray[2] + " return b.description";
            // match(a:Machinetype),(b:Keyid)where a.type="0iMC/TC" and a.brand="FANUC"and b.special="非法" return b.description
            //match(a:Machinetype),(b:Keyid)where a.type="0iMC/TC" and a.brand="FANUC"and b.description="非法使用小数点" match (b)-[r:generates]->(c) return c
//            System.out.println(query);
            Map<String, Object> parameters = new HashMap<String, Object>();
            Map<String, Object> parameters1 = new HashMap<String, Object>();
            Map<String, Object> parameters2 = new HashMap<String, Object>();
            Result result = null;
            Result result2 = null;
            if ((keyarray[0].equals("\"mb\"")) && !(keyarray[1].equals("\"mt\""))) {
                System.out.println("运行缺少品牌");

                result = graphDb.execute(query2, parameters);
                result2 = graphDb.execute(query2, parameters);


            }
            if (!(keyarray[0].equals("\"mb\"")) && (keyarray[1].equals("\"mt\""))) {
                System.out.println("运行缺少型号");

                result = graphDb.execute(query3, parameters1);
                result2 = graphDb.execute(query3, parameters1);


            }
            if (!(keyarray[0].equals("\"mb\"")) && !(keyarray[1].equals("\"mt\""))) {
                System.out.println("运行完备");
                result = graphDb.execute(query1, parameters2);
                result2 = graphDb.execute(query1, parameters2);

            }
            int count = 0;


            while (result.hasNext()) {
                count += 1;

                //result.next();
                for (Map.Entry<String, Object> entry : result.next().entrySet()) {

                }

            }
            System.out.println("count数目为:" + count);
            String savedp[] = new String[count];
            String savefinaldp[] = new String[count];
            Double savefinalchoice[] = new Double[count];

            int counti = 0;
            int countj = 0;
            int countk = 0;

            while (result2.hasNext()) {

                for (Map.Entry<String, Object> entry : result2.next().entrySet()) {
//                    System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue().toString());
                    savedp[counti] = entry.getValue().toString();
                    counti++;
                }

            }

            similarity getsimi = new similarity();

            Map<String, Double> savesimkv = new TreeMap<String, Double>();
            for (int i = 0; i < savedp.length; i++) {
                System.out.print("savedp[" + i + "]=" + savedp[i] + "  ");
                double simi = getsimi.getSimilarity(savedp[i], sentence);
                savesimkv.put(savedp[i], simi);
            }

            savesimkv = sortByValueDescending(savesimkv);

            for (Map.Entry<String, Double> entry : savesimkv.entrySet()) {
                System.out.println("待选问题 = " + entry.getKey() + ", 相似度 = " + entry.getValue().toString());
                savefinaldp[countj] = entry.getKey();
                savefinalchoice[countk] = entry.getValue();
                countj++;
                countk++;
            }
            for (int i = 0; i < savefinaldp.length; i++) {
                System.out.println("savefinaldp[" + i + "]=" + savefinaldp[i] + "  ");
                savefinaldp[i] = "\"" + savefinaldp[i] + "\"";
            }
            System.out.println();
            Result finalresult1 = null;
            Result finalresult2 = null;
            Result finalresult3 = null;
            Result finalresult4 = null;
            Result finalresult5 = null;

            String queryfinal1 = "match(a:Machinetype),(b:Keyid) where a.type=" + keyarray[1] + "and b.description=" + savefinaldp[0] + "match (b)-[r:generates]->(c) return c.solution";
            String queryfinal2 = "match(a:Machinetype),(b:Keyid) where a.brand=" + keyarray[0] + "and b.description=" + savefinaldp[0] + "match (b)-[r:generates]->(c) return c.solution";
            String queryfinal3 = "match(a:Machinetype),(b:Keyid) where a.type=" + keyarray[1] + "and a.brand=" + keyarray[0] + "and b.description=" + savefinaldp[0] + "match (b)-[r:generates]->(c) return c.solution";

            String queryfinal4 = "match(a:Machinetype),(b:Keyid) where a.type=" + keyarray[1] + "and b.description=" + savefinaldp[1] + "match (b)-[r:generates]->(c) return c.solution";
            String queryfinal5 = "match(a:Machinetype),(b:Keyid) where a.brand=" + keyarray[0] + "and b.description=" + savefinaldp[1] + "match (b)-[r:generates]->(c) return c.solution";
            String queryfinal6 = "match(a:Machinetype),(b:Keyid) where a.type=" + keyarray[1] + "and a.brand=" + keyarray[0] + "and b.description=" + savefinaldp[1] + "match (b)-[r:generates]->(c) return c.solution";

            String queryfinal7 = "match(a:Machinetype),(b:Keyid) where a.type=" + keyarray[1] + "and b.description=" + savefinaldp[2] + "match (b)-[r:generates]->(c) return c.solution";
            String queryfinal8 = "match(a:Machinetype),(b:Keyid) where a.brand=" + keyarray[0] + "and b.description=" + savefinaldp[2] + "match (b)-[r:generates]->(c) return c.solution";
            String queryfinal9 = "match(a:Machinetype),(b:Keyid) where a.type=" + keyarray[1] + "and a.brand=" + keyarray[0] + "and b.description=" + savefinaldp[2] + "match (b)-[r:generates]->(c) return c.solution";

            String queryfinal10 = "match(a:Machinetype),(b:Keyid) where a.type=" + keyarray[1] + "and b.description=" + savefinaldp[3] + "match (b)-[r:generates]->(c) return c.solution";
            String queryfinal11 = "match(a:Machinetype),(b:Keyid) where a.brand=" + keyarray[0] + "and b.description=" + savefinaldp[3] + "match (b)-[r:generates]->(c) return c.solution";
            String queryfinal12 = "match(a:Machinetype),(b:Keyid) where a.type=" + keyarray[1] + "and a.brand=" + keyarray[0] + "and b.description=" + savefinaldp[3] + "match (b)-[r:generates]->(c) return c.solution";

            String queryfinal13 = "match(a:Machinetype),(b:Keyid) where a.type=" + keyarray[1] + "and b.description=" + savefinaldp[4] + "match (b)-[r:generates]->(c) return c.solution";
            String queryfinal14 = "match(a:Machinetype),(b:Keyid) where a.brand=" + keyarray[0] + "and b.description=" + savefinaldp[4] + "match (b)-[r:generates]->(c) return c.solution";
            String queryfinal15 = "match(a:Machinetype),(b:Keyid) where a.type=" + keyarray[1] + "and a.brand=" + keyarray[0] + "and b.description=" + savefinaldp[4] + "match (b)-[r:generates]->(c) return c.solution";

            // System.out.println(queryfinal1);
            if ((keyarray[0].equals("\"mb\"")) && !(keyarray[1].equals("\"mt\""))) {

                finalresult1 = graphDb.execute(queryfinal1, parameters);
                finalresult2 = graphDb.execute(queryfinal4, parameters);
                finalresult3 = graphDb.execute(queryfinal7, parameters);
                finalresult4 = graphDb.execute(queryfinal10, parameters);
                finalresult5 = graphDb.execute(queryfinal13, parameters);

            }
            if (!(keyarray[0].equals("\"mb\"")) && (keyarray[1].equals("\"mt\""))) {


                finalresult1 = graphDb.execute(queryfinal2, parameters1);
                finalresult2 = graphDb.execute(queryfinal5, parameters1);
                finalresult3 = graphDb.execute(queryfinal8, parameters1);
                finalresult4 = graphDb.execute(queryfinal11, parameters1);
                finalresult5 = graphDb.execute(queryfinal14, parameters1);


            }
            if (!(keyarray[0].equals("\"mb\"")) && !(keyarray[1].equals("\"mt\""))) {
                finalresult1 = graphDb.execute(queryfinal3, parameters2);
                finalresult2 = graphDb.execute(queryfinal6, parameters1);
                finalresult3 = graphDb.execute(queryfinal9, parameters1);
                finalresult4 = graphDb.execute(queryfinal12, parameters1);
                finalresult5 = graphDb.execute(queryfinal15, parameters1);

            }
            savesolution = new TreeMap<String, Double>();
            while (finalresult1.hasNext()) {
                //System.out.println(finalresult1.next().toString());
                for (Map.Entry<String, Object> entry : finalresult1.next().entrySet()) {
//                    System.out.println("解决方法 : " + entry.getValue() + " 置信度: " + savefinalchoice[0]);
                    savesolution.put(entry.getValue().toString(), savefinalchoice[0]);

                }

            }
            while (finalresult2.hasNext()) {
                //System.out.println(finalresult2.next().toString());
                for (Map.Entry<String, Object> entry : finalresult2.next().entrySet()) {
//                    System.out.println("解决方法 : " + entry.getValue() + " 置信度: " + savefinalchoice[1]);
                    savesolution.put(entry.getValue().toString(), savefinalchoice[1]);

                }

            }
            while (finalresult3.hasNext()) {
                //System.out.println(finalresult3.next().toString());
                for (Map.Entry<String, Object> entry : finalresult3.next().entrySet()) {
//                    System.out.println("解决方法 : " + entry.getValue() + " 置信度: " + savefinalchoice[2]);
                    savesolution.put(entry.getValue().toString(), savefinalchoice[2]);

                }
            }
            while (finalresult4.hasNext()) {
                //System.out.println(finalresult4.next().toString());
                for (Map.Entry<String, Object> entry : finalresult4.next().entrySet()) {
//                    System.out.println("解决方法 : " + entry.getValue() + " 置信度: " + savefinalchoice[3]);
                    savesolution.put(entry.getValue().toString(), savefinalchoice[3]);
                }

            }
            while (finalresult5.hasNext()) {
                //System.out.println(finalresult5.next().toString());
                for (Map.Entry<String, Object> entry : finalresult5.next().entrySet()) {
//                    System.out.println("解决方法 : " + entry.getValue() + " 置信度: " + savefinalchoice[4]);
                    savesolution.put(entry.getValue().toString(), savefinalchoice[4]);
                }

            }
            savesolution = sortByValueDescending(savesolution);
            object = new JSONObject();
            int i = 0;
            for (Map.Entry<String, Double> entry : savesolution.entrySet()) {
                JSONObject obj = new JSONObject();
                obj.put("solution", entry.getKey());
                obj.put("percentage", entry.getValue());
                object.put("problem" + i, obj);
                i ++;
                System.out.println("解决方法map : " + entry.getKey() + " 置信度map: " + entry.getValue());
            }
            tx.success();
        } catch (QueryExecutionException e) {
            e.printStackTrace();
        }
        shutDown();
        return object;
    }



    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValueDescending(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new LinkedList<Map.Entry<K, V>>(map.entrySet());
        Collections.sort(list, new Comparator<Map.Entry<K, V>>()
        {
            @Override
            public int compare(Map.Entry<K, V> o1, Map.Entry<K, V> o2)
            {
                int compare = (o1.getValue()).compareTo(o2.getValue());
                return -compare;
            }
        });

        Map<K, V> result = new LinkedHashMap<K, V>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
            System.out.println(entry.getKey() + " ---:---" + entry.getValue());
        }
        return result;
    }
    void shutDown() {
        System.out.println();
        System.out.println( "Shutting down database ..." );
        graphDb.shutdown();
    }

    private static void registerShutdownHook( final GraphDatabaseService graphDb ) {
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }

    public JSONObject findid(String quesid, String brand, String machinetype, String questype)throws IOException {
        String quesid1 = "\"" + quesid + "\"";
        String brand1 = "\"" + brand + "\"";
        String machinetype1 = "\"" + machinetype + "\"";
        String questype1 = "\"" + questype + "\"";

        String queryfinalid = "match(a:Machinetype),(b:Keyid) where a.type=" + machinetype1 + "and a.brand=" + brand1 + "and b.questype=" + questype1 + "and b.quesid = " + quesid1 + "match (b)-[r:generates]->(c) return c.solution";
        // System.out.println(queryfinalid);
        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase(databaseDirectory);
        registerShutdownHook(graphDb);
        JSONObject save_solution_id = new JSONObject();
        try (Transaction tx = graphDb.beginTx()) {
            Result result = null;
            Map<String, Object> parameters = new HashMap<String, Object>();
            result = graphDb.execute(queryfinalid, parameters);
            String[] cassolu = null;
            while (result.hasNext()) {
                for (Map.Entry<String, Object> entry : result.next().entrySet()) {
                    cassolu = entry.getValue().toString().split("。");
                    System.out.println("故障原因: " + cassolu[0]+"。" + " 解决方法: " + entry.getValue().toString());
                    save_solution_id.put("problem", cassolu[0]+"。");
                    save_solution_id.put("solution", entry.getValue().toString());
                }
                //System.out.println(cassolu[0]);
            }
            System.out.println(save_solution_id.toString());
            tx.success();
//            for (Map.Entry<String, String> entry : save_solution_id.entrySet()) {
//                System.out.println("a : " + entry.getKey() + " b: " + entry.getValue());
//            }

        } catch (QueryExecutionException e) {
            e.printStackTrace();
        }
        shutDown();

        return save_solution_id;

    }

}



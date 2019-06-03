package doctor.aysst.www.neo4j;

import com.google.gson.JsonArray;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import doctor.aysst.www.utils.Http;
import org.neo4j.graphdb.*;
import org.neo4j.graphdb.factory.*;
import org.neo4j.cypher.internal.javacompat.ExecutionEngine;
import org.neo4j.cypher.internal.javacompat.ExecutionResult;
import org.neo4j.io.fs.FileUtils;
import java.io.*;




public class jsonjerk {
    public static String brand = "";
    public static String descrpition= "";
    public static String solution = "";
    public static String machinetype = "";
    public static String questiontype = "";
    public static String questionspecial = "";
    public static String questionid = "";
    File databaseDirectory = new File( "graph+.db" );
    GraphDatabaseService graphDb;

    ///申明结点标签
    private static enum Labels implements Label {
        Machinetype,Keyid,Solution
    }
    ///申明关系类型
    private static enum RelTypes implements RelationshipType {
        belongsto,generates,has
    }




//    public static void main(String args[]) {
//        jsonjerk hello = new jsonjerk();
//
//        try {
//            JsonParser parse = new JsonParser();
//            File file = new File(Http.ROOT_PATH + "/jsonCnc/fanuc.json");
//            FileReader fileReader = new FileReader(file);
//            BufferedReader bufferedReader = new BufferedReader(fileReader);
//            StringBuffer stringBuffer = new StringBuffer();
//            String line;
//            while ((line = bufferedReader.readLine()) != null) {
////                System.out.println(line);
//                try {
//                    JsonObject json = (JsonObject) parse.parse(line);
//                    brand = json.get("problem_equipment_brand").getAsString();
//                    brand ="'"+brand+"'";
//                    descrpition = json.get("problem_fault_description").getAsString();
//                    descrpition ="'"+descrpition+"'";
//                    solution = json.get("problem_fault_solution").getAsString();
//                    solution ="'"+solution+"'";
//                    machinetype = json.get("problem_equipment_system").getAsString();
//                    machinetype ="'"+machinetype+"'";
//                    questiontype = json.get("problem_fault_type").getAsString();
//                    questiontype ="'"+questiontype+"'";
//                    questionspecial = json.get("problem_fault_special").getAsString();
//                    questionspecial = "'"+questionspecial+"'";
//                    questionid = json.get("problem_fault_id").getAsString();
//                    questionid = "'"+questionid+"'";
//                    hello.createDb();
//                    hello.shutDown();
//
//
//                    System.out.println("设备品牌:" + brand);
//                    System.out.println("产品型号:" + machinetype);
//                    System.out.println("问题描述:" + descrpition);     //将json数据转为为String型的数据
//                    System.out.println("问题类型:" + questiontype);
////            JsonObject result=json.get("result").getAsJsonObject();
////            JsonObject today=result.get("today").getAsJsonObject();
//                    System.out.println("解决办法:" + solution);
//                    System.out.println("问题概括:" + questionspecial);
//                    System.out.println("问题代码:" + questionid);
//                    System.out.println("-----------------");
//
//
//
//                } catch (JsonIOException e) {
//                    e.printStackTrace();
//                } catch (JsonSyntaxException e) {
//                    e.printStackTrace();
//                }
//
//
//            }
//
//            fileReader.close();
////            System.out.println("json文件内容:");
////            System.out.println(stringBuffer.toString());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//
//    }
//

    void createDb() throws IOException
    {
//        FileUtils.deleteRecursively( databaseDirectory );

        graphDb = new GraphDatabaseFactory().newEmbeddedDatabase( databaseDirectory );
        registerShutdownHook( graphDb );

        try (Transaction tx = graphDb.beginTx())
        {
//            ///创建结点
//            Node n1 = graphDb.createNode();
//            n1.setProperty("机器型号",machinetype);
//            n1.setProperty("机器品牌",brand);
//            n1.addLabel(Labels.Machinetype);
//            Node n2 = graphDb.createNode();
//            n2.setProperty("问题描述", descrpition);
//            n2.setProperty("问题类型", questiontype);
//            n2.addLabel(Labels.Keyid);
//            Node n3 = graphDb.createNode();
//            n3.setProperty("解决方法", solution);
//            n3.addLabel(Labels.Solution);
//
//
//
//            ///创建关系
//            n1.createRelationshipTo(n2, RelTypes.has);
//            n2.createRelationshipTo(n1, RelTypes.belongsto);
//            n2.createRelationshipTo(n3, RelTypes.generates);
            String creatnode1 ="MERGE (n:"+Labels.Machinetype+"{type: "+machinetype+",brand:"+brand+"})";
            String creatnode2 ="MERGE (n:"+Labels.Keyid+"{description: "+descrpition+",questype:"+questiontype+",quesid:"+questionid+",special:"+questionspecial+"})";
            String creatnode3 ="MERGE (n:"+Labels.Solution+"{solution: "+solution+"})";

            Result result = graphDb.execute(creatnode1);
            Result result2 = graphDb.execute(creatnode2);
            Result result3 = graphDb.execute(creatnode3);

            String creatrelationship1="MATCH(a:"+Labels.Machinetype+"),(b:"+Labels.Keyid+")WHERE a.type ="+machinetype+"AND b.description="+descrpition+"   MERGE (a)-[r:"+RelTypes.has+"]->(b)";
            String creatrelationship2="MATCH(a:"+Labels.Keyid+"),(b:"+Labels.Machinetype+")WHERE a.description ="+descrpition+"AND b.type="+machinetype+"   MERGE (a)-[r:"+RelTypes.belongsto+"]->(b)";
            String creatrelationship3="MATCH(a:"+Labels.Keyid+"),(b:"+Labels.Solution+")WHERE a.description ="+descrpition+"AND b.solution="+solution+"   MERGE (a)-[r:"+RelTypes.generates+"]->(b)";


            Result result4 = graphDb.execute(creatrelationship1);
            Result result5 = graphDb.execute(creatrelationship2);
            Result result6 = graphDb.execute(creatrelationship3);
            tx.success();
        }catch(org.neo4j.graphdb.QueryExecutionException e){
            e.printStackTrace();
        }
    }
    void shutDown()
    {
        System.out.println();
        System.out.println( "Shutting down database ..." );
        graphDb.shutdown();
    }

    private static void registerShutdownHook( final GraphDatabaseService graphDb )
    {
        Runtime.getRuntime().addShutdownHook( new Thread()
        {
            @Override
            public void run()
            {
                graphDb.shutdown();
            }
        } );
    }
    }


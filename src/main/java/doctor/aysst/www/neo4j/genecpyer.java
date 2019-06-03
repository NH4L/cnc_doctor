package doctor.aysst.www.neo4j;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import doctor.aysst.www.utils.Http;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.mllib.classification.NaiveBayes;
import org.apache.spark.mllib.classification.NaiveBayesModel;
import org.apache.spark.mllib.linalg.Vector;
import org.apache.spark.mllib.linalg.Vectors;
import org.apache.spark.mllib.regression.LabeledPoint;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import scala.Char;
import java.util.*;
/**
 * Spark贝叶斯分类器 + HanLP分词器 + 实现问题语句的抽象+模板匹配+关键性语句还原
 */
public class genecpyer {


    /**
     * 分类标签号和问句模板对应表
     */
    Map<Double, String> questionsPattern;

    /**
     * Spark贝叶斯分类器
     */
    NaiveBayesModel nbModel;

    /**
     * 词语和下标的对应表   == 词汇表
     */
    Map<String, Integer> vocabulary;

    /**
     * 关键字与其词性的map键值对集合 == 句子抽象
     */
    Map<String, String> abstractMap;

    /**
     * 指定问题question及字典的txt模板所在的根目录
     */
    String rootDirPath = Http.ROOT_PATH + "/geneques/";

    /**
     * 分类模板索引
     */
    int modelIndex = 0;

    public genecpyer() throws Exception{
        questionsPattern = loadQuestionsPattern();
        vocabulary = loadVocabulary();
        nbModel = loadClassifierModel();
    }


    public genecpyer(String rootDirPath) throws Exception{
        this.rootDirPath = rootDirPath+'/';
        questionsPattern = loadQuestionsPattern();
        vocabulary = loadVocabulary();
        nbModel = loadClassifierModel();
    }

    public String analyQuery(String queryString) throws Exception {

        /**
         * 打印问句
         */
        System.out.println("原始句子："+queryString);
        System.out.println("========HanLP开始分词========");

        /**
         * 抽象句子，利用HanPL分词，将关键字进行词性抽象
         */
        String abstr = queryAbstract(queryString);
        System.out.println("句子抽象化结果："+abstr);// nm 的 导演 是 谁

        /**
         * 将抽象的句子与spark训练集中的模板进行匹配，拿到句子对应的模板
         */
        String strPatt = queryClassify(abstr);
        System.out.println("句子套用模板结果："+strPatt); // nm 制作 导演列表


        /**
         * 模板还原成句子，此时问题已转换为我们熟悉的操作
         */
        String finalPattern = queryExtenstion(strPatt);
        System.out.println("原始句子替换成系统可识别的结果："+finalPattern);// 但丁密码 制作 导演列表


        ArrayList<String> resultList = new ArrayList<String>();
        resultList.add(String.valueOf(modelIndex));
        String[] finalPattArray = finalPattern.split(" ");
        for (String word : finalPattArray)
            resultList.add(word);

        return finalPattern;
    }

    public  String queryAbstract(String querySentence) {

        // 句子抽象化
        Segment segment = HanLP.newSegment().enableCustomDictionary(true);
        List<Term> terms = segment.seg(querySentence);
        String abstractQuery = "";
        abstractMap = new HashMap<String, String>();
        int nrCount = 0; //nr 人名词性这个 词语出现的频率
        for (Term term : terms) {
            String word = term.word;
            String termStr = term.toString();
            System.out.println(termStr);
            if(term.word.contains("FANUC"))
            {
                abstractQuery+= "mb";
                abstractMap.put("mb", word);
            }
            else if(term.word.contains("mitsubishi"))
            {
                abstractQuery+= "mb";
                abstractMap.put("mb", "MITSUBISHI");
            }
            else if(term.word.contains("三菱"))
            {
                abstractQuery+= "mb";
                abstractMap.put("mb", "MITSUBISHI");
            }
            else if(term.word.contains("MITSUBISHI"))
            {
                abstractQuery+= "mb";
                abstractMap.put("mb", word);
            }
            else if(term.word.contains("fanuc"))
            {
                abstractQuery+= "mb";
                abstractMap.put("mb", "FANUC");
            }
            else if(term.word.contains("发那科"))
            {
                abstractQuery+= "mb";
                abstractMap.put("mb", "FANUC");
            }
            else if(term.word.contains("西门子"))
            {
                abstractQuery+= "mb";
                abstractMap.put("mb", "SIEMENS");
            }
            else if(term.word.contains("siemens"))
            {
                abstractQuery+= "mb";
                abstractMap.put("mb", "SIEMENS");
            }
            else if(term.word.contains("SIEMENS"))
            {
                abstractQuery+= "mb";
                abstractMap.put("mb", word);
            }
            else if(term.word.contains("0iMC/TC"))
            {
                abstractQuery+= "mt";
                abstractMap.put("mt", word);
            }
            else if(term.word.contains("FANUC_0i-A"))
            {
                abstractQuery+= "mt";
                abstractMap.put("mt", word);
            }
            else if(term.word.contains("840D/840Di/810D"))
            {
                abstractQuery+= "mt";
                abstractMap.put("mt", word);
            }
            else if(term.word.contains("60/60S"))
            {
                abstractQuery+= "mt";
                abstractMap.put("mt", word);
            }

            else if(term.word.contains("FANUC_0"))
            {
                abstractQuery+= "mt";
                abstractMap.put("mt", word);
            }

            else if(term.word.contains("OSP-E100L/OSP-E10L"))
            {
                abstractQuery+= "mt";
                abstractMap.put("mt", word);
            }
            else {
                abstractQuery += word + " ";
            }

        }
        System.out.println(abstractQuery);
        System.out.println("========HanLP分词结束========");

        return abstractQuery;
    }

    public  String queryExtenstion(String queryPattern) {
        // 句子还原
        Set<String> set = abstractMap.keySet();
        for (String key : set) {
            /**
             * 如果句子模板中含有抽象的词性
             */
            if (queryPattern.contains(key)) {

                /**
                 * 则替换抽象词性为具体的值
                 */
                String value = abstractMap.get(key);
                queryPattern = queryPattern.replace(key, value);
            }
        }
        String extendedQuery = queryPattern;
        /**
         * 当前句子处理完，抽象map清空释放空间并置空，等待下一个句子的处理
         */
        abstractMap.clear();
        abstractMap = null;
        return extendedQuery;
    }


    /**
     * 加载词汇表 == 关键特征 == 与HanLP分词后的单词进行匹配
     * @return
     */
    public  Map<String, Integer> loadVocabulary() {
        Map<String, Integer> vocabulary = new HashMap<String, Integer>();
        File file = new File(Http.ROOT_PATH + "/geneques/question/vocabulary.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String line;
        try {
            while ((line = br.readLine()) != null) {
//                System.out.println(line);
                String[] tokens = line.split(":");

//                    System.out.println(tokens[0].getClass().toString());
//                    System.out.println(tokens[0]);
//                    char s = tokens[0].charAt(0);
//                    System.out.println(tokens[1]);

                    int index = Integer.parseInt(tokens[0]);
//                    System.out.println("index:"+index);
//                    System.out.println(index);
                    String word = tokens[1];

                    vocabulary.put(word, index);
                }
//            for(String key : vocabulary.keySet()){   //只能遍历key
//                System.out.print("Key = "+key);
//            }
//
//
//            for(Integer value :vocabulary.values()){  //只能遍历value
//                System.out.print("Value = "+value);
//            }
//            for (Map.Entry<String, Integer> entry : vocabulary.entrySet()) {
//                System.out.println("Key = " + entry.getKey() + ", Value = " + entry.getValue());
//            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return vocabulary;
    }

    /**
     * 加载文件，并读取内容返回
     * @param filename
     * @return
     * @throws IOException
     */
    public  String loadFile(String filename) throws IOException {
        File file = new File(rootDirPath + filename);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String content = "";
        String line;
        while ((line = br.readLine()) != null) {
            /**
             * 文本的换行符暂定用"`"代替
             */
            content += line + "`";
        }
        /**
         * 关闭资源
         */
        br.close();
        return content;
    }

    /**
     * 句子分词后与词汇表进行key匹配转换为double向量数组
     * @param sentence
     * @return
     * @throws Exception
     */
    public  double[] sentenceToArrays(String sentence) throws Exception {

        double[] vector = new double[vocabulary.size()];
//        System.out.println(vocabulary.size());
        /**
         * 模板对照词汇表的大小进行初始化，全部为0.0
         */
        for (int i = 0; i < vocabulary.size(); i++) {
            vector[i] = 0;
        }

        /**
         * HanLP分词，拿分词的结果和词汇表里面的关键特征进行匹配
         */
        Segment segment = HanLP.newSegment();
        List<Term> terms = segment.seg(sentence);
        for (Term term : terms) {
            String word = term.word;
            /**
             * 如果命中，0.0 改为 1.0
             */
            if (vocabulary.containsKey(word)) {
                int index = vocabulary.get(word);
//                System.out.println("词语是"+word);
//                System.out.println("位置是"+index);
                vector[index] = 1;
            }
        }
        return vector;
    }

    /**
     * Spark朴素贝叶斯(naiveBayes)
     * 对特定的模板进行加载并分类
     */
    public  NaiveBayesModel loadClassifierModel() throws Exception {

        /**
         * 生成Spark对象
         * 一、Spark程序是通过SparkContext发布到Spark集群的
         * Spark程序的运行都是在SparkContext为核心的调度器的指挥下进行的
         * Spark程序的结束是以SparkContext结束作为结束
         * JavaSparkContext对象用来创建Spark的核心RDD的
         * 注意：第一个RDD,一定是由SparkContext来创建的
         *
         * 二、SparkContext的主构造器参数为 SparkConf
         * SparkConf必须设置appname和master，否则会报错
         * spark.master   用于设置部署模式
         * local[*] == 本地运行模式[也可以是集群的形式]，如果需要多个线程执行，可以设置为local[2],表示2个线程 ，*表示多个
         * spark.app.name 用于指定应用的程序名称  ==
         */

        SparkConf conf = new SparkConf().setAppName("NaiveBayesTest").setMaster("local[*]");
        JavaSparkContext sc = new JavaSparkContext(conf);

        /**
         * 训练集生成
         * labeled point 是一个局部向量，要么是密集型的要么是稀疏型的
         * 用一个label/response进行关联。在MLlib里，labeled points 被用来监督学习算法
         * 我们使用一个double数来存储一个label，因此我们能够使用labeled points进行回归和分类
         */
        List<LabeledPoint> train_list = new LinkedList<LabeledPoint>();
        String[] sentences = null;



        String jihuo = loadFile("question/【0】激活.txt");
        sentences = jihuo.split("`");
        for (String sentence : sentences) {
            System.out.println("sentence为"+sentence);
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(0.0, Vectors.dense(array));
            train_list.add(train_one);
        }


        String jiou = loadFile("question/【1】奇偶校验报警.txt");
        sentences = jiou.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(1.0, Vectors.dense(array));
            train_list.add(train_one);
        }



        String shuziwei = loadFile("question/【2】数字位.txt");
        sentences = shuziwei.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(2.0, Vectors.dense(array));
            train_list.add(train_one);
        }



        String dizhi = loadFile("question/【3】地址.txt");
        sentences = dizhi.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(3.0, Vectors.dense(array));
            train_list.add(train_one);
        }


        String feifa = loadFile("question/【4】非法.txt");
        sentences = feifa.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(4.0, Vectors.dense(array));
            train_list.add(train_one);
        }



        String jingeisudu = loadFile("question/【5】进给速度.txt");
        sentences = jingeisudu.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(5.0, Vectors.dense(array));
            train_list.add(train_one);
        }

        String zhoushu = loadFile("question/【6】轴数.txt");
        sentences = zhoushu.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(6.0, Vectors.dense(array));
            train_list.add(train_one);
        }

        String banjing = loadFile("question/【7】半径.txt");
        sentences = banjing.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(7.0, Vectors.dense(array));
            train_list.add(train_one);
        }

        String nrc = loadFile("question/【8】NRC.txt");
        sentences = nrc.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(8.0, Vectors.dense(array));
            train_list.add(train_one);
        }


        String daobu = loadFile("question/【9】刀补.txt");
        sentences = daobu.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(9.0, Vectors.dense(array));
            train_list.add(train_one);
        }


        String g31 = loadFile("question/【10】G31.txt");
        sentences = g31.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(10.0, Vectors.dense(array));
            train_list.add(train_one);
        }

        String ganshe = loadFile("question/【11】干涉.txt");
        sentences = ganshe.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(11.0, Vectors.dense(array));
            train_list.add(train_one);
        }


        String chfcnr = loadFile("question/【12】CHF-CNR.txt");
        sentences = chfcnr.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(12.0, Vectors.dense(array));
            train_list.add(train_one);
        }



        String chengxu = loadFile("question/【13】程序.txt");
        sentences = chengxu.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(13.0, Vectors.dense(array));
            train_list.add(train_one);
        }


        String weifaxian = loadFile("question/【14】未发现.txt");
        sentences = chengxu.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(14.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String mdi = loadFile("question/【15】MDI.txt");
        sentences = mdi.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(15.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String cuowu = loadFile("question/【16】错误.txt");
        sentences = cuowu.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(16.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String cunchuqi = loadFile("question/【17】存储器.txt");
        sentences = cunchuqi.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(17.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String baohu = loadFile("question/【18】保护.txt");
        sentences = baohu.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(18.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String xinhao = loadFile("question/【19】信号.txt");
        sentences = xinhao.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(19.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String yichu = loadFile("question/【20】溢出.txt");
        sentences = yichu.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(20.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String cankaodian = loadFile("question/【21】参考点.txt");
        sentences = cankaodian.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(21.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String pxing = loadFile("question/【22】P型.txt");
        sentences = pxing.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(22.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String g28 = loadFile("question/【23】G28.txt");
        sentences = g28.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(23.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String xieru = loadFile("question/【24】写入.txt");
        sentences = xieru.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(24.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String hongzhiling = loadFile("question/【25】宏指令.txt");
        sentences = hongzhiling.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(25.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String jieshu = loadFile("question/【26】结束.txt");
        sentences = jieshu.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(26.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String baojingxinxi = loadFile("question/【27】报警信息.txt");
        sentences = baojingxinxi.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(27.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String zhuzhou = loadFile("question/【28】主轴.txt");
        sentences = zhuzhou.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(28.0, Vectors.dense(array));
            train_list.add(train_one);
        }

        String daoju = loadFile("question/【29】刀具.txt");
        sentences = daoju.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(29.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String gangxinggongsi = loadFile("question/【30】刚性攻丝.txt");
        sentences = gangxinggongsi.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(30.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String m99 = loadFile("question/【31】M99.txt");
        sentences = m99.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(31.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String fssb = loadFile("question/【32】FSSB.txt");
        sentences = fssb.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(32.0, Vectors.dense(array));
            train_list.add(train_one);
        }
        String baojing = loadFile("question/【33】报警.txt");
        sentences = banjing.split("`");
        for (String sentence : sentences) {
            double[] array = sentenceToArrays(sentence);
            LabeledPoint train_one = new LabeledPoint(33.0, Vectors.dense(array));
            train_list.add(train_one);
        }




        /**
         * SPARK的核心是RDD(弹性分布式数据集)
         * Spark是Scala写的,JavaRDD就是Spark为Java写的一套API
         * JavaSparkContext sc = new JavaSparkContext(sparkConf);    //对应JavaRDD
         * SparkContext	    sc = new SparkContext(sparkConf)    ;    //对应RDD
         */
        JavaRDD<LabeledPoint> trainingRDD = sc.parallelize(train_list);
        NaiveBayesModel nb_model = NaiveBayes.train(trainingRDD.rdd());

        /**
         * 记得关闭资源
         */
        sc.close();

        /**
         * 返回贝叶斯分类器
         */
        return nb_model;

    }

    /**
     * 加载问题模板 == 分类器标签
     * @return
     */
    public  Map<Double, String> loadQuestionsPattern() {
        Map<Double, String> questionsPattern = new HashMap<Double, String>();
        File file = new File(Http.ROOT_PATH + "/geneques/question/question_classification.txt");
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        String line;
        try {
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(":");
//                System.out.println("tokens:" + tokens[0]);
                if (!(tokens[0].equals(""))) {
                    double index = Double.valueOf(tokens[0]);

                    String pattern = tokens[1];
                    questionsPattern.put(index, pattern);
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return questionsPattern;
    }

    /**
     * 贝叶斯分类器分类的结果，拿到匹配的分类标签号，并根据标签号返回问题的模板
     * @param sentence
     * @return
     * @throws Exception
     */
    public  String queryClassify(String sentence) throws Exception {

        double[] testArray = sentenceToArrays(sentence);
        Vector v = Vectors.dense(testArray);

        /**
         * 对数据进行预测predict
         * 句子模板在 spark贝叶斯分类器中的索引【位置】
         * 根据词汇使用的频率推断出句子对应哪一个模板
         */
        double index = nbModel.predict(v);
        modelIndex = (int)index;
        System.out.println("the model index is " + index);
        Vector vRes = nbModel.predictProbabilities(v);
//        for(int i=0;i<34;i++) {
//            System.out.println("问题模板分类【"+i+"】概率：" + vRes.toArray()[i]);
//
//        }
        return questionsPattern.get(index);
    }

//    public static void main(String[] agrs) throws Exception {
//        genecpyer A  =new genecpyer();
////        A.queryClassify("泰坦尼克号主要讲了什么？");
//        A.analyQuery("FANUC的0iMC/TC型号出现了非法使用小数点的问题。");
////        A.analyQuery("0iMC/TC型号刀具号数有毛病。");
//    }
}



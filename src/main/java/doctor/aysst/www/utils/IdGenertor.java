package doctor.aysst.www.utils;

import java.io.File;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

class IdGenertor {
    public static String FILE = "E:/HHU/SecondYear/files/";
//    public static String FILE = "/usr/projects/aysst/files/";
    /**
     * 生成UUID
     *
     * @return UUID
     */
    public static String generateGUID() {
        return new BigInteger(165, new Random()).toString(36).toUpperCase();
    }

    public static String generateOrdersNum() {
        //YYYYMMDD+当前时间（纳秒）  ：   1秒=1000毫秒=1000*1000纳秒
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String str = df.format(now);
        return str+System.nanoTime();
    }
    public static String genericPath(String filename, String storeDirectory) {
        int hashCode = filename.hashCode();
        int dir1 = hashCode&0xf;
        int dir2 = (hashCode&0xf0)>>4;

        String dir = "/"+dir1+"/"+dir2;

        File file = new File(storeDirectory, dir);
        if(!file.exists()){
            file.mkdirs();
        }
        return dir;
    }
}

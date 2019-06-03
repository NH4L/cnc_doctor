package doctor.aysst.www.neo4j;

import java.util.*;
import java.util.stream.Collectors;


public class teststream {

//    public static void main(String[] args) {
//
//       teststream a = new teststream();
////       a.addOperation();
////       a.findMax();
//        a.getArtistInfo();
//
//    }
    class Artist {
        private String name;
        private String nation;

        private Artist(String name, String nation) {
            this.name = name;
            this.nation = nation;
        }
    }




    private void addOperation() {
        Integer[] list = { 1, 2, 3, 4 };
        //1. 将数组转换为列表。
//        int sum = Arrays.asList(list)
//                //2. 使用列表的stream方法获取stream流。
//                .stream()
//                //3. 使用stream的reduce函数遍历列表对象，并将对象相加。
//                //Lambda表达式“(x, y) -> x + y”为累加器。
//                .reduce(0, (x, y) -> x + y);
//        System.out.println("sum : " + sum);

        int sum = Arrays.asList(list).stream().reduce(1,(x,y)->x*y);//得出24,若设置indetity为0则结果为0
        System.out.println(sum);

    }
    private void findMax() {
        Integer[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
        int max = Arrays.asList(array)
                .stream()//生成stream
                .max((x, y) -> x - y)//及早求值运算，获取最大值
                .get();//获取int值
        System.out.println("max : " + max);
    }
    private void getArtistInfo() {
        Artist[] array = { new Artist("Bob", "China"), new Artist("Tom", "USA"), new Artist("Jerry", "Japan"),
                new Artist("Tony", "England"), new Artist("Kitty", "China"), };
//        List<String> artistInfo = Arrays.asList(array)
//                .stream()//生成stream
//                .map(artist -> artist.name + "-" + artist.nation)//对stream里面的元素进行映射
//                .collect(Collectors.toList());//重新生成集合
//        System.out.println("artistInfo : " + artistInfo);
//        System.out.println(Arrays.asList(array));
        List<String> artistInfo = Arrays.asList(array).stream().map(artist -> artist.name+"----"+artist.nation).collect(Collectors.toList());
        System.out.println("artistInfo : " + artistInfo);
    }


}

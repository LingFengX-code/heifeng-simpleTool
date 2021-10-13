package com.heifeng.generator.utils;

import java.io.*;
import java.net.URL;
import java.util.*;

//文件处理工具类
public class FileUtils {

    // 得到相对路径
    public static String getRelativePath(File baseDir,File file) {
        if(baseDir.equals(file))
            return "";
        if(baseDir.getParentFile() == null)
            return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length());
        return file.getAbsolutePath().substring(baseDir.getAbsolutePath().length()+1);
    }

    /**
     * 查询某个目录下的所有文件
     * @param dir 文件对象
     * @return  所有文件夹下所有的文件
     * @throws IOException
     */
    public static List<File> searchAllFile(File dir) throws IOException {
        ArrayList<File> arrayList = new ArrayList<>();
        searchFiles(dir,arrayList);
        return arrayList;
    }

    //递归获取某个目录下的所有文件
    private static void searchFiles(File dir,List<File> collector) throws IOException {
        if(dir.isDirectory()) {
            File[] subFiles = dir.listFiles();
            if(subFiles==null || subFiles.length<=0) {return;}
            for (File subFile : subFiles) {
                searchFiles(subFile, collector);
            }
        }else{
            collector.add(dir);
        }
    }

    /**??
     * 创建文件目录
     * @param dir 父目录路径
     * @param file 子目录路径
     * @return
     */
    public static File mkdir(String dir,String file) {
        if(dir == null) throw new IllegalArgumentException("dir must be not null");
        File result = new File(dir,file);
        if(result.getParentFile() != null) {
            result.getParentFile().mkdirs();
        }
        return result;
    }
}

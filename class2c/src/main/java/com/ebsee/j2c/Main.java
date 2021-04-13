package com.ebsee.j2c;


import java.io.*;


/**
 *
 */
public class Main {


    public static void main(String[] args) throws IOException {
        String jsrcPath = "../app/java/"
//                + File.pathSeparator + "../../awtk-minijvm/java/src/main/java/"//
                ;
        String classesPath = "../app/out/classes/";
        String csrcPath = "../app/out/c/";


        if (args.length < 3) {
            System.out.println("Posix :");
            System.out.println("Convert java to c file:");
            System.out.println("java -cp ./class2ir/dist/class2c.jar com.ebsee.j2c.Main ./app/java ./app/out/classes ./app/out/c/");
        } else {
            jsrcPath = args[0] + "/";
            classesPath = args[1] + "/";
            csrcPath = args[2] + "/";
        }

        System.out.println("java source *.java path      : " + jsrcPath);
        System.out.println("classes *.class output path  : " + classesPath);
        System.out.println("c *.c output path            : " + csrcPath);

        File f;
        boolean res;
        f = new File(classesPath);
        System.out.println(f.getAbsolutePath() + (f.exists() ? " exists " : " not exists"));
        res = deleteTree(f);
        res = f.mkdirs();
        f = new File(csrcPath);
        System.out.println(f.getAbsolutePath() + (f.exists() ? " exists " : " not exists"));
        res = deleteTree(f);
        res = f.mkdirs();

        long startAt = System.currentTimeMillis();
        AssistLLVM.convert(jsrcPath, classesPath, csrcPath);
        System.out.println("convert success , cost :" + (System.currentTimeMillis() - startAt));
    }

    static boolean deleteTree(File f) {
        if (f.isDirectory()) {
            File[] files = f.listFiles();
            for (File sf : files) {
                //System.out.println("file:" + sf.getAbsolutePath());
                deleteTree(sf);
            }
        }
        boolean s = f.delete();
        //System.out.println("delete " + f.getAbsolutePath() + " state:" + s);
        return s;
    }
}



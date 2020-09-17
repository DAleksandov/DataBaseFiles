package model;

import java.io.File;

//Базы данных будут храниться в разных папках - гибрид/не гибрид
//А как определять тип данных в БД? Можно делать пометку в самом файле?
public class DB {
    private static String pathHybrid = "C:\\Users\\ddale\\IdeaProjects\\DataBaseFiles\\HybridDB\\";
    private static String pathNonHybrid = "C:\\Users\\ddale\\IdeaProjects\\DataBaseFiles\\NonHybridDB\\";

    public static void bd(String name, boolean isHybrid) {
        File file;
        if (isHybrid)
            file = new File(pathHybrid + name);
        else
            file = new File(pathNonHybrid + name);
        file.mkdirs();
    }

    public static void setPathHybrid(String newPath) {
        pathHybrid = newPath;
    }

    public static void setPathNonHybrid(String newPath) {
        pathNonHybrid = newPath;
    }

    public static String getPathNonHybrid() {
        return pathNonHybrid;
    }

    public static String getPathHybrid() {
        return pathHybrid;
    }

    public static String[] getListHybridDB() {
        File file = new File(pathHybrid);
        return file.list();
    }

    public static String[] getListNonHybridDB() {
        File file = new File(pathNonHybrid);
        return file.list();
    }
}

package com.anncode.amazonviewer.db;

public class DataBase {

    public static final String URL                      = "jdbc:mysql://localhost:3306/";
    public static final String DB_NAME                  = "amazonviewer";
    public static final String DB_USER                  = "root";
    public static final String DB_PASS                  = "Admin123!";

    public static final String TAB_MOVIE                = "movie";
    public static final String TAB_MOVIE_ID             = "id";
    public static final String TAB_MOVIE_TITLE          = "title";
    public static final String TAB_MOVIE_GENRE          = "genre";
    public static final String TAB_MOVIE_CREATOR        = "creator";
    public static final String TAB_MOVIE_DURATION       = "duration";
    public static final String TAB_MOVIE_YEAR           = "year";

    public static final String TAB_MATERIAL             = "material";
    public static final int[] TAB_MATERIAL_ID           = {1, 2, 3, 4, 5};

    public static final String TAB_USER                 = "user";
    public static final int TAB_USER_ID                 = 1;

    public static final String TAB_VIEWED               = "viewed";
    public static final String TAB_VIEWED_MATERIAL_ID   = "material_id";
    public static final String TAB_VIEWED_ELEMENT_ID    = "element_id";
    public static final String TAB_VIEWED_USER_ID       = "user_id";






}

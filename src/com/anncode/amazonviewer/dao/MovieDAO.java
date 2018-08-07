package com.anncode.amazonviewer.dao;

import com.anncode.amazonviewer.db.IDBConnection;
import com.anncode.amazonviewer.model.Movie;
import org.jetbrains.annotations.Contract;

import java.sql.*;
import java.util.ArrayList;
import static com.anncode.amazonviewer.db.DataBase.*;

public interface MovieDAO extends IDBConnection {



    default ArrayList<Movie> read(){
        ArrayList<Movie> movies = new ArrayList();
        String sql = "SELECT * FROM " +  TAB_MOVIE;
        try (Connection connection = connectToDB()){
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                Movie movie = new Movie(
                        rs.getString(TAB_MOVIE_TITLE),
                        rs.getString(TAB_MOVIE_GENRE),
                        rs.getString(TAB_MOVIE_CREATOR),
                        Integer.valueOf(rs.getString(TAB_MOVIE_DURATION)),
                        Short.valueOf(rs.getString(TAB_MOVIE_YEAR))
                );

                movie.setId(Integer.valueOf(rs.getString(TAB_MOVIE_ID)));
                movie.setViewed(getMovieViewed(
                        preparedStatement,
                        connection,
                        Integer.valueOf(rs.getString(TAB_MOVIE_ID))
                ));
                movies.add(movie);
            }
            System.out.println("Datos Extraidos");

        } catch (SQLException e){
            e.printStackTrace();
        }


        return movies;
    }


    default boolean getMovieViewed(PreparedStatement preparedStatement, Connection connection, int movie_id) {
          boolean viewed = false;
          String query = "SELECT * FROM " + TAB_VIEWED
                  + " WHERE " + TAB_VIEWED_MATERIAL_ID + "= ?"
                  + " AND " + TAB_VIEWED_ELEMENT_ID + "= ?"
                  + " AND " + TAB_VIEWED_USER_ID + "= ?";
          ResultSet rs = null;
          try{
            preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, TAB_MATERIAL_ID[0]);
            preparedStatement.setInt(2, movie_id);
            preparedStatement.setInt(3, TAB_USER_ID);

            rs = preparedStatement.executeQuery();
            viewed = rs.next();
          }catch (Exception e){
            e.printStackTrace();
          }

          return viewed;
    }

    default Movie setMovieViewed(Movie movie){
        String query = "INSERT INTO " + TAB_VIEWED
                + "(" + TAB_VIEWED_MATERIAL_ID  + ","
                      + TAB_VIEWED_ELEMENT_ID   + ","
                      + TAB_VIEWED_USER_ID      + ")"          +
                " VALUES(?,?,?)";
        try(Connection connection = connectToDB()){
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, TAB_MATERIAL_ID[0]);
            preparedStatement.setInt(2, movie.getId());
            preparedStatement.setInt(3, 1);

            if(preparedStatement.executeUpdate() > 0){
                System.out.println("Se marco en visto");
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        return movie;

    }

    default ArrayList<Movie> getMovieReport(String date){

        String query = "SELECT * FROM movie INNER JOIN viewed ON movie.id = viewed.element_id" +
                "  WHERE viewed.material_id = 1" +
                "  AND viewed.user_id = 1" +
                "  AND DATE (viewed.fecha) = ?";
        ArrayList<Movie> movies = new ArrayList<>();
        try(Connection connection = connectToDB()){

            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, date);

            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Movie movie = new Movie(
                        rs.getString("title"),
                        rs.getString("genre"),
                        rs.getString("creator"),
                        rs.getInt("duration"),
                        rs.getShort("year")
                );
                movies.add(movie);
            }

        }catch (SQLException sqle){
            sqle.printStackTrace();
        }

        return movies;

    }
}

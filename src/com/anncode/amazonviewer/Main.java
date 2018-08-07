package com.anncode.amazonviewer;


import java.io.IOException;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Predicate;

import com.anncode.amazonviewer.model.*;
import com.anncode.amazonviewer.util.*;
import com.ancode.makereport.Report;

/**
 * <h1>Amazon Viewer</h1>
 * Amazon Viewer es un programa que permite visualizar movies, series con sus respectivos chapters,
 * Books y magazines. Te permite generar reportes generales y con fecha del dia.
 * <p>
 * Existen algunas reglas como que todos los elementos pueden ser visualizados o leidos a exepcion de los magazines
 *
 * @author ahernand53
 * @version 1.1
 * @since 2018
 * */

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Movie movie = new Movie("Coco", "Animation", "", 120, (short)2017);
		Movie movie2 = new Movie("Coco", "Animation", "", 120, (short)2017);

		if (movie.equals(movie)) {
			System.out.println(true);
		} else {
			System.out.println(false);
		}
		System.out.println(movie);

		showMenu();


	}
	
	public static void showMenu() {
		int exit = 0;
		do {
			
			System.out.println("BIENVENIDOS AMAZON VIEWER");
			System.out.println("");
			System.out.println("Selecciona el número de la opción deseada");
			System.out.println("1. Movies");
			System.out.println("2. Series");
			System.out.println("3. Books");
			System.out.println("4. Magazines");
			System.out.println("5. Report");
			System.out.println("6. Report Fecha:");
			System.out.println("0. Exit");

            Scanner sc = new Scanner(System.in);
			int response = sc.nextInt();
			switch (response) {
				case 0:
					//salir
					
					break;
				case 1:
					showMovies();
					break;
				case 2:
					showSeries();
					break;
				case 3:
					showBooks();
					break;
				case 4:
					showMagazines();
					break;
				case 5:
					makeReport();
					break;
				case 6:
                    System.out.println("Digite La fecha del reporte Ej: 'yyyy-MM-dd");
                    String date = sc.next();
					makeReport(date);
					break;
	
				default:
					System.out.println();
					System.out.println("....¡¡Selecciona una opción!!....");
					System.out.println();
					break;
			}
			
			
		}while(exit != 0);
	}

	static ArrayList<Movie> movies = new ArrayList<>();

    public static void showMovies() {
        movies = Movie.makeMovieList();
        int exit = 1;

        do {
            System.out.println();
            System.out.println(":: MOVIES ::");
            System.out.println();

            AtomicInteger atomicInteger = new AtomicInteger(1);
            movies.forEach(
                    movie -> System.out.println(atomicInteger.getAndIncrement() + ". " + movie.getTitle() +  " Visto: " + movie.isViewed())
            );

            System.out.println("0. Regresar al Menu");
            System.out.println();

            //Leer Respuesta usuario
            Scanner sc = new Scanner(System.in);
            int response = sc.nextInt();

            if(response == 0) {
                exit = 0;
                showMenu();
            }
            if (response > 0) {
                Movie movieSelected = movies.get(response-1);
                movieSelected.view();
            }


        }while(exit !=0);

    }


    static ArrayList<Serie> series = Serie.makeSeriesList();
    public static void showSeries() {
        int exit = 1;
        do {
            System.out.println();
            System.out.println(":: SERIES ::");
            System.out.println();

            for (int i = 0; i < series.size(); i++) { //1. Serie 1
                System.out.println(i+1 + ". " + series.get(i).getTitle() + " Visto: " + series.get(i).isViewed());
            }

            System.out.println("0. Regresar al Menu");
            System.out.println();

            //Leer Respuesta usuario
            Scanner sc = new Scanner(System.in);
            int response = sc.nextInt();

            if(response == 0) {
                showMenu();
            }

            showChapters(series.get(response-1).getChapters());

        }while(exit !=0);
    }

    public static void showChapters(ArrayList<Chapter> chaptersOfSerieSelected) {
        int exit = 0;

        do {
            System.out.println();
            System.out.println(":: CHAPTERS ::");
            System.out.println();


            for (int i = 0; i < chaptersOfSerieSelected.size(); i++) { //1. Chapter 1
                System.out.println(i+1 + ". " + chaptersOfSerieSelected.get(i).getTitle() + " Visto: " + chaptersOfSerieSelected.get(i).isViewed());
            }

            System.out.println("0. Regresar al Menu");
            System.out.println();

            //Leer Respuesta usuario
            Scanner sc = new Scanner(System.in);
            int response = Integer.valueOf(sc.nextLine());

            if(response == 0) {
                exit = 0;
            }

            if(response > 0) {
                Chapter chapterSelected = chaptersOfSerieSelected.get(response-1);
                chapterSelected.view();
            }

        }while(exit !=0);
    }

    static ArrayList<Book> books = Book.makeBookList();
    public static void showBooks() {
        int exit = 1;

        do {
            System.out.println();
            System.out.println(":: BOOKS ::");
            System.out.println();

            for (int i = 0; i < books.size(); i++) { //1. Book 1
                System.out.println(i+1 + ". " + books.get(i).getTitle() + " Leído: " + books.get(i).isReaded());
            }

            System.out.println("0. Regresar al Menu");
            System.out.println();

            //Leer Respuesta usuario
            int response = AmazonUtil.validateUserResponseMenu(0, books.size());

            if(response == 0) {
                exit = 0;
                showMenu();
            }

            if(response > 0) {
                Book bookSelected = books.get(response-1);
                bookSelected.view();
            }

        }while(exit !=0);
    }
	
	public static void showMagazines() {
		int exit = 0;
		do {
			System.out.println();
			System.out.println(":: MAGAZINES ::");
			System.out.println();
		}while(exit !=0);
	}
	
	public static void makeReport() {
		Report report = new Report();
		report.setNameFile("reporte");
		report.setTitle(":: VISTOS ::");
		report.setExtension("txt");
		StringBuilder contentReport = new StringBuilder();

		movies.stream()
                .filter(movie -> movie.getIsViewed())
                .forEach(movie -> contentReport.append(movie.toString() + "\n"));

        //Predicate<Serie> predicate = serie -> serie.getIsViewed();
        Consumer<Serie> serieEach = serie -> {
            ArrayList<Chapter> chapters = serie.getChapters();
            chapters.stream()
                    .filter(chapter -> chapter.getIsViewed())
                    .forEach(chapter -> contentReport.append(chapter.toString() + "\n"));
        };
        series.stream().forEach(serieEach);

        books.stream().filter(book -> book.isReaded()).forEach(book -> contentReport.append(book.toString() + "\n"));

        report.setContent(contentReport.toString());
        try {
            report.makeReport();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
	
	public static void makeReport(String date) {

        Movie movie = new Movie();
        Report report = new Report();
        report.setNameFile("reporte");
        report.setTitle(":: VISTOS ::");
        report.setExtension("txt");
        String contentReport = movie.getReportContent(date);
        report.setContent(contentReport);
        try {
            report.makeReport();
        } catch (IOException e) {
            e.printStackTrace();
        }

	}
	
}







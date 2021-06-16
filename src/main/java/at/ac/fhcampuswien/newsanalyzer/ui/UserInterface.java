package at.ac.fhcampuswien.newsanalyzer.ui;


import at.ac.fhcampuswien.newsanalyzer.ctrl.Controller;
import at.ac.fhcampuswien.newsapi.beans.Article;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;
import at.ac.fhcampuswien.newsapi.enums.Category;
import at.ac.fhcampuswien.newsapi.enums.Country;
import at.ac.fhcampuswien.newsapi.enums.SortBy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Scanner;

public class UserInterface 
{
	Scanner scanner = new Scanner(System.in);
	private Controller ctrl = new Controller();

	public void getDataFromCtrl1(){
		System.out.println("ABC");
		ctrl.setKeyword("corona");
		ctrl.setCountry(Country.at);
		ctrl.setCategory(Category.health);
		ctrl.setSortBy(SortBy.RELEVANCY);
		ctrl.process();
		NewsResponse newsResponse = ctrl.getData();

		if(newsResponse != null){
			List<Article> articles = newsResponse.getArticles();
			articles.stream().forEach(article -> System.out.println(article.toString()));
		}
	}

	public void getDataFromCtrl2(){
		// TODO implement me
		ctrl.setKeyword("Sport");
		ctrl.setCountry(Country.at);
		ctrl.setCategory(Category.sports);
		ctrl.process();

		NewsResponse newsResponse = ctrl.getData();

		if(newsResponse != null){
			List<Article> articles = newsResponse.getArticles();
			articles.stream().forEach(article -> System.out.println(article.toString()));
		}

		ctrl.analysis(newsResponse);
	}

	public void getDataFromCtrl3(){
		// TODO implement me
	}
	
	public void getDataForCustomInput() {
		// TODO implement me
		boolean categorySet = false;
		boolean analysisSet = false;

		while(!categorySet){
			System.out.println("Wählen Sie eine der folgenden Kategorien: ");
			System.out.print(
					"b: Business\n"+
							"u: Unterhaltung\n"+
							"g: Gesundheit\n"+
							"w: Wissenschaft\n"+
							"t: Technologie\n"+
							"> ");

			String s = scanner.next();
			switch(s){
				case "b": ctrl.setCategory(Category.business); categorySet=true; break;
				case "u": ctrl.setCategory(Category.entertainment); categorySet=true; break;
				case "g": ctrl.setCategory(Category.health); categorySet=true; break;
				case "w": ctrl.setCategory(Category.science); categorySet=true; break;
				case "t": ctrl.setCategory(Category.technology); categorySet=true; break;
				default:
					System.out.println("Ungültige Eingabe"); break;
			}
			System.out.println();
		}

		System.out.print("Geben Sie ein Schlüsselwort ein: ");
		String s2 = scanner.next();
		ctrl.setKeyword(s2);
		System.out.println();

		ctrl.setCountry(Country.at);
		ctrl.setKeyword("");


		System.out.println("Hier Ihre Nachrichten in Österreich:");

		ctrl.process();

		NewsResponse newsResponse= ctrl.getData();

		while(!analysisSet){
			System.out.print("Möchten Sie unseren Algorithmus verwenden um Ihre Nachrichten zu analysieren? (j/n): ");
			String s3 = scanner.next();
			switch(s3){
				case "j": ctrl.analysis(newsResponse); analysisSet=true; break;
				case "n": analysisSet=true; break;
				default: System.out.println("Ungültige Eingabe"); break;
			}
			System.out.println();
		}

		if(newsResponse != null){
			List<Article> articles = newsResponse.getArticles();
			articles.stream().forEach(article -> System.out.println(article.toString()));
		}
	}


	public void start() {
		try {
			Menu<Runnable> menu = new Menu<>("User Interface");
			menu.setTitle("Wählen Sie aus:");
			menu.insert("a", "Top Nachrichten zu COVID-19 in Österreich", this::getDataFromCtrl1);
			menu.insert("b", "Alle News zu Sport in Österreich", this::getDataFromCtrl2);
			menu.insert("c", "Choice 3", this::getDataFromCtrl3);
			menu.insert("d", "Choice User Input:", this::getDataForCustomInput);
			menu.insert("q", "Quit", null);
			Runnable choice;
			while ((choice = menu.exec()) != null) {
				choice.run();
			}
			System.out.println("Program finished");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}


    protected String readLine() {
		String value = "\0";
		BufferedReader inReader = new BufferedReader(new InputStreamReader(System.in));
		try {
			value = inReader.readLine();
        } catch (IOException ignored) {
		}
		return value.trim();
	}

	protected Double readDouble(int lowerlimit, int upperlimit) 	{
		Double number = null;
        while (number == null) {
			String str = this.readLine();
			try {
				number = Double.parseDouble(str);
            } catch (NumberFormatException e) {
                number = null;
				System.out.println("Please enter a valid number:");
				continue;
			}
            if (number < lowerlimit) {
				System.out.println("Please enter a higher number:");
                number = null;
            } else if (number > upperlimit) {
				System.out.println("Please enter a lower number:");
                number = null;
			}
		}
		return number;
	}
}

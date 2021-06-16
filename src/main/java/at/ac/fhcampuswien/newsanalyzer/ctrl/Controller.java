package at.ac.fhcampuswien.newsanalyzer.ctrl;

import at.ac.fhcampuswien.newsapi.NewsApi;
import at.ac.fhcampuswien.newsapi.NewsApiBuilder;
import at.ac.fhcampuswien.newsapi.beans.Article;
import at.ac.fhcampuswien.newsapi.beans.NewsResponse;
import at.ac.fhcampuswien.newsapi.enums.Category;
import at.ac.fhcampuswien.newsapi.enums.Country;
import at.ac.fhcampuswien.newsapi.enums.Endpoint;
import at.ac.fhcampuswien.newsapi.enums.SortBy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import java.util.stream.Collectors;

public class Controller {
	// My APIKEY: c944288501dc48888b5a974255d0b318;
	public static final String APIKEY = "c944288501dc48888b5a974255d0b318";  //TODO add your api key
	private Country country;
	private String keyword;
	private Category category;
	private SortBy sortBy;

	private NewsResponse newsResponse;

	private Date date = Calendar.getInstance().getTime();
	private DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
	private String strDate = dateFormat.format(date);


	public void process() {
		System.out.println("Start process");

		//TODO implement Error handling

		//TODO load the news based on the parameters

		//TODO implement methods for analysis
		NewsApi newsApi = new NewsApiBuilder()
				.setApiKey(APIKEY)
				.setQ(this.keyword) //				.setEndPoint(Endpoint.EVERYTHING)
				.setEndPoint(Endpoint.TOP_HEADLINES)
				.setFrom("2021-05-20")
				.setSourceCountry(this.country)
				.setSourceCategory(this.category)
				.setSortBy(this.sortBy)
				.createNewsApi();
		newsResponse = newsApi.getNews();
		/*if(newsResponse != null){
			List<Article> articles = newsResponse.getArticles();
			articles.stream().forEach(article -> System.out.println(article.toString()));
		}
		newsApi = new NewsApiBuilder()
				.setApiKey(APIKEY)
				.setQ("corona")
				.setEndPoint(Endpoint.EVERYTHING)
				.setFrom(strDate)
				.setExcludeDomains("Lifehacker.com")
				.createNewsApi();
		newsResponse = newsApi.getNews();*/
	}

	public void setSortBy(SortBy sortBy) {
		this.sortBy = sortBy;
	}

	public void setKeyword(String keyword){
		this.keyword = keyword;
	}

	public void setCountry(Country country){
		this.country = country;
	}
	public void setCategory(Category category){
		this.category= category;
	}
	public void setDate(Date date){

	}

	String authorWithShortestName;
	int numberOfArticles;
	List<String> titles = new ArrayList<>();
	Map<String, Integer> providers = new HashMap<>();

	public void analysis(NewsResponse newsResponse){

		if(newsResponse != null){

			System.out.println();
			System.out.println("Hier die Ergebnisse unserer hochentwickelten Analysesoftware: ");
			System.out.println();

			List<Article> articles = newsResponse.getArticles();
			//List<Article> authors = articles.stream().filter(article -> article.getAuthor().length() < 10).collect(Collectors.toList());

			//Number of Articles
			numberOfArticles = (int) articles.stream().count();
			System.out.println("Anzahl der Artikel: "+numberOfArticles);

			//Which provider delivers the most articles
			articles.stream().forEach(article -> {
				if(!providers.containsKey(article.getSource().getName())){
					providers.put(article.getSource().getName(), 1);
				} else {
					providers.put(article.getSource().getName(), providers.get(article.getSource().getName()) + 1);
				}
			});

			String providerWithMostArticles = providers.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).get().getKey();
			System.out.println("Die Quelle mit den meisten Artikeln ist: "+providerWithMostArticles);

			//Author with Shortest Name
			authorWithShortestName = articles.get(0).getAuthor();
			articles.stream().forEach(article -> {
				if(article != null && !(article.getAuthor() == null) && article.getAuthor().length() < authorWithShortestName.length())
					authorWithShortestName = article.getAuthor();
			});
			System.out.println("Der Autor mit dem kürzesten Namen ist: "+authorWithShortestName);

			//sort for length of articles
			articles.stream().forEach(article -> {
				if(!titles.contains(article.getTitle()))
					titles.add(article.getTitle());
			});

			titles.sort((t1, t2) -> t2.length() - t1.length());
			System.out.println("Hier die Titel absteigend nach Länge sortiert:");
			titles.stream().forEach(System.out::println);
		}
	}

	public NewsResponse getData() {
		return newsResponse;
	}
}

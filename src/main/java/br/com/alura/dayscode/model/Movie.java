package br.com.alura.dayscode.model;

public class Movie {
	private String title;
	private String image;
	private Float imDbRating;
	private Integer year;

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public Float getImDbRating() {
		return imDbRating;
	}

	public void setImDbRating(Float imDbRating) {
		this.imDbRating = imDbRating;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}
}

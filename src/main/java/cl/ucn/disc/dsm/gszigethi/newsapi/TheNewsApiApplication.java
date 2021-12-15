package cl.ucn.disc.dsm.gszigethi.newsapi;

import cl.ucn.disc.dsm.gszigethi.newsapi.model.News;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

/**
 * The News API Application
 * @author Gustavo Patricio Szigethi Araya
 */
@SpringBootApplication
public class TheNewsApiApplication {
	/**
	 * The {@link NewsRepository} used to initialize the database.
	 */
	@Autowired
	private NewsRepository newsRepository;
	/**
	 * The main starting point.
	 * @param args to use
	 */
	public static void main(String[] args) {
		SpringApplication.run(TheNewsApiApplication.class, args);
	}

	/**
	 * Initialize the data inside the Database.
	 * @return the data to use
	 */
	@Bean
	protected InitializingBean initializingDatabase(){
		return () -> {
			final News news = new News(
					"Astronomía, Ciencias del Mar e intercambio estudiantil potenciará alianza entre UCN y embajada de EE. UU.",
					"Noticias UCN",
					"UCN",
					"https://www.noticias.ucn.cl/destacado/astronomia-ciencias-del-mar-e-intercambio-estudiantil-potenciara-alianza-entre-ucn-y-embajada-de-ee-uu/",
					"https://www.noticias.ucn.cl/wp-content/uploads/2021/12/nt-BIMG_5178.jpg",
					"Rector del plantel y representante de la misión diplomática norteamericana en Chile sostuvieron un encuentro en la Casa Central.",
					"Potenciar el área de astronomía tanto de la casa de estudios como también del norte en general, apoyar con implementos a la investigación oceánica que realiza la Facultad de Ciencias del Mar, y reactivar el intercambio estudiantil –suspendido por la pandemia- fueron algunos de los puntos abordados en una reunión sostenida entre la Universidad Católica del Norte (UCN) con la embajada de los Estados Unidos en nuestro país.",
					ZonedDateTime.now(ZoneId.of("-4"))
			);
			// Save the news
			this.newsRepository.save(news);
		};
	}
}

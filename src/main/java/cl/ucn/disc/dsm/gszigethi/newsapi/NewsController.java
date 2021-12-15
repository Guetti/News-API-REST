/*
 * MIT License
 *
 * Copyright (c) 2021 Gustavo Patricio Szigethi Araya
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package cl.ucn.disc.dsm.gszigethi.newsapi;

import cl.ucn.disc.dsm.gszigethi.newsapi.model.News;
import com.kwabenaberko.newsapilib.models.Article;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;
import com.kwabenaberko.newsapilib.network.APIClient;
import com.kwabenaberko.newsapilib.network.APIService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * The controller of News.
 * @author Gustavo Patricio Szigethi Araya.
 */
@Slf4j
@RestController
public class NewsController {

    /**
     * The Repo of News.
     */
    private final NewsRepository newsRepository;

    /**
     * The constructor of NewsController.
     * @param newsRepository to use.
     */
    public NewsController(NewsRepository newsRepository){
        this.newsRepository = newsRepository;
    }

    /**
     * @return all the News in the backend.
     */
    @GetMapping("/v1/news")
    public List<News> all(@RequestParam(required = false, defaultValue = "false") Boolean reload) {

        // If reload -> get news from NewsAPI.org
        if (reload){
            // FIXME: Avoid the duplicated!!
            this.reloadNewsFromNewsApi();
        }

        // Equals to SELECT * FROM News;
        final List<News> theNews = this.newsRepository.findAll();
        // TODO: Show the news in console
        return theNews;
    }

    /**
     * Get the News from NewsAPI and save into the database.
     */
    private void reloadNewsFromNewsApi() {
        // WARNING: Just for test
        final String API_KEY = "a80f0e7d2efb4580b1b049815f8456ac";
        final int pageSize = 100;

        // 1. Create the APIService from APIClient
        final APIService apiService = APIClient.getAPIService();

        // 2. Build the request params
        final Map<String, String> reqParams = new HashMap<>();
        // The ApiKey
        reqParams.put("apiKey", API_KEY);
        // Filter by category
        reqParams.put("category", "technology");
        // The number of results to turn per page (request). 20 is the default, 100 is the maximum
        reqParams.put("pageSize", String.valueOf(pageSize));

        // 3. Call the request
        try {
            Response<ArticleResponse> articlesResponse =
                    apiService.getTopHeadlines(reqParams).execute();

            if (articlesResponse.isSuccessful()){
                // TODO: Check for getArticles != null
                List<Article> articles = articlesResponse.body().getArticles();

                List<News> news = new ArrayList<>();
                // List<Article> to List<News>
                for (Article article: articles){
                    news.add(toNews(article));
                }

                // 4. Save into the local database.
                this.newsRepository.saveAll(news);
            }
        } catch (IOException e) {
            e.printStackTrace();
            log.error("Getting the articles from NewsAPI", e);
        }
    }

    /**
     * Convert an Article to News.
     * @param article to convert
     * @return the News.
     */
    private static News toNews(final Article article){

        // Protection: author
        if (article.getAuthor() == null || article.getAuthor().length() < 3){
            article.setAuthor("No Author*");
        }

        // Protection: title
        if (article.getTitle() == null || article.getTitle().length() < 3){
            article.setTitle("No Title*");
        }

        // Protection: description
        if (article.getDescription() == null || article.getDescription().length() < 4){
            article.setDescription("No description*");
        }

        // Parse the date and fix the zone
        ZonedDateTime publishedAt = ZonedDateTime
                .parse(article.getPublishedAt())
                // Correct from UTC to LocalTime (Chile)
                .withZoneSameInstant(ZoneId.of("-3"));
        return new News(
                article.getTitle(),
                article.getSource().getName(),
                article.getAuthor(),
                article.getUrl(),
                article.getUrlToImage(),
                article.getDescription(),
                article.getDescription(),
                publishedAt);
    }

    /**
     *
     * @param id of News to retrieve.
     * @return the news.
     */
    @GetMapping("/v1/news/{id}")
    public News one(@PathVariable final Long id){
        // FIXME: Change the RuntimeException to 404.
        return this.newsRepository.findById(id).orElseThrow(() -> new RuntimeException("News not Found :("));

    }
}

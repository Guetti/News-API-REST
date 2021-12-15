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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.threeten.bp.ZoneId;
import org.threeten.bp.ZonedDateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * The controller of News
 * @author Gustavo Patricio Szigethi Araya
 */
@RestController
public class NewsController {
    /**
     * @return all the News in the backend.
     */
    @GetMapping("/v1/news")
    public List<News> getNews() {
        return new ArrayList<>();
    }

    /**
     *
     * @param id of News to retrieve
     * @return the news
     */
    @GetMapping("/v1/news/{id}")
    public News one(@PathVariable final Long id){
        // FIXME: Only for testing.
        News news = new News(
                "Astronomía, Ciencias del Mar e intercambio estudiantil potenciará alianza entre UCN y embajada de EE. UU.",
                "Noticias UCN",
                "UCN",
                "https://www.noticias.ucn.cl/destacado/astronomia-ciencias-del-mar-e-intercambio-estudiantil-potenciara-alianza-entre-ucn-y-embajada-de-ee-uu/",
                "https://www.noticias.ucn.cl/wp-content/uploads/2021/12/nt-BIMG_5178.jpg",
                "Rector del plantel y representante de la misión diplomática norteamericana en Chile sostuvieron un encuentro en la Casa Central.",
                "Potenciar el área de astronomía tanto de la casa de estudios como también del norte en general, apoyar con implementos a la investigación oceánica que realiza la Facultad de Ciencias del Mar, y reactivar el intercambio estudiantil –suspendido por la pandemia- fueron algunos de los puntos abordados en una reunión sostenida entre la Universidad Católica del Norte (UCN) con la embajada de los Estados Unidos en nuestro país.",
                ZonedDateTime.now(ZoneId.of("-4"))
        );
        return news;
    }
}

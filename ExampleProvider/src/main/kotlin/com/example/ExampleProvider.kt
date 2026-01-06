package com.hcgn2005

import com.lagradost.cloudstream3.*
import com.lagradost.cloudstream3.utils.*
import org.jsoup.Jsoup

class AnikaiProvider : MainAPI() { 
    override var mainUrl = "https://anikai.to"
    override var name = "Anikai"
    override val supportedTypes = setOf(TvType.Anime, TvType.AnimeMovie)

    override suspend fun search(query: String): List<SearchResponse> {
        val link = "$mainUrl/search?keyword=$query"
        val html = app.get(link).text
        val document = Jsoup.parse(html)

        return document.select("div.ani-item").mapNotNull {
            val title = it.selectFirst(".title")?.text() ?: return@mapNotNull null
            val href = it.selectFirst("a")?.attr("href") ?: return@mapNotNull null
            val poster = it.selectFirst("img")?.attr("src")
            
            newAnimeSearchResponse(title, href, TvType.Anime) {
                this.posterUrl = poster
            }
        }
    }

    override suspend fun load(url: String): LoadResponse? {
        val html = app.get(url).text
        val document = Jsoup.parse(html)
        val title = document.selectFirst("h1.name")?.text() ?: return null
        val poster = document.selectFirst(".poster img")?.attr("src")
        val description = document.selectFirst(".description")?.text()

        return newAnimeLoadResponse(title, url, TvType.Anime) {
            this.posterUrl = poster
            this.plot = description
            // Add episodes logic here using document.select(".episodes a")
        }
    }

    override suspend fun loadLinks(
        data: String,
        isDataJob: Boolean,
        callback: (ExtractorLink) -> Unit
    ): Boolean {
        // Logic to extract video sources (e.g., GogoServer, Mp4Upload)
        // You can use loadExtractor(link, subtitleCallback, callback)
        return true
    }
}

package com.dk.englishcards.commons

class Url(url: String) {
    val url: String = url
    var protocol: String? = null
    var host: String? = null
    var port: String? = null
    var other: String? = null

    init {
        val regex = Regex("""^(.+?)://(.+?):?(\d+)?(/.*)?${'$'}""")
        val match = regex.find(url)
        this.protocol = match?.groupValues?.get(1)
        this.host = match?.groupValues?.get(2)
        this.port = match?.groupValues?.get(3)
        this.other = match?.groupValues?.get(4)
    }

    fun isUrl(): Boolean {
        return this.protocol == "https" || this.protocol == "http"
    }

    fun isYouTube(): Boolean {
        return this.isUrl() &&
                this.host?.endsWith("youtube.com", true)!!
    }

    fun getVideoId(): String? {
        if (!this.isYouTube() || this.other == null) {
            return null
        }
        val regex = Regex("""/watch\?v=([\w-]{11})""")
        val match = regex.find(this.other!!)
        return match?.groupValues?.get(1)
    }
}

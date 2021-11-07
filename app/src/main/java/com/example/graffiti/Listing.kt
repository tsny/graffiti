package com.example.graffiti

data class Listing(
    val `data`: Data,
    val kind: String
)

// Top level data object
data class Data(
    val after: String,
    val before: Any,
    val children: List<Children>,
    val dist: Int,
    val geo_filter: Any,
    val modhash: String
)

data class Children(
    val `data`: DataX,
    val kind: String
)

// Listing data
data class DataX(
    val allow_live_comments: Boolean,
    val approved_at_utc: Any,
    val approved_by: Any,
    val archived: Boolean,
    val author: String,
    val author_fullname: String,
    val author_premium: Boolean,
    val banned_at_utc: Any,
    val banned_by: Any,
    val can_gild: Boolean,
    val body: String,
    val can_mod_post: Boolean,
    val category: Any,
    val clicked: Boolean,
    val content_categories: Any,
    val contest_mode: Boolean,
    val created: Double,
    val created_utc: Double,
    val discussion_type: Any,
    val distinguished: String,
    val domain: String,
    val downs: Int,
    val edited: Any,
    val gilded: Int,
    val gildings: Gildings,
    val hidden: Boolean,
    val hide_score: Boolean,
    val id: String,
    val is_created_from_ads_ui: Boolean,
    val is_crosspostable: Boolean,
    val likes: Any,
    val name: String,
    val no_follow: Boolean,
    val num_comments: Int,
    val num_crossposts: Int,
    val num_reports: Any,
    val over_18: Boolean,
    val parent_whitelist_status: String,
    val permalink: String,
    val pinned: Boolean,
    val pwls: Int,
    val quarantine: Boolean,
    val saved: Boolean,
    val score: Int,
    val secure_media: Any,
    val secure_media_embed: SecureMediaEmbed,
    val send_replies: Boolean,
    val spoiler: Boolean,
    val stickied: Boolean,
    val subreddit: String,
    val subreddit_id: String,
    val suggested_sort: Any,
    val thumbnail: String,
    val title: String,
    val top_awarded_type: Any,
    val total_awards_received: Int,
    val treatment_tags: List<Any>,
//    val replies: Listing,
    val ups: Int,
    val upvote_ratio: Double,
    val url: String,
    val user_reports: List<Any>,
    val view_count: Any,
    val visited: Boolean,
    val whitelist_status: String,
    val wls: Int,
) {
    // If its got a title, its a listing
    // otherwise its a comment
    override fun toString(): String {
        if (title == null) {
            return "$body - $author - $score"
        }
        return "$title - $author - $score"
    }

    fun toTTSString(): String {
        return body
    }
}

data class AuthorFlairRichtext(
    val a: String,
    val e: String,
    val t: String,
    val u: String
)

class Gildings(
)

data class LinkFlairRichtext(
    val e: String,
    val t: String
)

class SecureMediaEmbed(
)

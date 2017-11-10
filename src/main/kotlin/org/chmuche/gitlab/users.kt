package org.chmuche.gitlab

import com.beust.klaxon.JsonObject
import com.beust.klaxon.array
import org.chmuche.gitlab.json.GitLabRessource
import org.chmuche.gitlab.json.SubGitLabRessource
import org.chmuche.json.JsonList
import org.chmuche.json.SubJsonResource
import java.time.LocalDateTime
import kotlin.reflect.KProperty

enum class UserState {
    ACTIVE, INACTIVE
}

open class User internal constructor(json: JsonObject) : GitLabRessource(json) {
    companion object : GitLbaRessourceCompanion<User>("/users") {
        override fun new(obj: JsonObject) = User(obj)

        fun search(
                username: String? = null,
                extern_uid: String? = null,
                created_before: LocalDateTime? = null,
                created_after: LocalDateTime? = null,
                customAttribute: List<Pair<String, Any>> = emptyList()): Collection<User> {
            val l = mutableListOf<Pair<String, Any>>()
            if (username != null) {
                l.add("username" to username)
            }
            if (extern_uid != null) {
                l.add("extern_uid" to extern_uid)
            }
            if (created_before != null) {
                l.add("created_before" to created_before)
            }
            if (created_after != null) {
                l.add("created_after" to created_after)
            }
            customAttribute.mapTo(l) { "custom_attributes[${it.first}]" to it.second }
            return getList(l)
        }

        fun keys() = SshKeys.getList()
    }

    class Identities private constructor(json: JsonObject) : GitLabRessource(json) {
        companion object : GitLbaRessourceCompanion<Identities>("/users") {
            override fun new(obj: JsonObject) = Identities(obj)
        }

        var provider by jsonStr("provider")
        var externUid by jsonStr("externUid")
    }

    class SshKeys private constructor(json: JsonObject) : SubGitLabRessource(json) {

        companion object : SubGitLbaRessourceCompanion<SshKeys, User.Companion>(User, "/keys") {
            override fun new(obj: JsonObject) = SshKeys(obj)
        }

        var id by jsonStr("id")
        var title by jsonStr("title")
        var key by jsonStr("key")
        var created_at by jsonDateTime("created_at")
    }
    fun keys():Collection<SshKeys>{
        return SshKeys.getList()

    }

    var id by jsonInt("id")
    var username by jsonStr("username")
    var name by jsonStr("name")
    var state by jsonEnum("state", UserState::class)
    var avatar_url by jsonStr("avatar_url")
    var web_url by jsonStr("web_url")
    var created_at by jsonDateTime("created_at")
    var createdAt by jsonDateTime("created_at")
    var isAdmin by jsonBoolean("is_admin")
    var bio by jsonStr("bio")
    var location by jsonStr("location")
    var skype by jsonStr("skype")
    var linkedin by jsonStr("linkedin")
    var twitter by jsonStr("twitter")
    var websiteUrl by jsonStr("website_url")
    var organization by jsonStr("organization")
    var lastSignInAt by jsonStr("last_sign_in_at")
    var confirmedAt by jsonStr("confirmed_at")
    var themeId by jsonStr("theme_id")
    var lastActivityOn by jsonStr("last_activity_on")
    var colorSchemeId by jsonStr("color_scheme_id")
    var projectsLimit by jsonStr("projects_limit")
    var currentSignInAt by jsonStr("current_sign_in_at")
    var identities by jsonList("identities", Identities::class)
    var canCreateGroup by jsonStr("can_create_group")
    var canCreateProject by jsonStr("can_create_project")
    var twoFactorEnabled by jsonStr("two_factor_enabled")
    var external by jsonStr("external")
}
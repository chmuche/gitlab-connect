package org.chmuche.gitlab

import com.beust.klaxon.JsonObject
import org.chmuche.gitlab.json.GitLabRessource

enum class ProjectVisibility {
    PUBLIC, INTERNAL, PRIVATE
}


class GitlabProject private constructor(json: JsonObject) : GitLabRessource(json) {
    companion object : GitLbaRessourceCompanion<GitlabProject>("/projects") {
        override fun new(obj: JsonObject) = GitlabProject(obj)


    }




    class Owner private constructor(json: JsonObject) : GitLabRessource(json) {
        companion object : GitLbaRessourceCompanion<Owner>("/projects") {
            override fun new(obj: JsonObject) = Owner(obj)
        }
        var id by jsonInt("id")
        var description by jsonStr("name")
        var created_at by jsonDateTime("created_at")
    }

    class NameSpace private constructor(json: JsonObject) : GitLabRessource(json) {
        companion object : GitLbaRessourceCompanion<NameSpace>("") {
            override fun new(obj: JsonObject) = NameSpace(obj)
        }
    }

    class Statistics private constructor(json: JsonObject) : GitLabRessource(json) {
        companion object : GitLbaRessourceCompanion<Statistics>("") {
            override fun new(obj: JsonObject) = Statistics(obj)
        }
    }

    class Links private constructor(json: JsonObject) : GitLabRessource(json) {
        companion object : GitLbaRessourceCompanion<Links>("") {
            override fun new(obj: JsonObject) = Links(obj)
        }
    }

    var id by jsonInt("id")
    var description by jsonStr("description")
    var defaultBranch by jsonStr("default_branch")
    var visibility by jsonEnum("visibility", ProjectVisibility::class)
    var sshUrlToRepo by jsonStr("ssh_url_to_repo")
    var httpUrlToRepo by jsonStr("http_url_to_repo")
    var webUrl by jsonStr("web_url")
    var tagList by jsonList("tag_list", String::class)
    var owner by jsonObj("owner", Owner.Companion)
    var nameWithNamespace by jsonStr("name_with_namespace")
    var path by jsonStr("path")
    var pathWithNamespace by jsonStr("path_with_namespace")
    var issuesEnabled by jsonBoolean("issues_enabled")
    var openIssuesCount by jsonInt("open_issues_count")
    var mergeRequestsEnabled by jsonBoolean("merge_requests_enabled")
    var jobsEnabled by jsonBoolean("jobs_enabled")
    var wikiEnabled by jsonBoolean("wiki_enabled")
    var snippetsEnabled by jsonBoolean("snippets_enabled")
    var resolveOutdatedDiffDiscussions by jsonBoolean("resolve_outdated_diff_discussions")
    var containerRegistryRnabled by jsonBoolean("container_registry_enabled")
    var createdAt by jsonDateTime("created_at")
    var lastActivityAt by jsonDateTime("last_activity_at")
    var creatorId by jsonInt("creator_id")
    var namespace by jsonObj("namespace", NameSpace.Companion)
    var importStatus by jsonStr("import_status")
    var archived by jsonBoolean("archived")
    var avatarUrl by jsonStr("avatar_url")
    var sharedRunnersEnabled by jsonBoolean("shared_runners_enabled")
    var forksCount by jsonInt("forks_count")
    var starCount by jsonInt("star_count")
    var runnersToken by jsonStr("runners_token")
    var publicJobs by jsonBoolean("public_jobs")
    var sharedWithGroups by jsonList("shared_with_groups", String::class)
    var onlyAllowMergeIfPipelineSucceeds by jsonBoolean("only_allow_merge_if_pipeline_succeeds")
    var onlyAllowMergeIfAllDiscussionsAreResolved by jsonBoolean("only_allow_merge_if_all_discussions_are_resolved")
    var requestAccessEnabled by jsonBoolean("request_access_enabled")
    var approvalsBeforeMerge by jsonInt("approvals_before_merge")
    var statistics by jsonObj("statistics", Statistics.Companion)
    var _links by jsonObj("_links", Links.Companion)
}
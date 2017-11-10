package org.chmuche.gitlab

import com.github.kittinunf.fuel.core.FuelManager
import org.chmuche.json.JsonResourceCompanion
import org.chmuche.json.JsonRessource
import org.chmuche.json.SubJsonResourceCompanion
import org.chmuche.json.SubJsonResource

const val JSON = "{\"id\":90,\"description\":\"\",\"default_branch\":\"1.9\",\"tag_list\":[],\"ssh_url_to_repo\":\"git@gitlab.ndp-systemes.fr:apasquier/deve-france.git\",\"http_url_to_repo\":\"https://gitlab.ndp-systemes.fr/apasquier/deve-france.git\",\"web_url\":\"https://gitlab.ndp-systemes.fr/apasquier/deve-france\",\"name\":\"deve-france\",\"name_with_namespace\":\"Alexis PASQUIER / deve-france\",\"path\":\"deve-france\",\"path_with_namespace\":\"apasquier/deve-france\",\"star_count\":0,\"forks_count\":0,\"created_at\":\"2017-10-16T14:56:38.729Z\",\"last_activity_at\":\"2017-10-17T14:52:18.488Z\",\"_links\":{\"self\":\"http://gitlab.ndp-systemes.fr/api/v4/projects/90\",\"issues\":\"http://gitlab.ndp-systemes.fr/api/v4/projects/90/issues\",\"merge_requests\":\"http://gitlab.ndp-systemes.fr/api/v4/projects/90/merge_requests\",\"repo_branches\":\"http://gitlab.ndp-systemes.fr/api/v4/projects/90/repository/branches\",\"labels\":\"http://gitlab.ndp-systemes.fr/api/v4/projects/90/labels\",\"events\":\"http://gitlab.ndp-systemes.fr/api/v4/projects/90/events\",\"members\":\"http://gitlab.ndp-systemes.fr/api/v4/projects/90/members\"},\"archived\":false,\"visibility\":\"internal\",\"owner\":{\"id\":47,\"name\":\"Alexis PASQUIER\",\"username\":\"apasquier\",\"state\":\"active\",\"avatar_url\":\"https://secure.gravatar.com/avatar/98ad97b563eb5df34fe922e26e205614?s=80&d=identicon\",\"web_url\":\"https://gitlab.ndp-systemes.fr/apasquier\"},\"resolve_outdated_diff_discussions\":null,\"container_registry_enabled\":true,\"issues_enabled\":true,\"merge_requests_enabled\":true,\"wiki_enabled\":true,\"jobs_enabled\":true,\"snippets_enabled\":false,\"shared_runners_enabled\":false,\"lfs_enabled\":true,\"creator_id\":47,\"namespace\":{\"id\":51,\"name\":\"apasquier\",\"path\":\"apasquier\",\"kind\":\"user\",\"full_path\":\"apasquier\",\"parent_id\":null},\"forked_from_project\":{\"id\":38,\"description\":\"\",\"default_branch\":\"9.0\",\"tag_list\":[],\"ssh_url_to_repo\":\"git@gitlab.ndp-systemes.fr:odoo-addons/deve-france.git\",\"http_url_to_repo\":\"https://gitlab.ndp-systemes.fr/odoo-addons/deve-france.git\",\"web_url\":\"https://gitlab.ndp-systemes.fr/odoo-addons/deve-france\",\"name\":\"deve-france\",\"name_with_namespace\":\"odoo-addons / deve-france\",\"path\":\"deve-france\",\"path_with_namespace\":\"odoo-addons/deve-france\",\"star_count\":2,\"forks_count\":1,\"created_at\":\"2016-05-30T12:54:48.218Z\",\"last_activity_at\":\"2017-10-31T15:14:20.906Z\"},\"import_status\":\"finished\",\"import_error\":null,\"avatar_url\":\"https://gitlab.ndp-systemes.fr/uploads/-/system/project/avatar/90/logo.png\",\"open_issues_count\":0,\"runners_token\":\"1NieoEGhTWZxvV3zK7qE\",\"public_jobs\":true,\"ci_config_path\":null,\"shared_with_groups\":[],\"only_allow_merge_if_pipeline_succeeds\":false,\"request_access_enabled\":false,\"only_allow_merge_if_all_discussions_are_resolved\":false,\"printing_merge_request_link_enabled\":true,\"permissions\":{\"project_access\":{\"access_level\":40,\"notification_level\":3},\"group_access\":null}}"

fun main(args: Array<String>) {
    FuelManager.instance.basePath = "https://gitlab.ndp-systemes.fr/api/v4/"
    FuelManager.instance.baseHeaders = mapOf("PRIVATE-TOKEN" to "_P4FTsPN4U7yk_-eD1-n")
    val proj = GitlabProject[90]
    println(proj.id)


}

abstract class GitLbaRessourceCompanion<out T : JsonRessource>(baseUrl: String) : JsonResourceCompanion<T>(baseUrl) {


}

abstract class SubGitLbaRessourceCompanion<out T : SubJsonResource, out P : JsonResourceCompanion<*>>
(parent: P, baseUrl: String)
    : SubJsonResourceCompanion<T, P>(parent, baseUrl) {


}





package org.chmuche.gitlab.json

import com.beust.klaxon.JsonObject
import org.chmuche.json.JsonRessource
import org.chmuche.json.SubJsonResource

open class GitLabRessource(obj: JsonObject) : JsonRessource(obj) {

}

open class SubGitLabRessource(obj: JsonObject) : SubJsonResource(obj) {

}
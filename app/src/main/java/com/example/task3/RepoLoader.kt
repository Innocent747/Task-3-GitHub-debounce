package com.example.task3

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONArray

suspend fun loadRepos(context: Context): List<Repo> =
    withContext(Dispatchers.IO) {

        val jsonString =
            context.assets.open("github_repos.json")
                .bufferedReader()
                .use { it.readText() }

        val jsonArray = JSONArray(jsonString)

        val list = mutableListOf<Repo>()

        for (i in 0 until jsonArray.length()) {

            val obj = jsonArray.getJSONObject(i)

            list.add(
                Repo(
                    id = obj.getLong("id"),
                    full_name = obj.getString("full_name"),
                    description = obj.getString("description"),
                    stargazers_count = obj.getInt("stargazers_count"),
                    language = obj.getString("language")
                )
            )
        }

        list
    }
package com.example.myerasmus.utils

data class PublicGroupProfile(
    val name: String,
    val description: String,
    val imageRes: Int,
    val members: List<String>
)

object DynamicGroupRepository {
    private val dynamicGroups = mutableMapOf<String, PublicGroupProfile>()

    fun addGroup(group: PublicGroupProfile) {
        dynamicGroups[group.name] = group
    }

    fun getGroup(name: String): PublicGroupProfile? {
        return dynamicGroups[name]
    }

    fun exists(name: String): Boolean {
        return dynamicGroups.containsKey(name)
    }

    fun clearAll() {
        dynamicGroups.clear()
    }

    fun getAll(): List<PublicGroupProfile> {
        return dynamicGroups.values.toList()
    }
}

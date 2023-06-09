package com.momen.bostatask.data.model

data class User(
    val address: Address,
    val email: String,
    val id: Int,
    val name: String,
    val phone: String,
    val username: String,
    val website: String
)

data class Address(
    val city: String,
    val street: String,
    val suite: String,
    val zipcode: String
)
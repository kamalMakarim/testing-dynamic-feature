package com.kamal.testingdynamicmodule.model

class User {
    var email: String = ""
    var password: String = ""
    var ID: String = ""
    var role: Role = Role.USER

    constructor(email: String, password: String, ID: String) {
        this.email = email
        this.password = password
        this.ID = ID
    }

    constructor(email: String, password: String, ID: String, role: Role) {
        this.email = email
        this.password = password
        this.ID = ID
        this.role = role
    }

    constructor(email: String = "", password: String = "") {
        this.email = email
        this.password = password
    }

    fun isUserAdmin(): Boolean {
        return this.role == Role.ADMIN
    }
}







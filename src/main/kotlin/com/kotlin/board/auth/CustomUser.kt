package com.kotlin.board.auth

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

class CustomUser(
    val userId: Long,
    userName: String,
    password: String,
    authroities: Collection<GrantedAuthority>,
) : User(userName, password, authroities)
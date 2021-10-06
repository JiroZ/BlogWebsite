package com.axisbank.server.service

import com.axisbank.server.authority.Authorities
import com.axisbank.server.authority.SecurityManagerExpression
import com.axisbank.server.contracts.UserService
import com.axisbank.server.dto.Messages.*
import com.axisbank.server.dto.blog.Blog
import com.axisbank.server.dto.user.BlogUser
import com.axisbank.server.exceptions.UserException
import com.axisbank.server.repository.UserRepository
import com.axisbank.server.utils.JwtUtil
import org.springframework.security.authentication.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service
import java.util.*

@Service
class DefaultUserDetailService(
    val userRepository: UserRepository,
    val authenticationManager: AuthenticationManager
) : UserDetailsService, UserService
{
    override fun loadUserByUsername(username: String): UserDetails {
        val user = getBlogUserByUsername(username)

        return User(user.userName, user.password, user.blogAuthorities)
    }

    @Throws(UserException::class)
    fun getBlogUserByUsername(username: String): BlogUser {
        try {
            return userRepository.findById(username).get()
        } catch (e: IllegalArgumentException) {
            throw UserException("BlogUser Not Found with username: $username")
        }
    }

    @Throws(UserException::class)
    override fun authUser(userAuthRequest: UserAuthRequest): UserAuthResponse {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    userAuthRequest.userName,
                    userAuthRequest.password
                )
            )

            val securityManager = SecurityManagerExpression(SecurityContextHolder.getContext().authentication)
            securityManager.hasAuthority(Authorities.ROLE_USER.toString())

            val user = getUserByUsername(userAuthRequest.userName)

            val userDetails = loadUserByUsername(user.userName)
            val userJwtToken = JwtUtil.generateToken(userDetails)

            return UserAuthResponse(user, true, "Login Successful", userJwtToken)

        } catch (e: Exception) {
            when (e) {
                is BadCredentialsException -> {
                    e.printStackTrace()
                    throw UserException("Wrong username/password")
                }
                is LockedException -> {
                    e.printStackTrace()
                    throw UserException("Your Account is Locked")
                }
                is DisabledException -> {
                    e.printStackTrace()
                    throw UserException("Your Account is disabled")
                }
                else -> throw e
            }
        }
    }

    override fun registerUser(userRegistrationRequest: UserRegistrationRequest): RegistrationMessage {
        var userFound = false
        val users: List<BlogUser> = allUser
        val registrationMessage = RegistrationMessage("", false, null)

        for (dbUser in users) {
            if (dbUser.email == userRegistrationRequest.email) {
                registrationMessage.registrationNotice = "Email Id is used"
                registrationMessage.user = dbUser
                registrationMessage.isRegistered = false
                userFound = true
            }
        }

        if (!userFound) {
            val user = BlogUser(
                userRegistrationRequest.userName,
                userRegistrationRequest.password,
                userRegistrationRequest.email,
                ArrayList(),
                HashSet(Collections.singletonList(SimpleGrantedAuthority(Authorities.ROLE_USER.toString())))
            )
            userRepository.insert(user)
            registrationMessage.user = user
            registrationMessage.registrationNotice = "User Registered Successfully, You may login now."
            registrationMessage.isRegistered = true
            //Todo: Send email verification
        }
        return registrationMessage
    }

    val allUser: List<BlogUser> = userRepository.findAll()

    @Throws(UserException::class)
    fun getUserByUsername(userName: String): BlogUser {
        try {
            return userRepository.findById(userName).get()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
            throw UserException("User $userName not found")
        }
    }

    @Throws(UserException::class)
    fun updateUserBlogByUserName(userName: String, blog: Blog) {
        val blogUser = getBlogUserByUsername(userName)
        blogUser.blogs.add(blog)
        userRepository.save(blogUser)
    }

    @Throws(UserException::class)
    fun deleteBlogFromUser(userName: String, savedBlog: Blog) {
        val user = getUserByUsername(userName)
        user.blogs.remove(savedBlog)
    }
}

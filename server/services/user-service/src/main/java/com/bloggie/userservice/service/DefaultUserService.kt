package com.bloggie.userservice.service

import com.bloggie.userservice.authority.Authorities
import com.bloggie.userservice.dto.Messages
import com.bloggie.userservice.dto.blog.Blog
import com.bloggie.userservice.dto.user.BlogUser
import com.bloggie.userservice.exceptions.UserException
import com.bloggie.userservice.repository.UserRepository
import com.bloggie.userservice.service.contracts.UserService
import com.bloggie.userservice.utils.JwtUtil
import org.springframework.dao.DuplicateKeyException
import org.springframework.security.authentication.*
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class DefaultUserService(
    private val userRepository: UserRepository,
    private val authenticationManager: AuthenticationManager,
    private val userDetailService: DefaultUserDetailService,
) : UserService {

    @Throws(UserException::class)
    fun getBlogUserByUsername(username: String): BlogUser {
        try {
            return userRepository.findById(username).get()
        } catch (e: IllegalArgumentException) {
            throw UserException("BlogUser Not Found with username: $username")
        }
    }

    @Throws(UserException::class)
    override fun authUser(userAuthRequest: Messages.UserAuthRequest): Messages.UserAuthResponse {
        try {
            authenticationManager.authenticate(
                UsernamePasswordAuthenticationToken(
                    userAuthRequest.userName,
                    userAuthRequest.password
                )
            )

            val user = getUserByUsername(userAuthRequest.userName)

            val userDetails = userDetailService.loadUserByUsername(user.userName)
            val userJwtToken = JwtUtil.generateToken(userDetails)

            return Messages.UserAuthResponse(user, true, "Login Successful", userJwtToken)

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
                is InternalAuthenticationServiceException -> {
                    throw UserException("User Account does not exists")
                }
                else -> throw e
            }
        }
    }

    @Throws(UserException::class)
    override fun registerUser(userRegistrationRequest: Messages.UserRegistrationRequest): Messages.RegistrationMessage {
        try {
            var userFound = false
            val users: List<BlogUser> = allUser
            val registrationMessage = Messages.RegistrationMessage("", false, null)

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
                    BCryptPasswordEncoder().encode(userRegistrationRequest.password),
                    userRegistrationRequest.email,
                    ArrayList(),
                    HashSet(Collections.singletonList(SimpleGrantedAuthority(Authorities.ROLE_USER.toString())))
                )
                userRepository.insert(user)
                registrationMessage.user = user
                registrationMessage.registrationNotice = "User Registered Successfully, You may login now."
                registrationMessage.isRegistered = true
            }
            return registrationMessage
        } catch (e: Exception) {
            when (e) {
                is DuplicateKeyException -> {
                    throw UserException("User with ${userRegistrationRequest.userName} Already Exists")
                }
                else -> throw e
            }
        }
    }

    val allUser: List<BlogUser> = userRepository.findAll()

    @Throws(UserException::class)
    fun getUserByUsername(userName: String): BlogUser {
        try {
            return userRepository.findById(userName).get()
        } catch (e: Exception) {
            when (e) {
                is NoSuchElementException, is IllegalArgumentException -> {
                    e.printStackTrace()
                    throw UserException("User $userName not found")
                }
                else -> throw e
            }
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

//    @Throws(UserException::class, BlogException::class)
//    fun deleteUser(userName: String) {
//        val principal = SecurityContextHolder.getContext().authentication.principal as UserDetails
//        val authorities = principal.authorities as Set<*>
//        try {
//            if (authorities.contains(Authorities.ROLE_ADMIN.toString())) {
//                val user = getUserByUsername(userName)
//                val userBlogs = user.blogs
//
//                userRepository.delete(user)
//
//                for (userBlog in userBlogs) {
//                    blogRepository.delete(userBlog)
//                    blogIndexRepository.deleteById(userBlog.id)
//                }
//            }
//        } catch (e: IllegalArgumentException) {
//            throw UserException("There was a problem with deleting your account. Error code: ${e.printStackTrace()}")
//        }
//    }

    val isUserAuthenticated = (SecurityContextHolder.getContext().authentication != null)
    val userAuthentication = SecurityContextHolder.getContext().authentication
}
package io.github.nullptrx.obfuscator.extension


import java.lang.reflect.Method
import java.util.*

open class StrExtension {
  var enabled: Boolean = true
  var packages: Array<String> = arrayOf()
  var implementation: String = ""

  // random, fixed
  var mode: String = "random"
  var randomPassword: Int = 3
  var fixedPassword: String = ""

  fun getImplClass(): String {
    try {
      return implementation.replace(".", "/")
    } catch (e: Exception) {
      throw  RuntimeException(e)
    }
  }

  fun getImplMethod(): Method {
    try {
      val clazz = Class.forName(implementation)
      if ("fixed".equals(mode, ignoreCase = true)) {
        if (fixedPassword.isEmpty()) {
          try {
            return clazz.getMethod("e", ByteArray::class.java)
          } catch (_: Exception) {
            return clazz.getMethod("e", ByteArray::class.java, String::class.java)
          }
        } else {
          return clazz.getMethod("e", ByteArray::class.java, String::class.java)
        }

      } else {
        if (randomPassword <= 0) {
          try {
            return clazz.getMethod("e", ByteArray::class.java)
          } catch (_: Exception) {
            return clazz.getMethod("e", ByteArray::class.java, String::class.java)
          }
        } else {
          return clazz.getMethod("e", ByteArray::class.java, String::class.java)
        }
      }

    } catch (e: Exception) {
      if (e is ClassNotFoundException) {
        throw ClassNotFoundException(e.localizedMessage)
      } else if (e is NoSuchMethodException) {
        throw NoSuchMethodException(e.localizedMessage)
      } else {
        throw e
      }
    }
  }

  fun getPassword(): String {
    if ("fixed".equals(mode, ignoreCase = true)) {
      return fixedPassword
    } else {
      if (randomPassword < -1 || randomPassword > 32) {
        return ""
      } else {
        return UUID.randomUUID().toString()
          .replace("-", "")
          .trim()
          .substring(0, randomPassword)
      }
    }
  }
}
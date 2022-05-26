package com.affise.attribution.deeplink

/**
 * Storage for isDeeplink model to determine if app was opened by deeplink
 */
interface DeeplinkClickRepository {
  /**
   * Sets flag [isDeeplink] describing if app was opened by deeplink
   */
  fun setDeeplinkClick(isDeeplink: Boolean)

  /**
   * Returns flag describing if app was opened by deeplink
   */
  fun isDeeplinkClick(): Boolean

  /**
   * Store deeplink that has been used to open this app
   */
  fun setDeeplink(deeplink: String)

  /**
   * returns deeplink that has been used to open this app or null
   */
  fun getDeeplink(): String?
}
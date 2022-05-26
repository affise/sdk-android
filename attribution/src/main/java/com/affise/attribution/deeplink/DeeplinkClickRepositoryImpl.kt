package com.affise.attribution.deeplink

/**
 * Implementation of [DeeplinkClickRepository]
 */
class DeeplinkClickRepositoryImpl : DeeplinkClickRepository {
  private var isDeeplinkClick: Boolean = false
  private var _deeplink: String? = null
  override fun setDeeplinkClick(isDeeplink: Boolean) {
    this.isDeeplinkClick = isDeeplink
  }

  override fun isDeeplinkClick(): Boolean {
    return isDeeplinkClick
  }

  override fun setDeeplink(deeplink: String) {
    _deeplink = deeplink
  }

  override fun getDeeplink(): String? {
    return _deeplink
  }
}
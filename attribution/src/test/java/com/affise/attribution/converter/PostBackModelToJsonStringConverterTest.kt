package com.affise.attribution.converter

import com.affise.attribution.events.SerializedEvent
import com.affise.attribution.logs.SerializedLog
import com.affise.attribution.network.entity.PostBackModel
import com.google.common.truth.Truth
import io.mockk.*
import org.json.JSONArray
import org.json.JSONObject
import org.junit.After
import org.junit.Before
import org.junit.Test

class PostBackModelToJsonStringConverterTest {

    private val model: PostBackModel = spyk(
        PostBackModel(
            uuid = "",
            affiseAppId = "",
            affisePkgAppName = "",
            appVersion = "",
            appVersionRaw = "",
            store = "",
            installedTime = 0L,
            firstOpenTime = 0L,
            installedHour = 0L,
            firstOpenHour = 0L,
            installFirstEvent = false,
            installBeginTime = 0L,
            installFinishTime = 0L,
            referrerInstallVersion = "",
            referralTime = 0L,
            referrerClickTimestamp = 0L,
            referrerClickTimestampServer = 0L,
            referrerGooglePlayInstant = false,
            createdTime = 0L,
            createdTimeMilli = 0L,
            createdTimeHour = 0L,
            lastSessionTime = 0L,
            connectionType = "",
            cpuType = "",
            hardwareName = "",
            networkType = "",
            deviceManufacturer = "",
            proxyIpAddress = "",
            deeplinkClick = false,
            deviceAtlasId = "",
            affDeviceId = "",
            affaltDeviceId = "",
            adid = "",
            androidId = "",
            androidIdMd5 = "",
            macSha1 = "",
            macMd5 = "",
            gaidAdid = "",
            gaidAdidMd5 = "",
            oaid = "",
            oaidMd5 = "",
            altstrAdid = "",
            fireosAdid = "",
            colorosAdid = "",
            reftoken = "",
            reftokens = "",
            referrer = "",
            userAgent = "",
            mccode = "",
            mncode = "",
            isp = "",
            region = "",
            country = "",
            language = "",
            deviceName = "",
            deviceType = "",
            osName = "",
            platform = "",
            apiLevelOs = "",
            affSdkVersion = "",
            osVersion = "",
            randomUserId = "",
            affSdkPos = "",
            timezoneDev = "",
            affEventToken = "",
            affEventName = "",
            lastTimeSession = 0L,
            timeSession = 0L,
            affSessionCount = 0L,
            lifetimeSessionCount = 0L,
            affDeeplink = "",
            affpartParamName = "",
            affpartParamNameToken = "",
            affAppToken = "",
            label = "",
            affsdkSecretId = "",
            pushtoken = "",
        )
    )

    @Before
    fun config() {
        mockkConstructor(JSONArray::class)
        mockkConstructor(JSONObject::class)

        every {
            constructedWith<JSONObject>().put(any(), any<String>())
        } returns JSONObject()

        every {
            constructedWith<JSONArray>().put(any<JSONObject>())
        } returns JSONArray()
    }

    @After
    fun tearDown() {
        unmockkConstructor(JSONArray::class)
        unmockkConstructor(JSONObject::class)
    }

    @Test
    fun convertEmpty() {
        val testEmptyData = "[{}]"

        every {
            constructedWith<JSONArray>().toString()
        } returns testEmptyData

        val models = listOf(model)
        val converter = PostBackModelToJsonStringConverter()

        val data = converter.convert(models)

        Truth.assertThat(data).isEqualTo(testEmptyData)
    }

    @Test
    fun convert() {
        val testData = """
            [
                {
                    "country": "test",
                    "hardware_name": "test",
                    "app_version": "test",
                    "reftoken": "test",
                    "isp": "test",
                    "aff_device_id": "test",
                    "affise_sdk_pos": "test",
                    "mac_sha1": "test",
                    "language": "test",
                    "device_type": "test",
                    "fireos_adid": "test",
                    "uuid": "test",
                    "aff_session_count": "test",
                    "oaid_md5": "test",
                    "android_id_md5": "test",
                    "cpu_type": "test",
                    "affsdk_secret_id": "test",
                    "affpart_param_name": "test",
                    "affise_pkg_app_name": "test",
                    "aff_deeplink": "test",
                    "lifetime_session_count": "test",
                    "app_version_raw": "test",
                    "reftokens": "test",
                    "mccode": "test",
                    "last_time_session": "test",
                    "affise_app_token": "test",
                    "mac_md5": "test",
                    "aff_event_name": "test",
                    "deeplink_click": "test",
                    "mncode": "test",
                    "region": "test",
                    "installed_time": "test",
                    "gaid_adid_md5": "test",
                    "installed_hour": "test",
                    "first_open_time": "test",
                    "affalt_device_id": "test",
                    "aff_event_token": "test",
                    "random_user_id": "test",
                    "affise_app_id": "test",
                    "device_manufacturer": "test",
                    "platform": "test",
                    "api_level_os": "test",
                    "device_name": "test",
                    "adid": "test",
                    "time_session": "test",
                    "aff_sdk_version": "test",
                    "device_atlas_id": "test",
                    "gaid_adid": "test",
                    "oaid": "test",
                    "user_agent": "test",
                    "install_finish_time": "test",
                    "connection_type": "test",
                    "proxy_ip_address": "test",
                    "altstr_adid": "test",
                    "os_version": "test",
                    "last_session_time": "test",
                    "timezone_dev": "test",
                    "affpart_param_name_token": "test",
                    "store": "test",
                    "label": "test",
                    "referral_time": "test",
                    "referrer": "test",
                    "coloros_adid": "test",
                    "install_begin_time": "test",
                    "first_open_hour": "test",
                    "os_name": "test",
                    "network_type": "test",
                    "android_id": "test"
                }
            ]
        """.trimIndent()

        every {
            constructedWith<JSONArray>().toString()
        } returns testData

        val models = listOf(model)
        val converter = PostBackModelToJsonStringConverter()

        val data = converter.convert(models)

        Truth.assertThat(data).isEqualTo(testData)
    }

    @Test
    fun convertStringWithSlash() {
        val testDataWithStringSlash = "[{\"user_agent\":\"Dalvik\\/2.1.0\"}]"
        val testDataWithSlash = "[{\"user_agent\":\"Dalvik/2.1.0\"}]"

        every {
            constructedWith<JSONArray>().toString()
        } returns testDataWithStringSlash

        val models = listOf(model)
        val converter = PostBackModelToJsonStringConverter()

        val data = converter.convert(models)

        Truth.assertThat(data).isEqualTo(testDataWithSlash)
    }

    @Test
    fun `verify events exists after serialization`() {
        unmockkConstructor(JSONArray::class)
        unmockkConstructor(JSONObject::class)

        val converter = PostBackModelToJsonStringConverter()
        val eventData = JSONObject()
        val event = SerializedEvent("id", eventData)

        every { model.events } returns listOf(event)

        val result = converter.convert(listOf(model))
            .let(::JSONArray)

        val eventDeserialized = try {
            result
                .getJSONObject(0)
                .getJSONArray("events")
                .getJSONObject(0)
        } catch (e: Exception) {
            null
        }

        Truth.assertThat(eventDeserialized).isNotNull()
    }

    @Test
    fun `verify logs exists after serialization`() {
        unmockkConstructor(JSONArray::class)
        unmockkConstructor(JSONObject::class)

        val converter = PostBackModelToJsonStringConverter()
        val logData = JSONObject()
        val log = SerializedLog("id", "type", logData)

        every { model.logs } returns listOf(log)

        val result = converter.convert(listOf(model))
            .let(::JSONArray)

        val logsDeserialized = try {
            result
                .getJSONObject(0)
                .getJSONArray("sdk_events")
        } catch (e: Exception) {
            null
        }

        val logsCount = try {
            result
                .getJSONObject(0)
                .getInt("affise_sdk_events_count")
        } catch (e: Exception) {
            null
        }

        Truth.assertThat(logsDeserialized).isNotNull()
        Truth.assertThat(logsDeserialized?.length()).isEqualTo(1)
        Truth.assertThat(logsCount).isEqualTo(1)
        Truth.assertThat(logsDeserialized?.getJSONObject(0)).isNotNull()
    }

    @Test
    fun `verify events, metric and logs exists if empty`() {
        unmockkConstructor(JSONArray::class)
        unmockkConstructor(JSONObject::class)

        val converter = PostBackModelToJsonStringConverter()

        every { model.events } returns listOf()
        every { model.logs } returns listOf()

        val result = converter.convert(listOf(model))
            .let(::JSONArray)

        val eventDeserialized = try {
            result
                .getJSONObject(0)
                .getJSONArray("events")
        } catch (e: Exception) {
            null
        }

        val eventCount = try {
            result
                .getJSONObject(0)
                .getInt("affise_events_count")
        } catch (e: Exception) {
            null
        }

        val logsDeserialized = try {
            result
                .getJSONObject(0)
                .getJSONArray("sdk_events")
        } catch (e: Exception) {
            null
        }

        val logsCount = try {
            result
                .getJSONObject(0)
                .getInt("affise_sdk_events_count")
        } catch (e: Exception) {
            null
        }

        val metricsDeserialized = try {
            result
                .getJSONObject(0)
                .getJSONArray("metrics_events")
        } catch (e: Exception) {
            null
        }

        val metricsCount = try {
            result
                .getJSONObject(0)
                .getInt("affise_metrics_events_count")
        } catch (e: Exception) {
            null
        }

        Truth.assertThat(eventDeserialized).isNotNull()
        Truth.assertThat(eventCount).isEqualTo(0)
        Truth.assertThat(eventDeserialized?.length()).isEqualTo(0)

        Truth.assertThat(logsDeserialized).isNotNull()
        Truth.assertThat(logsCount).isEqualTo(0)
        Truth.assertThat(logsDeserialized?.length()).isEqualTo(0)

        Truth.assertThat(metricsDeserialized).isNotNull()
        Truth.assertThat(metricsCount).isEqualTo(0)
        Truth.assertThat(metricsDeserialized?.length()).isEqualTo(0)
    }

}
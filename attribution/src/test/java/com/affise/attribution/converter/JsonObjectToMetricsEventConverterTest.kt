package com.affise.attribution.converter

import com.google.common.truth.Truth
import org.json.JSONObject
import org.junit.Test

/**
 * Test for [JsonObjectToMetricsEventConverter]
 */
class JsonObjectToMetricsEventConverterTest {

    private val testBeginDayTimestamp = 1650499200000
    private val testActivityName = "MainActivity"
    private val testOpenTime = 74678
    private val clicksData1Name = "AutoCatchingClickEvent_cad64327358b49a71dd7248913a51e5190bb54c5"
    private val clicksData1Count = 22
    private val clicksData2Name = "AutoCatchingClickEvent_4b2c39f023b1da54dd115f71692febb4565bf5fe"
    private val clicksData2Count = 3

        private val jsonString = "{\n" +
        "          \"begin_day_timestamp\": $testBeginDayTimestamp,\n" +
        "          \"data\": [\n" +
        "            {\n" +
        "              \"activity_mame\": \"$testActivityName\",\n" +
        "              \"open_time\": $testOpenTime,\n" +
        "              \"clicks_data\": [\n" +
        "                {\n" +
        "                  \"name\": \"$clicksData1Name\",\n" +
        "                  \"count\": $clicksData1Count\n" +
        "                },\n" +
        "                {\n" +
        "                  \"name\": \"$clicksData2Name\",\n" +
        "                  \"count\": $clicksData2Count\n" +
        "                },\n" +
        "              ]\n" +
        "            }\n" +
        "          ]\n" +
        "        }"

    @Test
    fun convert() {
        val converter = JsonObjectToMetricsEventConverter()
        val event = converter.convert(JSONObject(jsonString))

        Truth.assertThat(event.date).isEqualTo(testBeginDayTimestamp)
        Truth.assertThat(event.data.size).isEqualTo(1)

        val data = event.data.first()

        Truth.assertThat(data.activityName).isEqualTo(testActivityName)
        Truth.assertThat(data.openTime).isEqualTo(testOpenTime)
        Truth.assertThat(data.clicksData?.size).isEqualTo(2)

        val clickData1 = data.clicksData?.get(0)

        Truth.assertThat(clickData1?.name).isEqualTo(clicksData1Name)
        Truth.assertThat(clickData1?.count).isEqualTo(clicksData1Count)

        val clickData2 = data.clicksData?.get(1)

        Truth.assertThat(clickData2?.name).isEqualTo(clicksData2Name)
        Truth.assertThat(clickData2?.count).isEqualTo(clicksData2Count)
    }
}
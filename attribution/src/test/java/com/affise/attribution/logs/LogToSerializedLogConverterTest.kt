package com.affise.attribution.logs

import com.affise.attribution.converter.LogToSerializedLogConverter
import com.affise.attribution.events.predefined.AffiseLog
import com.affise.attribution.events.predefined.AffiseLogType
import com.affise.attribution.utils.generateUUID
import com.affise.attribution.utils.timestamp
import com.google.common.truth.Truth
import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verifyAll
import org.junit.Test
import java.util.UUID

class LogToSerializedLogConverterTest {

    @Test
    fun convert() {
        val typeValue = "typeValue"
        val logValue = "logValue"

        val logName: AffiseLogType = mockk {
            every { type } returns typeValue
        }

        val log: AffiseLog = mockk {
            every { name } returns logName
            every { value } returns logValue
        }

        val converter = LogToSerializedLogConverter()

        mockkStatic(::generateUUID) {
            every {
                generateUUID()
            } returns UUID(0, 0)

            mockkStatic(::timestamp) {
                every {
                    timestamp()
                } returns 1638265456848

                val result = converter.convert(log)

                Truth.assertThat(result.id).isEqualTo(ID)
                Truth.assertThat(result.data.toString()).isEqualTo(DATA)

                verifyAll {
                    logName.type
                    log.name
                    log.value
                    timestamp()
                }
            }
        }
    }

    companion object {
        const val ID = "00000000-0000-0000-0000-000000000000"
        const val DATA =
            "{" +
                "\"affise_sdkevent_name\":\"affise_event_sdklog\"," +
                "\"affise_sdkevent_parameters\":{" +
                    "\"typeValue\":\"logValue\"" +
                "}," +
                "\"affise_sdkevent_timestamp\":1638265456848," +
                "\"affise_sdkevent_id\":\"$ID\"" +
            "}"
    }

}
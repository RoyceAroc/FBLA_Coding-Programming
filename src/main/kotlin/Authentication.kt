import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.JsonValue
import java.io.BufferedReader
import java.io.File
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.nio.charset.Charset
import java.util.*
val random = Random()
/*
 val diff: Long = Date().getTime() - content.toLong()
            val seconds = diff / 1000
            val minutes = seconds / 60
            print(minutes)

 */
class Authentication() {
    var username: String = ""
    var identifier: String = ""
   /* fun checkPreviousSessionContinuity(): Boolean {
        val fileName = "clientData.txt"
        var file = File(fileName)
        var fileExists = file.exists()
        if(fileExists) {
            var ins: InputStream = file.inputStream()
            var content = ins.readBytes().toString(Charset.defaultCharset())
            val url = URL("https://backend.cssa.dev/getOngoingQuizzes")
            val con = url.openConnection() as HttpURLConnection

            con.requestMethod = "POST";
            con.doOutput = true;

            val data = """
            {
                "Questions": "5"
            }
            """.trimIndent()

            con.outputStream.use { os ->
                val input: ByteArray = data.toByteArray()

                os.write(input, 0, input.size)
            }

            BufferedReader(
                InputStreamReader(con.inputStream, "utf-8")
            ).use { br ->
                val response = StringBuilder()
                var responseLine: String?
                while (br.readLine().also { responseLine = it } != null) {
                    response.append(responseLine!!.trim { it <= ' ' })
                }
                val json: JsonValue = JsonKraken.deserialize(response.toString())
                if(response.toString() != "false") {
                    val json: JsonValue = JsonKraken.deserialize(response.toString())
                    val diff: Long = Date().getTime() - json["info"][1].cast<String>().toLong()
                    print(diff)
                   //if(json["info"][1].cast<String>())
                }

                //print(json["info"][1]["Question"].cast<String>())

            }
            /*for (i in 0 until content.length) {
                if(content[i] == '`') {
                    break
                }
                username += content[i]
            }*/
            return false //set to true after testing
        }
        return false
    }
*/
    fun Question(): JsonValue {
        val url = URL("https://backend.cssa.dev/generateRandom1")
        val con = url.openConnection() as HttpURLConnection

        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        con.requestMethod = "POST";
        con.doOutput = true;

        val data = """
            {
                "Questions": "5"
            }
            """.trimIndent()

        con.outputStream.use { os ->
            val input: ByteArray = data.toByteArray()

            os.write(input, 0, input.size)
        }

        BufferedReader(
            InputStreamReader(con.inputStream, "utf-8")
        ).use { br ->
            val response = StringBuilder()
            var responseLine: String?
            while (br.readLine().also { responseLine = it } != null) {
                response.append(responseLine!!.trim { it <= ' ' })
            }
            val json: JsonValue = JsonKraken.deserialize(response.toString())
            //print(json["info"][1]["Question"].cast<String>())
            return json["info"]
        }
    }

    fun checkClient(_username: String): Boolean {
        username = _username
        val fileName = "clientData.txt"
        var file = File(fileName)
        var fileExists = file.exists()
        if(fileExists) {
            var ins: InputStream = file.inputStream()
            var content = ins.readBytes().toString(Charset.defaultCharset())
            //Previous user reactivate
        } else {
            val dataCreated :Boolean = file.createNewFile()
            if(dataCreated) {
                identifier = (random.nextInt(100000000 - 10000000) + 10000000).toString();
                file.writeText("$identifier")
            } else {
                return false;
            }
        }
        return true;
        /*
        val url = URL("https://cssa-backend.herokuapp.com/check")
        val con = url.openConnection() as HttpURLConnection

        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        con.requestMethod = "POST";
        con.doOutput = true;

        val data = """
            {
                "Name": "$name"
            }
            """.trimIndent()

        con.outputStream.use { os ->
            val input: ByteArray = data.toByteArray()

            os.write(input, 0, input.size)
        }

        BufferedReader(
            InputStreamReader(con.inputStream, "utf-8")
        ).use { br ->
            val response = StringBuilder()
            var responseLine: String?
            while (br.readLine().also { responseLine = it } != null) {
                response.append(responseLine!!.trim { it <= ' ' })
            }
            if(response.toString() == "false") {
                return false;
            } else {
                val json: JsonValue = JsonKraken.deserialize(response.toString())
                email = json["info"][0].cast<String>()
                username = json["info"][1].cast<String>()
                fName = json["info"][2].cast<String>()
                lName = json["info"][3].cast<String>()
                return true;
            }
        }*/
    }
}

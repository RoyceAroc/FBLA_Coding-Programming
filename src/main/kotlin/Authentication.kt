
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
    var globalExamStart: Long= Date().getTime()
    var objectUse: String = ""
    var objectUseB: String = ""
    var objectUseC: String = ""
    var objectUseD: String = ""
    var objectUseE: String = ""
    fun checkPreviousSessionContinuity(): String {

        val fileName = "clientData.txt"
        var file = File(fileName)
        var fileExists = file.exists()
        if(fileExists) {
            var ins: InputStream = file.inputStream()
            identifier = ins.readBytes().toString(Charset.defaultCharset())
            val url = URL("https://backend.cssa.dev/getClientData")
            val con = url.openConnection() as HttpURLConnection

            con.setRequestProperty("Content-Type", "application/json; utf-8");
            con.setRequestProperty("Accept", "application/json");

            con.requestMethod = "POST";
            con.doOutput = true;

            val data = """
            {
                "identifier": "$identifier"
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
                    return "error";
                } else {
                    val json: JsonValue = JsonKraken.deserialize(response.toString())
                    username = json["info"][1].cast<String>()
                    var a = 2;
                    while(true) {
                        try {
                            if((Date().getTime() - json["info"][a].cast<String>().toLong())/60000 < 15) {
                                username = json["info"][a-1].cast<String>()
                                globalExamStart = json["info"][a].cast<String>().toLong()
                                objectUse = JsonKraken.serialize(Question(json["info"][a+1].cast<String>().toInt()))
                                objectUseB = JsonKraken.serialize(Question2(json["info"][a+2].cast<String>().toInt()))
                                objectUseC = JsonKraken.serialize(Question3(json["info"][a+3].cast<String>().toInt()))
                                objectUseD = JsonKraken.serialize(Question4(json["info"][a+4].cast<String>().toInt()))
                                objectUseE = JsonKraken.serialize(Question5(json["info"][a+5].cast<String>().toInt()))
                                return "home"

                            } else {
                                //Show Report on Past Exams
                            }
                            a += 13;
                        } catch (e: net.jemzart.jsonkraken.errors.collections.NoSuchIndexException) {
                            break;
                        }
                     }
                    //print(Date().getTime() - json["info"][1].cast<String>().toLong())
                    return "authen";
                }
            }
        }

        return "false"
    }

    fun Question(numeric: Int): JsonValue {
        val url = URL("https://backend.cssa.dev/generateRandom1")
        val con = url.openConnection() as HttpURLConnection

        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        con.requestMethod = "POST";
        con.doOutput = true;

        val data = """
            {
                "Questions": "$numeric"
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
            return json["info"]


        }
    }

     fun createSession(a: String, b: String, c: String, d: String, e:String) {
        val url = URL("https://backend.cssa.dev/createSession")
        val con = url.openConnection() as HttpURLConnection

        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");
        con.requestMethod = "POST";
        con.doOutput = true;
        val startTime = Date().getTime()
         globalExamStart = startTime
        val question1: JsonValue = JsonKraken.deserialize(a)
        val object1 = question1[0].cast<String>()
        val question2: JsonValue = JsonKraken.deserialize(b)
        val object2 = question2[0].cast<String>()
        val question3: JsonValue = JsonKraken.deserialize(c)
        val object3 = question3[0].cast<String>()
        val question4: JsonValue = JsonKraken.deserialize(d)
        val object4 = question4[0].cast<String>()
        val question5: JsonValue = JsonKraken.deserialize(e)
        val object5 = question5[0].cast<String>()
        val data = """
            {
                "Identifier": "$identifier",
                "Username": "$username",
                "TimeStarted": "$startTime",
                "Question1": "$object1", 
                "Question2": "$object2",
                "Question3": "$object3",
                "Question4": "$object4",
                "Question5": "$object5"     
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

        }
    }

    fun Question2(numeric: Int): JsonValue {
        val url = URL("https://backend.cssa.dev/generateRandom2")
        val con = url.openConnection() as HttpURLConnection

        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        con.requestMethod = "POST";
        con.doOutput = true;

        val data = """
            {
                "Questions": "$numeric"
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
            return json["info"]
        }
    }
    fun Question3(numeric: Int): JsonValue {
        val url = URL("https://backend.cssa.dev/generateRandom3")
        val con = url.openConnection() as HttpURLConnection

        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        con.requestMethod = "POST";
        con.doOutput = true;

        val data = """
            {
                "Questions": "$numeric"
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
            return json["info"]
        }
    }

    fun Question4(numeric: Int): JsonValue {
        val url = URL("https://backend.cssa.dev/generateRandom4")
        val con = url.openConnection() as HttpURLConnection

        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        con.requestMethod = "POST";
        con.doOutput = true;

        val data = """
            {
                "Questions": "$numeric"
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
            return json["info"]
        }
    }
    fun Question5(numeric: Int): JsonValue {
        val url = URL("https://backend.cssa.dev/generateRandom5")
        val con = url.openConnection() as HttpURLConnection

        con.setRequestProperty("Content-Type", "application/json; utf-8");
        con.setRequestProperty("Accept", "application/json");

        con.requestMethod = "POST";
        con.doOutput = true;

        val data = """
            {
               "Questions": "$numeric"
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
            return json["info"]
        }
    }
    fun checkClient(_username: String): Boolean {
        username = _username
        val fileName = "clientData.txt"
        var file = File(fileName)
        var fileExists = file.exists()
        if(fileExists) {

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

    }


}

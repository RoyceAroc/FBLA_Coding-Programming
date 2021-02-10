
import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.JsonValue
import java.awt.image.BufferedImage
import javax.imageio.ImageIO
fun main() = Window(title = "FBLA Quiz Portal", icon = loadImageResource("fbla-portal_logo.png"), size = IntSize(1080, 712)) {

    var objectUse: String = ""
    var authenticated by remember {
        mutableStateOf(false)
    }
    var home by remember {
        mutableStateOf(false)
    }
    var question1 by remember {
        mutableStateOf(false)
    }
    var question2 by remember {
        mutableStateOf(false)
    }
    var question3 by remember {
        mutableStateOf(false)
    }
    var question4 by remember {
        mutableStateOf(false)
    }
    var question5 by remember {
        mutableStateOf(false)
    }
    var expanded by remember {
        mutableStateOf(false)
    }

    var auth = Authentication()
    //if(auth.checkPreviousSessionContinuity())
        //authenticated = true;
    MaterialTheme() {
        if (authenticated) {
            Row {
                Column(Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(0.15f)
                    .background(Color(69, 133, 244))
                    .pointerMoveFilter(),
                    Arrangement.spacedBy(20.dp)) {
                        if(home) {
                            Text(
                                text = "Time here",
                                style = MaterialTheme.typography.body1.merge(),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                        }
                        Button(modifier = Modifier.align(Alignment.CenterHorizontally).padding(0.dp, 5.dp, 0.dp, 0.dp),
                            onClick = {
                                question1 = true;
                                question2 = false;
                                question3 = false;
                                question4 = false;
                                question5 = false;
                            }) {
                            Text("Question 1")
                        }
                        Button(modifier = Modifier.align(Alignment.CenterHorizontally).padding(0.dp, 5.dp, 0.dp, 0.dp),
                            onClick = {
                                question1 = false;
                                question2 = true;
                                question3 = false;
                                question4 = false;
                                question5 = false;
                            }) {
                            Text("Question 2")
                        }
                        Button(modifier = Modifier.align(Alignment.CenterHorizontally).padding(0.dp, 5.dp, 0.dp, 0.dp),
                            onClick = {
                                question1 = false;
                                question2 = false;
                                question3 = true;
                                question4 = false;
                                question5 = false;
                            }) {
                            Text("Question 3")
                        }
                        Button(modifier = Modifier.align(Alignment.CenterHorizontally).padding(0.dp, 5.dp, 0.dp, 0.dp),
                            onClick = {
                                question1 = false;
                                question2 = false;
                                question3 = false;
                                question4 = true;
                                question5 = false;
                            }) {
                            Text("Question 4")
                        }
                        Button(modifier = Modifier.align(Alignment.CenterHorizontally).padding(0.dp, 5.dp, 0.dp, 0.dp),
                            onClick = {
                                question1 = false;
                                question2 = false;
                                question3 = false;
                                question4 = false;
                                question5 = true;
                            }) {
                            Text("Question 5")
                        }
                        if(home) {
                            Button(modifier = Modifier.align(Alignment.CenterHorizontally).padding(0.dp, 5.dp, 0.dp, 0.dp),
                                onClick = {
                                    // Submits quiz
                                }) {
                                Text("Submit Quiz")
                            }
                        }
                    }


                Column(Modifier
                    .fillMaxHeight(0.92f)
                    .fillMaxWidth(if (expanded) 0.831f else 0.92f),
                    Arrangement.spacedBy(50.dp)) {
                    if(home == false) {
                        Text(
                            text = "Welcome, ${auth.username}!",
                            Modifier.align(Alignment.CenterHorizontally),
                            fontSize = 40.sp
                        )
                        Button(modifier = Modifier.align(Alignment.CenterHorizontally).padding(0.dp, 5.dp, 0.dp, 0.dp),
                            onClick = {
                                //Send request for questions
                                objectUse = JsonKraken.serialize(auth.Question());

                                home = true;
                            }) {
                            Text("Start Quiz")
                        }
                    }
                    Column(Modifier
                        .fillMaxWidth(0.8f)
                        .fillMaxHeight(0.6f)
                        .align(Alignment.CenterHorizontally)
                        .background(Color(243, 243, 243))) {
                        if(question1) {
                            val finale: JsonValue = JsonKraken.deserialize(objectUse)
                            if(finale[0].cast<String>() == "1") {
                                Text(
                                    text = finale[1]["Question"].cast<String>(),
                                    Modifier.align(Alignment.CenterHorizontally),
                                    fontSize = 20.sp
                                )
                                val radioOptions = listOf(finale[1]["Choices"]["ChoiceA"].cast<String>(), finale[1]["Choices"]["ChoiceB"].cast<String>(), finale[1]["Choices"]["ChoiceC"].cast<String>(), finale[1]["Choices"]["ChoiceD"].cast<String>())
                                val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
                                Column {
                                    radioOptions.forEach { text ->
                                        Row(
                                            Modifier
                                                .fillMaxWidth()
                                                .preferredHeight(56.dp)
                                                .selectable(
                                                    selected = (text == selectedOption),
                                                    onClick = { onOptionSelected(text) }
                                                )
                                                .padding(horizontal = 16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            // The [clearAndSetSemantics] causes the button's redundant
                                            // selectable semantics to be cleared in favor of the [Row]
                                            // selectable's, to improve usability with screen-readers.
                                            RadioButton(

                                                selected = (text == selectedOption),
                                                onClick = { onOptionSelected(text) }
                                            )
                                            Text(
                                                text = text,
                                                style = MaterialTheme.typography.body1.merge(),
                                                modifier = Modifier.padding(start = 16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        if(question2) {
                            if(true) {
                                Text(
                                    text = "True or False Question",
                                    Modifier.align(Alignment.CenterHorizontally),
                                    fontSize = 20.sp
                                )
                                val radioOptions = listOf("True", "False")
                                val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[0]) }
                                Column {
                                    radioOptions.forEach { text ->
                                        Row(
                                            Modifier
                                                .fillMaxWidth()
                                                .preferredHeight(56.dp)
                                                .selectable(
                                                    selected = (text == selectedOption),
                                                    onClick = { onOptionSelected(text) }
                                                )
                                                .padding(horizontal = 16.dp),
                                            verticalAlignment = Alignment.CenterVertically
                                        ) {
                                            // The [clearAndSetSemantics] causes the button's redundant
                                            // selectable semantics to be cleared in favor of the [Row]
                                            // selectable's, to improve usability with screen-readers.
                                            RadioButton(

                                                selected = (text == selectedOption),
                                                onClick = { onOptionSelected(text) }
                                            )
                                            Text(
                                                text = text,
                                                style = MaterialTheme.typography.body1.merge(),
                                                modifier = Modifier.padding(start = 16.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        if(question3) {
                            val checkedState = remember { mutableStateOf(false) }
                            val checkedStateB = remember { mutableStateOf(false) }
                            val checkedStateC = remember { mutableStateOf(false) }
                            val checkedStateD = remember { mutableStateOf(false) }
                            Text(
                                text = "Checkbox Question",
                                Modifier.align(Alignment.CenterHorizontally),
                                fontSize = 20.sp
                            )
                            Column {
                                    Row(
                                        Modifier
                                            .fillMaxWidth()
                                            .preferredHeight(56.dp)
                                            .padding(horizontal = 16.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // The [clearAndSetSemantics] causes the button's redundant
                                        // selectable semantics to be cleared in favor of the [Row]
                                        // selectable's, to improve usability with screen-readers.
                                        Checkbox(
                                            checked = checkedState.value,
                                            onCheckedChange = { checkedState.value = it }
                                        )
                                        Text(
                                            text = "Checkbox 1",
                                            style = MaterialTheme.typography.body1.merge(),
                                            modifier = Modifier.padding(start = 16.dp)
                                        )
                                    }
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .preferredHeight(56.dp)
                                        .padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // The [clearAndSetSemantics] causes the button's redundant
                                    // selectable semantics to be cleared in favor of the [Row]
                                    // selectable's, to improve usability with screen-readers.
                                    Checkbox(
                                        checked = checkedStateB.value,
                                        onCheckedChange = { checkedStateB.value = it }
                                    )
                                    Text(
                                        text = "Checkbox 2",
                                        style = MaterialTheme.typography.body1.merge(),
                                        modifier = Modifier.padding(start = 16.dp)
                                    )
                                }
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .preferredHeight(56.dp)
                                        .padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // The [clearAndSetSemantics] causes the button's redundant
                                    // selectable semantics to be cleared in favor of the [Row]
                                    // selectable's, to improve usability with screen-readers.
                                    Checkbox(
                                        checked = checkedStateC.value,
                                        onCheckedChange = { checkedStateC.value = it }
                                    )
                                    Text(
                                        text = "Checkbox 3",
                                        style = MaterialTheme.typography.body1.merge(),
                                        modifier = Modifier.padding(start = 16.dp)
                                    )
                                }
                                Row(
                                    Modifier
                                        .fillMaxWidth()
                                        .preferredHeight(56.dp)
                                        .padding(horizontal = 16.dp),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    // The [clearAndSetSemantics] causes the button's redundant
                                    // selectable semantics to be cleared in favor of the [Row]
                                    // selectable's, to improve usability with screen-readers.
                                    Checkbox(
                                        checked = checkedStateD.value,
                                        onCheckedChange = { checkedStateD.value = it }
                                    )
                                    Text(
                                        text = "Checkbox 4",
                                        style = MaterialTheme.typography.body1.merge(),
                                        modifier = Modifier.padding(start = 16.dp)
                                    )
                                }
                            }
                        }
                        if(question4) {
                            var answer by remember {
                                mutableStateOf("")
                            }

                            // The [clearAndSetSemantics] causes the button's redundant
                            // selectable semantics to be cleared in favor of the [Row]
                            // selectable's, to improve usability with screen-readers.
                            Text(
                                text = "FBLA was founded in ",
                                Modifier.align(Alignment.CenterHorizontally),
                                fontSize = 20.sp
                            )
                            TextField(
                                modifier = Modifier.align(Alignment.CenterHorizontally),
                                value = answer,
                                onValueChange = { answer = it },
                            )


                        }
                    }
                }
            }
        } else {
            Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {

                Row(Modifier.fillMaxSize().align(Alignment.CenterHorizontally)) {

                    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("FBLA Quiz Portal", modifier = Modifier.padding(0.dp, 0.dp, 0.dp, 30.dp), fontSize = 40.sp, textAlign = TextAlign.Right)

                        Column(Modifier
                            .align(Alignment.CenterHorizontally)
                            .background(Color(0xF0, 0xF0, 0xF0), RoundedCornerShape(8.dp))
                            .border(3.dp, Color(33, 33, 33), RoundedCornerShape(8.dp))) {
                            var name by remember {
                                mutableStateOf("")
                            }
                            Column(Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(10.dp, 15.dp, 10.dp, 15.dp), Arrangement.spacedBy(15.dp)) {

                                Text("Name", textAlign = TextAlign.Left)

                                TextField(
                                    modifier = Modifier.align(Alignment.CenterHorizontally),
                                    value = name,
                                    onValueChange = { name = it },
                                )

                                Button(modifier = Modifier.align(Alignment.CenterHorizontally).padding(0.dp, 5.dp, 0.dp, 0.dp),
                                    onClick = {
                                        if(auth.checkClient(name)) {
                                            authenticated = true
                                        }
                                    }) {
                                    Text("Enter Portal")
                                }
                            }

                            Divider(color = Color.Gray, thickness = 2.dp, modifier = Modifier.width(250.dp).align(Alignment.CenterHorizontally).padding(0.dp, 0.dp, 0.dp, 10.dp))

                            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                                onClick = {
                                    //Display information to judges

                                }) {
                                Text("Judge Info")
                            }
                            Divider(color = Color.Transparent, thickness = 2.dp, modifier = Modifier.width(250.dp).align(Alignment.CenterHorizontally).padding(0.dp, 0.dp, 0.dp, 10.dp))
                        }
                    }
                }
            }
        }
    }
}

private fun loadImageResource(path: String): BufferedImage {
    val resource = Thread.currentThread().contextClassLoader.getResource(path)
    requireNotNull(resource) { "Resource at path '$path' not found" }
    return resource.openStream().use(ImageIO::read)
}

@Composable
fun Text(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily = fontFamily(androidx.compose.ui.text.platform.font("Quicksand", "Quicksand-Medium.ttf")),
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    style: TextStyle = AmbientTextStyle.current
) {
    Text(
        AnnotatedString(text),
        modifier,
        color,
        fontSize,
        fontStyle,
        fontWeight,
        fontFamily,
        letterSpacing,
        textDecoration,
        textAlign,
        lineHeight,
        overflow,
        softWrap,
        maxLines,
        emptyMap(),
        onTextLayout,
        style
    )
}
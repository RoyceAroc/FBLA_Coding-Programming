import androidx.compose.desktop.Window
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.CoreTextField
import androidx.compose.material.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerMoveFilter
import androidx.compose.ui.text.*
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.fontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.ImeOptions
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import net.jemzart.jsonkraken.JsonKraken
import net.jemzart.jsonkraken.JsonValue
import java.awt.image.BufferedImage
import java.lang.Math.floor
import java.util.*
import javax.imageio.ImageIO
import kotlin.concurrent.fixedRateTimer

annotation class Preview



@InternalTextApi
 fun main() = Window(title = "FBLA Quiz Portal", icon = loadImageResource("fbla-portal_logo.png"), size = IntSize(1080, 712)) {

    var index = 0
    var indexB = 0
    var indexC = 0
    val checkedState = remember { mutableStateOf(false) }
    val checkedStateB = remember { mutableStateOf(false) }
    val checkedStateC = remember { mutableStateOf(false) }
    val checkedStateD = remember { mutableStateOf(false) }

    val timerValue = remember { mutableStateOf("") }
    var blankAnswer by remember {
        mutableStateOf(TextFieldValue(""))
    }
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





    if(auth.checkPreviousSessionContinuity() == "home") {
        authenticated = true
        home = true
    } else if(auth.checkPreviousSessionContinuity() == "authen") {
        authenticated = true
    }


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
                                text = timerValue.value,
                                style = MaterialTheme.typography.body1.merge(),
                                modifier = Modifier.padding(start = 16.dp)
                            )

                            Button(modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(0.dp, 5.dp, 0.dp, 0.dp),
                                onClick = {
                                    question1 = true;
                                    question2 = false;
                                    question3 = false;
                                    question4 = false;
                                    question5 = false;
                                }) {
                                Text("Question 1")
                            }
                            Button(modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(0.dp, 5.dp, 0.dp, 0.dp),
                                onClick = {
                                    question1 = false;
                                    question2 = true;
                                    question3 = false;
                                    question4 = false;
                                    question5 = false;
                                }) {
                                Text("Question 2")
                            }
                            Button(modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(0.dp, 5.dp, 0.dp, 0.dp),
                                onClick = {
                                    question1 = false;
                                    question2 = false;
                                    question3 = true;
                                    question4 = false;
                                    question5 = false;
                                }) {
                                Text("Question 3")
                            }
                            Button(modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(0.dp, 5.dp, 0.dp, 0.dp),
                                onClick = {
                                    question1 = false;
                                    question2 = false;
                                    question3 = false;
                                    question4 = true;
                                    question5 = false;
                                }) {
                                Text("Question 4")
                            }
                            Button(modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(0.dp, 5.dp, 0.dp, 0.dp),
                                onClick = {
                                    question1 = false;
                                    question2 = false;
                                    question3 = false;
                                    question4 = false;
                                    question5 = true;
                                }) {
                                Text("Question 5")
                            }

                            Button(modifier = Modifier.align(Alignment.CenterHorizontally)
                                .padding(0.dp, 5.dp, 0.dp, 0.dp),
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
                    if(!home) {
                        Text(
                            text = "Welcome, ${auth.username}!",
                            Modifier.align(Alignment.CenterHorizontally),
                            fontSize = 40.sp
                        )
                        Button(modifier = Modifier.align(Alignment.CenterHorizontally).padding(0.dp, 5.dp, 0.dp, 0.dp),
                            onClick = {
                                //Get questions
                                auth.objectUse = JsonKraken.serialize(auth.Question(-1));
                                auth.objectUseB = JsonKraken.serialize(auth.Question2(-1));
                                auth.objectUseC = JsonKraken.serialize(auth.Question3(-1));
                                auth.objectUseD = JsonKraken.serialize(auth.Question4(-1));
                                auth.objectUseE = JsonKraken.serialize(auth.Question5(-1));
                                //Create session for backup purposes
                                auth.createSession( auth.objectUse,  auth.objectUseB,  auth.objectUseC,  auth.objectUseD,  auth.objectUseE);
                                home = true;
                                fixedRateTimer("default", false, 0L, 1000){
                                    //15 minute timer
                                    var seconds = 60 - ((Date().getTime() - auth.globalExamStart) / 1000).toInt()
                                    if(((Date().getTime()-auth.globalExamStart)/60000).toInt() == 0) {
                                        if(((Date().getTime()-auth.globalExamStart)/1000).toInt() == 0) {
                                            timerValue.value = "15: 00"
                                        } else {
                                            timerValue.value = "14 : " + seconds
                                        }
                                    } else {
                                        var minutes = 15-((Date().getTime()-auth.globalExamStart)/60000).toInt()-1
                                        seconds = 60 - ((Date().getTime() - auth.globalExamStart) / 1000).toInt() -60*(floor(minutes.toDouble())).toInt() +840
                                        if(seconds == 60) {
                                            minutes += 1
                                            seconds = 0
                                        }
                                        timerValue.value = minutes.toString() + " : " + seconds.toString()
                                        if(seconds == 0 && minutes == 0) {

                                            //testEnded = true
                                            cancel()
                                        }
                                    }
                                }

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
                            val finale: JsonValue = JsonKraken.deserialize( auth.objectUse)
                                Text(
                                    text = finale[1]["Question"].cast<String>(),
                                    Modifier.align(Alignment.CenterHorizontally),
                                    fontSize = 20.sp
                                )
                                val radioOptions = listOf(finale[1]["Choices"]["ChoiceA"].cast<String>(), finale[1]["Choices"]["ChoiceB"].cast<String>(), finale[1]["Choices"]["ChoiceC"].cast<String>(), finale[1]["Choices"]["ChoiceD"].cast<String>())
                                val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[index]) }
                                if(selectedOption == finale[1]["Choices"]["ChoiceA"].cast<String>()) {
                                    index = 0
                                } else if(selectedOption == finale[1]["Choices"]["ChoiceB"].cast<String>()) {
                                    index = 1
                                } else if(selectedOption == finale[1]["Choices"]["ChoiceC"].cast<String>()) {
                                    index = 2
                                } else if(selectedOption == finale[1]["Choices"]["ChoiceD"].cast<String>()) {
                                    index = 3
                                }
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
                        if(question2) {
                            val finale: JsonValue = JsonKraken.deserialize( auth.objectUseB)
                            Text(
                                text = finale[1]["Question"].cast<String>(),
                                Modifier.align(Alignment.CenterHorizontally),
                                fontSize = 20.sp
                            )
                            val radioOptions = listOf(finale[1]["Choices"]["ChoiceA"].cast<String>(), finale[1]["Choices"]["ChoiceB"].cast<String>())
                            val (selectedOption, onOptionSelected) = remember { mutableStateOf(radioOptions[indexB]) }
                            if(selectedOption == finale[1]["Choices"]["ChoiceA"].cast<String>()) {
                                indexB = 0
                            } else if(selectedOption == finale[1]["Choices"]["ChoiceB"].cast<String>()) {
                                indexB = 1
                            }
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
                        if(question3) {
                            val finale: JsonValue = JsonKraken.deserialize( auth.objectUseC)

                            Text(
                                text = finale[1]["Question"].cast<String>(),
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
                                            text = finale[1]["Choices"]["ChoiceA"].cast<String>(),
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
                                        text = finale[1]["Choices"]["ChoiceB"].cast<String>(),
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
                                        text = finale[1]["Choices"]["ChoiceC"].cast<String>(),
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
                                        text = finale[1]["Choices"]["ChoiceD"].cast<String>(),
                                        style = MaterialTheme.typography.body1.merge(),
                                        modifier = Modifier.padding(start = 16.dp)
                                    )
                                }
                            }
                        }
                        if(question4) {
                            val finale: JsonValue = JsonKraken.deserialize( auth.objectUseD)
                            val items = listOf(finale[1]["Choices"]["ChoiceA"].cast<String>(), finale[1]["Choices"]["ChoiceB"].cast<String>(),finale[1]["Choices"]["ChoiceC"].cast<String>(), finale[1]["Choices"]["ChoiceD"].cast<String>())
                            var showMenu by remember { mutableStateOf( false ) }
                            var selectedIndex by remember { mutableStateOf(indexC) }
                            Text(
                                text = finale[1]["Question"].cast<String>(),
                                style = MaterialTheme.typography.body1.merge(),
                                modifier = Modifier.padding(start = 16.dp)
                            )
                            DropdownMenu(
                                toggle = {
                                    Text(items[selectedIndex], modifier = Modifier.fillMaxWidth().clickable(onClick = { showMenu = true }))
                                },
                                expanded = showMenu,
                                onDismissRequest = { showMenu = false },
                                toggleModifier = Modifier.fillMaxWidth(0.15f).background(Color.Gray),
                                dropdownModifier = Modifier.fillMaxWidth(0.15f).background(Color.Transparent)
                            ) {
                                items.forEachIndexed { index, s ->
                                    DropdownMenuItem(
                                        enabled = true,
                                        onClick = {
                                            selectedIndex = index
                                            indexC = index
                                            showMenu = false
                                        }
                                    ) {

                                        Text(text = s)
                                    }
                                }
                            }
                        }
                        if(question5) {
                            val finale: JsonValue = JsonKraken.deserialize( auth.objectUseE)
                            Row {



                                Column {
                                    QuestionText("Fill in the blank below")

                                    Row {
                                        Text(finale[1]["Question"].cast<String>().substringBefore('~'))
                                        QuestionField(
                                            modifier = Modifier
                                                .width(200.dp)
                                                .height(25.dp),
                                            value = blankAnswer,
                                            onValueChange = {
                                                if (it.text.length < 25) {
                                                    blankAnswer = it
                                                }
                                            },
                                        )
                                        Text(finale[1]["Question"].cast<String>().substringAfter('~'))
                                    }
                                }
                            }
                        }
                    }
                }
            }
        } else {
            var openedJudgeInfo by remember {
                mutableStateOf(false)
            }
            var openedFAQInfo by remember {
                mutableStateOf(false)
            }

            Column(Modifier.fillMaxSize(), Arrangement.spacedBy(5.dp)) {

                Row(Modifier.fillMaxSize().align(Alignment.CenterHorizontally)) {

                    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                        if(openedJudgeInfo) {

                            Box {
                                val popupWidth = 500.dp
                                val popupHeight = 500.dp
                                val cornerSize = 16.dp

                                Popup(alignment = Alignment.Center) {
                                    // Draw a rectangle shape with rounded corners inside the popup
                                    Box(
                                        Modifier
                                            .preferredSize(popupWidth, popupHeight)
                                            .background(Color.Blue, RoundedCornerShape(cornerSize))

                                    )
                                    Text("ok");
                                }
                            }

                        }
                        if(openedFAQInfo) {

                            Box {
                                val popupWidth = 500.dp
                                val popupHeight = 500.dp
                                val cornerSize = 16.dp

                                Popup(alignment = Alignment.Center) {
                                    // Draw a rectangle shape with rounded corners inside the popup
                                    Box(
                                        Modifier
                                            .preferredSize(popupWidth, popupHeight)
                                            .background(Color.Blue, RoundedCornerShape(cornerSize))

                                    )
                                    Text("ok");
                                }
                            }

                        }
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
                                    openedJudgeInfo = true;
                                    //Display information to judges

                                }) {
                                Text("Judge Info")
                            }
                            Divider(color = Color.Transparent, thickness = 2.dp, modifier = Modifier.width(250.dp).align(Alignment.CenterHorizontally).padding(0.dp, 0.dp, 0.dp, 10.dp))
                            Button(modifier = Modifier.align(Alignment.CenterHorizontally),
                                onClick = {
                                    openedFAQInfo = true;
                                   //Popup interactive help
                                }) {
                                Text("Help")
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
@Composable
fun QuestionText(
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
        modifier.padding(bottom = 10.dp),
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
@InternalTextApi
@Composable
fun QuestionField(
    value: TextFieldValue,
    modifier: Modifier = Modifier,
    onValueChange: (TextFieldValue) -> Unit,
    textStyle: TextStyle = TextStyle.Default,
    onImeActionPerformed: (ImeAction) -> Unit = {},
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onTextLayout: (TextLayoutResult) -> Unit = {},
    onTextInputStarted: (SoftwareKeyboardController) -> Unit = {},
    cursorColor: Color = Color.Black,
    softWrap: Boolean = true,
    imeOptions: ImeOptions = ImeOptions.Default
) {
    CoreTextField(value,
        modifier.border(1.dp, Color.Black, RoundedCornerShape(2.dp)),
        onValueChange,
        textStyle,
        onImeActionPerformed,
        visualTransformation,
        onTextLayout,
        onTextInputStarted,
        cursorColor,
        softWrap,
        imeOptions)
}

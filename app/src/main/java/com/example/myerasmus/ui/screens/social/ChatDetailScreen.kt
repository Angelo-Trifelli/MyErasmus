package com.example.myerasmus.ui.screens.social

import androidx.compose.foundation.background
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.AttachFile
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.example.myerasmus.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import androidx.compose.ui.layout.ContentScale
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.font.FontWeight
import com.example.myerasmus.utils.profileImageRes
import androidx.compose.foundation.layout.navigationBarsPadding


val userNameColors = mapOf(
    "Luca Agnellini" to Color(0xFF1E88E5),        // Blue
    "Martina Monelli" to Color(0xFFD81B60),       // Pink
    "Giulia Casaldi" to Color(0xFF43A047),        // Green
    "Carolina Monterini" to Color(0xFFFB8C00),    // Orange
    "Lucía Fernández" to Color(0xFF6A1B9A),       // Purple
    "Oliver Bennett" to Color(0xFF3949AB),        // Indigo
    "Lukas Schneider" to Color(0xFF00897B)        // Teal
)



data class Message(
    val text: String,
    val isUser: Boolean,
    val time: String,
    val senderName: String? = null,
    val date: String,
    val isSeen: Boolean = false,
    val isSystemMessage: Boolean = false // 👈 aggiunto
)


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatDetailScreen(
    contactName: String,
    isGroup: Boolean,
    onBack: () -> Unit,
    onProfileClick: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val coroutineScope = rememberCoroutineScope()
    var message by remember { mutableStateOf("") }

    val allMessages = remember {
        when (contactName) {
            "Carolina Monterini" -> listOf(
                Message("Ciao Carolina! Eccomi, scusa il ritardo ahah", true, "9:30", date = "30/02/2025", isSeen = true),
                Message("Ciao Anna! Tranquilla ahah. Anche tu vuoi andare a Barcellona quindi?", false, "11:30", date = "30/02/2025"),
                Message("Assolutamente, è la mia città preferita\uD83D\uDE0D", true, "11:32", date = "30/02/2025", isSeen = true),
                Message("Quando pensavi di partire? Primo o secondo semestre?", false, "11:33", date = "30/02/2025"),
                Message("Io faccio domanda per il primo semestre", false, "11:33", date = "30/02/2025"),
                Message("Anch'io! Che ne dici allora se rimaniamo in contatto " +
                        "e nel caso, se dovessimo vincere la borsa, " +
                        "cerchiamo casa insieme?", true, "11:40", date = "30/02/2025", isSeen = true),
                Message("Certamente! Mi farebbe molto piacere❤️", false, "18:31", date = "30/02/2025"),
                Message("Ciao Anna, allora? Sono uscite le gradutorie. Com'è andata?", false, "15:32", date = "05/05/2025"),
                Message("Ciao Carolina, presa a Barcellona\uD83E\uDD73", true, "17:27", date = "05/05/2025", isSeen = true),
                Message("Te invece? Com'è andata?", true, "17:27", date = "05/05/2025", isSeen = true),
                Message("Presa anche io!! Si parte per Barcellona\uD83C\uDF89\uD83C\uDF89", false, "17:38", date = "05/05/2025"),
                Message("\uD83D\uDE0D\uD83D\uDE0D\uD83D\uDE0D", true, "18:01", date = "05/05/2025", isSeen = true),
                Message("Buongiorno Caro! Stai facendo il learning agreement? Come sta andando?", true, "8:31", date = "18/06/2025", isSeen = true),
                Message("Buenos Dias Anna! Si, alcuni esami messi, ora sto valutando " +
                        "quale altro esame mettere, te invece?", false, "10:52", date = "18/06/2025"),
                Message("Io anche, fatto ma ancora aspetto per inviarlo. Ma quanto è " +
                        "facile ora fare il Learning Agreement con MyErasmus?\uD83D\uDE0E", true, "11:11", date = "18/06/2025", isSeen = true),
                Message("Mio fratello che lo ha fatto parecchi anni fa mi ha detto che " +
                        "per lui fare il LA è stato un vero inferno ahah. " +
                        "Ma ti immagini cercare gli esami compatibili uno per uno sui siti dell'università?", true, "11:12", date = "18/06/2025", isSeen = true),
                Message("Mamma mia veramente ahah, amo questa applicazione. " +
                        "Ho provato a cercare io gli esami dal sito di Barcellona ma dopo un pò " +
                        "di tempo a smanettare ci ho perso la speranza\uD83E\uDD2F", false, "11:15", date = "18/06/2025"),
                Message("Mamma mia veramente\uD83D\uDE02", true, "11:17", date = "18/06/2025")
            )
            "Lucía Fernández" -> listOf(
                Message("Hola Lucia, Soy Anna, ¡un placer conocerte!\uD83D\uDE0A", true, "14:12", date = "06/05/2025"),
                Message("¿Tú también vas a Barcelona de Erasmus?", true, "14:12", date = "06/05/2025", isSeen = true),
                Message("Hola Anna, el placer es mío. Absolutamente! Y como puedo ver en su biografía también vamos el mismo período ajaj", false, "14:48", date = "06/05/2025"),
                Message("Muy bien. De hecho, quería preguntarte por si necesitabas encontrar una casa, ya que yo y otra chica italiana estamos buscando una", true, "16:01", date = "06/05/2025", isSeen = true),
                Message("Muchas gracias por tu interés pero ya encontré un hogar hace tiempo, lo siento\uD83D\uDE22", false, "17:04", date = "06/05/2025"),
                Message("Avísame si necesitas ayuda para encontrarla", false, "17:05", date = "06/05/2025"),
                Message("No te preocupes, en realidad todavía tenemos que empezar a buscarlo ajaj. fue un placer, espero conocerte en Barcelona", true, "18:45", date = "06/05/2025"),
                Message("En cuanto lleguemos nos organizaremos para ir a tomar algo juntos\uD83E\uDD42", true, "18:45", date = "06/05/2025", isSeen = true),
                Message("Absolutamente\uD83E\uDD19", false, "22:01", date = "06/05/2025")
            )
            "Barcelona Erasmus 25/26!😎✈️🇪🇸" -> listOf(
                Message(
                    text = "MyErasmus ti ha aggiunta al gruppo",
                    isUser = false,
                    time = "15:59",
                    date = "06/06/2025",
                    isSystemMessage = true
                ),
                Message("Hi to everyone, I'm Anna! Nice to meet you\uD83D\uDC4B", true, "16:00", date = "06/06/2025", isSeen = true),
                Message("Hi, my name is Lucía\uD83D\uDE0A", false, "16:06", senderName = "Lucía Fernández", date = "06/06/2025"),
                Message("Hiii, my name is Carolina, happy to be here!\uD83E\uDD2A", false, "16:45", senderName = "Carolina Monterini", date = "06/06/2025"),
                Message("Hi, nice to meet you, I'm Oliver\uD83E\uDD19", false, "18:03",senderName = "Oliver Bennett", date = "06/06/2025"),
                Message("Hello to everyone from Germany\uD83C\uDDE9\uD83C\uDDEA", false, "20:13", senderName = "Lukas Schneider", date = "06/06/2025")
            ) "Italiani a Barcellona 24/25!🇮🇹🍝🇪🇸" -> listOf(
            Message(
                text = "Sei stata accettata nel gruppo",
                isUser = false,
                time = "10:00",
                date = "28/02/2025",
                isSystemMessage = true
            ),
            Message("Benvenuta Anna!", false, "10:01", senderName = "Luca Agnellini", date = "28/02/2025"),
            Message("Benvenuta!", false, "10:03", senderName = "Martina Monelli", date = "28/02/2025"),
            Message("Grazie mille per avermi accettata! Sono entrata perché ho intenzione di fare domanda per Barcellona", true, "10:06", date = "28/02/2025", isSeen = true),
            Message("Benvenuta! Qualsiasi cosa chiedi pure! Noi stiamo facendo il nostro secondo semestre qui", false, "10:10", senderName = "Giulia Casaldi", date = "28/02/2025"),
            Message("Grazie per la disponibilità! Nel caso di qualche dubbio vi mando un messaggio", true, "10:12", date = "28/02/2025", isSeen = true),
            Message("\uD83E\uDD19", false, "11:13", senderName = "Luca Agnellini", date = "28/02/2025"),
            Message("Ciao Anna! Anche io ho intenzione di fare domanda per l'anno prossimo\uD83E\uDD29", false, "9:05", senderName = "Carolina Monterini", date = "29/02/2025"),
            Message("Scrivimi\uD83D\uDE19", false, "9:05", senderName = "Carolina Monterini", date = "29/02/2025")
        )

            else -> emptyList()
        }
    }

    val messages = remember { mutableStateListOf<Message>().apply { addAll(allMessages) } }
    val groupedMessages = messages.groupBy { it.date }

    val listState = rememberLazyListState()
    var currentDate by remember { mutableStateOf("") }
    var showFloatingDate by remember { mutableStateOf(false) }

    // 🧠 Mostra data flottante durante scroll
    LaunchedEffect(listState) {
        snapshotFlow { listState.firstVisibleItemIndex }
            .map { idx ->
                // cerca data del primo messaggio visibile
                val flatList = groupedMessages.flatMap { (date, msgs) ->
                    listOf(date) + msgs.map { it.date }
                }
                flatList.getOrNull(idx + 1) ?: ""
            }
            .distinctUntilChanged()
            .collectLatest { date ->
                currentDate = date
                showFloatingDate = true
                delay(1500)
                showFloatingDate = false
            }
    }

    Scaffold(
        topBar = {
            ChatHeader(
                contactName = contactName,
                isGroup = isGroup,
                onBack = onBack,
                onProfileClick = { onProfileClick(contactName) }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.fillMaxSize()) {

            LazyColumn(
                state = listState,
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(bottom = 60.dp)
            ) {
                groupedMessages.forEach { (date, dailyMessages) ->
                    // Divider della data
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Surface(
                                color = Color(0xFFE0E0E0),
                                shape = MaterialTheme.shapes.small
                            ) {
                                Text(
                                    text = date,
                                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }

                    itemsIndexed(dailyMessages) { index, msg ->
                        val isFirstFromSender = index == 0 ||
                                dailyMessages[index - 1].isUser != msg.isUser ||
                                dailyMessages[index - 1].senderName != msg.senderName

                        val isLastFromSender = index == dailyMessages.lastIndex ||
                                dailyMessages[index + 1].isUser != msg.isUser ||
                                dailyMessages[index + 1].senderName != msg.senderName

                        if (msg.isSystemMessage) {
                            SystemMessage(text = msg.text)
                        } else {
                            MessageBubble(
                                message = msg,
                                isGroup = isGroup,
                                showMeta = isFirstFromSender,
                                showTime = isLastFromSender
                            )
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                    }
                }
            }

            // ⏱ Data flottante in alto
            if (showFloatingDate) {
                Box(
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 8.dp)
                ) {
                    Surface(
                        color = Color(0xFFE0E0E0),
                        shape = MaterialTheme.shapes.small,
                        shadowElevation = 4.dp
                    ) {
                        Text(
                            text = currentDate,
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }

            // 💬 Input messaggio
            Row(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 8.dp)
                    .navigationBarsPadding(), // 👈 aggiunge padding automatico sotto la barra di sistema
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = { }) {
                    Icon(
                        imageVector = Icons.Default.AttachFile,
                        contentDescription = "Attach"
                    )
                }

                OutlinedTextField(
                    value = message,
                    onValueChange = { message = it },
                    placeholder = { Text("Write a message...") },
                    singleLine = true,
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Send),
                    keyboardActions = KeyboardActions(
                        onSend = {
                            if (message.isNotBlank()) {
                                messages.add(
                                    Message(
                                        text = message,
                                        isUser = true,
                                        time = "Now",
                                        date = "08/06/2025"
                                    )
                                )
                                message = ""
                                focusManager.clearFocus()
                            }
                        }
                    )
                )

                IconButton(onClick = {
                    if (message.isNotBlank()) {
                        coroutineScope.launch {
                            messages.add(
                                Message(
                                    text = message,
                                    isUser = true,
                                    time = "Now",
                                    date = "08/06/2025"
                                )
                            )
                            message = ""
                            focusManager.clearFocus()
                        }
                    }
                }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.Send,
                        contentDescription = "Send"
                    )
                }
            }
        }
    }
}

@Composable
fun MessageBubble(
    message: Message,
    isGroup: Boolean,
    showMeta: Boolean, // mostra nome+icona
    showTime: Boolean  // mostra orario+spunte
) {
    val backgroundColor = if (message.isUser) Color(0xFFDCF8C6) else Color(0xFFECECEC)
    val alignment = if (message.isUser) Arrangement.End else Arrangement.Start
    val bubbleShape = if (message.isUser) {
        RoundedCornerShape(topStart = 12.dp, topEnd = 0.dp, bottomEnd = 12.dp, bottomStart = 12.dp)
    } else {
        RoundedCornerShape(topStart = 0.dp, topEnd = 12.dp, bottomEnd = 12.dp, bottomStart = 12.dp)
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = alignment
    ) {
        Column(horizontalAlignment = if (message.isUser) Alignment.End else Alignment.Start) {

            // NOME + ICONA (solo se richiesto)
            if (isGroup && !message.isUser && message.senderName != null && showMeta) {
                val color = userNameColors[message.senderName] ?: Color(0xFF3366CC)
                val profileImage = profileImageRes(message.senderName)

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Image(
                        painter = painterResource(id = profileImage),
                        contentDescription = "${message.senderName} profile image",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(20.dp)
                            .clip(CircleShape)
                    )
                    Text(
                        text = message.senderName,
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                        color = color
                    )
                }
                Spacer(modifier = Modifier.height(4.dp))
            }

            // BOLLA DEL MESSAGGIO
            Box(
                modifier = Modifier
                    .widthIn(max = 300.dp)
                    .background(color = backgroundColor, shape = bubbleShape)
                    .padding(12.dp)
            ) {
                Text(
                    text = message.text,
                    style = MaterialTheme.typography.bodyLarge
                )
            }

            // ORARIO + SPUNTA (solo se ultimo messaggio consecutivo)
            if (showTime) {
                Spacer(modifier = Modifier.height(2.dp))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.End
                ) {
                    Text(
                        text = message.time,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color.Gray
                    )
                    if (message.isUser) {
                        Icon(
                            painter = painterResource(
                                id = if (message.isSeen) R.drawable.ic_check_double else R.drawable.ic_check_single
                            ),
                            contentDescription = "Seen status",
                            modifier = Modifier
                                .size(16.dp)
                                .padding(start = 4.dp)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatHeader(
    contactName: String,
    isGroup: Boolean,
    onBack: () -> Unit,
    onProfileClick: () -> Unit
) {
    TopAppBar(
        title = {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onProfileClick() }
            ) {
                Text(
                    text = contactName,
                    modifier = Modifier.weight(1f)
                )

                val imageRes = when {
                    !isGroup && contactName == "Carolina Monterini" -> R.drawable.carolina_profile
                    !isGroup && contactName == "Lucía Fernández" -> R.drawable.lucia_profile
                    isGroup && contactName == "Barcelona Erasmus 25/26!😎✈️🇪🇸" -> R.drawable.barcelona_group
                    isGroup && contactName == "Italiani a Barcellona 24/25!🇮🇹🍝🇪🇸" -> R.drawable.italians_group
                    else -> R.drawable.user_profile
                }

                Image(
                    painter = painterResource(id = imageRes),
                    contentDescription = "Profile image",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                )
            }
        },
        navigationIcon = {
            IconButton(onClick = onBack) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back"
                )
            }
        }
    )
}

@Composable
fun SystemMessage(text: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        contentAlignment = Alignment.Center
    ) {
        Surface(
            color = Color(0xFFE0E0E0),
            shape = RoundedCornerShape(8.dp),
            shadowElevation = 2.dp
        ) {
            Text(
                text = text,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                style = MaterialTheme.typography.labelMedium.copy(color = Color.DarkGray)
            )
        }
    }
}

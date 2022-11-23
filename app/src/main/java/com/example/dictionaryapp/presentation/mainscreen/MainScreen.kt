package com.example.dictionaryapp.presentation.mainscreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.dictionaryapp.model.wordContent.Definition
import com.example.dictionaryapp.model.wordContent.Meaning
import com.example.dictionaryapp.model.wordContent.WordMeaningItem
import com.example.dictionaryapp.model.wrapper.DataOrException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

@Composable
fun MainScreen(viewModel: MainViewModel = hiltViewModel()) {
    var srcWord by rememberSaveable {
        mutableStateOf("")
    }
    var word = ""
    var clicked by rememberSaveable {
        mutableStateOf(false)
    }
    var icClicked by rememberSaveable {
        mutableStateOf(false)
    }
    Scaffold(topBar = {
        TopAppBar(
            modifier = Modifier.padding(horizontal = 4.dp,
                vertical = 8.dp),
            backgroundColor = Color.Transparent,
            elevation = 0.dp
        ) {
            Row(
                Modifier.padding(
                    horizontal = 4.dp,
                    vertical = 0.dp
                )
            ) {
                if (!clicked) {
                    TextField(
                        modifier = Modifier.weight(1f),
                        value = srcWord,
                        shape = RoundedCornerShape(8.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent
                        ),
                        onValueChange = {
                            srcWord = it
                        },
                        trailingIcon = {
                            Icon(
                                modifier = Modifier
                                    .clickable {
                                        if (srcWord != "") {
                                            clicked = true
                                            word = srcWord
                                        }
                                    }
                                    .size(35.dp)
                                    .padding(end = 5.dp),
                                imageVector = Icons.Default.Search,
                                contentDescription = "Search"
                            )
                        },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = {
                                if (srcWord != "") {
                                    clicked = true
                                    word = srcWord.trim()
                                }
                            }
                        ),
                        maxLines = 1,
                    )
                } else {
                    Icon(
                        modifier = Modifier.clickable {
                            clicked = false
                            word = srcWord
                            srcWord = ""
                        },
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Search"
                    )
                    Row(modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center) {
                        Text(text = word)
                    }
                }
            }
        }
    }
    ) {

        if (clicked && word != "") {

            val wordData =
                produceState<DataOrException<ArrayList<WordMeaningItem>, Boolean, Exception>>(
                    initialValue = DataOrException(
                        isLoading = true
                    )
                ) {
                    value = viewModel.getWordMeaning(
                        word = word
                    )
                }.value
            if (wordData.isLoading == true) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 8.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.Top
                ) {
                    Spacer(modifier = Modifier.height(10.dp))
                    wordData.data?.forEach { wordMeaningItem: WordMeaningItem ->
                        wordMeaningItem.meanings.forEach { meaning: Meaning ->
                            Text(
                                text = "Meaning".uppercase(),
                                fontWeight = FontWeight.Bold
                            )
                            Def(definition = meaning.definitions)
                            Spacer(modifier = Modifier.height(10.dp))
                        }
                    }
                }
            }
        } else {
            Box {}
        }
    }
}

@Composable
fun Def(definition: List<Definition>) {
    definition.forEach {
        Text(
            text = "- ${it.definition}",
            modifier = Modifier.padding(vertical = 5.dp, horizontal = 3.dp),
            fontWeight = FontWeight.Medium
        )
        if (it.example.isNullOrEmpty()) {
            Box {}
        } else
            Text(text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Normal
                    )
                ) {
                    append("Example:")
                }
                withStyle(
                    style = SpanStyle(
                        fontWeight = FontWeight.Light
                    )
                ) {
                    append(it.example)
                }
            }
            )
    }
}
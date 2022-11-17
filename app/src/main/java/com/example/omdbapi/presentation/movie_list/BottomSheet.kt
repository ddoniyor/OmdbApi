package com.example.omdbapi.presentation.movie_list

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch


@SuppressLint("CoroutineCreationDuringComposition")
@ExperimentalMaterialApi
@Composable
fun SheetLayout(){
    val sheetState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
    )
    val scope = rememberCoroutineScope()

    //Our flag variable
    val showModalSheet = rememberSaveable {
        mutableStateOf(false)
    }

    ModalBottomSheetLayout(
        sheetState = sheetState,
        sheetContent = {
            BottomSheetContent()
        }) {
        showModalSheet.value = !showModalSheet.value
        scope.launch {
            sheetState.show()
        }
    }
}

@Composable
fun BottomSheetContent( ){
    Surface(
        modifier = Modifier.height(300.dp),
        color = Color(0xff7353ba)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Modal Bottom Sheet",
                fontSize = 20.sp,
                modifier = Modifier.padding(10.dp),
                color = Color.White)
            Divider(
                modifier = Modifier.padding(5.dp),
                color = Color.White)
            Text(
                text = "dsaыывыфвыфвыфвфывфывфвфвффвфвфввыфвыфвыфвыфвыфвыф",
                fontSize = 15.sp,
                fontStyle = FontStyle.Italic,
                color = Color.White,
                modifier = Modifier.padding(10.dp))
        }
    }
}


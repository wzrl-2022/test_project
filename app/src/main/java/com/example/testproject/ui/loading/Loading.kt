package com.example.testproject.ui.loading

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.testproject.R
import com.example.testproject.ui.theme.Typography

@Composable
fun LoadingContent(
    modifier: Modifier = Modifier
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(id = R.string.loading_title),
            style = Typography.titleLarge
        )
        
        Spacer(
            modifier = Modifier
                .size(size = dimensionResource(id = R.dimen.padding_big))
        )

        CircularProgressIndicator(
            modifier = Modifier.width(64.dp)
        )
    }
}
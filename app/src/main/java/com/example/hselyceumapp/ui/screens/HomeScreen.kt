package com.example.hselyceumapp.ui.screens

import android.content.Intent
import android.net.Uri
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.hselyceumapp.R
import com.example.hselyceumapp.domain.model.User
import com.example.hselyceumapp.ui.viewModels.UsersViewModel
import com.google.gson.Gson
import org.koin.androidx.compose.koinViewModel

@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: UsersViewModel = koinViewModel()
) {
    val users by viewModel.users.collectAsState()
    var clicked by rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(clicked) {
        if (clicked) {
            viewModel.fetchUsers()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .padding(top = 10.dp)
            .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Top))
    ) {
        Button(onClick = { clicked = true }) {
            Text(stringResource(R.string.get_user))
        }

        LazyColumn {
            items(users) { user ->
                UserCard(user = user) {
                    viewModel.selectUser(user)
                    navController.navigate("user_screen")
                }
            }
        }
    }
}


@Composable
fun UserCard(
    user: User,
    onClick: () -> Unit
) {
    val context = LocalContext.current

    OutlinedCard(
        shape = RoundedCornerShape(20.dp),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable { onClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(14.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(36.dp)
                        .background(Color(0xFFD1E3FF), shape = RoundedCornerShape(18.dp))
                ) {
                    Text(
                        text = user.login.first().toString(),
                        modifier = Modifier.align(Alignment.Center),
                        color = Color(0xFF3B5998),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = user.login,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = {

                    }
                ) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Favorite")
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Image(
                painter = rememberAsyncImagePainter(user.avatarUrl),
                contentDescription = "User Avatar",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .background(Color.Gray, shape = RoundedCornerShape(8.dp))
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = stringResource(R.string.github_profile),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.clickable {
                    openGitHub(context, user.htmlUrl)
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Outlined.Person, contentDescription = "User ID", tint = Color(0xFF3B5998))
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = "ID: ${user.id}", color = Color.Gray, fontSize = 14.sp)
            }

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        onClick()
                    }
                ) {
                    Text(
                        text = stringResource(R.string.open),
                        textAlign = TextAlign.Center,
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}

fun openGitHub(context: android.content.Context, link: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link))
    try {
        context.startActivity(intent)
    } catch (e: Exception) {
        Log.e("INFOG", "$link", e)
    }
}

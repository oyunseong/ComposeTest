# 안드로이드 컴포즈 예제 코드

> Jetpack Compose는 네이티브 Android UI를 빌드하기 위한 최신 도구 키트입니다.Jetpack Compose는 더 적은 수의 코드, 가역한 도구, 직관적인 Koltin API로 Android에서의 UI개발을 간소화하고 가속화합니다. 

Jetpack Compose는 구성 가능한 함수를 중심으로 빌드되었습니다. 

구성 가능한 함수를 만드려면 함수 이름에 `Composable` 어노테이션을 추가하면 됩니다🥸

미리보기 창을 추가하려면 `Preview` 어노테이션 추가하고 미리보기 창 상단의 새로고침 버튼을 클릭하면됩니다!

### 레이아웃
UI 요소는 계층적이며 다른 요소에 포함된 요소가 있습니다. Compose에서는 다른 구성 가능한 함수로부터 구성 가능한 함수를 호출하여 UI계층 구조를 빌드합니다.


#### 여러 텍스트 추가
MessageCard 함수를 통해 텍스트를 입력받고 출력합니다.
하지만 텍스트가 겹쳐서 읽을 수 없는 문제가 있습니다.
![](https://velog.velcdn.com/images/oyunseong/post/c535d7a0-5707-4731-a954-a78185c6675f/image.png)


~~~
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MessageCard(Message("Android","Jetpack Compose"))
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Text(text = msg.author)
    Text(text = msg.body)
}

@Preview
@Composable
fun PreviewMessageCard() {
    MessageCard(msg = Message("Colleague", "Hey, take a look at Jetpack Compose!!"))
}
~~~

### 열 사용
`Column` 함수를 사용하면 요소를 수직으로 정렬할 수 있습니다. 
`Row`를 사용하면 항목을 수평으로 정렬할 수 있고 `Box`를 사용하면 요소를 쌓을 수 있습니다.

![](https://velog.velcdn.com/images/oyunseong/post/76ad76d2-b5e3-495e-b79c-78490ba52c24/image.png)

#### 이미지 요소 추가
이미지 사진을 추가하여 메시지 카드를 꾸며보겠습니다. 
`Resource Manager`를 사용하여 사진 라이브러리에서 이미지를 가져오거나 직접 사진을 추가하여 사용합니다. 
디자인 구조가 잘 구성된 `Row` 컴포저블을 추가하고 그 내부에 `Image` 컴포저블을 추가합니다. 

![](https://velog.velcdn.com/images/oyunseong/post/177becf0-9781-49ce-bf02-22b2fea215e1/image.png)

~~~
@Composable
fun MessageCard(msg: Message) {
    Row {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Contact profile picture",
        )
    }
    Column {
        Text(text = msg.author)
        Text(text = msg.body)
    }
}
~~~


### 레이아웃 구성
메시지 레이아웃의 구조는 올바르지만 요소의 간격이 균등하지 않고 이미지가 너무 큽니다. 컴포저블을 장식하거나 구성하기 위해 Compose는 **수정자, modifier**를 사용합니다. 이를 통해 컴포저블의 크기, 레이아웃, 모양을 변경하거나 상위 수준의 상호작용을 추가할 수 있습니다. 

> 테마 이름은 ui.theme 하위 패키지의 Theme.kt 파일에서 찾을 수 있습니다!

![](https://velog.velcdn.com/images/oyunseong/post/348e26b5-337b-4c63-bb6b-82f5e823f996/image.png)

~~~
@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)    // 사이즈
                .clip(CircleShape) // 이미지 모양
        )

        // 이미지와 컬럼 사이의 수평공간
        Spacer(modifier = Modifier.width(8.dp))


        Column {
            Text(text = msg.author)
              // 텍스트 Column 간의 높이 간격
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = msg.body)
        }
    }
}
~~~


### Material Design 사용 
프로젝트에서 만든 머티리얼 테마 `ComposeTutorialTheme`와 `Surface`로 `MessageCard` 함수를 래핑합니다. 

![](https://velog.velcdn.com/images/oyunseong/post/03c88e06-d627-4d03-83ad-74c3f0a7cf4c/image.png)

~~~

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    MessageCard(Message("Android", "Jetpack Compose"))
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewMessageCard() {
    ComposeTestTheme() {
        Surface {
            MessageCard(msg = Message("Colleague", "Hey, take a look at Jetpack Compose!!"))
        }
    }
}

~~~

### 색상
`MaterialTheme.colors`를 사용하여 래핑된 테마의 생상으로 스타일을 지정할 수 있습니다.

제목 스타일을 지정하고 이미지에 테두리를 추가합니다. 
![](https://velog.velcdn.com/images/oyunseong/post/2dd1de49-07b5-4a58-85df-c0a12817291d/image.png)

~~~
@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)    // 사이즈
                .clip(CircleShape) // 이미지 모양
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        // 이미지와 컬럼 사이의 수평공간
        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondary
            )
            // 텍스트 Column 간의 높이 간격
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = msg.body)
        }
    }
}
~~~

### 서체
머티리얼 서체 스타일은 `MaterialTheme`에서 사용할 수 있으며, `Text`컴포저블에 추가하면 됩니다. 

![](https://velog.velcdn.com/images/oyunseong/post/da4a9307-77f3-4161-b621-faea5d463aa4/image.png)

~~~
Column {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2
            )
            // 텍스트 Column 간의 높이 간격
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = msg.body, style = MaterialTheme.typography.body2
            )
        }
~~~

### 도형
`Shape`을 사용하여 최종 터치를 추가할 수 있습니다. 먼저 `Surface` 컴포저블을 중심으로 메시지 본문 텍스트를 래핑합니다. 이렇게 하면 본문의 도형과 높이를 맞춤설정할 수 있습니다.

예쁘진 않지만 직관적이게 크기를 조절하였습니다!

![](https://velog.velcdn.com/images/oyunseong/post/dc7b0593-e021-4058-b114-cb48bc2ac64a/image.png)


~~~
        Column {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2
            )
            // 텍스트 Column 간의 높이 간격
            Spacer(modifier = Modifier.height(4.dp))
            Surface(shape = MaterialTheme.shapes.medium, elevation = 10.dp) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 10.dp),
                    style = MaterialTheme.typography.body2,
                )
            }
        }
~~~

### 어두운 테마 사용
파일에서 별도의 함수로 여러 미리보기를 만들거나 동일한 함수에 여러 어노테이션을 추가할 수 있습니다. 

새 미리보기 어노테이션을 추가하고 야간 모드를 사용 설정합니다.

![](https://velog.velcdn.com/images/oyunseong/post/b311bbc7-5121-44a3-bc9e-f2ab2ccf4149/image.png)

~~~
@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    ComposeTestTheme() {
        Surface {
            MessageCard(msg = Message("Colleague", "Hey, take a look at Jetpack Compose!!"))
        }
    }
}
~~~

각 테마의 생상 선택은 IDE로 생성된 Theme.kt 파일에 정의되어 있습니다.

## 목록 및 애니메이션
### 메시지 목록 만들기
메시지 하나는 허전해 보이니 메시지를 두 개 이상 포함하도록 대화를 변경해 보겠습니다.
`LazyColumn`과 `LazyRow`를 사용하여 여러 메시지를 표시하는 `Conversation` 함수를 만듭니다! 

메시지를 확장하여 더 길게 보여주고 콘텐츠 크기와 배경 색상 모두에 애니메이션 효과를 적용하는 기능을 추가해 보겠습니다. 이 로컬 UI 상태를 저장하려면 메시지가 확장되었는지 추적해야합니다. 이 상태 변경을 추적하려면 `remember`와 `mutableStateOf` 함수를 사용해야 합니다.

구성 가능한 함수는 `remember` 를 사용하여 메모리에 로컬 상태를 저장하고 `mutableStateOf`에 전달된 값의 변경사항을 추적할 수 있습니다. 이 상태를 사용하는 컴포저블 및 하위 요소는 값이 업데이트되면 자동으로 다시 그려집니다. 이를 **재구성** 이라고 합니다.
`remember` 및 `mutableStateOf`와 같은 Compose의 상태 API를 사용하여 상태를 변경하면 UI가 자동으로 업데이트됩니다. 


**전체 코드**
~~~
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeTestTheme {
                Conversation(msg = SampleData.conversationSample)
            }
        }
    }
}

data class Message(val author: String, val body: String)

@Composable
fun MessageCard(msg: Message) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
            painter = painterResource(id = R.drawable.ic_launcher_foreground),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                .size(40.dp)    // 사이즈
                .clip(CircleShape) // 이미지 모양
                .border(1.5.dp, MaterialTheme.colors.secondary, CircleShape)
        )

        // 이미지와 컬럼 사이의 수평공간
        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // 메시지가 확장되었는지 여부를 추적
        var isExpanded by remember {
            mutableStateOf(false)
        }

        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = msg.author,
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2
            )
            // 텍스트 Column 간의 높이 간격
            Spacer(modifier = Modifier.height(4.dp))
            Surface(shape = MaterialTheme.shapes.medium, elevation = 1.dp) {
                Text(
                    text = msg.body,
                    modifier = Modifier.padding(all = 4.dp),
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.body2,
                    )
            }
        }
    }
}

@Preview(name = "Light Mode")
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewMessageCard() {
    ComposeTestTheme() {
        Surface {
            MessageCard(msg = Message("Colleague", "Hey, take a look at Jetpack Compose!!"))
        }
    }
}

@Composable
fun Conversation(msg: List<Message>) {
    LazyColumn {
        items(msg) { msg ->
            MessageCard(msg = msg)
        }
    }
}

@Preview
@Composable
fun PreviewConversation() {
    ComposeTestTheme {
        Conversation(SampleData.conversationSample)
    }
}

~~~

**미리보기**
![](https://velog.velcdn.com/images/oyunseong/post/2cf8eb07-5c73-44c6-bc52-9693a50569cd/image.png)

**실제 실행**
![](https://velog.velcdn.com/images/oyunseong/post/82caf412-35bd-4774-a146-7dabfd66ddda/image.png)


참고 자료 : https://developer.android.com/jetpack/compose?hl=ko

<img width="329" alt="image" src="https://user-images.githubusercontent.com/42116216/190127645-4cccef62-37fb-43e9-9069-af5fd578008c.png">
<img width="274" alt="image" src="https://user-images.githubusercontent.com/42116216/190127768-bdfcd40a-94a8-40a5-9947-518d96b6eed3.png">



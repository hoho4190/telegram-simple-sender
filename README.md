[![Gradle Build](https://github.com/hoho4190/telegram-simple-sender/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/hoho4190/telegram-simple-sender/actions/workflows/gradle-build.yml)
[![Gradle Package](https://github.com/hoho4190/telegram-simple-sender/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/hoho4190/telegram-simple-sender/actions/workflows/gradle-publish.yml)
[![](https://jitpack.io/v/hoho4190/telegram-simple-sender.svg)](https://jitpack.io/#hoho4190/telegram-simple-sender)

# Telegram simple sender
Telegram simple message sender using [Retrofit](https://square.github.io/retrofit/)

## [Dependency](https://jitpack.io/#hoho4190/telegram-simple-sender)
- Step 1. Add the JitPack repository to your build file
  ```kotlin
  allprojects {
      repositories {
          // ...
          maven {
              url = uri("https://jitpack.io")
          }
      }
  }
  ```
- Step 2. Add the dependency
  ```kotlin
  dependencies {
      implementation("com.github.hoho4190:telegram-simple-sender:Tag")
  }
  ```

## Usage
```kotlin
// 1. Create TelegramInfo instance.
val telegramInfo = TelegramInfo(TOKEN)
val telegramInfo = TelegramInfo(TOKEN, CHAT_ID)
val telegramInfo = TelegramInfo(TOKEN, CHAT_IDS)


// 2. Set telegram info in the XXXSender.
// telegramSender
val telegramSender = TelegramSender(telegramInfo)

// TelegramSimpleSender
TelegramSimpleSender.setup(telegramInfo)


// 3. Call the message sending method.
// telegramSender
val call = telegramSender.sendMessage(message)

// TelegramSimpleSender
val call = TelegramSimpleSender.sendMessage(message)


// 4. Synchronous call or Asynchronous call
// Synchronous call
val response = call.execute()
// Asynchronous call
call.enqueue(object : Callback<SendResponse> { ... }


// 5. Get a response.
val sendResponse = SendResponse.from(response)
```

- message sending methods
  - `sendMessage(message)`
  - `sendMessage(message, chatId)`
  - `sendAllMessage(message)`
  - `sendAllMessage(message, chatIds)`
  
### Synchronous call
- Kotlin
    ```kotlin
    val call = telegramSender.sendMessage(message)
    val response = call.execute()
    
    val sendResponse = SendResponse.from(response)
    ```

- Java
    ```java
    Call<SendResponse> call = telegramSender.sendMessage(message);
    Response<SendResponse> response = call.execute();
    
    SendResponse sendResponse = SendResponse.from(response);
    ```

### Asynchronous call
- Kotlin
    ```kotlin
    val call = telegramSender.sendMessage(message)
    call.enqueue(object : Callback<SendResponse> {
    
        override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
            val sendResponse = SendResponse.from(response)
        }
    
        override fun onFailure(call: Call<SendResponse>, t: Throwable) {
            t.printStackTrace()
        }
    })
    ```

- Java
    ```java
    Call<SendResponse> call = telegramSender.sendMessage(message);
    call.enqueue(new Callback<>() {
        
        @Override
        public void onResponse(Call<SendResponse> call, Response<SendResponse> response) {
            SendResponse sendResponse = SendResponse.from(response);
        }
    
        @Override
        public void onFailure(Call<SendResponse> call, Throwable t) {
            t.printStackTrace();
        }
    });
    ```

## References
- [Retrofit](https://square.github.io/retrofit/)

[![Gradle Build](https://github.com/hoho4190/telegram-simple-sender/actions/workflows/gradle-build.yml/badge.svg)](https://github.com/hoho4190/telegram-simple-sender/actions/workflows/gradle-build.yml)
[![Gradle Package](https://github.com/hoho4190/telegram-simple-sender/actions/workflows/gradle-publish.yml/badge.svg)](https://github.com/hoho4190/telegram-simple-sender/actions/workflows/gradle-publish.yml)
[![](https://jitpack.io/v/hoho4190/telegram-simple-sender.svg)](https://jitpack.io/#hoho4190/telegram-simple-sender)

# Telegram simple sender
Telegram simple message sender using [Retrofit](https://square.github.io/retrofit/)

## [Download](https://jitpack.io/#hoho4190/telegram-simple-sender)
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
1. Create TelegramInfo instance.
    ```kotlin
    val telegramInfo = TelegramInfo(TOKEN)
    val telegramInfo = TelegramInfo(TOKEN, CHAT_ID)
    val telegramInfo = TelegramInfo(TOKEN, CHAT_IDS)
    ```

2. Set telegram info in the XXXSender.
    ```kotlin
    // telegramSender
    val telegramSender = TelegramSender(telegramInfo)
    
    // TelegramSimpleSender
    TelegramSimpleSender.setup(telegramInfo)
    ```

3. Call the message sending method.
    ```kotlin
    // telegramSender
    val call = telegramSender.sendMessage(message)
    
    // TelegramSimpleSender
    val call = TelegramSimpleSender.sendMessage(message)
    
    // message sending methods
    XXXSender.sendMessage(message)
    XXXSender.sendMessage(message, chatId)
    XXXSender.sendAllMessage(message)
    XXXSender.sendAllMessage(message, chatIds)
    ```

### Synchronous call
- Kotlin
    ```kotlin
    val call = telegramSender.sendMessage(message)
    val response = call.execute()
    
    if (response.isSuccessful) {
        val data = response.body()
    } else {
        val errorBody = response.errorBody()
    }
    ```

- Java
    ```java
    Call<SendResponse> call = telegramSender.sendMessage(message);
    Response<SendResponse> response = call.execute();
    
    if (response.isSuccessful()) {
        SendResponse data = response.body();
    } else {
        ResponseBody errorBody = response.errorBody();
    }
    ```

### Asynchronous call
- Kotlin
    ```kotlin
    val call = telegramSender.sendMessage(message)
    call.enqueue(object : Callback<SendResponse> {
    
        override fun onResponse(call: Call<SendResponse>, response: Response<SendResponse>) {
            if (response.isSuccessful) {
                val data = response.body()
            } else {
                val errorBody = response.errorBody()
            }
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
            if (response.isSuccessful()) {
                SendResponse data = response.body();
            } else {
                ResponseBody errorBody = response.errorBody();
            }
        }
    
        @Override
        public void onFailure(Call<SendResponse> call, Throwable t) {
            t.printStackTrace();
        }
    });
    ```

## References
- [Retrofit](https://square.github.io/retrofit/)

package com.hoho.telsimsender;

import com.hoho.telsimsender.dto.SendResponse;
import com.hoho.telsimsender.dto.TelegramInfo;
import com.hoho.telsimsender.util.TestUtil;
import org.junit.jupiter.api.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Disabled
@DisplayName("Java E2E Test - TelegramSender")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TelegramSenderJavaTest {

    private TelegramInfo telegramInfo;

    @BeforeAll
    void beforeAll() {
        telegramInfo = TestUtil.getTelegramInfo();
    }

    @Disabled
    @Test
    @DisplayName("sendMessage Test")
    public void sendMessageTest() throws IOException {
        // Given
        TelegramSender telegramSender = new TelegramSender(telegramInfo);
        String message = "Java TelegramSender sendMessage test";

        // When
        Call<SendResponse> call = telegramSender.sendMessage(message);
        Response<SendResponse> response = call.execute();

        // Then
        System.out.println("url: " + response.raw().request().url());
        if (response.isSuccessful()) {
            System.out.println(response.body());
        } else {
            System.out.println(response.errorBody());
        }
        Assertions.assertTrue(response.isSuccessful());
    }

    @Disabled
    @Test
    @DisplayName("sendMessage Test - async")
    public void sendMessageTest_async() throws InterruptedException {
        // Given
        TelegramSender telegramSender = new TelegramSender(telegramInfo);
        String message = "Java TelegramSender sendMessage test - async";

        // When
        Call<SendResponse> call = telegramSender.sendMessage(message);
        call.enqueue(new Callback<SendResponse>() {

            // Then
            @Override
            public void onResponse(Call<SendResponse> call, Response<SendResponse> response) {
                System.out.println("url: " + response.raw().request().url());
                if (response.isSuccessful()) {
                    System.out.println(response.body());
                } else {
                    System.out.println(response.errorBody());
                }
                Assertions.assertTrue(response.isSuccessful());
            }

            @Override
            public void onFailure(Call<SendResponse> call, Throwable t) {
                System.out.println(t.getMessage());
            }
        });

        Thread.sleep(1000L);
    }

    @Disabled
    @Test
    @DisplayName("sendAllMessage Test")
    public void sendAllMessageTest() {
        // Given
        TelegramSender telegramSender = new TelegramSender(telegramInfo);
        String message = "Java TelegramSender sendAllMessage test";

        // When
        List<Call<SendResponse>> callList = telegramSender.sendAllMessage(message);
        List<Response<SendResponse>> responseList = callList.stream().map(call -> {
            try {
                return call.execute();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());

        // Then
        Assertions.assertAll(
                responseList.stream().map(response -> {
                    System.out.println("url: " + response.raw().request().url());
                    if (response.isSuccessful()) {
                        System.out.println(response.body());
                    } else {
                        System.out.println(response.errorBody());
                    }
                    return () -> Assertions.assertTrue(response.isSuccessful());
                })
        );
    }
}
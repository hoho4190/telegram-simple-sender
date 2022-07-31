package com.hoho.telsimsender;

import com.hoho.telsimsender.dto.SendResponse;
import com.hoho.telsimsender.dto.TelegramInfo;
import com.hoho.telsimsender.util.TestUtil;
import org.junit.jupiter.api.*;
import retrofit2.Call;
import retrofit2.Response;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Disabled
@DisplayName("Java E2E Test - TelegramSimpleSender")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class TelegramSimpleSenderJavaTest {

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
        TelegramSimpleSender.setup(telegramInfo);
        String message = "Java TelegramSimpleSender sendMessage test";

        // When
        Call<SendResponse> call = TelegramSimpleSender.sendMessage(message);
        Response<SendResponse> response = call.execute();

        // Then
        System.out.println("url: " + response.raw().request().url());
        if (response.isSuccessful()) {
            SendResponse result = response.body();
            System.out.println(result);
        } else {
            System.out.println(response.errorBody());
        }
        Assertions.assertTrue(response.isSuccessful());
    }

    @Disabled
    @Test
    @DisplayName("sendAllMessage Test")
    public void sendAllMessageTest() {
        // Given
        TelegramSimpleSender.setup(telegramInfo);
        String message = "Java TelegramSimpleSender sendAllMessage test";

        // When
        List<Call<SendResponse>> callList = TelegramSimpleSender.sendAllMessage(message);
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
                        SendResponse result = response.body();
                        System.out.println(result);
                    } else {
                        System.out.println(response.errorBody());
                    }
                    return () -> Assertions.assertTrue(response.isSuccessful());
                })
        );
    }
}

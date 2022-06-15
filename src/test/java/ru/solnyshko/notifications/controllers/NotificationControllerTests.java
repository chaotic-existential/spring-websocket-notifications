package ru.solnyshko.notifications.controllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.solnyshko.notifications.controller.NotificationController;
import ru.solnyshko.notifications.factory.TestNotificationFactory;
import ru.solnyshko.notifications.payload.dto.NotificationDTO;
import ru.solnyshko.notifications.payload.request.NotificationCreateRequest;
import ru.solnyshko.notifications.payload.request.NotificationUpdateRequest;
import ru.solnyshko.notifications.services.NotificationService;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(NotificationController.class)
class NotificationControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificationService mockService;

    private TestNotificationFactory testNotificationFactory;

    @BeforeEach
    void setUp() {
        testNotificationFactory = new TestNotificationFactory();
    }

    void testGetAllRequest(ResultMatcher... results) throws Exception {

        final List<NotificationDTO> dtoList = List.of(
                testNotificationFactory.getNotificationDTO(0L),
                testNotificationFactory.getNotificationDTO(1L),
                testNotificationFactory.getNotificationDTO(2L));

        when(mockService.getAll()).thenReturn(dtoList);

        RequestBuilder request = MockMvcRequestBuilders.get("/api/notifications");
        mockMvc.perform(request).andExpectAll(results);
    }

    void testCreateRequest(String jsonCreateRequest, ResultMatcher... results) throws Exception {
        final NotificationDTO dto = testNotificationFactory.getNotificationDTO(0L);
        when(mockService.create(any(NotificationCreateRequest.class))).thenReturn(dto);

        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonCreateRequest);

        mockMvc.perform(request).andExpectAll(results);
    }

    void testUpdateRequest(String jsonUpdateRequest, ResultMatcher... results) throws Exception {
        final NotificationDTO dto = testNotificationFactory.getNotificationDTO(0L);
        when(mockService.update(any(Long.class), any(NotificationUpdateRequest.class))).thenReturn(dto);

        RequestBuilder request = MockMvcRequestBuilders
                .put("/api/notifications/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonUpdateRequest);

        mockMvc.perform(request).andExpectAll(results);
    }

    void testDeleteRequest(ResultMatcher result) throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .delete("/api/notifications/1");

        mockMvc.perform(request).andExpect(result);
    }

    void testSendRequest(ResultMatcher result) throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/notifications/1/send");

        mockMvc.perform(request).andExpect(result);
    }

    void testScheduleRequest(ResultMatcher result) throws Exception {
        RequestBuilder request = MockMvcRequestBuilders
                .post("/api/notifications/1/schedule"
                        +"?scheduledTime=2022-06-04T23:06:50");

        mockMvc.perform(request).andExpect(result);
    }

    @Test
    void testGetAll() throws Exception {
        testGetAllRequest(status().isOk(),
                jsonPath("$[0].id").value(0),
                jsonPath("$[1].id").value(1),
                jsonPath("$[2].id").value(2),
                jsonPath("$[0].type").value("SUCCESS"),
                jsonPath("$[0].title").value("Test title"),
                jsonPath("$[0].content").value("Test content")
        );
    }

    @Test
    void testCreate() throws Exception {
        testCreateRequest("""
                {
                  "type": "SUCCESS",
                  "title": "Notification title",
                  "content": "Notification content"
                }
                """, status().isOk(),
                jsonPath("$.id").value(0),
                jsonPath("$.type").value("SUCCESS"),
                jsonPath("$.title").value("Test title"),
                jsonPath("$.content").value("Test content"));
    }

    @Test
    void testCreateWithTitleTooLong() throws Exception {
        testCreateRequest("""
                {
                  "type": "SUCCESS",
                  "title": "Notification title with over 50 characters which is too long",
                  "content": "Notification content"
                }
                """, status().isBadRequest());
    }

    @Test
    void testCreateWithNullProperty() throws Exception {
        testCreateRequest("""
                {
                  "title": "Notification title",
                  "content": "Notification content"
                }
                """, status().isBadRequest());
    }

    @Test
    void testCreateWithBlankProperty() throws Exception {
        testCreateRequest("""
                {
                  "type": "SUCCESS",
                  "title": "Notification title",
                  "content": ""
                }
                """, status().isBadRequest());
    }

    @Test
    void testUpdate() throws Exception {
        testUpdateRequest("""
                {
                  "type": "SUCCESS",
                  "title": "Updated notification title",
                  "content": "Updated notification content"
                }
                """, status().isOk(),
                jsonPath("$.id").value(0),
                jsonPath("$.type").value("SUCCESS"),
                jsonPath("$.title").value("Test title"),
                jsonPath("$.content").value("Test content"));
    }

    @Test
    void testUpdateWithNullProperty() throws Exception {
        testUpdateRequest("""
                {
                  "content": "Updated notification content"
                }
                """, status().isBadRequest());
    }

    @Test
    void testUpdateWithNBlankProperty() throws Exception {
        testUpdateRequest("""
                {
                  "title": "Notification title",
                  "content": ""
                }
                """, status().isBadRequest());
    }

    @Test
    void testDelete() throws Exception {
        testDeleteRequest(status().isOk());
    }

    @Test
    void testSend() throws Exception {
        testSendRequest(status().isOk());
    }

    @Test
    void testSchedule() throws Exception {
        testScheduleRequest(status().isOk());
    }
}
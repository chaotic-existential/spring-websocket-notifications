package ru.solnyshko.notifications.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.scheduling.TaskScheduler;
import ru.solnyshko.notifications.domain.entity.Notification;
import ru.solnyshko.notifications.domain.mapper.NotificationMapper;
import ru.solnyshko.notifications.domain.repository.NotificationRepository;
import ru.solnyshko.notifications.factory.TestNotificationFactory;
import ru.solnyshko.notifications.payload.dto.NotificationDTO;
import ru.solnyshko.notifications.payload.request.NotificationCreateRequest;
import ru.solnyshko.notifications.payload.request.NotificationUpdateRequest;
import ru.solnyshko.notifications.services.impl.NotificationServiceImpl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class NotificationServiceTests {

    @Mock private WebSocketService mockWebSocketService;
    @Mock private TaskScheduler mockTaskScheduler;

    @Mock private NotificationRepository mockRepository;
    @Mock private NotificationMapper mockMapper;

    @InjectMocks
    private NotificationServiceImpl notificationServiceImpl;
    private TestNotificationFactory testNotificationFactory;

    @BeforeEach
    void setUp() {
        testNotificationFactory = new TestNotificationFactory();
    }

    @Test
    void testGetAll() {

        final List<Long> ids = List.of(0L, 1L, 2L);

        final List<NotificationDTO> dtoList = new ArrayList<>();
        final List<Notification> entityList = new ArrayList<>();

        for (int i = 0; i < ids.size(); i++) {
            dtoList.add(testNotificationFactory.getNotificationDTO(ids.get(i)));
            entityList.add(testNotificationFactory.getNotification(ids.get(i)));
            when(mockMapper.toDto(eq(entityList.get(i)))).thenReturn(dtoList.get(i));
        }

        when(mockRepository.findAll()).thenReturn(entityList);

        final List<NotificationDTO> result = notificationServiceImpl.getAll();

        assertEquals(dtoList, result);
    }

    @Test
    void testGetById() {

        final Long id = 0L;

        final Notification entity = testNotificationFactory.getNotification(id);
        final NotificationDTO dto = testNotificationFactory.getNotificationDTO(id);

        final Optional<Notification> optional = Optional.of(entity);

        when(mockMapper.toDto(any(Notification.class))).thenReturn(dto);
        when(mockRepository.findById(id)).thenReturn(optional);

        final NotificationDTO result = notificationServiceImpl.getById(id);

        assertEquals(dto, result);

        verify(mockRepository, times(2)).findById(eq(id));
    }

    @Test
    void testCreate() {

        final Long id = 0L;

        final Notification entity = testNotificationFactory.getNotification(id);
        final NotificationDTO dto = testNotificationFactory.getNotificationDTO(id);

        final NotificationCreateRequest createRequest =
                testNotificationFactory.getNotificationCreateRequest();

        when(mockMapper.toDto(any(Notification.class))).thenReturn(dto);
        when(mockRepository.save(any(Notification.class))).thenReturn(entity);
        when(mockMapper.toEntity(any(NotificationCreateRequest.class))).thenReturn(entity);

        final NotificationDTO result = notificationServiceImpl.create(createRequest);

        assertEquals(dto, result);

        verify(mockRepository).save(eq(entity));
    }

    @Test
    void testUpdate() {

        final Long id = 0L;

        final Notification entity = testNotificationFactory.getNotification(id);
        final NotificationDTO dto = testNotificationFactory.getNotificationDTO(id);

        final NotificationUpdateRequest updateRequest =
                testNotificationFactory.getNotificationUpdateRequest();

        final Optional<Notification> optional = Optional.of(entity);

        when(mockRepository.findById(id)).thenReturn(optional);
        when(mockMapper.toDto(any(Notification.class))).thenReturn(dto);
        when(mockRepository.save(any(Notification.class))).thenReturn(entity);

        final NotificationDTO result = notificationServiceImpl.update(id, updateRequest);

        assertEquals(dto, result);

        verify(mockRepository, times(2)).findById(eq(id));
        verify(mockRepository).save(eq(entity));
    }

    @Test
    void testDelete() {

        final Long id = 0L;

        final Notification entity = testNotificationFactory.getNotification(id);

        final Optional<Notification> optional = Optional.of(entity);

        when(mockRepository.findById(id)).thenReturn(optional);

        notificationServiceImpl.delete(id);

        verify(mockRepository).findById(eq(id));
        verify(mockRepository).deleteById(id);
    }

    @Test
    void testSend() {

        final Long id = 0L;

        final Notification entity = testNotificationFactory.getNotification(id);
        final NotificationDTO dto = testNotificationFactory.getNotificationDTO(id);

        final Notification entitySpy = Mockito.spy(entity);

        final Optional<Notification> optional = Optional.of(entitySpy);

        when(mockMapper.toDto(any(Notification.class))).thenReturn(dto);
        when(mockRepository.findById(id)).thenReturn(optional);

        notificationServiceImpl.send(id);

        verify(mockRepository, times(2)).findById(eq(id));
        verify(mockWebSocketService).sendNotification(dto);

        verify(entitySpy).setLastSentAt(any(LocalDateTime.class));
        verify(mockRepository).save(eq(entitySpy));
    }

    @Test
    void testSchedule() {

        final Long id = 0L;

        final Notification entity = testNotificationFactory.getNotification(id);

        final Optional<Notification> optional = Optional.of(entity);

        when(mockRepository.findById(id)).thenReturn(optional);

        notificationServiceImpl.schedule(id, LocalDateTime.now().plusSeconds(1));

        verify(mockTaskScheduler).schedule(any(Runnable.class), any(Date.class));
    }
}

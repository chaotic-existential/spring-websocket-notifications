let stompClient = null;

$(document).ready(function() {
    connect();
    $("#send").click(function() {
        sendNotificationFromDB();
    });
    $("#schedule").click(function() {
        scheduleNotificationFromDB();
    });
});

function connect() {
    const socket = new SockJS('/notification-system');
    stompClient = Stomp.over(socket);
    stompClient.heartbeat.outgoing = 2000;
    stompClient.heartbeat.incoming = 2000;
    stompClient.connect({}, function (frame) {

        console.log('Connected: ' + frame);

        stompClient.subscribe('/topic/notifications', function (notification) {
            showNotification(JSON.parse(notification.body));
        });
    });
}

const options = {
    year: 'numeric',
    month: 'long',
    day: 'numeric',
    weekday: 'long',
    timezone: 'UTC',
    hour: 'numeric',
    minute: 'numeric',
    second: 'numeric'
};

function showNotification(notification) {

    let icon = "💤";

    switch (notification.type) {
        case 'SUCCESS':
            icon = "🎉"
            break
        case 'WARNING':
            icon = "⚠"
            break
        case 'FAIL':
            icon = "💀"
            break
    }

    $("#notifications").append(
        "<tr><td>"
        + new Date().toLocaleString("eng", options) + ": "
        + icon + " "
        + notification.title + ": "
        + notification.content
        + "</td></tr>");
}

function sendNotificationFromDB() {
    stompClient.send("/app/" + $("#send-id").val() + "/send", {});
}

function scheduleNotificationFromDB() {
    stompClient.send("/app/" + $("#schedule-id").val() + "/schedule", {}, $("#datetime").val());
}

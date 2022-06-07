### Инструкция по развертыванию приложения

- В терминале из корневой папки проекта запустить контейнер с бд 
➡ ```docker-compose --file docker-compose.setup.yaml up -d```
- Далее можно запустить приложение локально, либо вынести его в контейнер (см.далее)


- Для того, чтобы вынести приложение в контейнер, нужно сбилдить .jar при помощи gradle.
При этом в момент билда должен быть поднят контейнер с бд, чтобы не возникло проблемы с таской тестирования
- После этого нужно свернуть setup контейнер
➡ ```docker-compose --file docker-compose.setup.yaml down```
- Далее, соответственно, развернуть контейнер с обоими сервисами
➡ ```docker-compose --file docker-compose.full.yaml up -d```
- ⚠ Если при первой попытке развернуть оба сервиса не был создан .jar, необходимо удалить созданный образ spring-app, 
так как при последующих попытках собрать сервис будет пуллиться первый созданный образ, где этого файла попросту нет


- При необходимости, можно загрузить в бд дамп с тестовыми записями в notifications
➡ ```docker exec -i mysqldb mysql -uroot -p52*w2HK < dump.sql```
- ⚠ PowerShell ругается на ```<```, команду нужно запускать из cmd 


### Примечания к реализации задачи
- Вебсокеты реализованы через STOMP брокер сообщений
- Для проверки подключения клиентов используется heartbeat

### Примечания к реализации API
- Получение, создание, обновление и удаление уведомлений в бд реализовано только для REST API
- Отправка и отложенная отправка уведомлений реализована также для подключения по вебсокету
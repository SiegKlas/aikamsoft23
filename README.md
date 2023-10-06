Тестовые файлы и дамп бд находятся https://github.com/SiegKlas/aikamsoft23/tree/master/test_files
Инструкция для БД:
1. docker pull postgres
2. docker run --name my-postgres-container -e POSTGRES_PASSWORD=mysecretpassword -p 5432:5432 -d postgres
3. docker cp "путь до"\dump.sql "id контейнера":/tmp/dump.sql
4. psql -h localhost -U postgres -d postgres < /tmp/dump.sql
   Опционально убедиться, что все сработало
5. psql -h localhost -U postgres -d postgres
6. \dt
Инструкция по сборке и запуску:
1. В файле application.yml настроить конфигурацию подключения к БД
2. В файле pom.xml изменить или оставить версию java на 17 (сейчас 21)
3. Собрать jar:
   a. Через Intellij Idea: Справа Maven -> aikamsoft23 -> Lifecycle -> clean -> install
   b. Через терминал (при наличии maven): mvn clean package
4. Запустить jar файл, передав аргументом 1. search/stat 2. Путь до input.json 3. Путь до output.json (можно найти в тестовых файлах)

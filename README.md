# Java_New_Chat
Приложение - чат, для обмена текстовыми сообщениями.

Вход на главный экран приложение выполняется через регистрацию и авторизацию.
Пользователь пройдя авторизацию попадает на главный экран, где видит списки
пользователей и может осуществлять переписку с ними. Иконки пользователей
генерируются автоматически на основе логина.

Пользователи по умолчанию для тестирования приложения:

1. логин: JaJoba303, пароль: 200,
2. логин: malk, пароль: 300,
3. логин: steve128, пароль: 008

Запуск приложения:

1. git clone https://github.com/Kashevar128/Java_New_Chat.git
2. cd Java_New_Chat
3. mvn clean package
4. java -jar server/target/server-1.0-SNAPSHOT-jar-with-dependencies.jar
5. java -jar client/target/client-1.0-SNAPSHOT-jar-with-dependencies.jar   

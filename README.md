# Weather Viewer

# Overview
Данное веб-приложение предназначено для просмотра текущей погоды. 
Пользователь может авторизоваться и добавить в коллекцию одну или несколько локаций,
после чего на главной странице приложения отображается 
список локаций с их текущей погодой.

При авторизации пользователя бэкенд приложение создаёт сессию с идентификатором,
и устанавливает этот идентификатор в cookies HTTP ответа, которым приложение отвечает
на POST запрос формы авторизации. Сессия содержит в себе ID авторизовавшегося юзера.
При каждом запросе пользователя анализирутся ID сессии, переданное в cookies, и
проверяется на наличие сессии в БД. Если сессия существует, то страницы приложения рендерятся 
для пользователя указанного в данной сессии.

Поиск прогнозов погоды и локаций осуществляется с помощью внешнего API https://openweathermap.org/api  
Проект выполнен в соответствии ТЗ https://zhukovsd.github.io/java-backend-learning-course/Projects/WeatherViewer/

# Technologies / tools used:
- Hibernate
- Java Servlets
- PostgreSQL
- Maven
- Tomcat 10
- HTML
- Thymeleaf
- Bootstrap
- JUnit 5
- Mockito
- Lombok
- Jackson
- SLF4J-log4j2

# Installation
1. Указать в переменной среды:
   + KEY_API_WEATHER - ключ для внешнего API поиска погоды.
   + JDBC_USER - имя пользователя для доступа к БД.
   + JDBC_PASSWORD - пароль для доступа к БД.
   + JDBC_URL - адрес подключения к БД (jdbc:postgresql://localhost:5432/weather). 
2. Собрать c помощью Maven war артефакт приложения.
3. Развернуть war артефакт в Tomcat.

# Usage
### 1. Главная страница.
Адрес - /home.

![image](https://github.com/Nikitavj/WeatherForecast/assets/134765675/c1d0cad1-0a53-4e95-bd07-2d0dc8281072)

### 2. Страница поиска локаций.
Адрес - /locations.  
GET параметр name содержит название запрашиваемой локации.

![image](https://github.com/Nikitavj/WeatherForecast/assets/134765675/10595404-4f92-4f10-a88b-65ce36539e7b)

+ Добавление локации.  
  Адрес - /locations.  
  POST параметр name содержит название, latitude и longitude содержат координаты локации.

+ Удаление локации.   
  Адрес - /locations.  
  POST параметр id содержит порядковый номер локации юзера,  
    _method содержит значение DELETE.

### 3. Страница прогноза погоды для локации.
  Адрес - /forecast.     
  GET параметр id содержит порядковый номер локации юзера.

![image](https://github.com/Nikitavj/WeatherForecast/assets/134765675/b99eec27-aeef-436f-88e0-950c06e85315)

### 4. Страница авторизации.
  Адрес - /logup.  
  POST параметр user_name сожержит email, password и repeat_password содержат пароли.

![image](https://github.com/Nikitavj/WeatherForecast/assets/134765675/09a20c6f-ea05-4d1e-ab0c-44784eae8ca8)

### 5. Страница входа.
  Адрес - /login.  
  POST параметр user_name сожержит email, password содержит пароль.

![image](https://github.com/Nikitavj/WeatherForecast/assets/134765675/9a372bf1-f5b1-4bf8-82d3-0623b8b71011)

### 6. Страница выхода.
  Адрес - /logout.


# Сервис - Кинотеатр
[![Java CI](https://github.com/PerpetuumEbner/job4j_cinema/actions/workflows/maven.yml/badge.svg)](https://github.com/PerpetuumEbner/job4j_cinema/actions/workflows/maven.yml)
## Общее описание:

Сайт по покупке билетов в кинотеатре.

***

## Реализовано:
* Регистрация/Вход
* Вывод фильмов
* Покупка/выбор билетов
* Вывод купленных билетов

***

## Технологии:
[![java](https://img.shields.io/badge/java-17-red)](https://www.java.com/)
[![maven](https://img.shields.io/badge/apache--maven-3.8.3-blue)](https://maven.apache.org/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.0.4-brightgreen)](https://spring.io/projects/spring-boot)
[![PostgresSQL](https://img.shields.io/badge/PostgreSQL-15-blue)](https://www.postgresql.org/)
[![Thymeleaf](https://img.shields.io/badge/Thymeleaf-3.1.1-green)](https://www.thymeleaf.org/)
[![Lombok](https://img.shields.io/badge/Lombok-1.18.26-red)](https://projectlombok.org/)

***

## Запуск проекта:
* maven install
* java -jar target/job4j_cinema-1.0.jar
* Перейти по ссылке http://localhost:8080/films

***

## Структура сайта:

### Главная страница
Список всех фильмов, которые показывают в кинотеатре. Не зарегистрированные пользователи так же могут просматривать.

![1](img/1.png)

### Страница фильма.

![2](img/2.png)

### Страница выбора места в зале.
Могут выбирать только зарегистрированные пользователи.

![3](img/3.png)

### Информация о приобретаемом билете
Страница подробной информации о билете с возможностью его купить. Одновременно могут выбрать этот сеанс несколько человек, но продажа пройдет только у первого.

![4](img/4.png)

### Страница купленных билетов
После покупки билетов перенаправляет в список купленных билетов купивший зарегистрированный пользователь.

![5](img/5.png)

### Ошибка покупки билета.
Если билет ранее был уже куплен, то об этом будет уведомление.

![6](img/6.png)

### Страница регистрации.
Проверка валидации ввода.

![7](img/7.png)

### Ошибка регистрации.
Если пользователь уже есть с такой почтой, то об этом будет уведомление.

![8](img/8.png)

### Страница авторизации.
Проверка валидации ввода.

![9](img/9.png)

### Ошибка авторизации.
Если введены не верные учётные данные, то об этом будет уведомление.

![10](img/10.png)

### Добавление фильма.
Администратор сайта может добавлять фильмы в базу данных через форму. 

![11](img/11.png)




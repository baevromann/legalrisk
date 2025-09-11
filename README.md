# LegalRiskProcessor (Spring Boot)

Реализация по UML и задачам 1–2 из `легенда6.docx`:

- **Задача 1. Event Sourcing архитектура** — LegalRiskProcessor не вызывает внешние API напрямую. 
  Он создаёт события-команды (`RosreestrDataRequested`, `NBKIDataRequested`, `CheckPersonVerificationRequested`) и делает **append-only** в `EventStore`.
  Исполнители (интеграционные сервисы) потребляют CDC/Kafka и выполняют реальные HTTP/SOAP вызовы (в проекте — стабы).
- **Задача 2. Анализ гаражей** — реализован `GaragePropertyAnalyzer` с аналитикой земельных прав и ГСК, скорингом и генерацией пунктов договора.
  ML-клиенты для gRPC оформлены как интерфейсы со стабами для автономной сборки.

## Запуск

```bash
mvn spring-boot:run
# или
mvn -q -DskipTests package && java -jar target/legalriskprocessor-1.0.0.jar
```

## Примеры запросов

- Анализ по кадастровому номеру:
```
POST http://localhost:8080/legal-risk/analyze?cadastralNumber=77:01:0004012:1234
```
- Запрос дорогостоящей проверки CheckPerson:
```
POST http://localhost:8080/legal-risk/check-person?ownerId=p1&justification=High+risk
```

Проект самодостаточен, компилируется без внешних сервисов и может служить основой для подключения Cassandra + Debezium + Kafka и реальных gRPC клиентов.

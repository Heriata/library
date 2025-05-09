## Library Service
Service for library subscription management. For "excel_as_json_data migration" use [Excel Data Upload Controller](excel),
for subscription information use [Subscription Controller](info).
### Java 17, Spring Boot, Spring Data, Apache Kafka, Postgresql, Mapstruct

Swagger:<p><code>http://localhost:8080/library/swagger-ui/index.html</code><p>
### <a name="excel"></a> <b>Excel Data Upload Controller</b><p>
- Send JSON file:<p> <code>http://localhost:8080/library/file</code><p>
- Send JSON string: <p><code>curl -X 'POST' \
  'http://localhost:8080/library/' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d
  '{
  data:[  
  {
  "username":"same",
  "userFullName":"Dukie Beament",
  "userActive":true,
  "bookName":"book1",
  "bookAuthor":"author"
  }
  ]
  }'</code><p>

### <a name="info"></a><b>Subscription Controller</b><p>
- Get subscription info by full user name:<p><code>http://localhost:8080/library/?fullName=Dukie%20Beament&page=0&size=10</code>

Check files in <code>mock_data</code><p>

To start Kafka and Postgresql containers use: <code>docker compose up -d</code><p>
To start the whole application as docker container use: <code>docker compose up -d --build</code><p>
### Description

Excel Data Upload Controller -> consumes JSON file, parses and sends to Kafka for high throughput.<p>
MessageProcessingService -> reads batches from Kafka, verifies and saves batches in database.
Subscription Controller -> provides information about subscription by user full name.

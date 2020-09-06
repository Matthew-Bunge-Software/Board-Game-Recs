# Board-Game-Recs

> I want to know what Boardgames are fun to play with 3 people

This project was created to answer the above question. It is a tool to discover new board games given some criteria.

## Getting Started

This is a Java Spring boot project. You can import it as a Gradle project in your favorite Java IDE or you can run the following commmands to build it yourself:

```
./gradlew clean
./gradlew build
```

By default, the project uses an embedded H2 database. However, if you have your own favorite db engine, you can edit 'application.properties' with the proper options. Hibernate will autogenerate all the necessary tables. As an example, here is what the options look like for the MySQL DB on my machine:

```
spring.datasource.url=jdbc:mysql://localhost:3306/BggPollDb?createDatabaseIfNotExist=true
spring.datasource.driver-class-name=com.mysql.jdbc.Driver
spring.datasource.username=testuser
spring.datasource.password=qwerty
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
```

## Contributing

All contributions are welcome and appreciated. For the most up to date information on contributing, please view our [Contributors' Guide](https://github.com/Matthew-Bunge-Software/Board-Game-Recs/blob/master/docs/CONTRIBUTING.md)

When interacting with other contributors, please keep in mind the [Code of Conduct](https://github.com/Matthew-Bunge-Software/Board-Game-Recs/blob/master/docs/CODE_OF_CONDUCT.md)

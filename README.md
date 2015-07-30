# cassandra-maven-example
An example of integrating Cassandra with Maven. This project is setup to run with Scala, but if pure Java is required one can simply remove the Scala dependencies, and translate the existing Scala code to Java (the code is very very simple)

This setup does the following:

    * Finds some free network ports to use with Cassandra. Useful if you run multiple builds concurrently in the same host, like you would in a build server
    * Configures the Surefire plugin to ignore test classes ending in 'IntegrationTest' or 'ITCase'
    * Configures the Failsafe plugin to execute integration tests, only picking up classes ending in 'IntegrationTest' or 'ITCase'
    * Passes the Cassandra ports to the integration tests as environment variables

A utility class `CassandraTestUtils` is provided that reads the environment variables to retrieve the setup Cassandra ports. See simple example test case

In development, you can start a Cassandra instance by invoking `mvn cassandra:run`. This will start Cassandra using standard ports. You can then run your tests from your favourite IDE using standard ports

You can customize the logging from the Cassandra instance by altering the cassandra_log4j.properties config file

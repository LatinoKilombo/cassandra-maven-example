package example

import com.datastax.driver.core.{Cluster, HostDistance, PoolingOptions, Session}
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import org.scalatest.{BeforeAndAfter, FunSuite}

@RunWith(classOf[JUnitRunner])
class CassandraConnectionIntegrationTest extends FunSuite with BeforeAndAfter {
  var cluster: Cluster = _
  implicit var session: Session = _

  val keyspace: String = getClass.getSimpleName + "_keyspace"
  val table: String = keyspace + ".testtable"

  before {
    cluster = Cluster.builder
      .addContactPoint("localhost")
      .withPort(CassandraTestUtils.nativePort)
      .withPoolingOptions(new PoolingOptions().setMaxConnectionsPerHost(HostDistance.LOCAL, 128))
      .build
    session = cluster.connect()

    CassandraTestUtils.deleteKeyspace(keyspace)
  }

  after {
    cluster.close()
    session.close()
  }

  test("Can create a keyspace, a table and insert data into it") {
    session.execute("CREATE KEYSPACE " + keyspace + " WITH replication = {'class': 'SimpleStrategy', 'replication_factor' : 1};")
    session.execute(s"CREATE TABLE $table (rowid timeuuid primary key, key text, value text)")
    session.execute(s"INSERT INTO $table (rowid, key, value) VALUES (now(), 'key1', 'value1')")
  }
}

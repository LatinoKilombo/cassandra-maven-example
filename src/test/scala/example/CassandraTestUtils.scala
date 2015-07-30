package example

import com.datastax.driver.core.Session
import com.datastax.driver.core.exceptions.InvalidQueryException

object CassandraTestUtils {

  def jmxPort = Option(java.lang.System.getenv("cassandra.jmxPort")).map(_.toInt).getOrElse(7199)
  def stopPort = Option(java.lang.System.getenv("cassandra.stopPort")).map(_.toInt).getOrElse(8081)
  def storagePort = Option(java.lang.System.getenv("cassandra.storagePort")).map(_.toInt).getOrElse(7000)
  def nativePort = Option(java.lang.System.getenv("cassandra.nativePort")).map(_.toInt).getOrElse(9042)
  def rpcPort = Option(java.lang.System.getenv("cassandra.rpcPort")).map(_.toInt).getOrElse(9016)

  def deleteKeyspace(keyspace: String)(implicit session: Session): Unit = {
    try {
      session.execute(s"DROP KEYSPACE $keyspace")
    } catch {
      case e: InvalidQueryException => //throws if keyspace didn't exist, we ignore it
    }
  }
}

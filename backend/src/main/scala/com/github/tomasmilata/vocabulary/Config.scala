package com.github.tomasmilata.vocabulary

import com.typesafe.config.ConfigFactory

class Config {
  private val root = ConfigFactory.load.getConfig("vocabulary")

  object mongo {
    object connection {
      val string: String = root.getString("mongo.connection.string")
    }
  }
}

object GlobalConfig {
  val config: Config = new Config
}

trait ConfigProvider {
  def config: Config
}


trait GlobalConfigModule extends ConfigProvider {
  override def config: Config = GlobalConfig.config
}
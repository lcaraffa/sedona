/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */


import LidarExampleExec._
import org.apache.log4j.{Level, Logger}
import org.apache.sedona.spark.SedonaContext
import org.apache.sedona.viz.core.Serde.SedonaVizKryoRegistrator
import org.apache.sedona.viz.sql.utils.SedonaVizRegistrator


object Main extends App {
  Logger.getRootLogger().setLevel(Level.WARN)

  val config = SedonaContext.builder().appName("SedonaSQL-demo")
    .master("local[*]") // Please comment out this when use it on a cluster
    .config("spark.sql.files.maxPartitionBytes","5342177")
    .config("spark.kryo.registrator", classOf[SedonaVizKryoRegistrator].getName)
    .getOrCreate()
  val sedona = SedonaContext.create(config)

  sedona.sparkContext.getConf.getAll.map(println(_))
  sedona.sparkContext.setLogLevel("WARN")

  SedonaVizRegistrator.registerAll(sedona)

  val resourceFolder = System.getProperty("user.dir")+"/src/test/resources/"
  vizLidar(sedona)

}

package db.dao

//object DbSettings {
////    val db by lazy {
////        val dbHost = System.getenv("DB_HOST") ?: "localhost"
////        val dbPort = System.getenv("DB_PORT") ?: "5432"
////        val dbName = System.getenv("DB_NAME") ?: "postgres"
////        val dbUser = System.getenv("DB_USER") ?: "postgres"
////        val dbPassword = System.getenv("DB_PASSWORD") ?: ""
////        val db = Database.connect(
////            "jdbc:postgresql://$dbHost:$dbPort/$dbName",
////            driver = "org.postgresql.Driver",
////            user = dbUser,
////            password = dbPassword
////        )
////        transaction {
////            addLogger(StdOutSqlLogger)
////            SchemaUtils.create(Profiles, Students, Instructors, Achievements, Jobs, Tags, ResearchWorks)
////        }
////        db
////    }
//    init {
//        val dbHost = System.getenv("DB_HOST") ?: "localhost"
//        val dbPort = System.getenv("DB_PORT") ?: "5432"
//        val dbName = System.getenv("DB_NAME") ?: "postgres"
//        val dbUser = System.getenv("DB_USER") ?: "postgres"
//        val dbPassword = System.getenv("DB_PASSWORD") ?: ""
//        Database.connect(
//            "jdbc:postgresql://$dbHost:$dbPort/$dbName",
//            driver = "org.postgresql.Driver",
//            user = dbUser,
//            password = dbPassword
//        )
//        transaction {
//            addLogger(StdOutSqlLogger)
//            SchemaUtils.create(Profiles, Students, Instructors, Achievements, Jobs, Tags, ResearchWorks)
//        }
//    }
//}
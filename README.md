[![Build Status](https://secure.travis-ci.org/vy/liquibase-jooq-demo.svg)](http://travis-ci.org/vy/liquibase-jooq-demo)
[![License](https://img.shields.io/github/license/vy/liquibase-jooq-demo.svg)](http://www.apache.org/licenses/LICENSE-2.0.txt)

This is a sample application demonstrating the usage of
[Liquibase](www.liquibase.org/) in combination with
[JOOQ](https://www.jooq.org/).

During Maven build,

1. Liquibase plugin applies the migrations to an [HSQLDB](http://hsqldb.org/)
   persisted on filesystem,
2. JOOQ examines the HSQLDB to populate Java models, and
3. JUnit tests employ the migrated HSQLDB and created JOOQ models.

Once you start the application,

1. it connects to a PostgreSQL database,
2. calls Liquibase to perform necessary migrations, and
3. enjoys the migrated PostgreSQL database with type-safe queries
   powered by JOOQ models.

# License

Copyright &copy; 2018 [Volkan Yazıcı](http://vlkan.com/)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

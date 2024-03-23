![img.png](img.png)

Required:
PostgreSQL

Create a new database and set it's properties into
application.properties


Set "spring.sql.init.mode=always" if you want to populate
database with data.

After filling your database with data set "spring.sql.init.mode=never"
in order to prevent exception.

Default port: 5565

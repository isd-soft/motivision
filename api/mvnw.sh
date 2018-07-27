psql -U postgres -d postgres -c "drop database if exists motivision"
psql -U postgres -d postgres -c "create database motivision"
psql -U postgres -d postgres -c "grant all on database motivision to postgres"
cd ..
psql --username=postgres motivision < motivision.sql
cd api
mvn package && java -jar target/motivision-1.jar
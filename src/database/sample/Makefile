DBUSER=postgres

sample: prepare
	psql -f sample_data.sql -U $(DBUSER)
	psql -d cudu -c "COPY asociado FROM '$(CURDIR)/data.csv' WITH DELIMITER ';' CSV;" -U $(DBUSER)
	psql -f fix_import_postgres.sql -U $(DBUSER)
	rm data.csv

prepare:
	iconv -f MACROMAN -t UTF-8 sample_data.csv > tmp1
	tr '\r' '\n' < tmp1 | sed -e "s/$$/;FALSE;FALSE;FALSE;FALSE;FALSE;0/g" > tmp2
	tail +2 tmp2 > data.csv
	rm tmp1 tmp2

postgres:
	psql -f ../cudu_schema_postgresql.sql -U $(DBUSER)

clean:
	-psql -c "drop database cudu;" -U $(DBUSER)

all: clean postgres prepare sample


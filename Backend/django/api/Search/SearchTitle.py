import mariadb
import sys
from django.db import connection


def dictfetchall(cursor):
    "Return all rows from a cursor as a dict"
    columns = [col[0] for col in cursor.description]
    return [
        dict(zip(columns, row))
        for row in cursor.fetchall()
    ]


def search_title(keyword):
    try:
        cur = connection.cursor()
        use_db_query = "use oosooDB"

        select_keyword_query = f"SELECT * FROM contents WHERE MATCH(title) AGAINST('{keyword}*' IN BOOLEAN MODE);"
        print(select_keyword_query)

        cur.execute(use_db_query)
        cur.execute(select_keyword_query)

        search_contents = dictfetchall(cur)

    except mariadb.Error as e:
        print(f"failed: Error connecting to Mariadb: {e}")
        sys.exit(1)

    connection.close()

    return search_contents


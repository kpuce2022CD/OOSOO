import mariadb
import sys
from django.db import connection

def searchTitle(keyword):
    try:
        cur = connection.cursor()
        use_db_query = "use oosooDB"

        select_keyword_query = f"SELECT title FROM contents WHERE MATCH(title) AGAINST('{keyword}*' IN BOOLEAN MODE);"
        print(select_keyword_query)

        cur.execute(use_db_query)
        cur.execute(select_keyword_query)
        result_set = cur.fetchall()

        title_tuple = tuple()
        for title in result_set:
            title_tuple += title

        title_list = list(title_tuple)

    except mariadb.Error as e:
        print(f"failed: Error connecting to Mariadb: {e}")
        sys.exit(1)
        return title_list

    connection.close()

    return title_list


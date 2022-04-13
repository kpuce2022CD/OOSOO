import sys
import mariadb
from django.db import connection

def AllReview(c_id):
    try:
        cur = connection.cursor()
        use_db_query = "use oosooDB"
        select_query = f"SELECT * FROM contents_review WHERE c_id = '{c_id}' ORDER BY _like DESC"

        cur.execute(use_db_query)
        cur.execute(select_query)
        result = cur.fetchall()

    except mariadb.Error as e:
        print(f"failed: Error connecting to Mariadb: {e}")
        sys.exit(1)
        result = []

    connection.close()

    return result
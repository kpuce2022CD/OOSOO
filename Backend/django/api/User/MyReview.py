import sys
import mariadb
from django.db import connection

def dictfetchall(cursor):
    "Return all rows from a cursor as a dict"
    columns = [col[0] for col in cursor.description]
    return [
        dict(zip(columns, row))
        for row in cursor.fetchall()
    ]

def MyReview(u_email):
    try:
        cur = connection.cursor()
        use_db_query = "use oosooDB"
        select_query = f"SELECT * FROM contents_review WHERE u_email = '{u_email}' ORDER BY _datetime DESC"

        cur.execute(use_db_query)
        cur.execute(select_query)
        my_reviews = dictfetchall(cur)

    except mariadb.Error as e:
        print(f"failed: Error connecting to Mariadb: {e}")
        sys.exit(1)
        my_reviews = []

    connection.close()

    return my_reviews
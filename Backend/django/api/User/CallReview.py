import sys
import mariadb
from django.db import connection

def CallReview(c_id, u_email):
    try:
        cur = connection.cursor()
        use_db_query = "use oosooDB"
        select_query = f"SELECT _like, rating, review, _datetime FROM contents_review WHERE c_id = '{c_id}' and u_email = '{u_email}'"

        cur.execute(use_db_query)
        cur.execute(select_query)
        result = cur.fetchall()
        result_list = list()
        for _like, rating, review, _datetime in result:
            result_list.append(_like)
            result_list.append(rating)
            result_list.append(review)
            result_list.append(_datetime)

    except mariadb.Error as e:
        print(f"failed: Error connecting to Mariadb: {e}")
        sys.exit(1)
        result_list = []

    connection.close()

    return result_list
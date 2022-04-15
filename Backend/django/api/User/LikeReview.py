import sys
import mariadb
from django.db import connection

def CallReviewLike(id, _like, islike):
    try:
        cur = connection.cursor()
        use_db_query = "use oosooDB"
        like = int(_like)
        if islike == '1':
            like = like + 1
        else:
            like = like - 1

        update_query = f"UPDATE contents_review set _like = '{like}' WHERE id = '{id}'"

        cur.execute(use_db_query)
        cur.execute(update_query)

        result = True

    except mariadb.Error as e:
        print(f"failed: Error connecting to Mariadb: {e}")
        sys.exit(1)
        result = False

    connection.close()

    return result